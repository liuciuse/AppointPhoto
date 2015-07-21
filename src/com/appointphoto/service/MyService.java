package com.appointphoto.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.appointphoto.activity.util.Util;
import com.appointphoto.model.Task;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.LayoutInflater;

public class MyService extends Service implements Runnable {
	public static ArrayList<Activity> allActivity = new ArrayList();
	private static boolean isReadyToExit = false;
	public boolean isRunning = true;// 正在运行
	public static ArrayList<Task> allTask = new ArrayList();//可以在全局某地方添加任务，在此执行

	//此service一直执行任务
	private void doTask(Task paramTask) {

	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	public void onCreate() {
		super.onCreate();
		this.isRunning = true;
		new Thread(this).start();
	}

	public void onDestroy() {
		super.onDestroy();
		startService(new Intent(this, MyService.class));
	}

	// 按两次退出
	public static void doubleTapToExit(Context paramContext) {
		if (!isReadyToExit) {
			isReadyToExit = true;
			Util.showShortToast(paramContext, "再按一次退出程序");
			new Timer().schedule(new TimerTask() {
				public void run() {
					MyService.isReadyToExit = false;
				}
			}, 2000L);
			return;
		}
		exitApp(paramContext);
	}

	// 退出
	public static void exitApp(Context paramContext) {
		paramContext.stopService(new Intent(
				"com.appointphoto.service.MyService"));
		Iterator localIterator = allActivity.iterator();
		while (true) {
			if (!localIterator.hasNext()) {
				System.exit(0);
				return;
			}
			((Activity) localIterator.next()).finish();
		}
		
	}

	// 杀死activity
	public static void finishActivities(String[] paramArrayOfString) {
		int i = paramArrayOfString.length;
		for (int j = 0;; j++) {
			if (j >= i)
				return;
			finishActivity(paramArrayOfString[j]);
		}
	}

	// 用activity名字杀死activity
	public static void finishActivity(String paramString) {
		Activity localActivity = getActivityByName(paramString);
		allActivity.remove(localActivity);
		localActivity.finish();
	}

	// 杀死activity
	public static void finishActivity(Activity activ){
		allActivity.remove(activ);
		activ.finish();
	}
	
	// 通过名字获取acitivity
	public static Activity getActivityByName(String paramString) {
		Iterator localIterator = allActivity.iterator();
		Activity localActivity;
		do {
			if (!localIterator.hasNext())
				return null;
			localActivity = (Activity) localIterator.next();
		} while (localActivity.getClass().getName().indexOf(paramString) < 0);
		return localActivity;
	}
	

	// 循环执行任务
	public void run() {
		if (!this.isRunning)
			return;
		while (true) {
			synchronized (allTask) {
				while (true) {
					try {
						if (allTask.size() > 0)
							doTask((Task) allTask.get(0));
						else
							break;
						if (allTask.size() > 0) {
							Thread.sleep(10000L);
						}
					} catch (Exception localException) {
					}
				}
				try {
					Thread.sleep(30000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
