package base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements OnClickListener {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setData();
		setListener();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return initView(inflater, container, savedInstanceState);
	}

	public abstract View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState);

	public abstract void setData();

	public abstract void setListener();

	public abstract void processClick(View v);

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		processClick(v);
	}
}
