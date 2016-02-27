package ui.dialog;

import base.BaseDialog;

import com.example.sms.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmDialog extends BaseDialog {

	private TextView dialog_title;
	private TextView dialog_msg;
	private Button dialog_bt_cancel;
	private Button dialog_bt_confirm;
	private String title;
	private String msg;
	private onClickListener listener;

	protected ConfirmDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param context
	 * @param title 标题
	 * @param msg 显示的消息
	 */
	public ConfirmDialog(Context context, String title, String msg, onClickListener listener) {
		this(context);
		this.title = title;
		this.msg = msg;
		this.listener = listener;
	}

	public static void showConfirmDialog(Context context, String title, String msg, onClickListener listener) {
		ConfirmDialog dialog = new ConfirmDialog(context, title, msg, listener);
		dialog.show();
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.confirmdialog);
		dialog_title = (TextView) findViewById(R.id.confirm_dialog_title);
		dialog_msg = (TextView) findViewById(R.id.confirm_dialog_msg);
		dialog_bt_cancel = (Button) findViewById(R.id.confirm_dialog_bt_cancel);
		dialog_bt_confirm = (Button) findViewById(R.id.confirm_dialog_bt_confirm);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		dialog_title.setText(title);
		dialog_msg.setText(msg);
	}

	@Override
	public void processClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.confirm_dialog_bt_cancel:
			listener.onCancel();
			break;
		case R.id.confirm_dialog_bt_confirm:
			listener.onConfirm();
			break;
		}
		dismiss();
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		dialog_bt_cancel.setOnClickListener(this);
		dialog_bt_confirm.setOnClickListener(this);
	}

	public interface onClickListener{
		void onCancel();
		void onConfirm();
	}
}
