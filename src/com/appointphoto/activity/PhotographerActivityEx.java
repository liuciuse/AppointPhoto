package com.appointphoto.activity;

import java.util.ArrayList;

import org.json.JSONArray;

import com.appointphoto.activity.util.JsonUtil;
import com.appointphoto.activity.util.MyListViewUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.activity.util.Util;
import com.appointphoto.adapter.ServicePagerAdapter;
import com.appointphoto.adapter.PhotographerAdapter;
import com.appointphoto.adapter.WorkAdapter;
import com.appointphoto.service.MyService;
import com.appointphoto.widget.PageControl;
import com.example.appointphoto.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PhotographerActivityEx extends Activity {
	private PullToRefreshListView mPullRefreshListView;// 可刷新listview
	private WorkAdapter adapter;
	private LayoutInflater inflater;
	private PageControl pagecontrol;
	private LinearLayout emptyactivities;
	private View convertView;// 头部图像

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		inflater = getLayoutInflater();
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		setContentView(R.layout.photographer_main2);
		// 初始化界面
		init();
	}

	private void init() {
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		adapter = new WorkAdapter(this);
		// 初始化刷新
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		// 设置两端刷新
		mPullRefreshListView.setMode(Mode.BOTH);
		ILoadingLayout startLabels = mPullRefreshListView
				.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
		startLabels.setReleaseLabel("释放刷新...");// 下来达到一定距离时，显示的提示

		ILoadingLayout endLabels = mPullRefreshListView.getLoadingLayoutProxy(
				false, true);
		endLabels.setPullLabel("加载更多...");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在载入...");// 刷新时
		endLabels.setReleaseLabel("释放加载...");// 下来达到一定距离时，显示的提示

		mPullRefreshListView
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

		// 设置listview数据源
		mPullRefreshListView.setAdapter(adapter);

		// 设置item点击事件
		mPullRefreshListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> container,
							View view, int position, long id) {

					}

				});
		// 刷新列表添加头部
		ListView mListView = mPullRefreshListView.getRefreshableView();
		convertView = inflater.inflate(R.layout.photographer_header, null,
				false);
		initheadview(convertView);
		mListView.addHeaderView(convertView);

	}

	// 初始化头部
	private void initheadview(View convertView) {
		// 初始化中间的活动项
		ViewPager mactivities = (ViewPager) convertView
				.findViewById(R.id.photographer_item_viewpager);

		// 设置Adapter
		mactivities.setAdapter(new ServicePagerAdapter(PhotographerActivityEx.this));
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

		pagecontrol = (PageControl) convertView
				.findViewById(R.id.activity_pagecontrol);
		pagecontrol.setCount(3);
		// 当有activities时，隐藏空项
		emptyactivities = (LinearLayout) convertView.findViewById(R.id.item_empty_layout);
		emptyactivities.setVisibility(View.GONE);
	}

	// 下拉刷新
	@SuppressLint("NewApi")
	private class PullRefresh extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			int statusCode[] = new int[1];
			try {
				adapter.setWorkList((JsonUtil.jsonToWorklist(new JSONArray(
						MyURI.uri2Str(MyURI.RefreshWorkURI, MyURI
								.refreshWorks().toString(), statusCode)))));
			} catch (Exception e) {
				this.cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled(Void result) {
			if (!Util.isNetWorkAvailable(PhotographerActivityEx.this)) {
				Util.showShortToast(PhotographerActivityEx.this, "网络未连接");
			} else {
				Util.showShortToast(PhotographerActivityEx.this, "汗,服务器失联了");
			}
			mPullRefreshListView.onRefreshComplete();
			super.onCancelled(result);
		}

		@Override
		protected void onPostExecute(Void result) {
			adapter.notifyDataSetChanged();
			mPullRefreshListView.onRefreshComplete();
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
				adapter.getWorkList().addAll(
						((JsonUtil.jsonToWorklist(new JSONArray(MyURI.uri2Str(
								MyURI.getmoreWorkURI, MyURI.getmoreWorks()
										.toString(), statusCode))))));
			} catch (Exception e) {
				this.cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled(Void result) {
			if (!Util.isNetWorkAvailable(PhotographerActivityEx.this)) {
				Util.showShortToast(PhotographerActivityEx.this, "网络未连接");
			} else {
				Util.showShortToast(PhotographerActivityEx.this, "汗,服务器失联了");
			}
			mPullRefreshListView.onRefreshComplete();
			super.onCancelled(result);
		}

		@Override
		protected void onPostExecute(Void result) {
			adapter.notifyDataSetChanged();
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

}
