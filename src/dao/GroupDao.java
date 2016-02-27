package dao;

import global.Constans;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

public class GroupDao {

	public static void updateGroupNameById(ContentResolver resolver, String name, int _id) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		resolver.update(Constans.MY_PROVIDER_URI_GROUPS_UPDATE, values, "_id="+_id, null);
	}
	
	public static void deleteGroupById(ContentResolver resolver, int _id) {
		resolver.delete(Constans.MY_PROVIDER_URI_GROUPS_DELETE, "_id="+_id, null);
	}
	
	public static void insertGroup(ContentResolver resolver, String name) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("create_time", System.currentTimeMillis());
		values.put("thread_count", 0);
		resolver.insert(Constans.MY_PROVIDER_URI_GROUPS_INSERT, values);
	}

	public static Cursor getAllGroups(ContentResolver contentResolver) {
		// TODO Auto-generated method stub
		return contentResolver.query(Constans.MY_PROVIDER_URI_GROUPS_QUERY, null, null, null, null);
	}

	public static String getGroupNameById(ContentResolver contentResolver,
			int group_id) {
		// TODO Auto-generated method stub
		Cursor cursor = contentResolver.query(Constans.MY_PROVIDER_URI_GROUPS_QUERY, new String[]{"name"}, "_id = "+group_id, null, null);
		cursor.moveToFirst();
		String name = cursor.getString(0);
		return name;
	}

	public static void addThreadCountById(ContentResolver contentResolver,
			int group_id) {
		// TODO Auto-generated method stub
		int count = getThreadCountById(contentResolver, group_id);
		ContentValues values = new ContentValues();
		values.put("thread_count", count+1);
		contentResolver.update(Constans.MY_PROVIDER_URI_GROUPS_UPDATE, values , "_id = "+group_id, null);
	}
	public static void minusThreadCountById(ContentResolver contentResolver,
			int group_id) {
		// TODO Auto-generated method stub
		int count = getThreadCountById(contentResolver, group_id);
		ContentValues values = new ContentValues();
		values.put("thread_count", count-1);
		contentResolver.update(Constans.MY_PROVIDER_URI_GROUPS_UPDATE, values , "_id = "+group_id, null);
	}

	private static int getThreadCountById(ContentResolver contentResolver,
			int group_id) {
		// TODO Auto-generated method stub
		Cursor cursor = contentResolver.query(Constans.MY_PROVIDER_URI_GROUPS_QUERY, new String[]{"thread_count"}, "_id = "+group_id, null, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
}
