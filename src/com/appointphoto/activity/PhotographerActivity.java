package com.appointphoto.activity;

import java.util.ArrayList;

import com.appointphoto.activity.util.MyListViewUtil;
import com.example.appointphoto.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PhotographerActivity extends Activity {
	private Button navibackbtn;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private ListView mListView;
	private ListAdapter adapter;
	private LayoutInflater inflater;
	private ViewPager mactivities;
	private ArrayList views;// 保存了可以滚动的view
	private LinearLayout emptyactivities;
	private LinearLayout graph_header;//滚动到顶部
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		setContentView(R.layout.photographer_main);
		// 初始化界面
		init();
	}

	private void init() {

		inflater = getLayoutInflater();
		// 初始化左边按钮
		navibackbtn = (Button) findViewById(R.id.title_bar_left_menu);
		navibackbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotographerActivity.this.finish();
			}
		});

		// 初始化刷新滚动条
		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase refreshView) {
						new PullRefresh().execute();
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
						new PullupGetMore().execute();
					}

				});

		// 初始化数据源
		adapter = new MyAdapter();
		// 初始化列表
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setAdapter(adapter);
		// 设置列表高度
		MyListViewUtil.setListViewHeightBasedOnChildren(mListView);

		// 初始化中间的活动项
		mactivities = (ViewPager) findViewById(R.id.photographer_item_viewpager);

		// 初始化viewpager所用views
		views = new ArrayList();
		for (int i = 0; i < 3; i++) {
			View tempView = inflater.inflate(R.layout.list_activity_item,
					mactivities, false);
			views.add(tempView);
			//点击进入服务
			tempView.setOnClickListener(new MyOnClickListener());
		}

		// 设置Adapter
		mactivities.setAdapter(new MyPagerAdapter());
		// 设置监听状态变化，用来表达其他美化表示
		mactivities.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		// 当有activities时，隐藏空项
		emptyactivities = (LinearLayout) findViewById(R.id.item_empty_layout);
		emptyactivities.setVisibility(View.GONE);

		graph_header = (LinearLayout) findViewById(R.id.graph_header);
		graph_header.setFocusable(true);
		graph_header.setFocusableInTouchMode(true);
		graph_header.requestFocus(); 
	}


	// 下拉刷新
	private class PullRefresh extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mPullRefreshScrollView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	// 上拉加载更多
	private class PullupGetMore extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mPullRefreshScrollView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	// 设计listview数据源
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup container) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_work_item,
						container, false);
				holder = new ViewHolder();
				// 得到各控件的对象

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();// 获得暂存的引用
			}
			// 设置数据
			return convertView;
		}

		@Override
		public boolean isEnabled(int position) {
			return true;
		}

		// 暂存item view中的控件引用
		public class ViewHolder {

			private ViewHolder() {
			}
		}

	}

	// 设置中间部分滑动内容 数据源
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) views.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView((View) views.get(position));
			return views.get(position);
		}
	}
	
	//点击服务项进入具体服务页面
	private class MyOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			PhotographerActivity.this.startActivity(new Intent(PhotographerActivity.this, ServiceDetailActivity.class));
		}
		
	}

}
