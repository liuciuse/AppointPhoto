package com.appointphoto.activity;

import java.util.ArrayList;

import org.json.JSONArray;

import com.appointphoto.activity.ListPhotographerFragment.MyHandler;
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
import android.widget.LinearLayout;
import android.widget.ListView;

public class PhotographerActivityEx extends Activity {
	private PullToRefreshListView mPullRefreshListView;// ��ˢ��listview
	private WorkAdapter adapter;
	private ServicePagerAdapter serviceadapter;
	private LayoutInflater inflater;
	private PageControl pagecontrol;
	public PageControl getPagecontrol() {
		return pagecontrol;
	}

	private LinearLayout emptyactivities;
	private View convertView;// ͷ��ͼ��

	private MyHandler myHandler = new MyHandler();
	private Runnable mythread;
	private ViewPager mactivities;//����ҳ��

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		inflater = getLayoutInflater();
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �Ƴ�ActionBar
		setContentView(R.layout.photographer_main2);
		// ��ʼ������
		init();
		firstInitView();
	}

	private void firstInitView() {
		if (mythread == null) {
			mythread = new Runnable() {
				@Override
				public void run() {
					int statusCode[] = new int[1];
					try {
						adapter.setWorkList((JsonUtil
								.jsonToWorklist(new JSONArray(MyURI.uri2Str(
										MyURI.RefreshWorkURI, MyURI
												.refreshWorks().toString(),
										statusCode)))));
					} catch (Exception e) {
						Message msg = new Message();
						msg.what = 404;
						myHandler.dispatchMessage(msg);
						e.printStackTrace();
					}
					Message msg = new Message();
					msg.what = 200;
					myHandler.sendMessage(msg);

				}
			};
		}
		new Thread(mythread).run();
	}

	private void init() {
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		adapter = new WorkAdapter(this);
		// ��ʼ��ˢ��
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		// ��������ˢ��
		mPullRefreshListView.setMode(Mode.BOTH);
		ILoadingLayout startLabels = mPullRefreshListView
				.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("����ˢ��...");// ������ʱ����ʾ����ʾ
		startLabels.setRefreshingLabel("����ˢ��...");// ˢ��ʱ
		startLabels.setReleaseLabel("�ͷ�ˢ��...");// �����ﵽһ������ʱ����ʾ����ʾ

		ILoadingLayout endLabels = mPullRefreshListView.getLoadingLayoutProxy(
				false, true);
		endLabels.setPullLabel("���ظ���...");// ������ʱ����ʾ����ʾ
		endLabels.setRefreshingLabel("��������...");// ˢ��ʱ
		endLabels.setReleaseLabel("�ͷż���...");// �����ﵽһ������ʱ����ʾ����ʾ

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

		// ����listview����Դ
		mPullRefreshListView.setAdapter(adapter);

		// ����item����¼�
		mPullRefreshListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> container,
							View view, int position, long id) {
						PhotographerActivityEx.this.startActivity(new Intent(
								PhotographerActivityEx.this,
								ImageBrowActivity.class));
					}

				});
		// ˢ���б����ͷ��
		ListView mListView = mPullRefreshListView.getRefreshableView();
		convertView = inflater.inflate(R.layout.photographer_header, null,
				false);
		initheadview(convertView);
		mListView.addHeaderView(convertView);

	}

	// ��ʼ��ͷ��
	private void initheadview(View convertView) {
		mactivities = (ViewPager) convertView
				.findViewById(R.id.photographer_item_viewpager);

		serviceadapter = new ServicePagerAdapter(PhotographerActivityEx.this);
		mactivities.setAdapter(serviceadapter);
		// mactivities.setAdapter(new ServicePagerAdapter(
		// PhotographerActivityEx.this));
//		mactivities.setAdapter(serviceadapter);
		new Thread(new MypageGet()).start();
		// ���ü���״̬�仯�������������������ʾ
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
//		pagecontrol.setCount(3);
		// ����activitiesʱ�����ؿ���
		emptyactivities = (LinearLayout) convertView
				.findViewById(R.id.item_empty_layout);
		emptyactivities.setVisibility(View.GONE);
	}

	// ����ˢ��
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
				Util.showShortToast(PhotographerActivityEx.this, "����δ����");
			} else {
				Util.showShortToast(PhotographerActivityEx.this, "��,������ʧ����");
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

	// �������ظ���
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
				Util.showShortToast(PhotographerActivityEx.this, "����δ����");
			} else {
				Util.showShortToast(PhotographerActivityEx.this, "��,������ʧ����");
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

	// �����첽�ص�
	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// ���������д�˷�������������
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 200) {
				//��ʼ��ˢ�½���
				adapter.notifyDataSetChanged();
			} else if (msg.what == 404) {
				// ��ȡ����ʧ��
			} else if (msg.what == 201) {
				//��ʼ���������
				serviceadapter.initViews();
				serviceadapter.notifyDataSetChanged();
			}

		}
	}

	// ��ȡviewpager����
	class MypageGet implements Runnable {

		@Override
		public void run() {
			int statusCode[] = new int[1];
			try {
				serviceadapter.setServices(((JsonUtil.jsonToServiceList(new JSONArray(
						MyURI.uri2Str(MyURI.testServiceURI, MyURI
								.getServices().toString(), statusCode))))));
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
