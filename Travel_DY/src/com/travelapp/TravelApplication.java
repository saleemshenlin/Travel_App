package com.travelapp;

import android.app.Application;
import android.content.Context;

public class TravelApplication extends Application {
	/**
	 * ����һ����ǩ,��LogCat�ڱ�ʾLBSApplication
	 */
	private final static String TAG = "TravelApplication";
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
}
