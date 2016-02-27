package dao;

import utils.CursorUtils;
import global.Constans;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

public class ThreadGroupDao {

	public static boolean hasGroup(ContentResolver contentResolver,
			int thread_id) {
		// TODO Auto-generated method stub
		Cursor cursor = contentResolver.query(
				Constans.MY_PROVIDER_URI_THREAD_GROUP_QUERY, null,
				"thread_id = " + thread_id, null, null);
		return cursor.moveToFirst();
	}

	public static String getGroupNameByThreadId(
			ContentResolver contentResolver, int thread_id) {
		// TODO Auto-generated method stub
		Cursor cursor = contentResolver.query(
				Constans.MY_PROVIDER_URI_THREAD_GROUP_QUERY,
				new String[] { "group_id" }, "thread_id = " + thread_id, null,
				null);
		cursor.moveToFirst();
		int group_id = cursor.getInt(0);
		String name = GroupDao.getGroupNameById(contentResolver, group_id);
		return name;
	}

	public static void addToGroup(ContentResolver contentResolver,
			int thread_id, int group_id) {
		ContentValues values = new ContentValues();
		values.put("thread_id", thread_id);
		values.put("group_id", group_id);
		contentResolver.insert(Constans.MY_PROVIDER_URI_THREAD_GROUP_INSERT,
				values);
		GroupDao.addThreadCountById(contentResolver, group_id);
	}

	public static void deleteThreadGroupById(ContentResolver contentResolver,
			int thread_id) {
		// TODO Auto-generated method stub
		int group_id = getGroupIdByThreadId(contentResolver, thread_id);
		contentResolver.delete(Constans.MY_PROVIDER_URI_THREAD_GROUP_DELETE,
				"thread_id = " + thread_id, null);
		GroupDao.minusThreadCountById(contentResolver, group_id);
	}

	private static int getGroupIdByThreadId(ContentResolver contentResolver,
			int thread_id) {
		Cursor cursor = contentResolver.query(
				Constans.MY_PROVIDER_URI_THREAD_GROUP_QUERY,
				new String[] { "group_id" }, "thread_id = " + thread_id, null,
				null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(0);
		}
		return -1;
	}

}
