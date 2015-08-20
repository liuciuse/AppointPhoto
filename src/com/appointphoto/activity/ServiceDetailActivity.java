package com.appointphoto.activity;

import java.util.ArrayList;

import com.appointphoto.activity.util.HeadViewUtil;
import com.appointphoto.adapter.ServiceImgAdapter;
import com.appointphoto.service.MyService;
import com.appointphoto.widget.PageControl;
import com.example.appointphoto.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class ServiceDetailActivity extends Activity {

	private Button order_btn;// ԤԼ��ť
	private ViewPager activity_viewpager;// �Ϸ���ͼƬ
	private PageControl pagecontrol;
	private ArrayList views;// �����˿��Թ�����view
	private LayoutInflater inflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �Ƴ�ActionBar
		setContentView(R.layout.item_detail);
		initItems();
	}

	private void initItems() {
		HeadViewUtil.back(this);
		inflater = getLayoutInflater();
		order_btn = (Button) findViewById(R.id.item_order_btn);
		order_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ServiceDetailActivity.this.startActivity(new Intent(
						ServiceDetailActivity.this, BookActivity.class));
			}
		});

		// �����Ϸ�ͼƬ
		// ��ʼ���м�Ļ��
		activity_viewpager = (ViewPager) findViewById(R.id.activity_viewpager);

		// ����Adapter
		activity_viewpager.setAdapter(new ServiceImgAdapter(this));
		// ��ʼ��pagecontrol
		pagecontrol = (PageControl) findViewById(R.id.activity_pagecontrol);
		pagecontrol.setCount(3);

		// ���ü���״̬�仯�������������������ʾ
		activity_viewpager.setOnPageChangeListener(new OnPageChangeListener() {
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
	}

}
