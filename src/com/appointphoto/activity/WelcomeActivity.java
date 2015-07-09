package com.appointphoto.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

public class WelcomeActivity extends Activity {
	ImageView localImageView;

	// 注释
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startService(new Intent(this, MyService.class));
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		// 定义全屏参数
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		// 获得当前窗体对象
		Window window = WelcomeActivity.this.getWindow();
		// 设置当前窗体为全屏显示
		window.setFlags(flag, flag);
		setContentView(R.layout.welcome);
		// new Handler().postDelayed(new Runnable(){
		// @Override
		// public void run() {
		// //execute the task
		// Intent mIntent = new Intent(WelcomeActivity.this,MainActivity.class);
		// startActivity(mIntent);
		// WelcomeActivity.this.finish();
		// }
		// }, 1000);
		AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.5F, 1.0F);
		localAlphaAnimation.setDuration(1500L);
		localAlphaAnimation
				.setAnimationListener(new Animation.AnimationListener() {
					public void onAnimationEnd(Animation paramAnimation) {
						WelcomeActivity.this.startMain();
					}

					public void onAnimationRepeat(Animation paramAnimation) {
					}

					public void onAnimationStart(Animation paramAnimation) {
					}
				});
		localImageView = (ImageView) findViewById(R.id.welcome_imageView);
		localImageView.startAnimation(localAlphaAnimation);
	}

	protected void startMain() {
		Intent mIntent = new Intent(WelcomeActivity.this, MainActivity.class);
		startActivity(mIntent);
		WelcomeActivity.this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
