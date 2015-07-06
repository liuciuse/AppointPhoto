package com.appointphoto.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.appointphoto.activity.util.Util;
import com.appointphoto.service.MyService;
import com.example.appointphoto.R;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends FragmentActivity implements
		View.OnClickListener {

	private ResideMenu resideMenu;
	private MainActivity mContext;
	private ResideMenuItem itemHome;
	private ResideMenuItem itemMine;
	private ResideMenuItem itemSetting;
	private ResideMenuItem itemQuit;
	private ResideMenuItem itemRegAsPhotographer;
	private ResideMenuItem itemRegAsModel;
	private ResideMenuItem itemRegAsdresser;
	private TextView headerName;

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (resideMenu.isOpened()) {
				resideMenu.closeMenu();
				return false;
			}
			MyService.doubleTapToExit(this);
			return false;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (resideMenu.isOpened()) {
				resideMenu.closeMenu();
			} else {
				resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �Ƴ�ActionBar
		setContentView(R.layout.main);
		mContext = this;
		initItems();
		setUpMenu();
		if (savedInstanceState == null)
			changeFragment(new ListPhotographerFragment());
	}

	private void initItems() {
		headerName = (TextView) findViewById(R.id.layout_top_name);
		headerName.setText("��Ӱʦ");
	}

	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);
		// ���������һ���
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		// create menu items;
		itemHome = new ResideMenuItem(this, R.drawable.menu_icon_home, "��ҳ");
		itemMine = new ResideMenuItem(this, R.drawable.menu_icon_authenticate,
				"�ҵ�");
		itemSetting = new ResideMenuItem(this, R.drawable.menu_icon_setting,
				"����");
		itemRegAsPhotographer = new ResideMenuItem(this,
				R.drawable.menu_icon_invite, "����Ϊ��֤��Ӱʦ");
		itemRegAsdresser = new ResideMenuItem(this,
				R.drawable.menu_icon_invite, "����Ϊ��֤��ױʦ");
		itemRegAsModel = new ResideMenuItem(this, R.drawable.menu_icon_invite,
				"����Ϊ��֤ģ��");
		itemQuit = new ResideMenuItem(this, R.drawable.menu_icon_quit, "�˳���¼");

		itemHome.setOnClickListener(this);
		itemMine.setOnClickListener(this);
		itemSetting.setOnClickListener(this);
		itemQuit.setOnClickListener(this);
		itemRegAsPhotographer.setOnClickListener(this);
		itemRegAsModel.setOnClickListener(this);
		itemRegAsdresser.setOnClickListener(this);

		resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemMine, ResideMenu.DIRECTION_LEFT);
		resideMenu
				.addMenuItem(itemRegAsPhotographer, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemRegAsdresser, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemRegAsModel, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemSetting, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemQuit, ResideMenu.DIRECTION_LEFT);

		findViewById(R.id.title_bar_left_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
	}

	// ���Ʋ������Ĭ����Ϊ
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	// ѡ��˵����Ĵ���
	@Override
	public void onClick(View view) {

		if (view == itemHome) {
			changeFragment(new ListPhotographerFragment());
			headerName.setText("��Ӱʦ");
		} else if (view == itemMine) {
			changeFragment(new MineFragment());
			headerName.setText("�ҵ�");
		} else if (view == itemSetting) {
			startActivity(new Intent(this, SettingActivity.class));
		} else if (view == itemQuit) {
			exit();
		} else if (view == itemRegAsPhotographer) {
			startActivity(new Intent(this, LoginActivity.class));
		} else if (view == itemRegAsdresser) {
			Util.showDlg(this, "dress", "�����ȷ��");
		} else if (view == itemRegAsModel) {

		}

		resideMenu.closeMenu();
	}

	// �˳���Ӧ��
	private void exit() {
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("��ܰ��ʾ")
				// ���öԻ������
				.setMessage("��,��ȷ��Ҫ�˳���")
				// ������ʾ������
				.setPositiveButton("�˳�", new DialogInterface.OnClickListener() {// ���ȷ����ť
							@Override
							public void onClick(DialogInterface dialog,
									int which) {//

								MainActivity.this.finish();
							}
						})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť
							@Override
							public void onClick(DialogInterface dialog,
									int which) {// ��Ӧ�¼�
								Log.i("alertdialog", " �뱣�����ݣ�");
							}
						}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���
	}

	// �����˵�״̬
	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			// Toast.makeText(mContext, "Menu is opened!",
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		public void closeMenu() {
			// Toast.makeText(mContext, "Menu is closed!",
			// Toast.LENGTH_SHORT).show();
		}
	};

	private void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	// What good method is to access resideMenu��
	public ResideMenu getResideMenu() {
		return resideMenu;
	}

	public TextView getHeaderName() {
		return headerName;
	}
}
