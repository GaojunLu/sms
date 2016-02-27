package base;

import com.example.sms.R;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;

public abstract class BaseDialog extends AlertDialog implements android.view.View.OnClickListener{

	protected BaseDialog(Context context) {
		super(context, R.style.Dialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		initView();
		initData();
		setListener();
	}
	
	public abstract void initView();
	public abstract void initData();
	public abstract void processClick(View view);
	public abstract void setListener();
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		processClick(v);
	}
}
