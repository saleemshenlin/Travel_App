package com.travelapp;

public class Query {
	/**
	 * ����һ����ǩ,��LogCat�ڱ�ʾEventData
	 */
	private static final String TAG = "Query";
	/**
	 * ����ʵ������EventProvider
	 */
	private PoiProvider mPoiProvider = new PoiProvider();

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
}
