package com.appointphoto.activity;

import org.json.JSONException;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.appointphoto.adapter.MyWorkList;
import com.appointphoto.adapter.PhotoViewPagerAdapter;
import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

public class ImageBrowActivity extends Activity {
	private ViewPager mViewPager;
	private PagerAdapter adapter;
	private MyWorkList worklist;// ��Ʒ
	private int selected;// ѡ�е����
	private MyHandler mhHandler;

	private Button add_like_btn;// ����
	private Button add_review_btn;//����
	private Button left_bar_btn;//�˳�

	public PagerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		mhHandler = new MyHandler();
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �Ƴ�ActionBar
		setContentView(R.layout.brower_image);
		selected = getIntent().getIntExtra("number", 0);
		initview();
	}

	// ��ʼ������
	private void initview() {
		mViewPager = (ViewPager) findViewById(R.id.brower_pager);
		PhotographerActivityEx pg = (PhotographerActivityEx) MyService
				.getActivityByName("PhotographerActivityEx");
		adapter = new PhotoViewPagerAdapter(ImageBrowActivity.this,
				pg.getWorklist());
		worklist = pg.getWorklist();
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(selected);
		// ���ü���״̬�仯�������������������ʾ
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			boolean flag = true;

			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrolled(int position, float percent, int swift) {
				if (position == adapter.getCount() - 1 && percent == 0.0
						&& swift == 0) {
					if (flag) {
						new Thread(new MyGetMoreThread()).start();
						flag = !flag;
						return;
					}
				}
				flag = true;
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		// ����
		add_like_btn = (Button) findViewById(R.id.add_like_btn);
		add_like_btn.setOnClickListener(new MyOnClick());
		// ����
		add_review_btn = (Button) findViewById(R.id.add_review_btn);
		add_review_btn.setOnClickListener(new MyOnClick());
		//�˳�
		left_bar_btn = (Button) findViewById(R.id.left_bar_btn);
		left_bar_btn.setOnClickListener(new MyOnClick());
		
	}

	// ��viewPager��ȡ��������
	protected void getmoreviews() {
		try {
			worklist.getmore();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Message msg = new Message();
		msg.what = 200;
		mhHandler.sendMessage(msg);
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
				// ��ʼ��ˢ�½���
				adapter.notifyDataSetChanged();
			} else if (msg.what == 404) {
				// ��ȡ����ʧ��
			} else if (msg.what == 201) {

			}
		}
	}

	class MyGetMoreThread implements Runnable {
		@Override
		public void run() {
			getmoreviews();
		}
	}

	class MyOnClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.add_like_btn:
				deallike();
				break;
			case R.id.add_review_btn:
				dealreview();
				break;
			case R.id.left_bar_btn:
				turnback();
				break;

			default:
				break;
			}
		}
	}

	
	//����
	public void turnback() {
		MyService.finishActivity(this);
	}
	//���ϲ��
	public void deallike() {
		
	}
	//�������
	public void dealreview() {
		Intent intent = new Intent(this,ReViewActivity.class);
		startActivity(intent);
	}
}
