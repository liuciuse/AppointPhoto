package com.appointphoto.activity;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.appointphoto.XApplication;
import com.appointphoto.activity.LoginDetailActivity.MyHandler;
import com.appointphoto.activity.util.ImageLoaderUtil;
import com.appointphoto.activity.util.JsonUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.activity.util.Util;
import com.appointphoto.adapter.PhotographerAdapter;
import com.appointphoto.model.Photographer;
import com.example.appointphoto.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.special.ResideMenu.ResideMenu;

/**
 * User: special Date: 13-12-22 Time: 涓嬪崍1:33 Mail: specialcyci@gmail.com
 */
public class ListPhotographerFragment extends Fragment {

	private View parentView;// 整个界面
	private ResideMenu resideMenu;// 侧栏
	private PullToRefreshListView mPullRefreshListView;// 可刷新listview
	private PhotographerAdapter adapter;
	private LayoutInflater inflater;
	private MainActivity parentActivity;

	// 所有类别
	Button title_bar_item0;
	Button title_bar_item1;
	Button title_bar_item2;
	Button title_bar_item3;
	Button title_bar_item4;
	Button title_bar_item5;
	Button title_bar_item6;
	Button title_bar_item7;
	Button title_bar_item8;

	private MyHandler myHandler = new MyHandler();;
	XApplication application;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		parentView = inflater.inflate(R.layout.homelistview, container, false);
		adapter = new PhotographerAdapter(getActivity());
		application = (XApplication) getActivity().getApplication();
		setUpViews();
		return parentView;
	}

	private void setUpViews() {
		parentActivity = (MainActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();

		// add gesture operation's ignored views 水平滚动条不触发菜单手势
		HorizontalScrollView ignored_view = (HorizontalScrollView) parentView
				.findViewById(R.id.header_ScrollView);
		resideMenu.addIgnoredView(ignored_view);

		// 初始化刷新
		mPullRefreshListView = (PullToRefreshListView) parentView
				.findViewById(R.id.pull_refresh_list);
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
						// 设置点击摄影师后，打开摄影师主界面
						Intent mIntent = new Intent(parentActivity,
								PhotographerActivity.class);
						startActivity(mIntent);

					}

				});

		// 下面类别选择处理
		title_bar_item0 = (Button) parentView
				.findViewById(R.id.title_bar_item0);
		title_bar_item0.setOnClickListener(new MyBtnClickListener());
		title_bar_item1 = (Button) parentView
				.findViewById(R.id.title_bar_item1);
		title_bar_item1.setOnClickListener(new MyBtnClickListener());
		title_bar_item2 = (Button) parentView
				.findViewById(R.id.title_bar_item2);
		title_bar_item2.setOnClickListener(new MyBtnClickListener());
		title_bar_item3 = (Button) parentView
				.findViewById(R.id.title_bar_item3);
		title_bar_item3.setOnClickListener(new MyBtnClickListener());
		title_bar_item4 = (Button) parentView
				.findViewById(R.id.title_bar_item4);
		title_bar_item4.setOnClickListener(new MyBtnClickListener());
		title_bar_item5 = (Button) parentView
				.findViewById(R.id.title_bar_item5);
		title_bar_item5.setOnClickListener(new MyBtnClickListener());
		title_bar_item6 = (Button) parentView
				.findViewById(R.id.title_bar_item6);
		title_bar_item6.setOnClickListener(new MyBtnClickListener());
		title_bar_item7 = (Button) parentView
				.findViewById(R.id.title_bar_item7);
		title_bar_item7.setOnClickListener(new MyBtnClickListener());
		title_bar_item8 = (Button) parentView
				.findViewById(R.id.title_bar_item8);
		title_bar_item8.setOnClickListener(new MyBtnClickListener());
	}

	// 下拉刷新
	@SuppressLint("NewApi")
	private class PullRefresh extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			int statusCode[] = new int[1];
			try {
				adapter.setPhotographers(JsonUtil
						.jsonToPhotographerList(new JSONArray(MyURI.uri2Str(
								MyURI.RefreshPsURI, MyURI.refreshPts()
										.toString(), statusCode))));
			} catch (Exception e) {
				this.cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled(Void result) {
			if (!Util.isNetWorkAvailable(application)) {
				Util.showShortToast(application, "网络未连接");
			} else {
				Util.showShortToast(application, "汗,服务器失联了");
			}
			mPullRefreshListView.onRefreshComplete();
			super.onCancelled(result);
		}

		@Override
		protected void onPostExecute(Void result) {
			((BaseAdapter) adapter).notifyDataSetChanged();
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
				// 获取更多摄影师信息
				adapter.getPhotographers().addAll(
						JsonUtil.jsonToPhotographerList(new JSONArray(MyURI
								.uri2Str(MyURI.getmorePsURI, MyURI.morePts()
										.toString(), statusCode))));
			} catch (Exception e) {
				this.cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled(Void result) {
			if (!Util.isNetWorkAvailable(application)) {
				Util.showShortToast(application, "网络未连接");
			} else {
				Util.showShortToast(application, "汗,服务器失联了");
			}
			mPullRefreshListView.onRefreshComplete();
			
			super.onCancelled(result);
		}

		@Override
		protected void onPostExecute(Void result) {
			// 滚动到底部
			((BaseAdapter) adapter).notifyDataSetChanged();
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}


	// 选择类别处理
	public class MyBtnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			int vid = v.getId();
			changeSelcolor(v);
			switch (vid) {
			case R.id.title_bar_item0:

				break;
			case R.id.title_bar_item1:

				break;
			case R.id.title_bar_item2:

				break;
			case R.id.title_bar_item3:

				break;
			case R.id.title_bar_item4:

				break;
			case R.id.title_bar_item5:

				break;
			case R.id.title_bar_item6:

				break;
			case R.id.title_bar_item7:

				break;
			case R.id.title_bar_item8:

				break;

			default:
				break;
			}
		}

	}

	// 选中某一类别，标示
	private void changeSelcolor(View v) {
		title_bar_item0.setTextColor(getResources().getColor(R.color.black));
		title_bar_item1.setTextColor(getResources().getColor(R.color.black));
		title_bar_item2.setTextColor(getResources().getColor(R.color.black));
		title_bar_item3.setTextColor(getResources().getColor(R.color.black));
		title_bar_item4.setTextColor(getResources().getColor(R.color.black));
		title_bar_item5.setTextColor(getResources().getColor(R.color.black));
		title_bar_item6.setTextColor(getResources().getColor(R.color.black));
		title_bar_item7.setTextColor(getResources().getColor(R.color.black));
		title_bar_item8.setTextColor(getResources().getColor(R.color.black));
		((Button) v).setTextColor(getResources().getColor(R.color.orange));
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
			if (msg.what == 1) {
				Bundle b = msg.getData();
				String reslut = b.getString("result");
			} else if (msg.what == 404) {
				// 获取数据失败
			}

		}
	}

	

}
