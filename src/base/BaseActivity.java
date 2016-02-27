package base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		setData();
		setListener();
	}
	/**
	 * 获取组件
	 */
	public abstract void initView();
	/**
	 * 设置组件数据（文字、图片...）
	 */
	public abstract void setData();
	/**
	 * 设置侦听
	 */
	public abstract void setListener();
	/**
	 * 各种点击事件
	 */
	public abstract void processClick(View v);
	/**
	 * 执行点击事件
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		processClick(v);
	}
}
