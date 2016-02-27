package test;

import java.io.InputStream;

import global.Constans;
import utils.CursorUtils;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.test.AndroidTestCase;

public class Test extends AndroidTestCase {

	// 测试短信内容提供者
	public void smsConversation() {
		String[] projection = { "sms.body AS snippet",
				 "sms.thread_id AS thread_id",
				"groups.msg_count AS msg_count", "address as address", "date AS date"};
		Cursor cursor = getContext().getContentResolver().query(
				Constans.SMS_CONVERSATION, projection, null, null, null);
		CursorUtils.printCursor(cursor);
	}

	public void sms() {
		Cursor cursor = getContext().getContentResolver().query(
				Constans.SMS_URI, null, null, null, null);
		CursorUtils.printCursor(cursor);
	}

	public void smsThreadId() {
		Cursor cursor = getContext().getContentResolver().query(
				Constans.SMS_THREADID, null, null, null, null);
		CursorUtils.printCursor(cursor);
	}

	public void contact() {
		Cursor cursor = getContext().getContentResolver().query(
				Constans.CONTACT_RAW, null, null, null, null);
		CursorUtils.printCursor(cursor);
	}
	public void contactPhone() {
		Uri CONTACT_PHONE = Uri.parse("content://com.android.contacts/data/phones/filter/111");
		Cursor cursor = getContext().getContentResolver().query(
				CONTACT_PHONE, null, null, null, null);
		CursorUtils.printCursor(cursor);
		Cursor cursor2 = getContext().getContentResolver().query(
				Uri.parse("content://com.android.contacts/display_photo/1"), null, null, null, null);
		//01-08 04:12:15.210: I/System.out(13486): photo_uri:content://com.android.contacts/display_photo/1

	}
	public void getPhoto() {
		InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContext().getContentResolver(), Uri.parse("content://com.android.contacts/display_photo/1"));
		
	}
	public void getContactIdByAdress() {
		ContentResolver resolver = getContext().getContentResolver();
//		Uri uri = Uri
//				.parse("content://com.android.contacts/data/phones/filter/"
//						+ 555);
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, 5554+"");
		Cursor cursor = resolver.query(uri, null,
				null, null, null);
		CursorUtils.printCursor(cursor);
	}

}
