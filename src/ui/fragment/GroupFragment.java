package ui.fragment;

import global.Constans;
import ui.activity.GroupDetailActivity;
import ui.dialog.EditTextDialog;
import ui.dialog.EditTextDialog.EditTextDialogListener;
import ui.dialog.ListDialog;
import ui.dialog.ListDialog.ListDialogListener;

import com.example.sms.R;

import dao.GroupDao;
import dao.MyOpenHelper;
import dao.MyQueryHandler;
import adapter.GroupAdapter;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import base.BaseFragment;
import bean.Group;

public class GroupFragment extends BaseFragment {

	private Button bt_group_newgroup;
	private ListView listview_group;
	private GroupAdapter adapter;

	@Override
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_group, null);
		bt_group_newgroup = (Button) view.findViewById(R.id.bt_group_newgroup);
		listview_group = (ListView) view.findViewById(R.id.listview_group);
		return view; 
	}

	@Override
	public void setData() {
		adapter = new GroupAdapter(getActivity(), null);
		listview_group.setAdapter(adapter);
		MyQueryHandler myQueryHandler = new MyQueryHandler(getActivity().getContentResolver());
		myQueryHandler.startQuery(0, adapter, Constans.MY_PROVIDER_URI_GROUPS_QUERY, null, null, null, "create_time desc");
	}

	@Override
	public void setListener() { 
		// TODO Auto-generated method stub
		bt_group_newgroup.setOnClickListener(this);
		listview_group.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor cursor = (Cursor) listview_group.getItemAtPosition(position);
				final Group group = Group.getGroupByCursor(cursor);
				ListDialog.showListDialog(getActivity(), "选择操作", new String[]{"重命名", "删除"}, new ListDialogListener() {

					@Override
					public void onClick(int pos) {
						// TODO Auto-generated method stub
						switch (pos) {
						case 0://重命名
							EditTextDialog.showEditTextDialog(getActivity(), "重命名群组名称", new EditTextDialogListener() {
								
								@Override
								public void onConfirm(String name) {
									GroupDao.updateGroupNameById(getActivity().getContentResolver(), name, group.get_id());
								}
								
								@Override
								public void onCancel() {
									// TODO Auto-generated method stub
									
								}
							});
							break;
						case 1://删除
							GroupDao.deleteGroupById(getActivity().getContentResolver(), group.get_id());
							break;
						}
					}
					
				});
				return false;
			}
		});
		listview_group.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Cursor cursor = (Cursor) adapter.getItem(position);
				Group group = Group.getGroupByCursor(cursor);
				if(group.getThread_count()>0){
					Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
					intent.putExtra("name", group.getName());
					intent.putExtra("group_id", group.get_id());
					startActivity(intent);
				}else {
					Toast.makeText(getActivity(), "该群组中还没有会话", 0).show();
				}
			}
		});
	}

	@Override
	public void processClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_group_newgroup:
			EditTextDialog.showEditTextDialog(getActivity(), "群组名称", new EditTextDialogListener() {
				
				@Override
				public void onConfirm(String name) {
					// TODO Auto-generated method stub
					//点击后通过内容提供者插入数据库,需要输入框里的内容
					GroupDao.insertGroup(getActivity().getContentResolver(), name);
				}
				
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					
				}
			});
			break;
		}
	}

}
