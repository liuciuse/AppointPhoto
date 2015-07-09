package com.appointphoto.nouse;

import java.util.ArrayList;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appointphoto.activity.ServiceDetailActivity;
import com.appointphoto.activity.util.JsonUtil;
import com.appointphoto.activity.util.MyListViewUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.activity.util.Util;
import com.appointphoto.adapter.WorkAdapter;
import com.appointphoto.service.MyService;
import com.appointphoto.widget.PageControl;
import com.example.appointphoto.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class PhotographerActivity extends Activity {
	private Button navibackbtn;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private ListView mListView;
	private WorkAdapter adapter;
	private LayoutInflater inflater;
	private ViewPager mactivities;
	private ArrayList views;// 保存了可以滚动的view
	private LinearLayout emptyactivities;
	private LinearLayout graph_header;// 滚动到顶部
	private PageControl pagecontrol;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
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
		adapter = new WorkAdapter(this);
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
			// 点击进入服务
			tempView.setOnClickListener(new MyOnClickListener());
		}
		// 初始化pagecontrol
		pagecontrol = (PageControl) findViewById(R.id.activity_pagecontrol);
		pagecontrol.setCount(3);

		// 设置Adapter
		mactivities.setAdapter(new MyPagerAdapter());
		// 设置监听状态变化，用来表达其他美化表示
		mactivities.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				pagecontrol.setSelected(position);
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

	public ListView getmListView() {
		return mListView;
	}

	// 下拉刷新
	@SuppressLint("NewApi")
	private class PullRefresh extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			int statusCode[] = new int[1];
			try {
				adapter.setWorkList((JsonUtil.jsonToWorklist(new JSONArray(
						MyURI.uri2Str(MyURI.RefreshWorkURI, MyURI.refreshWorks()
								.toString(), statusCode)))));
			} catch (Exception e) {
				this.cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled(Void result) {
			if (!Util.isNetWorkAvailable(PhotographerActivity.this)) {
				Util.showShortToast(PhotographerActivity.this, "网络未连接");
			} else {
				Util.showShortToast(PhotographerActivity.this, "汗,服务器失联了");
			}
			mPullRefreshScrollView.onRefreshComplete();
			super.onCancelled(result);
		}

		@Override
		protected void onPostExecute(Void result) {
			adapter.notifyDataSetChanged();
			// 设置列表高度
			MyListViewUtil.setListViewHeightBasedOnChildren(mListView);
			mPullRefreshScrollView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	// 上拉加载更多
	@SuppressLint("NewApi")
	private class PullupGetMore extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			int statusCode[] = new int[1];
			try {
				adapter.getWorkList().addAll(((JsonUtil.jsonToWorklist(new JSONArray(
						MyURI.uri2Str(MyURI.getmoreWorkURI, MyURI.getmoreWorks()
								.toString(), statusCode))))));
			} catch (Exception e) {
				this.cancel(true);
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onCancelled(Void result) {
			if (!Util.isNetWorkAvailable(PhotographerActivity.this)) {
				Util.showShortToast(PhotographerActivity.this, "网络未连接");
			} else {
				Util.showShortToast(PhotographerActivity.this, "汗,服务器失联了");
			}
			mPullRefreshScrollView.onRefreshComplete();
			super.onCancelled(result);
		}

		@Override
		protected void onPostExecute(Void result) {
			adapter.notifyDataSetChanged();
			// 设置列表高度
			MyListViewUtil.setListViewHeightBasedOnChildren(mListView);
			mPullRefreshScrollView.onRefreshComplete();
			super.onPostExecute(result);
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

	// 点击服务项进入具体服务页面
	private class MyOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			PhotographerActivity.this.startActivity(new Intent(
					PhotographerActivity.this, ServiceDetailActivity.class));
		}

	}

}
