package com.appointphoto.activity;

import java.util.ArrayList;

import org.json.JSONArray;

import com.appointphoto.activity.ListPhotographerFragment.MyHandler;
import com.appointphoto.activity.util.HeadViewUtil;
import com.appointphoto.activity.util.ImageLoaderUtil;
import com.appointphoto.activity.util.JsonUtil;
import com.appointphoto.activity.util.MyListViewUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.activity.util.Util;
import com.appointphoto.adapter.MyWorkList;
import com.appointphoto.adapter.ServicePagerAdapter;
import com.appointphoto.adapter.PhotographerAdapter;
import com.appointphoto.adapter.WorkAdapter;
import com.appointphoto.model.Photographer;
import com.appointphoto.service.MyService;
import com.appointphoto.widget.PageControl;
import com.example.appointphoto.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PhotographerActivityEx extends Activity {
	private PullToRefreshListView mPullRefreshListView;// 可刷新listview
	private WorkAdapter adapter;
	private ServicePagerAdapter serviceadapter;
	private LayoutInflater inflater;
	private PageControl pagecontrol;
	private Photographer photographer;
	ProgressDialog mypDialog;
	MyWorkList worklist = new MyWorkList();// 工作作品

	public MyWorkList getWorklist() {
		return worklist;
	}

	public void setWorklist(MyWorkList worklist) {
		this.worklist = worklist;
	}

	private ImageLoadingListener animateFirstListener;

	public PageControl getPagecontrol() {
		return pagecontrol;
	}

	private LinearLayout emptyactivities;
	private View convertView;// 头部图像

	private MyHandler myHandler = new MyHandler();
	private Runnable mythread;
	private ViewPager mactivities;// 服务页面

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		photographer = (Photographer) getIntent().getSerializableExtra(
				"photographer");
		MyService.allActivity.add(this);
		inflater = getLayoutInflater();
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		setContentView(R.layout.photographer_main2);
		// 初始化界面
		init();
		firstInitView();
		// 初始化activity相关信息
		initactivityview();
	}

	// 注册对话框
	private void initDialog() {
		mypDialog = new ProgressDialog(this);
		// 设置进度条风格，风格为圆形，旋转的
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置ProgressDialog 标题
		mypDialog.setTitle("载入");
		mypDialog.setMessage("正在载入...");
		// 设置ProgressDialog 的进度条是否不明确
		mypDialog.setIndeterminate(false);
		// 设置ProgressDialog 是否可以按退回按键取消
		mypDialog.setCancelable(false);
	}

	// 初始化 头部Views
	private void initactivityview() {
		ImageView photographer_avatar_imageV = (ImageView) convertView
				.findViewById(R.id.photographer_avatar_imageV);
		ImageLoader.getInstance().displayImage(MyURI.testavaterURI,
				photographer_avatar_imageV, ImageLoaderUtil.options,
				animateFirstListener);

	}

	// 载入界面时，初始化部分数据
	private void firstInitView() {
		if (mythread == null) {
			mythread = new Runnable() {
				@Override
				public void run() {
					try {
						worklist.refresh();
					} catch (Exception e) {
						Message msg = new Message();
						msg.what = 404;
						myHandler.sendMessage(msg);
						e.printStackTrace();
					}
					Message msg = new Message();
					msg.what = 200;
					myHandler.sendMessage(msg);

				}
			};
		}
		new Thread(mythread).run();
		mypDialog.show();
	}

	private void init() {

		HeadViewUtil.back(this);
		initDialog();
		animateFirstListener = new ImageLoaderUtil.AnimateFirstDisplayListener(
				this);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		adapter = new WorkAdapter(this, worklist);
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
						Intent intent = new Intent(PhotographerActivityEx.this,
								ImageBrowActivity.class);
						intent.putExtra("number", position - 2);
						PhotographerActivityEx.this.startActivity(intent);
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
		mactivities = (ViewPager) convertView
				.findViewById(R.id.photographer_item_viewpager);

		serviceadapter = new ServicePagerAdapter(PhotographerActivityEx.this);
		mactivities.setAdapter(serviceadapter);
		// mactivities.setAdapter(new ServicePagerAdapter(
		// PhotographerActivityEx.this));
		// mactivities.setAdapter(serviceadapter);
		new Thread(new MypageGet()).start();
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
		// pagecontrol.setCount(3);
		// 当有activities时，隐藏空项
		emptyactivities = (LinearLayout) convertView
				.findViewById(R.id.item_empty_layout);
		emptyactivities.setVisibility(View.GONE);
	}

	// 下拉刷新
	@SuppressLint("NewApi")
	private class PullRefresh extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				worklist.refresh();
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
			try {
				worklist.getmore();
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
				// 初始化服务界面
				serviceadapter.initViews();
				serviceadapter.notifyDataSetChanged();
			}

			mypDialog.cancel();

		}
	}

	// 获取viewpager数据
	class MypageGet implements Runnable {

		@Override
		public void run() {
			int statusCode[] = new int[1];
			try {
				serviceadapter.setServices(((JsonUtil
						.jsonToServiceList(new JSONArray(MyURI.uri2Str(
								MyURI.testServiceURI, MyURI.getServices()
										.toString(), statusCode))))));
			} catch (Exception e) {
				Message msg = new Message();
				msg.what = 404;
				myHandler.dispatchMessage(msg);
				e.printStackTrace();
			}
			Message msg = new Message();
			msg.what = 201;
			myHandler.sendMessage(msg);
		}

	}
}
