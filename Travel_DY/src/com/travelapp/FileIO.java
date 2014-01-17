package com.travelapp;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

public class FileIO {
	/**
	 * ����һ����ǩ,��LogCat�ڱ�ʾFileIO
	 */
	private static final String TAG = "FileIO";

	/**
	 * ��Data.xml��ȡ���ݴ���sqlite3<br>
	 * ���巽������:<br>
	 * 1)�ж�ʱ����Ҫ�������� <br>
	 * 2)ʹ��pull��ʽ����XML<br>
	 * 3)����ÿһ������,Ȼ�����EventData.insertOrIgnore()�������ݿ�
	 */
	public void getDateFromXML() {
		Context mContext = TravelApplication.getContext();
		Resources mResources = mContext.getResources();

		XmlResourceParser mXmlResourceParser = mResources.getXml(R.xml.data);
		int intEventType;
		StringBuffer mStringBuffer = new StringBuffer();
		ContentValues mContentValues = new ContentValues();
		String strRowName = "";
		try {
			intEventType = mXmlResourceParser.getEventType();
			while (intEventType != XmlResourceParser.END_DOCUMENT) {
				if (intEventType == XmlResourceParser.START_TAG) {
					String tagName = mXmlResourceParser.getName().toString()
							.trim();
					if (!tagName.equals("Alldata")) {
						mStringBuffer.append(mXmlResourceParser.getName());
						if (tagName.equals("poi")) {
							mStringBuffer.append("(");
						} else {
							mStringBuffer.append(":");
							strRowName = tagName;
						}
					}
				} else if (intEventType == XmlResourceParser.END_TAG) {
					String tagName = mXmlResourceParser.getName().toString()
							.trim();
					if (tagName.equals("poi")) {
						mStringBuffer.append(")");
						Log.d(TAG, mStringBuffer.toString());
						TravelApplication.getPoiDB().insertOrIgnore(
								mContentValues);
						mStringBuffer.delete(0, mStringBuffer.length() - 1);
					} else if (tagName.equals("Alldata")) {
						Log.d(TAG, "end");
					} else {
						mStringBuffer.append(", ");
					}
				} else if (intEventType == XmlResourceParser.TEXT) {
					String tagText = mXmlResourceParser.getText().toString()
							.trim();
					mStringBuffer.append(mXmlResourceParser.getText()
							.toString().trim());
					if (strRowName.equals("id")) {
						mContentValues.put(PoiDB.C_ID, tagText);
					} else if (strRowName.equals("name")) {
						mContentValues.put(PoiDB.C_NAME, tagText);
					} else if (strRowName.equals("d_name")) {
						mContentValues.put(PoiDB.C_D_NAME, tagText);
					} else if (strRowName.equals("address")) {
						mContentValues.put(PoiDB.C_ADDRESS, tagText);
					} else if (strRowName.equals("time")) {
						mContentValues.put(PoiDB.C_TIME, tagText);
					} else if (strRowName.equals("ticket")) {
						mContentValues.put(PoiDB.C_PRICE, tagText);
					} else if (strRowName.equals("type")) {
						mContentValues.put(PoiDB.C_TYPE, tagText);
					} else if (strRowName.equals("tele")) {
						mContentValues.put(PoiDB.C_TELE, tagText);
					} else if (strRowName.equals("abstract")) {
						mContentValues.put(PoiDB.C_ABSTRACT, tagText);
					} else if (strRowName.equals("c_id")) {
						mContentValues.put(PoiDB.C_C_ID, tagText);
					}
				}
				intEventType = mXmlResourceParser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
