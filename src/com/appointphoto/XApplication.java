package com.appointphoto;

import com.appointphoto.model.User;

import android.app.Application;
import android.content.Intent;

//ȫ�ֵ�
public class XApplication extends Application {
	private User user;

	@Override
	public void onCreate() {
		super.onCreate();

	}

	// Ӧ����ֹʱ���رշ���
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
