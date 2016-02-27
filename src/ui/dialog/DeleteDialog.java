package ui.dialog;

import com.example.sms.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import base.BaseDialog;

public class DeleteDialog extends BaseDialog {

	private TextView delete_dialog_title;
	private ProgressBar delete_dialog_pb;
	private Button delete_dialog_bt_cancel;
	private DeleteListener listener;
	private int maxProgress;

	public DeleteDialog(Context context,int maxProgress, DeleteListener listener) {
		super(context);
		this.listener = listener;
		this.maxProgress = maxProgress;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.deletedialog);
		delete_dialog_title = (TextView) findViewById(R.id.delete_dialog_title);
		delete_dialog_pb = (ProgressBar) findViewById(R.id.delete_dialog_pb);
		delete_dialog_bt_cancel = (Button) findViewById(R.id.delete_dialog_bt_cancel);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		delete_dialog_title.setText("正在删除...("+0+"/"+maxProgress+")");
		delete_dialog_pb.setMax(maxProgress);
	}

	@Override
	public void processClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.delete_dialog_bt_cancel:
			listener.onCancel();
			dismiss();
			break;
		}
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		delete_dialog_bt_cancel.setOnClickListener(this);
	}
	
	public interface DeleteListener{
		void onCancel();
	}

	public void setProgressAndTitle(int progress) {
		// TODO Auto-generated method stub
		delete_dialog_pb.setProgress(progress);
		delete_dialog_title.setText("正在删除...("+progress+"/"+maxProgress+")");
	}

}
