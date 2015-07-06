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
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		setContentView(R.layout.main);
		mContext = this;
		initItems();
		setUpMenu();
		if (savedInstanceState == null)
			changeFragment(new ListPhotographerFragment());
	}

	private void initItems() {
		headerName = (TextView) findViewById(R.id.layout_top_name);
		headerName.setText("摄影师");
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
		// 不允许向右滑动
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		// create menu items;
		itemHome = new ResideMenuItem(this, R.drawable.menu_icon_home, "首页");
		itemMine = new ResideMenuItem(this, R.drawable.menu_icon_authenticate,
				"我的");
		itemSetting = new ResideMenuItem(this, R.drawable.menu_icon_setting,
				"设置");
		itemRegAsPhotographer = new ResideMenuItem(this,
				R.drawable.menu_icon_invite, "申请为认证摄影师");
		itemRegAsdresser = new ResideMenuItem(this,
				R.drawable.menu_icon_invite, "申请为认证化妆师");
		itemRegAsModel = new ResideMenuItem(this, R.drawable.menu_icon_invite,
				"申请为认证模特");
		itemQuit = new ResideMenuItem(this, R.drawable.menu_icon_quit, "退出登录");

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

	// 控制侧边栏的默认行为
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	// 选择菜单项后的处理
	@Override
	public void onClick(View view) {

		if (view == itemHome) {
			changeFragment(new ListPhotographerFragment());
			headerName.setText("摄影师");
		} else if (view == itemMine) {
			changeFragment(new MineFragment());
			headerName.setText("我的");
		} else if (view == itemSetting) {
			startActivity(new Intent(this, SettingActivity.class));
		} else if (view == itemQuit) {
			exit();
		} else if (view == itemRegAsPhotographer) {
			startActivity(new Intent(this, LoginActivity.class));
		} else if (view == itemRegAsdresser) {
			Util.showDlg(this, "dress", "你真的确定");
		} else if (view == itemRegAsModel) {

		}

		resideMenu.closeMenu();
	}

	// 退出此应用
	private void exit() {
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("温馨提示")
				// 设置对话框标题
				.setMessage("亲,您确定要退出吗？")
				// 设置显示的内容
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {// 添加确定按钮
							@Override
							public void onClick(DialogInterface dialog,
									int which) {//

								MainActivity.this.finish();
							}
						})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {// 添加返回按钮
							@Override
							public void onClick(DialogInterface dialog,
									int which) {// 响应事件
								Log.i("alertdialog", " 请保存数据！");
							}
						}).show();// 在按键响应事件中显示此对话框
	}

	// 监听菜单状态
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

	// What good method is to access resideMenu？
	public ResideMenu getResideMenu() {
		return resideMenu;
	}

	public TextView getHeaderName() {
		return headerName;
	}
}
