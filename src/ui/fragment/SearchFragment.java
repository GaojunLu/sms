package ui.fragment;

import ui.activity.ConversationDetailActivity;
import global.Constans;

import com.example.sms.R;

import dao.MyQueryHandler;
import adapter.ConversationAdapter;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import base.BaseFragment;

public class SearchFragment extends BaseFragment {

	private ListView listview_search_fragment;
	private EditText et_search_fragment;
	private ConversationAdapter adapter;
	private MyQueryHandler handler;
	String[] projection = { "sms.body AS snippet", "sms.thread_id AS _id",
			"groups.msg_count AS msg_count", "address as address",
			"date AS date" };

	@Override
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_search, null);
		et_search_fragment = (EditText) view.findViewById(R.id.et_search_fragment);
		listview_search_fragment = (ListView) view.findViewById(R.id.listview_search_fragment);
		return view;
	}

	@Override
	public void setData() {
		adapter = new ConversationAdapter(getActivity(), null);
		listview_search_fragment.setAdapter(adapter);
		handler = new MyQueryHandler(getActivity().getContentResolver());
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		listview_search_fragment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), ConversationDetailActivity.class);
				Cursor cursor = (Cursor) adapter.getItem(position);
				intent.putExtra("thread_id", cursor.getString(cursor.getColumnIndex("_id")));
				intent.putExtra("address", cursor.getString(cursor.getColumnIndex("address")));
				startActivity(intent);
			}
		});
		et_search_fragment.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				handler.startQuery(0, adapter, Constans.SMS_CONVERSATION, projection, "body like '%"+s+"%'", null, "date desc");
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		listview_search_fragment.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(listview_search_fragment.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void processClick(View v) {
		// TODO Auto-generated method stub

	}

}
