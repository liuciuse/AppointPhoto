package com.appointphoto;

import com.appointphoto.model.User;

import android.app.Application;
import android.content.Intent;

//全局的
public class XApplication extends Application {
	private User user;

	@Override
	public void onCreate() {
		super.onCreate();

	}

	// 应用终止时，关闭服务
	public void onTerminate() {
		stopService(new Intent("MyService.class"));
		super.onTerminate();
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User paramUser) {
		this.user = paramUser;
	}
}
