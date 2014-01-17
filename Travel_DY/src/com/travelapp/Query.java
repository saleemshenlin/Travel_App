package com.travelapp;


public class Query {
	/**
	 * 定义一个标签,在LogCat内表示EventData
	 */
	private static final String TAG = "Query";
	/**
	 * 用于实例化类EventProvider
	 */
	private PoiProvider mPoiProvider = new PoiProvider();

	/**
	 * 用于设置查询的排序条件<br>
	 * 按Event发生时间的升序排序 <br>
	 * 
	 * @return String 排序条件
	 */
	public String getSortOrder(String mString) {
		String strSQL = null;
		strSQL = mString + " ASC";
		return strSQL;
	}

	/**
	 * 用于Event根据查询类型设置查询条件<br>
	 * 类型 0 学术讲座 1 电影演出 2 精品课程 3 我关注的<br>
	 * 时间设置为一周,从查询当天开始算<br>
	 * 
	 * @param intIndex
	 *            查询类型
	 * @return String 查询条件
	 */
	public String getSectionViaType(int intIndex) {
		String strSQL;
		switch (intIndex) {
		case 0:
			strSQL = "";
			return strSQL;
		case 1:
			strSQL = "";
			return strSQL;
		case 2:
			strSQL = "";
			return strSQL;
		case 3:
			strSQL = "";
			return strSQL;
		default:
			return null;
		}
	}
}
