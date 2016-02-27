package bean;

import android.database.Cursor;

public class Group {
	private int _id;
	private String name;
	private long create_time;
	private int thread_count;
	
	public static Group getGroupByCursor(Cursor cursor) {
		Group group = new Group();
		group.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
		group.setName(cursor.getString(cursor.getColumnIndex("name")));
		group.setCreate_time(cursor.getLong(cursor.getColumnIndex("create_time")));
		group.setThread_count(cursor.getInt(cursor.getColumnIndex("thread_count")));
		return group;
	}
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	public int getThread_count() {
		return thread_count;
	}
	public void setThread_count(int thread_count) {
		this.thread_count = thread_count;
	}
	
}
