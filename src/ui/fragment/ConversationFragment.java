package ui.fragment;

import java.util.ArrayList;
import java.util.List;

import ui.activity.ConversationDetailActivity;
import ui.activity.NewSmsActivity;
import ui.dialog.ConfirmDialog;
import ui.dialog.DeleteDialog;
import ui.dialog.ConfirmDialog.onClickListener;
import ui.dialog.DeleteDialog.DeleteListener;
import ui.dialog.ListDialog;
import ui.dialog.ListDialog.ListDialogListener;
import global.Constans;

import com.example.sms.R;

import dao.ContactDao;
import dao.GroupDao;
import dao.MyQueryHandler;
import dao.SmsDao;
import dao.ThreadGroupDao;
import adapter.ConversationAdapter;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import base.BaseFragment;
import bean.Group;

public class ConversationFragment extends BaseFragment {
	public static final int WHAT_DELETE_COMPLET = 0;
	public static final int WHAT_DELETE_CANCEL = 1;
	public static final int WHAT_DELETE_REFRESH = 3;

	private ListView listView;
	ConversationAdapter adapter;
	private LinearLayout linear_edit;
	private LinearLayout linear_delete;
	private Button bt_conversation_edit;
	private Button bt_conversation_newmsg;
	private Button bt_conversation_selectall;
	private Button bt_conversation_delete;
	private Button bt_conversation_cancelselect;
	private DeleteDialog deleteDialog;

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case WHAT_DELETE_COMPLET:
				adapter.setSelectMode(false);
				showEditMenue();
				adapter.notifyDataSetChanged();
				deleteDialog.dismiss();
				break;
			case WHAT_DELETE_REFRESH:
				deleteDialog.setProgressAndTitle(msg.arg1);
				break;
			}
		}

	};
	private List<Integer> positions;

	@Override
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_conversation, null);
		listView = (ListView) view.findViewById(R.id.listview_conversation);
		linear_edit = (LinearLayout) view.findViewById(R.id.linear_edit);
		linear_delete = (LinearLayout) view.findViewById(R.id.linear_delete);
		bt_conversation_edit = (Button) view
				.findViewById(R.id.bt_conversation_edit);
		bt_conversation_newmsg = (Button) view
				.findViewById(R.id.bt_conversation_newmsg);
		bt_conversation_selectall = (Button) view
				.findViewById(R.id.bt_conversation_selectall);
		bt_conversation_delete = (Button) view
				.findViewById(R.id.bt_conversation_delete);
		bt_conversation_cancelselect = (Button) view
				.findViewById(R.id.bt_conversation_cancelselect);
		return view;
	}

	@Override
	public void setData() {
		// TODO Auto-generated method stub
		adapter = new ConversationAdapter(getActivity(), null);
		queryConversation();
		listView.setAdapter(adapter);
	}

	/**
	 * 查询数据库得到：最后日期，短信条数，预览，会话id
	 */
	private void queryConversation() {
		// TODO Auto-generated method stub
		String[] projection = { "sms.body AS snippet", "sms.thread_id AS _id",
				"groups.msg_count AS msg_count", "address as address",
				"date AS date" };
		MyQueryHandler myQueryHandler = new MyQueryHandler(getActivity()
				.getContentResolver());
		myQueryHandler.startQuery(0, adapter, Constans.SMS_CONVERSATION,
				projection, null, null, "date desc");
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		bt_conversation_edit.setOnClickListener(this);
		bt_conversation_cancelselect.setOnClickListener(this);
		bt_conversation_selectall.setOnClickListener(this);
		bt_conversation_delete.setOnClickListener(this);
		bt_conversation_newmsg.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(adapter.isSelectMode()){
					adapter.selectSinglePosition(position);
				}else{
					Intent intent = new Intent(getActivity(), ConversationDetailActivity.class);
					Cursor cursor = (Cursor) adapter.getItem(position);
					intent.putExtra("thread_id", cursor.getString(cursor.getColumnIndex("_id")));
					intent.putExtra("address", cursor.getString(cursor.getColumnIndex("address")));
					startActivity(intent);
				}
			}
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor cursor = (Cursor) adapter.getItem(position);
				final int thread_id = cursor.getInt(cursor.getColumnIndex("_id"));
				// 查询会话所属群组，如果已有群组，就弹出确定取消对话框询问是否退群
				if(ThreadGroupDao.hasGroup(getActivity().getContentResolver(),thread_id)){
					String groupname = ThreadGroupDao.getGroupNameByThreadId(getActivity().getContentResolver(),thread_id);
					String msg = "该联系人已在["+groupname+"]群组，是否退出？";
					ConfirmDialog.showConfirmDialog(getActivity(), "提示", msg, new onClickListener() {
						
						@Override
						public void onConfirm() {
							// TODO Auto-generated method stub
							//###############退出群组逻辑#################
							ThreadGroupDao.deleteThreadGroupById(getActivity().getContentResolver(),thread_id);
							Toast.makeText(getActivity(), "删除成功", 0).show();
						}
						
						@Override
						public void onCancel() {
							// TODO Auto-generated method stub
							
						}
					});
				}else{
				//否则弹出list对话框，选择群组后加入
					showListDialog(thread_id);
				}
				return true;
			}
		});
	}

	protected void showListDialog(final int thread_id) {
		final Cursor cursor = GroupDao.getAllGroups(getActivity().getContentResolver());
		//如果查询群组数为0，toast提示先创建群组
		if(!cursor.moveToFirst()){
			Toast.makeText(getActivity(), "还没有创建群组，请先创建后再添加", 0).show();
			return;
		}
		String[] items = new String[cursor.getCount()];
		do{
			Group group = Group.getGroupByCursor(cursor);
			items[cursor.getPosition()] = group.getName();
		}while(cursor.moveToNext());
		ListDialog.showListDialog(getActivity(), "选择加入的群组", items, new ListDialogListener() {
			
			@Override
			public void onClick(int pos) {
				// TODO Auto-generated method stub
				cursor.moveToPosition(pos);
				Group group = Group.getGroupByCursor(cursor);
				ThreadGroupDao.addToGroup(getActivity().getContentResolver(), thread_id, group.get_id());
				Toast.makeText(getActivity(), "添加成功", 0).show();
			}
		});
	}

	@Override
	public void processClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_conversation_edit:
			showDeleteMenue();
			adapter.setSelectMode(true);
			break;
		case R.id.bt_conversation_cancelselect:
			showEditMenue();
			adapter.setSelectMode(false);
			adapter.cancelAllPosition();
			break;
		case R.id.bt_conversation_selectall:
			adapter.selectAllPosition();
			break;
		case R.id.bt_conversation_delete:
			positions = adapter.getPositions();
			if (positions.size() != 0)
				showConfirmDialog();
			break;
		case R.id.bt_conversation_newmsg:
			Intent intent = new Intent(getActivity(), NewSmsActivity.class);
			startActivity(intent);
			break;
		}
	}

	private void showConfirmDialog() {
		// TODO Auto-generated method stub
		ConfirmDialog dialog = new ConfirmDialog(getActivity(), "提示",
				"真的要删除吗？", new onClickListener() {

					@Override
					public void onConfirm() {
						// TODO Auto-generated method stub
						deleteConversation(getActivity());
					}

					@Override
					public void onCancel() {
						// TODO Auto-generated method stub

					}
				});
		dialog.show();
	}

	public void showDeleteMenue() {
		linear_edit.animate().translationY(linear_edit.getHeight())
				.setDuration(100);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				linear_delete.animate().translationY(0).setDuration(100);
			}
		}, 100);
	}

	public void showEditMenue() {
		linear_delete.animate().translationY(linear_edit.getHeight())
				.setDuration(100);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				linear_edit.animate().translationY(0).setDuration(100);
			}
		}, 100);
	}

	private void showDeleteDialog() {
		// TODO Auto-generated method stub
		deleteDialog = new DeleteDialog(getActivity(), positions.size(), new DeleteListener() {

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				adapter.setStop(true);
			}
		});
		deleteDialog.show();
	}

	public void deleteConversation(final FragmentActivity context) {
		showDeleteDialog();
		Cursor cursor = adapter.getCursor();
		adapter.setStop(false);
		final List<String> ids = new ArrayList<String>();
		for (int pos : positions) {
			cursor.moveToPosition(pos);
			ids.add(cursor.getString(cursor.getColumnIndex("_id")));
		}
		new Thread() {
			public void run() {
				ContentResolver resolver = context.getContentResolver();
				for (int i = 0; i < positions.size(); i++) {
					if (adapter.isStop()) {
						adapter.setStop(false);
						break;
					}
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String thread_id = ids.get(i);
					SmsDao.deleteConversationByID(thread_id, resolver);
					// 刷新对话框
					Message msg = handler.obtainMessage();
					msg.what = ConversationFragment.WHAT_DELETE_REFRESH;
					msg.arg1 = i + 1;
					handler.sendMessage(msg);
				}
				handler.sendEmptyMessage(ConversationFragment.WHAT_DELETE_COMPLET);
			}
		}.start();
	}
}
