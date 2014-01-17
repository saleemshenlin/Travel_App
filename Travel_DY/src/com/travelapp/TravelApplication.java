package com.travelapp;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class TravelApplication extends Application {
	/**
	 * ����һ����ǩ,��LogCat�ڱ�ʾLBSApplication
	 */
	private final static String TAG = "TravelApplication";
	/**
	 * ����һ������,���ڱ�ʾ��Ļ���
	 */
	private static int SCREENWIDTH;
	/**
	 * ����һ������,���ڱ�ʾ��Ļ�߶�
	 */
	private static int SCREENHEIGHT;
	/**
	 * ����һ������,���ڱ�ʾDPI
	 */
	private static double SCREENDPI;
	/**
	 * ����һ������,���ڱ�ʾ������
	 */
	private static Context CONTEXT;
	/**
	 * ʵ��һ��mEventData
	 */
	private static PoiDB mPoiDB;

	public static Context getContext() {
		return CONTEXT;
	}

	public static PoiDB getPoiDB() {
		mPoiDB = new PoiDB(getContext());
		return mPoiDB;
	}


	/**
	 * ����LbsApplication<br>
	 * 1)��ȡ������,��ֵ��CONTEXT<br>
	 * 2)��ȡ��Ļ�ֱ���<br>
	 * 3)��ʼ��SuperMap����<br>
	 * 4)��ʼ��mPoint2d��LOCATIONACCUCRACY<br>
	 * 
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CONTEXT = getApplicationContext();
		Log.i(TAG, "LBSApplication onCreate!");
		getScreenDesplay();
		Log.i(TAG, "LBSApplication getScreenDisplay height:" + SCREENHEIGHT);
	}

	/**
	 * ���ڻ�ȡ��Ļ�ֱ���
	 */
	private void getScreenDesplay() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		setScreenWidth(dm.widthPixels);
		setScreenHeight(dm.heightPixels);
		setScreenDPI(dm.densityDpi);
	}

	public static int getScreenWidth() {
		return SCREENWIDTH;
	}

	public static void setScreenWidth(float xdpi) {
		TravelApplication.SCREENWIDTH = (int) xdpi;
	}

	public static int getScreenHeight() {
		return SCREENHEIGHT;
	}

	public static void setScreenHeight(float ydpi) {
		TravelApplication.SCREENHEIGHT = (int) ydpi;
	}

	public static double getScreenDPI() {
		return SCREENDPI;
	}

	public static void setScreenDPI(double screenDPI) {
		TravelApplication.SCREENDPI = screenDPI;
	}
}
