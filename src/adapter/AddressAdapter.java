package adapter;

import com.example.sms.R;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AddressAdapter extends CursorAdapter {

	private ViewHolder viewHolder;

	public AddressAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		return View.inflate(context, R.layout.item_search_address, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		String name = cursor.getString(cursor.getColumnIndex("display_name"));
		String address = cursor.getString(cursor.getColumnIndex("data1"));
		viewHolder = getViewHolder(view);
		viewHolder.tv_search_address_name.setText(name);
		viewHolder.tv_search_address_number.setText(address);
	}
	
	public ViewHolder getViewHolder(View view){
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		if(viewHolder==null){
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
			return viewHolder;
		}else{
			return viewHolder;
		}
	}
	
	class ViewHolder{
		private TextView tv_search_address_name;
		private TextView tv_search_address_number;

		public ViewHolder(View view) {
			tv_search_address_name = (TextView) view.findViewById(R.id.tv_search_address_name);
			tv_search_address_number = (TextView) view.findViewById(R.id.tv_search_address_number);
		}
	}

	@Override
	public CharSequence convertToString(Cursor cursor) {
		// TODO Auto-generated method stub
		return cursor.getString(cursor.getColumnIndex("data1"));
	}
}
