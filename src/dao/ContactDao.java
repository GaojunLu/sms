package dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;

public class ContactDao {
	public static String getNameByAddress(String address, Context context) {
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
		Cursor cursor = resolver.query(uri, new String[] { "display_name" },
				null, null, null);
		if (cursor.moveToFirst()) {
			return cursor.getString(0);
		}else{
			return null;
		}
	}

	public static String getPhotoUriByAdress(String address, Context context) {
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
		Cursor cursor = resolver.query(uri, new String[] { "photo_thumb_uri" },
				null, null, null);
		if (cursor.moveToFirst()) {
			return cursor.getString(0);
		}else{
			return null;
		}
	}

	public static String getContactIdByAdress(String address, Context context) {
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
		Cursor cursor = resolver.query(uri, new String[] { "_id" },
				null, null, null);
		if (cursor.moveToFirst()) {
			return cursor.getString(0);
		}else{
			return null;
		}
	}
}
