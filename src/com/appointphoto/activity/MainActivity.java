package com.appointphoto.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appointphoto.activity.util.ImageLoaderUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.activity.util.Util;
import com.appointphoto.service.MyService;
import com.example.appointphoto.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
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
	// private ResideMenuItem itemRegAsPhotographer;
	// private ResideMenuItem itemRegAsModel;
	// private ResideMenuItem itemRegAsdresser;
	private TextView headerName;

	SharedPreferences user;// �û�
	JSONObject userJson = new JSONObject();// �����û���Ϣ
	boolean islogin = false;// �Ƿ��¼
	private ResideMenuItem itemRegAsOther;
	private ImageLoadingListener animateFirstListener;

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
		initUser();
		initItems();
		setUpMenu();

		if (savedInstanceState == null)
			changeFragment(new ListPhotographerFragment());
	}

	// �ٴδ򿪽���
	@Override
	protected void onStart() {
		super.onStart();
		if (islogin) {
			itemQuit.setVisibility(View.VISIBLE);
		}
	}

	// ��ʼ���û���Ϣ
	private void initUser() {
		user = getSharedPreferences("user", Context.MODE_PRIVATE);
		try {
			userJson = new JSONObject(user.getString("user", ""));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String loginstate = userJson.optString("loginstate","");
		if (loginstate.equals("logined")) {
			islogin = true;
		}
	}

	private void initItems() {
		animateFirstListener = new ImageLoaderUtil.AnimateFirstDisplayListener(
				this);
		headerName = (TextView) findViewById(R.id.layout_top_name);
		headerName.setText("��Ӱʦ");
	}

	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		initresideMenu(resideMenu);
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
		// itemRegAsPhotographer = new ResideMenuItem(this,
		// R.drawable.menu_icon_invite, "����Ϊ��֤��Ӱʦ");
		// itemRegAsdresser = new ResideMenuItem(this,
		// R.drawable.menu_icon_invite, "����Ϊ��֤��ױʦ");
		// itemRegAsModel = new ResideMenuItem(this,
		// R.drawable.menu_icon_invite,
		// "����Ϊ��֤ģ��");
		itemRegAsOther = new ResideMenuItem(this, R.drawable.menu_icon_invite,
				"����Ϊ��֤��ɫ");
		itemQuit = new ResideMenuItem(this, R.drawable.menu_icon_quit, "�˳���¼");
		itemQuit.setVisibility(View.GONE);

		itemHome.setOnClickListener(this);
		itemMine.setOnClickListener(this);
		itemSetting.setOnClickListener(this);
		itemQuit.setOnClickListener(this);
		// itemRegAsPhotographer.setOnClickListener(this);
		// itemRegAsModel.setOnClickListener(this);
		// itemRegAsdresser.setOnClickListener(this);
		itemRegAsOther.setOnClickListener(this);

		resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemMine, ResideMenu.DIRECTION_LEFT);
		// resideMenu
		// .addMenuItem(itemRegAsPhotographer, ResideMenu.DIRECTION_LEFT);
		// resideMenu.addMenuItem(itemRegAsdresser, ResideMenu.DIRECTION_LEFT);
		// resideMenu.addMenuItem(itemRegAsModel, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemRegAsOther, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemSetting, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemQuit, ResideMenu.DIRECTION_LEFT);

		findViewById(R.id.title_bar_left_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
		// ����ͷ��
		ImageView avarimg = (ImageView) resideMenu
				.findViewById(R.id.user_avatar_image_view);
		avarimg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
			}
		});
	}

	//��ʼ��Menu��Views
	private void initresideMenu(ResideMenu resideMenu2) {
		ImageView user_avatar_image_view = (ImageView) resideMenu2
				.findViewById(R.id.user_avatar_image_view);
		ImageLoader.getInstance().displayImage(MyURI.testavaterURI,
				user_avatar_image_view, ImageLoaderUtil.options,
				animateFirstListener);
		TextView user_nickname_text_view = (TextView) resideMenu2.findViewById(R.id.user_nickname_text_view);
		String userName = userJson.optString("user", "");
		user_nickname_text_view.setText(userName);
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
		} else if (view == itemRegAsOther) {
			resideMenu.closeMenu();
			startActivity(new Intent(this, ChangeRoleActivity.class));
		} else if (view == itemQuit) {
			exit();
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
								clear();
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

	// �˳��û�����������
	protected void clear() {
		user.edit().clear().commit();
		itemQuit.setVisibility(View.GONE);
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

	public ResideMenu getResideMenu() {
		return resideMenu;
	}

	public TextView getHeaderName() {
		return headerName;
	}
}
