package com.appointphoto.test;

import com.appointphoto.activity.MainActivity;

import android.content.Intent;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.Button;

public class SampleTest extends InstrumentationTestCase {
	private MainActivity sample = null;

	@Override
	protected void setUp() {
		try {
			super.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Intent intent = new Intent();
		intent.setClassName("com.hustophone.sample",
				MainActivity.class.getName());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sample = (MainActivity) getInstrumentation().startActivitySync(intent);
	}

	/*
	 * 垃圾清理与资源回收
	 * 
	 * @see android.test.InstrumentationTestCase#tearDown()
	 */
	@Override
	protected void tearDown() {
		sample.finish();
		try {
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 活动功能测试
	 */
	public void testActivity() throws Exception {
		Log.v("testActivity", "test the Activity");
		SystemClock.sleep(1500);
//		getInstrumentation().runOnMainSync(new PerformClick(button));
		SystemClock.sleep(3000);
//		assertEquals("Hello Android", text.getText().toString());
	}

	/*
	 * 模拟按钮点击的接口
	 */
	private class PerformClick implements Runnable {
		Button btn;

		public PerformClick(Button button) {
			btn = button;
		}

		public void run() {
			btn.performClick();
		}
	}

	/*
	 * 测试类中的方法
	 */
	public void testAdd() throws Exception {
		String tag = "testAdd";
		Log.v(tag, "test the method");
//		int test = sample.add(1, 1);
//		assertEquals(2, test);
	}
}
