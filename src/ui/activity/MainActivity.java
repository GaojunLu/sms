package ui.activity;

import java.util.ArrayList; 
import java.util.List;

import ui.fragment.ConversationFragment;
import ui.fragment.GroupFragment;
import ui.fragment.SearchFragment;
import base.BaseActivity;

import com.example.sms.R;

import adapter.MyPagerAdapter;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends BaseActivity {

	private ViewPager viewPager;
	private PagerAdapter adapter;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private TextView tv_conversation;
	private TextView tv_group;
	private TextView tv_search;
	private View tagline;
	private LinearLayout linear_conversation;
	private LinearLayout linear_group;
	private LinearLayout linear_search;

	@Override
	/**
	 * 找组件
	 */
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		tv_conversation = (TextView) findViewById(R.id.tv_conversation);
		tv_group = (TextView) findViewById(R.id.tv_group);
		tv_search = (TextView) findViewById(R.id.tv_search);
		tagline = findViewById(R.id.tagline);
	}

	@Override
	/**
	 * 初始化数据
	 */
	public void setData() {
		// TODO Auto-generated method stub
		fragments.add(new ConversationFragment());
		fragments.add(new GroupFragment());
		fragments.add(new SearchFragment());
		adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
		animateTag();
		tagline.getLayoutParams().width = getWindowManager().getDefaultDisplay().getWidth()/3;//红线三分之一
		linear_conversation = (LinearLayout) findViewById(R.id.linear_conversation);
		linear_group = (LinearLayout) findViewById(R.id.linear_group);
		linear_search = (LinearLayout) findViewById(R.id.linear_search);
	}

	@Override
	/**
	 * 组件的监听
	 */
	public void setListener() {
		// TODO Auto-generated method stub
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				//文字动画
				animateTag();
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				//移动红线
				tagline.animate().translationX(position*tagline.getWidth()+positionOffsetPixels/3).setDuration(0);
//				System.out.println(positionOffsetPixels);
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(viewPager.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		});
		linear_conversation.setOnClickListener(this);
		linear_group.setOnClickListener(this);
		linear_search.setOnClickListener(this);
	}
/**
 * 根据当前页面，显示动画效果
 */
	protected void animateTag() {
		// TODO Auto-generated method stub
		int position = viewPager.getCurrentItem(); 
		//文字动画
		tv_conversation.animate().scaleX(position==0?1.2f:1).setDuration(200);
		tv_conversation.animate().scaleY(position==0?1.2f:1).setDuration(200);
		tv_group.animate().scaleX(position==1?1.2f:1).setDuration(200);
		tv_group.animate().scaleY(position==1?1.2f:1).setDuration(200);
		tv_search.animate().scaleX(position==2?1.2f:1).setDuration(200);
		tv_search.animate().scaleY(position==2?1.2f:1).setDuration(200);
		//文字颜色
		tv_conversation.setTextColor(position==0?0xffffffff:0xffB6B8B6);
		tv_group.setTextColor(position==1?0xffffffff:0xffB6B8B6);
		tv_search.setTextColor(position==2?0xffffffff:0xffB6B8B6);
	}

	@Override
	/**
	 * 点击事件
	 */
	public void processClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linear_conversation:
			viewPager.setCurrentItem(0);
			break;
		case R.id.linear_group:
			viewPager.setCurrentItem(1);
			break;
		case R.id.linear_search:
			viewPager.setCurrentItem(2);
			break;

		}
	} 

	
	
}
