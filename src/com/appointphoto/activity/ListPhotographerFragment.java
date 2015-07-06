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
import android.widget.ListAdapter;
import android.widget.TextView;

import com.appointphoto.XApplication;
import com.appointphoto.activity.LoginDetailActivity.MyHandler;
import com.appointphoto.activity.util.JsonUtil;
import com.appointphoto.activity.util.MyURI;
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
	private ListAdapter adapter;
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
		adapter = new PSAdapter(getActivity());
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
				application.setPhotographers(JsonUtil
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
				application.getPhotographers().addAll(
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

	// 设机数据源
	private class PSAdapter extends BaseAdapter {
		private Context context;
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		private DisplayImageOptions options;

		public PSAdapter(Context context) {
			this.context  = context;
			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(
							R.drawable.home_photographer_work_default_icon)
					.showImageForEmptyUri(
							R.drawable.home_photographer_work_default_icon)
					.showImageOnFail(
							R.drawable.home_photographer_work_default_icon)
					.cacheInMemory(true).cacheOnDisk(true)
					.considerExifParams(true)
					.displayer(new RoundedBitmapDisplayer(2)).build();
			
		}

		@Override
		public int getCount() {
			return application.getPhotographers().size();
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
				convertView = inflater.inflate(R.layout.list_photographer_item,
						container, false);
				holder = new ViewHolder();
				// 得到各控件的对象
				holder.avatarImgView = ((ImageView) convertView
						.findViewById(R.id.user_avatar_image_view));
				holder.userNameTxtView = ((TextView) convertView
						.findViewById(R.id.user_nickname_text_view));
				holder.desTextView = ((TextView) convertView
						.findViewById(R.id.user_description_text_view));
				holder.priceTxtView = ((TextView) convertView
						.findViewById(R.id.price_text_view));
				holder.priceUnitTxtView = ((TextView) convertView
						.findViewById(R.id.price_unit_text_view));
				holder.tagTxtView = ((TextView) convertView
						.findViewById(R.id.photographer_tags_text_view));
				holder.workImageView1 = ((ImageView) convertView
						.findViewById(R.id.work_image_view_1));
				holder.workImageView2 = ((ImageView) convertView
						.findViewById(R.id.work_image_view_2));
				holder.numWorksTxtView = ((TextView) convertView
						.findViewById(R.id.home_num_work_textV));
				holder.numLikesTxtView = ((TextView) convertView
						.findViewById(R.id.home_num_like_textV));
				holder.numReviewsTxtView = ((TextView) convertView
						.findViewById(R.id.home_num_review_textV));
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();// 获得暂存的引用
			}
			// 设置数据
			Photographer pg = application.getPhotographers().get(position);
			ImageLoader.getInstance().displayImage(pg.getAvatar(),
					holder.avatarImgView, options, animateFirstListener);
			holder.numLikesTxtView.setText(pg.getNumLikes() + "");
			holder.numReviewsTxtView.setText(pg.getNumReviews() + "");
			holder.numWorksTxtView.setText(pg.getNumWorks() + "");
			holder.priceTxtView.setText(pg.getPrice() + "");
			holder.priceUnitTxtView.setText(pg.getPriceUnit() + "");
			holder.userNameTxtView.setText(pg.getNickname() + "");
			ImageLoader.getInstance().displayImage(
					pg.getWorkList().get(0).getImageBaseUrl(),
					holder.workImageView1, options, animateFirstListener);
			ImageLoader.getInstance().displayImage(pg.getWorkList().get(1)
					.getImageBaseUrl(),
					holder.workImageView2, options, animateFirstListener);
			return convertView;
		}

		@Override
		public boolean isEnabled(int position) {
			return true;
		}

		// 暂存item view中的控件引用
		public class ViewHolder {
			ImageView avatarImgView;
			TextView desTextView;
			TextView numLikesTxtView;
			TextView numReviewsTxtView;
			TextView numWorksTxtView;
			TextView priceTxtView;
			TextView priceUnitTxtView;
			TextView tagTxtView;
			TextView userNameTxtView;
			ImageView workImageView1;
			ImageView workImageView2;

			private ViewHolder() {
			}
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

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
