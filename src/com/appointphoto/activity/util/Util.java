package com.appointphoto.activity.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

public class Util {
	// 发送短消息
	public static void showShortToast(Context paramContext, String paramString) {
		if (paramString == null)
			return;
		Toast localToast = Toast.makeText(paramContext, paramString, 0);
		localToast.setGravity(17, 0, 0);
		localToast.show();
	}

	// wifi是否可用
	public static boolean isWifiNetwork(Context paramContext) {
		return ((ConnectivityManager) paramContext
				.getSystemService("connectivity")).getActiveNetworkInfo()
				.getType() == 1;
	}

	// 是否是有效的移动电话号码
	public static boolean isValidMobilePhoneNum(String paramString) {
		return Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))[0-9]{8}$")
				.matcher(paramString).matches();
	}

	// 是否是有效的电话号码
	public static boolean isValidVerificationNum(String paramString) {
		return Pattern.compile("^\\d{6}$").matcher(paramString).matches();
	}

	// 网络是否可用
	public static boolean isNetWorkAvailable(Context paramContext) {
		ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext
				.getSystemService("connectivity");
		if (localConnectivityManager == null)
			return false;
		NetworkInfo localNetworkInfo;
		localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
		if ((localNetworkInfo == null) || (!localNetworkInfo.isAvailable()))
			return false;
		return true;
	}

	// 获取状态条高度
	public static int getStatusBarHeight(Activity paramActivity) {
		int i = paramActivity.getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		int j = 0;
		if (i > 0)
			j = paramActivity.getResources().getDimensionPixelSize(i);
		return j;
	}

	// 获取圆角图片
	public static Bitmap getRoundedCornerBitmap(Bitmap paramBitmap) {
		Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(),
				paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap);
		Paint localPaint = new Paint();
		Rect localRect = new Rect(0, 0, paramBitmap.getWidth(),
				paramBitmap.getHeight());
		RectF localRectF = new RectF(localRect);
		localPaint.setAntiAlias(true);
		localCanvas.drawARGB(0, 0, 0, 0);
		localPaint.setColor(-12434878);
		localCanvas.drawRoundRect(localRectF, 10.0F, 10.0F, localPaint);
		localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
		return localBitmap;
	}

	// 获取设备高
	public static int getDeviceHeight(Activity paramActivity) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		paramActivity.getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		return localDisplayMetrics.heightPixels;
	}

	// 获取设备高去掉状态条
	public static int getDeviceHeightNoStatusBar(Activity paramActivity) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		paramActivity.getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		return localDisplayMetrics.heightPixels
				- getStatusBarHeight(paramActivity);
	}

	//获取设备宽
	public static int getDeviceWidth(Activity paramActivity) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		paramActivity.getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		return localDisplayMetrics.widthPixels;
	}

	public static int dip2px(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				* paramContext.getResources().getDisplayMetrics().density);
	}

	public static int px2dip(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				/ paramContext.getResources().getDisplayMetrics().density);
	}

	// 显示对话框
	public static void showDlg(Context paramContext, String paramString1,
			String paramString2) {
		new AlertDialog.Builder(paramContext).setTitle(paramString1)
				.setMessage(paramString2).setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						paramDialogInterface.dismiss();
					}
				}).create().show();
	}

	// 显示键盘
	public static void showKeyboard(Activity paramActivity) {
		((InputMethodManager) paramActivity.getSystemService("input_method"))
				.showSoftInputFromInputMethod(paramActivity.getCurrentFocus()
						.getWindowToken(), 1);
	}

	// 缩放图片
	public static Bitmap zoomImg(Bitmap paramBitmap, int paramInt1,
			int paramInt2) {
		int i = paramBitmap.getWidth();
		int j = paramBitmap.getHeight();
		float f = paramInt1 / i;
		if (f * j < paramInt2)
			f = paramInt2 / j;
		Matrix localMatrix = new Matrix();
		localMatrix.postScale(f, f);
		Bitmap localBitmap1 = Bitmap.createBitmap(paramBitmap, 0, 0, i, j,
				localMatrix, true);
		Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, 0,
				paramInt1, paramInt2);
		localBitmap1.recycle();
		return localBitmap2;
	}

	public static InputStream Bitmap2IS(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
		return sbs;
	}


}
