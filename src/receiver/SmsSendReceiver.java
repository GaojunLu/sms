package receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SmsSendReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int resultCode = getResultCode();
		if(resultCode==Activity.RESULT_OK){
			Toast.makeText(context, "发送成功", 0).show();
		}else{
			Toast.makeText(context, "发送失败", 0).show();
		}
	}

}
