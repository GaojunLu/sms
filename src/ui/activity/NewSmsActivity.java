package ui.activity;

import utils.CursorUtils;
import global.Constans;

import com.example.sms.R;

import dao.SmsDao;
import adapter.AddressAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import base.BaseActivity;

public class NewSmsActivity extends BaseActivity {

	private AutoCompleteTextView et_newsms_address;
	private ImageView iv_newsms_select_contact;
	private EditText et_newsms_body;
	private Button bt_newsms_send;
	private AddressAdapter adapter;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_new_sms);
		et_newsms_address = (AutoCompleteTextView) findViewById(R.id.et_newsms_address);
		iv_newsms_select_contact = (ImageView) findViewById(R.id.iv_newsms_select_contact);
		et_newsms_body = (EditText) findViewById(R.id.et_newsms_body);
		bt_newsms_send = (Button) findViewById(R.id.bt_newsms_send);
		initTitalBar();
	}

	private void initTitalBar() {
		// TODO Auto-generated method stub
		findViewById(R.id.iv_titlebar_back_bt).setOnClickListener(this);
		((TextView)findViewById(R.id.tv_titlebar_title)).setText("发送短信");
	}

	@Override
	public void setData() {
		adapter = new AddressAdapter(this, null);
		et_newsms_address.setAdapter(adapter);
		//思考此处的机制
		adapter.setFilterQueryProvider(new FilterQueryProvider() {
			
			@Override
			public Cursor runQuery(CharSequence constraint) {
				// TODO Auto-generated method stub
				String[] projects = {
					"_id",
					"display_name",
					"data1"
				};
				String selection = "data1 like '%"+constraint+"%'";
				Cursor cursor = getContentResolver().query(Phone.CONTENT_URI, projects, selection, null, null);
//				CursorUtils.printCursor(cursor);
				return cursor;
			}
		});
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		iv_newsms_select_contact.setOnClickListener(this);
		bt_newsms_send.setOnClickListener(this);
	}

	@Override
	public void processClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_titlebar_back_bt:
			finish();
			break;
		case R.id.iv_newsms_select_contact:
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("vnd.android.cursor.dir/contact");
			startActivityForResult(intent, 0);
			break;
		case R.id.bt_newsms_send:
			String address = et_newsms_address.getText().toString();
			String body = et_newsms_body.getText().toString();
			if(!TextUtils.isEmpty(address)&&!TextUtils.isEmpty(body)){
				SmsDao.sendSMS(address, body, this);
			}else{
				Toast.makeText(this, "请输入内容", 0).show();
			}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Uri uri = data.getData();
		String[] projection ={
			"_id",
			"has_phone_number"
		};
		Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
		cursor.moveToFirst();
		String id = cursor.getString(0);
		int has_phone_number = cursor.getInt(1);
		if(has_phone_number==1){
			Cursor cursor2 = getContentResolver().query(Phone.CONTENT_URI, new String[]{"data1"}, "contact_id="+id, null, null);
			cursor2.moveToFirst();
			String address = cursor2.getString(0);
			et_newsms_address.setText(address);
			et_newsms_body.requestFocus();
		}else{
			Toast.makeText(this, "该联系人没有号码", 0).show();
			et_newsms_address.setText("");
		}
	}
}
