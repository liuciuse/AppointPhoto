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
	private ArrayList views;// �����˿��Թ�����view
	private LinearLayout emptyactivities;
	private LinearLayout graph_header;//����������
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �Ƴ�ActionBar
		setContentView(R.layout.photographer_main);
		// ��ʼ������
		init();
	}

	private void init() {

		inflater = getLayoutInflater();
		// ��ʼ����߰�ť
		navibackbtn = (Button) findViewById(R.id.title_bar_left_menu);
		navibackbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotographerActivity.this.finish();
			}
		});

		// ��ʼ��ˢ�¹�����
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

		// ��ʼ������Դ
		adapter = new MyAdapter();
		// ��ʼ���б�
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setAdapter(adapter);
		// �����б�߶�
		MyListViewUtil.setListViewHeightBasedOnChildren(mListView);

		// ��ʼ���м�Ļ��
		mactivities = (ViewPager) findViewById(R.id.photographer_item_viewpager);

		// ��ʼ��viewpager����views
		views = new ArrayList();
		for (int i = 0; i < 3; i++) {
			View tempView = inflater.inflate(R.layout.list_activity_item,
					mactivities, false);
			views.add(tempView);
			//����������
			tempView.setOnClickListener(new MyOnClickListener());
		}

		// ����Adapter
		mactivities.setAdapter(new MyPagerAdapter());
		// ���ü���״̬�仯�������������������ʾ
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

		// ����activitiesʱ�����ؿ���
		emptyactivities = (LinearLayout) findViewById(R.id.item_empty_layout);
		emptyactivities.setVisibility(View.GONE);

		graph_header = (LinearLayout) findViewById(R.id.graph_header);
		graph_header.setFocusable(true);
		graph_header.setFocusableInTouchMode(true);
		graph_header.requestFocus(); 
	}


	// ����ˢ��
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

	// �������ظ���
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

	// ���listview����Դ
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
				// �õ����ؼ��Ķ���

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();// ����ݴ������
			}
			// ��������
			return convertView;
		}

		@Override
		public boolean isEnabled(int position) {
			return true;
		}

		// �ݴ�item view�еĿؼ�����
		public class ViewHolder {

			private ViewHolder() {
			}
		}

	}

	// �����м䲿�ֻ������� ����Դ
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
	
	//������������������ҳ��
	private class MyOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			PhotographerActivity.this.startActivity(new Intent(PhotographerActivity.this, ServiceDetailActivity.class));
		}
		
	}

}
