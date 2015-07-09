package com.appointphoto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.appointphoto.model.Photographer;
import com.appointphoto.model.User;
import com.appointphoto.model.Work;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

//全局的
public class XApplication extends Application {
	// 当前用户
	private User user = new User();
	
	

	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
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
	
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}

}
