package dao;

import utils.CursorUtils;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter; 

public class MyQueryHandler extends AsyncQueryHandler {

	public MyQueryHandler(ContentResolver cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
		// TODO Auto-generated method stub
		super.onQueryComplete(token, cookie, cursor);
		if(cookie!=null&&cookie instanceof CursorAdapter){
			((CursorAdapter)cookie).changeCursor(cursor);
		}
//		CursorUtils.printCursor(cursor);
	}

}
