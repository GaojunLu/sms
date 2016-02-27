package ui.dialog;

import com.example.sms.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import base.BaseDialog;

public class ListDialog extends BaseDialog {

	private TextView tv_title_listdialog;
	private ListView listview_dialog;
	private String title;
	private String[] items;
	private Context context;
	private ListDialogListener listDialogListener;

	protected ListDialog(Context context, String title, String[] items, ListDialogListener listDialogListener) {
		super(context);
		this.title = title;
		this.items = items;
		this.context =  context;
		this.listDialogListener = listDialogListener;
		// TODO Auto-generated constructor stub
	}
	
	public static void showListDialog(Context context, String title, String[] items, ListDialogListener listDialogListener){
		ListDialog dialog = new ListDialog(context, title, items, listDialogListener);
		dialog.show();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.listdialog);
		tv_title_listdialog = (TextView) findViewById(R.id.tv_title_listdialog);
		listview_dialog = (ListView) findViewById(R.id.listview_dialog);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tv_title_listdialog.setText(title);
		listview_dialog.setAdapter(new MyAdapter());
	}

	@Override
	public void processClick(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		listview_dialog.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(listDialogListener!=null)
					listDialogListener.onClick(position);
				dismiss();
			}
		});
	}

	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(context, R.layout.item_dialog_list, null);
			TextView tv_item_listdialog = (TextView) view.findViewById(R.id.tv_item_listdialog);
			tv_item_listdialog.setText(items[position]);
			return view;
		}
		
	}
	public interface ListDialogListener{
		void onClick(int pos);
	}
}
