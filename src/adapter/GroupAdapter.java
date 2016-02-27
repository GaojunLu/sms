package adapter;

import bean.Group;

import com.example.sms.R;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class GroupAdapter extends CursorAdapter {

	private ViewHolder viewHolder;

	public GroupAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		return View.inflate(context, R.layout.item_group_list, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		Group group = Group.getGroupByCursor(cursor);
		viewHolder = getViewHolder(view);
		viewHolder.tv_name_group_item.setText(group.getName()+"("+group.getThread_count()+")");
		if(!DateUtils.isToday(group.getCreate_time())){
			viewHolder.tv_date_group_item.setText(DateFormat.getDateFormat(context).format(group.getCreate_time()));
		}else{
			viewHolder.tv_date_group_item.setText(DateFormat.getTimeFormat(context).format(group.getCreate_time()));
		}
	}

	ViewHolder getViewHolder(View view){
		viewHolder = (ViewHolder) view.getTag();
		if(viewHolder==null){
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		}
		return viewHolder;
	}
	
	class ViewHolder{
		private TextView tv_name_group_item;
		private TextView tv_date_group_item;

		public ViewHolder(View view) {
			tv_name_group_item = (TextView) view.findViewById(R.id.tv_name_group_item);
			tv_date_group_item = (TextView) view.findViewById(R.id.tv_date_group_item);
		}
	}
	
}
