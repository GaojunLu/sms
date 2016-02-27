package provider;

import global.Constans;

import org.apache.http.client.utils.URIUtils;

import dao.MyOpenHelper;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MyProvider extends ContentProvider {

	private MyOpenHelper helper;
	private SQLiteDatabase db;
	private static String TABLE_GROUPS = "groups";
	private static String TABLE_THREAD_GROUP = "thread_group";
	private static final String authority = "a.b.myprovider";
	private static final int GROUPS_INSERT = 0;
	private static final int GROUPS_QUERY = 1;
	private static final int GROUPS_UPDATE = 2;
	private static final int GROUPS_DELETE = 3;
	private static final int THREAD_GROUP_INSERT = 4;
	private static final int THREAD_GROUP_QUERY = 5;
	private static final int THREAD_GROUP_UPDATE = 6;
	private static final int THREAD_GROUP_DELETE = 7;
	
	UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	{
		matcher.addURI(authority, "groups/insert", GROUPS_INSERT);
		matcher.addURI(authority, "groups/query", GROUPS_QUERY);
		matcher.addURI(authority, "groups/update", GROUPS_UPDATE);
		matcher.addURI(authority, "groups/delete", GROUPS_DELETE);
		matcher.addURI(authority, "thread_group/insert", THREAD_GROUP_INSERT);
		matcher.addURI(authority, "thread_group/query", THREAD_GROUP_QUERY);
		matcher.addURI(authority, "thread_group/update", THREAD_GROUP_UPDATE);
		matcher.addURI(authority, "thread_group/delete", THREAD_GROUP_DELETE);
	}
	
	@Override
	public boolean onCreate() {
		helper = MyOpenHelper.getMyOpenHelperInstance(getContext());
		db = helper.getWritableDatabase();
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		int code = matcher.match(uri);
		switch (code) {
		case GROUPS_QUERY:
			Cursor cursor = db.query(TABLE_GROUPS, projection, selection, selectionArgs, null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), Constans.MY_PROVIDER_URI);//cursor携带更新地址
			return cursor;
		case THREAD_GROUP_QUERY:
			cursor = db.query(TABLE_THREAD_GROUP, projection, selection, selectionArgs, null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), Constans.MY_PROVIDER_URI);//cursor携带更新地址
			return cursor;
		default:
			throw new IllegalArgumentException("不正确的uri："+uri);
		}
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		int code = matcher.match(uri);
		switch (code) {
		case GROUPS_INSERT:
			long l = db.insert(TABLE_GROUPS, null, values);
			if(l!=-1){
				getContext().getContentResolver().notifyChange(uri, null);//更新通知
				return ContentUris.withAppendedId(uri, l);
			}else {
				return null;
			}
		case THREAD_GROUP_INSERT:
			l = db.insert(TABLE_THREAD_GROUP, null, values);
			if(l!=-1){
				getContext().getContentResolver().notifyChange(uri, null);//更新通知
				return ContentUris.withAppendedId(uri, l);
			}else {
				return null;
			}
		default:
			throw new IllegalArgumentException("不正确的uri："+uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int code = matcher.match(uri);
		switch (code) {
		case GROUPS_DELETE:
			int l = db.delete(TABLE_GROUPS, selection, selectionArgs);
			if(l!=-1){
				getContext().getContentResolver().notifyChange(uri, null);//更新通知
				return l;
			}else {
				return -1;
			}
		case THREAD_GROUP_DELETE:
			l = db.delete(TABLE_THREAD_GROUP, selection, selectionArgs);
			if(l!=-1){
				getContext().getContentResolver().notifyChange(uri, null);//更新通知
				return l;
			}else {
				return -1;
			}
		default:
			throw new IllegalArgumentException("不正确的uri："+uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int code = matcher.match(uri);
		switch (code) {
		case GROUPS_UPDATE:
			int l = db.update(TABLE_GROUPS, values, selection, selectionArgs);
			if(l!=-1){
				getContext().getContentResolver().notifyChange(uri, null);//更新通知
				return l;
			}else {
				return -1;
			}
		case THREAD_GROUP_UPDATE:
			l = db.update(TABLE_THREAD_GROUP, values, selection, selectionArgs);
			if(l!=-1){
				getContext().getContentResolver().notifyChange(uri, null);//更新通知
				return l;
			}else {
				return -1;
			}
		default:
			throw new IllegalArgumentException("不正确的uri："+uri);
		}
	}

}
