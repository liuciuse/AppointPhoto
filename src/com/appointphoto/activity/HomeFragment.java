package com.appointphoto.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListAdapter;

import com.example.appointphoto.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.special.ResideMenu.ResideMenu;

/**
 * User: special Date: 13-12-22 Time: 涓嬪崍1:33 Mail: specialcyci@gmail.com
 */
public class HomeFragment extends Fragment {

	private View parentView;// 整个界面
	private ResideMenu resideMenu;// 侧栏
	private PullToRefreshListView mPullRefreshListView;// 可刷新listview
	private ListAdapter adapter;
	private LayoutInflater inflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		parentView = inflater.inflate(R.layout.homelistview, container, false);
		adapter = new MyAdapter();
		setUpViews();
		return parentView;
	}

	private void setUpViews() {
		MenuActivity parentActivity = (MenuActivity) getActivity();
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
		ILoadingLayout startLabels = mPullRefreshListView.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
		startLabels.setReleaseLabel("释放刷新...");// 下来达到一定距离时，显示的提示

		ILoadingLayout endLabels = mPullRefreshListView.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel("加载更多...");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在载入...");// 刷新时
		endLabels.setReleaseLabel("释放加载...");// 下来达到一定距离时，显示的提示
		mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

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
		
		//设置listview数据源
		mPullRefreshListView.setAdapter(adapter);
		
		//设置item点击事件
		mPullRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> container, View view, int position,
					long id) {
				//设置点击摄影师后，打开摄影师主界面
			}
			
		});
	}

	
	// 下拉刷新
	private class PullRefresh extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
				// 从数据库获取最新数据

			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	// 上拉加载更多
	private class PullupGetMore extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
				// 从数据库加载更多数据

			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//滚动到底部
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	
	//设机数据源
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 100;
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
				convertView = inflater.inflate(R.layout.home_list_item, container, false);
				holder = new ViewHolder();
				//得到各控件的对象
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();//获得暂存的引用
			}
			//设置数据
			return convertView;
		}
		
		@Override
		public boolean isEnabled(int position) {
			return true;
		}
		
		//暂存item view中的控件引用
		public class ViewHolder {
			
		}
		
	}
	
}
