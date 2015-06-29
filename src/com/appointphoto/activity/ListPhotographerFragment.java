package com.appointphoto.activity;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.appointphoto.welcome.WelcomeActivity;
import com.example.appointphoto.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.special.ResideMenu.ResideMenu;

/**
 * User: special Date: 13-12-22 Time: 下午1:33 Mail: specialcyci@gmail.com
 */
public class ListPhotographerFragment extends Fragment {

	private View parentView;// ��������
	private ResideMenu resideMenu;// ����
	private PullToRefreshListView mPullRefreshListView;// ��ˢ��listview
	private ListAdapter adapter;
	private LayoutInflater inflater;
	private MainActivity parentActivity;

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
		parentActivity = (MainActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();

		// add gesture operation's ignored views ˮƽ�������������˵�����
		HorizontalScrollView ignored_view = (HorizontalScrollView) parentView
				.findViewById(R.id.header_ScrollView);
		resideMenu.addIgnoredView(ignored_view);

		// ��ʼ��ˢ��
		mPullRefreshListView = (PullToRefreshListView) parentView
				.findViewById(R.id.pull_refresh_list);
		// ��������ˢ��
		mPullRefreshListView.setMode(Mode.BOTH);
		ILoadingLayout startLabels = mPullRefreshListView.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("����ˢ��...");// ������ʱ����ʾ����ʾ
		startLabels.setRefreshingLabel("����ˢ��...");// ˢ��ʱ
		startLabels.setReleaseLabel("�ͷ�ˢ��...");// �����ﵽһ������ʱ����ʾ����ʾ

		ILoadingLayout endLabels = mPullRefreshListView.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel("���ظ���...");// ������ʱ����ʾ����ʾ
		endLabels.setRefreshingLabel("��������...");// ˢ��ʱ
		endLabels.setReleaseLabel("�ͷż���...");// �����ﵽһ������ʱ����ʾ����ʾ
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
		
		//����listview����Դ
		mPullRefreshListView.setAdapter(adapter);
		
		//����item����¼�
		mPullRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> container, View view, int position,
					long id) {
				//���õ����Ӱʦ�󣬴���Ӱʦ������
				Intent mIntent = new Intent(parentActivity,PhotographerActivity.class);  
                startActivity(mIntent);
				
			}
			
		});
	}

	
	// ����ˢ��
	private class PullRefresh extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
//			try {
//				Thread.sleep(1000);
//				// �����ݿ��ȡ��������
//
//			} catch (InterruptedException e) {
//			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	// �������ظ���
	private class PullupGetMore extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
//			try {
//				Thread.sleep(1000);
//				// �����ݿ���ظ�������
//
//			} catch (InterruptedException e) {
//			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//�������ײ�
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	
	//�������Դ
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
				convertView = inflater.inflate(R.layout.list_photographer_item, container, false);
				holder = new ViewHolder();
				//�õ����ؼ��Ķ���
				  holder.avatarImgView = ((ImageView)convertView.findViewById(R.id.avatar_imageView));
			      holder.userNameTxtView = ((TextView)convertView.findViewById(R.id.user_nickname_text_view));
			      holder.desTextView = ((TextView)convertView.findViewById(R.id.user_description_text_view));
			      holder.priceTxtView = ((TextView)convertView.findViewById(R.id.price_text_view));
			      holder.priceUnitTxtView = ((TextView)convertView.findViewById(R.id.price_unit_text_view));
			      holder.tagTxtView = ((TextView)convertView.findViewById(R.id.photographer_tags_text_view));
			      holder.workImageView1 = ((ImageView)convertView.findViewById(R.id.work_image_view_1));
			      holder.workImageView2 = ((ImageView)convertView.findViewById(R.id.work_image_view_2));
			      holder.numWorksTxtView = ((TextView)convertView.findViewById(R.id.home_num_work_textV));
			      holder.numLikesTxtView = ((TextView)convertView.findViewById(R.id.home_num_like_textV));
			      holder.numReviewsTxtView = ((TextView)convertView.findViewById(R.id.home_num_review_textV));
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();//����ݴ������
			}
			//��������
			return convertView;
		}
		
		@Override
		public boolean isEnabled(int position) {
			return true;
		}
		
		//�ݴ�item view�еĿؼ�����
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

		    private ViewHolder()
		    {
		    }
		}
		
	}
	
}
