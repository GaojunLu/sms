package bean;

import android.database.Cursor;

public class Sms {
	private String body;
	private long date;
	private int type;
	
	public static Sms getSmsByCursor(Cursor cursor) {
		Sms sms = new Sms();
		sms.setBody(cursor.getString(cursor.getColumnIndex("body")));
		sms.setDate(cursor.getLong(cursor.getColumnIndex("date")));
		sms.setType(cursor.getInt(cursor.getColumnIndex("type")));
		return sms;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
