package global;

import android.net.Uri;

public interface Constans {
	public Uri SMS_URI = Uri.parse("content://sms");
	public Uri SMS_CONVERSATION = Uri.parse("content://sms/conversations");
	public String SMS_CONVERSATION_ID = "content://sms/conversations/";
	public Uri SMS_THREADID = Uri.parse("content://sms/threadID/2");
	public Uri CONTACT = Uri.parse("content://com.android.contacts/contacts");
	public Uri CONTACT_RAW = Uri.parse("content://com.android.contacts/raw_contacts");
//	public Uri CONTACT_PHONE = Uri.parse("content://com.android.contacts/data/phones/filter/*");
	public Uri CONTACT_DATA = Uri.parse("content://com.android.contacts/data");
	public int SMS_RECEIVE = 1;
	public int SMS_SEND = 2;
	public String ACTION_SEND_SMS = "a.b.sms";
	public Uri MY_PROVIDER_URI = Uri.parse("content://a.b.myprovider/");
	public Uri MY_PROVIDER_URI_GROUPS_INSERT = Uri.parse(MY_PROVIDER_URI + "groups/insert");
	public Uri MY_PROVIDER_URI_GROUPS_QUERY = Uri.parse(MY_PROVIDER_URI + "groups/query");
	public Uri MY_PROVIDER_URI_GROUPS_UPDATE = Uri.parse(MY_PROVIDER_URI + "groups/update");
	public Uri MY_PROVIDER_URI_GROUPS_DELETE = Uri.parse(MY_PROVIDER_URI + "groups/delete");
	public Uri MY_PROVIDER_URI_THREAD_GROUP_INSERT = Uri.parse(MY_PROVIDER_URI + "thread_group/insert");
	public Uri MY_PROVIDER_URI_THREAD_GROUP_QUERY = Uri.parse(MY_PROVIDER_URI + "thread_group/query");
	public Uri MY_PROVIDER_URI_THREAD_GROUP_UPDATE = Uri.parse(MY_PROVIDER_URI + "thread_group/update");
	public Uri MY_PROVIDER_URI_THREAD_GROUP_DELETE = Uri.parse(MY_PROVIDER_URI + "thread_group/delete");
}
