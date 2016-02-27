package dao;

import java.util.ArrayList;

import global.Constans;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class SmsDao {

	public static int deleteConversationByID(String thread_id,
			ContentResolver resolver) {
		// TODO Auto-generated method stub
		return resolver.delete(Constans.SMS_URI, "thread_id = "+thread_id, null);
	}

	public static void sendSMS(String address, String msg, Context context) {
		// TODO Auto-generated method stub
		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> divideMessage = smsManager.divideMessage(msg);
		for (String text : divideMessage) {
			Intent intent = new Intent(Constans.ACTION_SEND_SMS);
			PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, intent , PendingIntent.FLAG_ONE_SHOT);
			smsManager.sendTextMessage(address, null, text, sentIntent, null);
			saveSms(address, msg, context);
		}
	}

	private static void saveSms(String address, String msg, Context context) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("type", 2);
		values.put("body", msg);
		values.put("address", address);
		context.getContentResolver().insert(Constans.SMS_URI, values);
	}

}
