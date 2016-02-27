package adapter;

import java.util.Date;

import global.Constans;
import bean.Sms;

import com.example.sms.R;

import adapter.ConversationAdapter.ViewHolder;
import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ConversationDetailAdapter extends CursorAdapter{

	private ViewHolder viewHolder;
	private Sms sms;
	private ListView listView;
	private int duration = 3*60*1000;

	public ConversationDetailAdapter(Context context, Cursor c, ListView listView) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.listView = listView;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		return View.inflate(context, R.layout.item_conversation_detail_list, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		sms = Sms.getSmsByCursor(cursor);
		ViewHolder viewHolder = getViewHolder(view);
		if(getSmsDuration(cursor)>=duration){
			showDate(viewHolder, context);
		}else {
			viewHolder.tv_conversation_detail_date.setVisibility(View.GONE);
		}
		if(sms.getType()==Constans.SMS_RECEIVE){
			viewHolder.tv_conversation_detail_send.setVisibility(View.GONE);
			viewHolder.tv_conversation_detail_recieve.setVisibility(View.VISIBLE);
			viewHolder.tv_conversation_detail_recieve.setText(sms.getBody());
		}else{
			viewHolder.tv_conversation_detail_send.setVisibility(View.VISIBLE);
			viewHolder.tv_conversation_detail_recieve.setVisibility(View.GONE);
			viewHolder.tv_conversation_detail_send.setText(sms.getBody());
		}
	}

	private long getSmsDuration(Cursor cursor) {
		// TODO Auto-generated method stub
		if(cursor.getPosition()>0){
			Cursor cursor2 = (Cursor) getItem(cursor.getPosition()-1);
			long l =  sms.getDate()-Sms.getSmsByCursor(cursor2).getDate();
			return l;
		}
		return duration;
	}

	private void showDate(ViewHolder viewHolder, Context context) {
		Date date = new Date(sms.getDate());
		viewHolder.tv_conversation_detail_date.setVisibility(View.VISIBLE);
		if(DateUtils.isToday(sms.getDate())){
			viewHolder.tv_conversation_detail_date.setText(DateFormat.getTimeFormat(context).format(date));
		}else{
			viewHolder.tv_conversation_detail_date.setText(DateFormat.getDateFormat(context).format(date));
		}
	}
	public ViewHolder getViewHolder(View v) {
		viewHolder = (ViewHolder) v.getTag();
		if (viewHolder == null) {
			viewHolder = new ViewHolder(v);
			v.setTag(viewHolder);
		}
		return viewHolder;
	}

	class ViewHolder {

		private TextView tv_conversation_detail_date;
		private TextView tv_conversation_detail_recieve;
		private TextView tv_conversation_detail_send;

		public ViewHolder(View v) {
			tv_conversation_detail_date = (TextView) v.findViewById(R.id.tv_conversation_detail_date);
			tv_conversation_detail_recieve = (TextView) v.findViewById(R.id.tv_conversation_detail_recieve);
			tv_conversation_detail_send = (TextView) v.findViewById(R.id.tv_conversation_detail_send);
		}
	}

	@Override
	public void changeCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		super.changeCursor(cursor);
		listView.setSelection(cursor.getCount());
	}
}
