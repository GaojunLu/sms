package ui.activity;

import global.Constans;

import com.example.sms.R;

import dao.ContactDao;
import dao.MyQueryHandler;
import dao.SmsDao;
import adapter.ConversationDetailAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import base.BaseActivity;

public class ConversationDetailActivity extends BaseActivity {

	private String thread_id;
	private String address;
	private TextView tv_titlebar_title;
	private ImageView iv_titlebar_back_bt;
	private MyQueryHandler myQueryHandler;
	private ListView listview_conversation_detail;
	private ConversationDetailAdapter adapter;
	private Button bt_conversation_detail_send;
	private EditText et_conversation_detail;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_conversation_detail);
		tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
		iv_titlebar_back_bt = (ImageView) findViewById(R.id.iv_titlebar_back_bt);
		listview_conversation_detail = (ListView) findViewById(R.id.listview_conversation_detail);
		bt_conversation_detail_send = (Button) findViewById(R.id.bt_conversation_detail_send);
		et_conversation_detail = (EditText) findViewById(R.id.et_conversation_detail);
		initTitleBar();
		listview_conversation_detail.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
	}

	private void initTitleBar() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		thread_id = intent.getStringExtra("thread_id");
		address = intent.getStringExtra("address");
		String name = ContactDao.getNameByAddress(address, this);
		tv_titlebar_title.setText(name==null?address:name);
	}

	@Override
	public void setData() {
		adapter = new ConversationDetailAdapter(this, null, listview_conversation_detail);
		String[] projection = {
			"body",	
			"date",	
			"type",	
			"_id",	
		};
		String selection = "thread_id="+thread_id;
		myQueryHandler = new MyQueryHandler(getContentResolver());
		myQueryHandler.startQuery(0, adapter, Constans.SMS_URI, projection, selection, null, "date");
		listview_conversation_detail.setAdapter(adapter);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		iv_titlebar_back_bt.setOnClickListener(this);
		bt_conversation_detail_send.setOnClickListener(this);
	}

	@Override
	public void processClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_titlebar_back_bt:
			finish();
			break;
		case R.id.bt_conversation_detail_send:
			String msg = et_conversation_detail.getText().toString();
			if(msg!=null){
				SmsDao.sendSMS(address, msg, this);
			}
			et_conversation_detail.setText("");
			break;
		}
	}

}
