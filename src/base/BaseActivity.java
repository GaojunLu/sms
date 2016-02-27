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
	 * ��ȡ���
	 */
	public abstract void initView();
	/**
	 * ����������ݣ����֡�ͼƬ...��
	 */
	public abstract void setData();
	/**
	 * ��������
	 */
	public abstract void setListener();
	/**
	 * ���ֵ���¼�
	 */
	public abstract void processClick(View v);
	/**
	 * ִ�е���¼�
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		processClick(v);
	}
}
