package ui.dialog;

import com.example.sms.R;

import android.content.Context;
import android.provider.MediaStore.Images.Thumbnails;
import android.text.TextDirectionHeuristic;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import base.BaseDialog;

public class EditTextDialog extends BaseDialog {

	private EditText et_edittext_dialog;
	private Button bt_cancel_edittext_dialog;
	private Button bt_confirm_edittext_dialog;
	private EditTextDialogListener editTextDialogListener;
	private Context context;
	private TextView tv_title_edittext_dialog;
	private String title;

	protected EditTextDialog(Context context,String title, EditTextDialogListener editTextDialogListener) {
		super(context);
		// TODO Auto-generated constructor stub
		this.editTextDialogListener = editTextDialogListener;
		this.context = context;
		this.title = title;
	}
	
	public static void showEditTextDialog(Context context,String title, EditTextDialogListener newGroupDialogListener){
		EditTextDialog dialog = new EditTextDialog(context, title, newGroupDialogListener);
		dialog.setView(new EditText(context));
		dialog.show();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.edittextdialog);
		tv_title_edittext_dialog = (TextView) findViewById(R.id.tv_title_edittext_dialog);
		et_edittext_dialog = (EditText) findViewById(R.id.et_edittext_dialog);
		bt_cancel_edittext_dialog = (Button) findViewById(R.id.bt_cancel_edittext_dialog);
		bt_confirm_edittext_dialog = (Button) findViewById(R.id.bt_confirm_edittext_dialog);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tv_title_edittext_dialog.setText(title);
	}

	@Override
	public void processClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.bt_confirm_edittext_dialog:
			if(editTextDialogListener!=null){
				String name = et_edittext_dialog.getText().toString();
				if(!TextUtils.isEmpty(name)){
					editTextDialogListener.onConfirm(name);
				}else{
					Toast.makeText(context, "请输入群组名称", 0).show();
				}
			}
			break;
		case R.id.bt_cancel_edittext_dialog:
			if(editTextDialogListener!=null){
				editTextDialogListener.onCancel();
			}
			break;
		}
		dismiss();
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		bt_cancel_edittext_dialog.setOnClickListener(this);
		bt_confirm_edittext_dialog.setOnClickListener(this);
	}
	
	public interface EditTextDialogListener{
		void onConfirm(String name);
		void onCancel();
	}

}
