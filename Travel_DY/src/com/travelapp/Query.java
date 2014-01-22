package com.travelapp;

import android.database.Cursor;
import android.graphics.Color;

import com.esri.android.map.GraphicsLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.MultiPath;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;

public class Query {
	/**
	 * ����һ����ǩ,��LogCat�ڱ�ʾEventData
	 */
	private static final String TAG = "Query";
	/**
	 * ����ʵ������EventProvider
	 */
	private PoisProvider mPoisProvider = new PoisProvider();
	Cursor mItemCursor = null;

	/**
	 * �������ò�ѯ����������<br>
	 * ��Event����ʱ����������� <br>
	 * 
	 * @return String ��������
	 */
	public String getSortOrder(String mString) {
		String strSQL = null;
		strSQL = mString + " ASC";
		return strSQL;
	}

	/**
	 * ����Event���ݲ�ѯ�������ò�ѯ����<br>
	 * ���� 0 ѧ������ 1 ��Ӱ�ݳ� 2 ��Ʒ�γ� 3 �ҹ�ע��<br>
	 * ʱ������Ϊһ��,�Ӳ�ѯ���쿪ʼ��<br>
	 * 
	 * @param intIndex
	 *            ��ѯ����
	 * @return String ��ѯ����
	 */
	public String getSectionViaType(int intIndex) {
		String strSQL;
		switch (intIndex) {
		case 1:
			strSQL = PoiDB.C_C_ID + " = '01'";
			return strSQL;
		case 2:
			strSQL = PoiDB.C_C_ID + " = '02'";
			return strSQL;
		case 3:
			strSQL = PoiDB.C_C_ID + " = '03'";
			return strSQL;
		case 4:
			strSQL = PoiDB.C_C_ID + " = '04'";
			return strSQL;
		default:
			return null;
		}
	}

	public static Geometry WKTToGeometry(String wkt) {
		Geometry geo = null;
		if (wkt == null || wkt == "") {
			return null;
		}
		String headStr = wkt.substring(0, wkt.indexOf("("));
		String temp = wkt.substring(wkt.indexOf("(") + 1, wkt.lastIndexOf(")"));
		if (headStr.equals("POINT")) {
			String[] values = temp.split(" ");
			geo = new Point(Double.valueOf(values[0]),
					Double.valueOf(values[1]));
		} else if (headStr.equals("POLYGON") || headStr.equals("Polygon")) {
			geo = parseWKT(temp, headStr);
		} else if (headStr.equals("Envelope")) {
			String[] extents = temp.split(",");
			geo = new Envelope(Double.valueOf(extents[0]),
					Double.valueOf(extents[1]), Double.valueOf(extents[2]),
					Double.valueOf(extents[3]));
		} else if (headStr.equals("MultiPoint")) {
		} else {
			return null;
		}
		return geo;
	}

	private static Geometry parseWKT(String multipath, String type) {
		String subMultipath = multipath.substring(1, multipath.length() - 1);
		String[] paths;
		if (subMultipath.indexOf("),(") >= 0) {
			paths = subMultipath.split("),(");// ������ζ�����ַ���
		} else {
			paths = new String[] { subMultipath };
		}
		Point startPoint = null;
		MultiPath path = null;
		if (type.equals("POLYGON")) {
			path = new Polyline();
		} else {
			path = new Polygon();
		}
		for (int i = 0; i < paths.length; i++) {
			String[] points = paths[i].split(",");
			startPoint = null;
			for (int j = 0; j < points.length; j++) {
				String[] pointStr = points[j].split(" ");
				if (startPoint == null) {
					startPoint = new Point(Double.valueOf(pointStr[0]),
							Double.valueOf(pointStr[1]));
					path.startPath(startPoint);
				} else {
					path.lineTo(new Point(Double.valueOf(pointStr[0]), Double
							.valueOf(pointStr[1])));
				}
			}
		}
		return path;
	}

	public GraphicsLayer getPois() {
		GraphicsLayer mGraphicsLayer = new GraphicsLayer();
		mItemCursor = mPoisProvider.query(PoisProvider.CONTENT_URI, null, null,
				null, this.getSortOrder(PoiDB.C_ID));
		mItemCursor.moveToFirst();
		while (mItemCursor.moveToNext()) {
			String WKT = mItemCursor.getString(mItemCursor
					.getColumnIndex(PoiDB.C_SHAPE));
			Point mPoint = (Point) Query.WKTToGeometry(WKT);
			SimpleMarkerSymbol sms = new SimpleMarkerSymbol(Color.RED, 5,
					STYLE.CIRCLE);
			Graphic mGraphic = new Graphic(mPoint, sms);
			mGraphicsLayer.addGraphic(mGraphic);
		}
		return mGraphicsLayer;

	}
}
