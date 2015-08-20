package com.appointphoto.activity;

import org.json.JSONException;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.appointphoto.adapter.MyWorkList;
import com.appointphoto.adapter.PhotoViewPagerAdapter;
import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

public class ImageBrowActivity extends Activity {
	private ViewPager mViewPager;
	private PagerAdapter adapter;
	private MyWorkList worklist;// 作品
	private int selected;// 选中的序号
	private MyHandler mhHandler;

	private Button add_like_btn;// 点赞
	private Button add_review_btn;//评论
	private Button left_bar_btn;//退出

	public PagerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		mhHandler = new MyHandler();
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		setContentView(R.layout.brower_image);
		selected = getIntent().getIntExtra("number", 0);
		initview();
	}

	// 初始化界面
	private void initview() {
		mViewPager = (ViewPager) findViewById(R.id.brower_pager);
		PhotographerActivityEx pg = (PhotographerActivityEx) MyService
				.getActivityByName("PhotographerActivityEx");
		adapter = new PhotoViewPagerAdapter(ImageBrowActivity.this,
				pg.getWorklist());
		worklist = pg.getWorklist();
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(selected);
		// 设置监听状态变化，用来表达其他美化表示
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			boolean flag = true;

			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrolled(int position, float percent, int swift) {
				if (position == adapter.getCount() - 1 && percent == 0.0
						&& swift == 0) {
					if (flag) {
						new Thread(new MyGetMoreThread()).start();
						flag = !flag;
						return;
					}
				}
				flag = true;
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		// 点赞
		add_like_btn = (Button) findViewById(R.id.add_like_btn);
		add_like_btn.setOnClickListener(new MyOnClick());
		// 评论
		add_review_btn = (Button) findViewById(R.id.add_review_btn);
		add_review_btn.setOnClickListener(new MyOnClick());
		//退出
		left_bar_btn = (Button) findViewById(R.id.left_bar_btn);
		left_bar_btn.setOnClickListener(new MyOnClick());
		
	}

	// 给viewPager获取更多数据
	protected void getmoreviews() {
		try {
			worklist.getmore();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Message msg = new Message();
		msg.what = 200;
		mhHandler.sendMessage(msg);
	}

	// 处理异步回调
	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法，接受数据
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 200) {
				// 初始化刷新界面
				adapter.notifyDataSetChanged();
			} else if (msg.what == 404) {
				// 获取数据失败
			} else if (msg.what == 201) {

			}
		}
	}

	class MyGetMoreThread implements Runnable {
		@Override
		public void run() {
			getmoreviews();
		}
	}

	class MyOnClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.add_like_btn:
				deallike();
				break;
			case R.id.add_review_btn:
				dealreview();
				break;
			case R.id.left_bar_btn:
				turnback();
				break;

			default:
				break;
			}
		}
	}

	
	//返回
	public void turnback() {
		MyService.finishActivity(this);
	}
	//点击喜欢
	public void deallike() {
		
	}
	//点击评论
	public void dealreview() {
		Intent intent = new Intent(this,ReViewActivity.class);
		startActivity(intent);
	}
}
