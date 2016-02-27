package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
	private static MyOpenHelper myOpenHelper;
	
	public static MyOpenHelper getMyOpenHelperInstance(Context context){
		if(myOpenHelper==null){
			myOpenHelper = new MyOpenHelper(context, "group.db", null, 1);
		}
		return myOpenHelper;
	}

	private MyOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String createGroup = 	
				"create table groups("
				+ "_id integer primary key autoincrement,"
				+ "name varchar,"
				+ "create_time integer,"
				+ "thread_count integer)";
		String createThread_group = 
				"create table thread_group("
				+ "_id integer primary key autoincrement,"
				+ "group_id integer,"
				+ "thread_id integer)";
		db.execSQL(createGroup);
		db.execSQL(createThread_group);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
