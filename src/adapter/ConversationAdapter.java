package adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ui.dialog.DeleteDialog;
import ui.dialog.DeleteDialog.DeleteListener;
import ui.fragment.ConversationFragment;

import com.example.sms.R;

import dao.ContactDao;
import dao.SmsDao;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConversationAdapter extends CursorAdapter {

	public boolean isStop() {
		return stop;
	}

	public List<Integer> getPositions() {
		return positions;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	private List<Integer> positions = new ArrayList<Integer>();// 被选中的条目
	private boolean isSelectMode = false;
	private boolean stop;

	public ConversationAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		return View.inflate(context, R.layout.item_conversation_list, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		ViewHolder holder = getViewHolder(view);
		holder.tv_item_body.setText(cursor.getString(cursor
				.getColumnIndex("snippet")));
		String address = cursor.getString(cursor.getColumnIndex("address"));
		String name = ContactDao.getNameByAddress(address, context);
		String count = cursor.getString(cursor.getColumnIndex("msg_count"));
		String date = getDate(cursor.getString(cursor.getColumnIndex("date")), context);
		// 名字
		holder.tv_item_name.setText(name==null?address + "(" + count + ")":name + "(" + count + ")");
		holder.tv_item_date.setText(date);
		// 头像
		String id = ContactDao.getContactIdByAdress(address, context);
		Bitmap bm = getBitmapByContactId(id, context);
		if (bm != null) {
			holder.iv_avetar.setImageBitmap(bm);
		} else {
			holder.iv_avetar.setImageResource(R.drawable.img_default_avatar);
		}
		// 选中状态：
		if (isSelectMode) {
			holder.iv_check.setVisibility(View.VISIBLE);
			if (positions.contains((Integer) cursor.getPosition())) {
				holder.iv_check
						.setBackgroundResource(R.drawable.common_checkbox_checked);
			} else {
				holder.iv_check
						.setBackgroundResource(R.drawable.common_checkbox_normal);
			}
		} else {// 普通状态
			holder.iv_check.setVisibility(View.GONE);
		}		
	}

	private Bitmap getBitmapByContactId(String id, Context context) {
		ContentResolver resolver = context.getContentResolver();
		try {
			// InputStream is = resolver.openAssetFileDescriptor(uri,
			// "r").createInputStream();
			// InputStream is = resolver.openInputStream(uri);
			if(id!=null){
				Uri contactUri = Uri.withAppendedPath(Contacts.CONTENT_URI, id);
				InputStream is = Contacts.openContactPhotoInputStream(resolver,
						contactUri);
				return BitmapFactory.decodeStream(is);
				}
			else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return null;
		}
	}

	private String getDate(String string, Context context) {
		// TODO Auto-generated method stub
		long time = Long.parseLong(string);
		Date date = new Date(time);
		if(DateUtils.isToday(time)){
			return DateFormat.getTimeFormat(context).format(date);
		}else{
			 return DateFormat.getDateFormat(context).format(date);
		}
	}

	public ViewHolder getViewHolder(View v) {
		ViewHolder viewHolder = (ViewHolder) v.getTag();
		if (viewHolder == null) {
			viewHolder = new ViewHolder(v);
			v.setTag(viewHolder);
			return viewHolder;
		}
		return viewHolder;
	}

	class ViewHolder {
		ImageView iv_avetar;
		TextView tv_item_name;
		TextView tv_item_body;
		TextView tv_item_date;
		ImageView iv_check;

		public ViewHolder(View v) {
			iv_avetar = (ImageView) v.findViewById(R.id.iv_avetar);
			tv_item_name = (TextView) v.findViewById(R.id.tv_item_name);
			tv_item_body = (TextView) v.findViewById(R.id.tv_item_body);
			tv_item_date = (TextView) v.findViewById(R.id.tv_item_date);
			iv_check = (ImageView) v.findViewById(R.id.iv_check);
		}
	}

	public boolean isSelectMode() {
		return isSelectMode;
	}

	public void setSelectMode(boolean isSelectMode) {
		this.isSelectMode = isSelectMode;
		notifyDataSetChanged();
	}

	public void selectSinglePosition(int position) {
		// TODO Auto-generated method stub
		if (positions.contains(position)) {
			positions.remove((Integer) position);
		} else {
			positions.add(position);
		}
		notifyDataSetChanged();
	}

	public void selectAllPosition() {
		// TODO Auto-generated method stub
		positions.clear();
		int count = getCursor().getCount();
		for (int i = 0; i < count; i++) {
			positions.add(i);
		}
		notifyDataSetChanged();
	}

	public void cancelAllPosition() {
		// TODO Auto-generated method stub
		positions.clear();
		notifyDataSetChanged();
	}



	
}
