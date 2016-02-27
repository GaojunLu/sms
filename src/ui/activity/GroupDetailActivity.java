package ui.activity;

import global.Constans;

import com.example.sms.R;

import dao.MyQueryHandler;
import adapter.ConversationAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import base.BaseActivity;

public class GroupDetailActivity extends BaseActivity {

	private TextView tv_titlebar_title;
	private ListView listview_conversation_detail;
	private ConversationAdapter adapter;
	private int group_id;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_group_detail);
		listview_conversation_detail = (ListView) findViewById(R.id.listview_group_detail);
	}

	@Override
	public void setData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		group_id = intent.getIntExtra("group_id", -1);
		initTitleBar();
		tv_titlebar_title.setText(name);
		adapter = new ConversationAdapter(this, null);
		listview_conversation_detail.setAdapter(adapter);
		MyQueryHandler handler = new MyQueryHandler(getContentResolver());
		String[] projection = { "sms.body AS snippet", "sms.thread_id AS _id",
				"groups.msg_count AS msg_count", "address as address",
				"date AS date" };
		handler.startQuery(0, adapter, Constans.SMS_CONVERSATION, projection, getSelection(), null, "date desc");
	}


	private String getSelection() {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("thread_id in (");
		Cursor cursor = getContentResolver().query(Constans.MY_PROVIDER_URI_THREAD_GROUP_QUERY, new String[]{"thread_id"}, "group_id = "+group_id, null, null);
		while(cursor.moveToNext()){
			if(cursor.isLast()){
				builder.append(cursor.getString(0));
			}else {
				builder.append(cursor.getString(0)).append(",");
			}
		}
		builder.append(")");
		return builder.toString();
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		listview_conversation_detail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GroupDetailActivity.this, ConversationDetailActivity.class);
				Cursor cursor = (Cursor) adapter.getItem(position);
				intent.putExtra("thread_id", cursor.getString(cursor.getColumnIndex("_id")));
				intent.putExtra("address", cursor.getString(cursor.getColumnIndex("address")));
				startActivity(intent);
			}
		});
	}

	@Override
	public void processClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_titlebar_back_bt:
			finish();
			break;
		}
	}

	private void initTitleBar() {
		// TODO Auto-generated method stub
		findViewById(R.id.iv_titlebar_back_bt).setOnClickListener(this);
		tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
	}
}
