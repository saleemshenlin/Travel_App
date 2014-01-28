package com.travelapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RouteDetailActivity extends Activity {
	ImageView mBackImageView;
	TextView mTitleTextView;
	TextView mRouteTitle;
	Intent mIntent;
	Bundle mBundle;
	Cursor mItemCursor;
	ListView mListView;
	Resources mResources;
	List<Route> mList;
	int mRouteID;
	static String TAG = "RouteDetailActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routedetail);
		mIntent = getIntent();
		mBundle = mIntent.getExtras();
		mRouteID = mBundle.getInt("ID");
		mResources = this.getResources();
		initView();
		mList = new ArrayList<Route>();
		String mPOIs = getRoute(mRouteID);
		getRoutePoi(mPOIs);
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mBackImageView = (ImageView) findViewById(R.id.imgListBack);
		mTitleTextView = (TextView) findViewById(R.id.txtListTitle);
		mRouteTitle = (TextView) findViewById(R.id.txtRouteTitle);
		mListView = (ListView) findViewById(R.id.listRoute);
		mTitleTextView.setText("Â·ÏßÏêÇé");
		mBackImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RouteDetailActivity.this,
						RouteListActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				RouteDetailActivity.this.startActivity(intent);
				RouteDetailActivity.this.finish();
				RouteDetailActivity.this.overridePendingTransition(
						R.anim.anim_in_left2right, R.anim.anim_out_left2right);
			}
		});
	}

	private String getRoute(int routeid) {
		String mRoutePOi = null;
		final Uri queryUri = Uri.parse(RouteProvider.CONTENT_URI.toString()
				+ "/" + routeid);
		RouteProvider mRouteProvider = new RouteProvider();
		mItemCursor = mRouteProvider.query(queryUri, null, null, null, null);
		try {
			if (mItemCursor.moveToFirst()) {
				mRouteTitle.setText(mItemCursor.getString(mItemCursor
						.getColumnIndex(PoiDB.C_NAME)));
				mRoutePOi = mItemCursor.getString(mItemCursor
						.getColumnIndex(PoiDB.C_ABSTRACT));
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		} finally {
			if (mItemCursor.isClosed()) {
				mItemCursor.close();
			}
			TravelApplication.getPoiDB().closeDatabase();
		}
		return mRoutePOi;
	}

	private void getRoutePoi(String pois) {
		String[] mPois = pois.split(";");
		for (String poi : mPois) {
			final Uri queryUri = Uri.parse(RouteProvider.CONTENT_URI.toString()
					+ "/" + poi.substring(1));
			PoiProvider mPoiProvider = new PoiProvider();
			mItemCursor = mPoiProvider.query(queryUri, null, null, null, null);
			try {
				if (mItemCursor.moveToFirst()) {
					String mImg = "img_0" + poi.substring(1);
					String mName = mItemCursor.getString(mItemCursor
							.getColumnIndex(PoiDB.C_NAME));
					String mAbstract = mItemCursor.getString(mItemCursor
							.getColumnIndex(PoiDB.C_ABSTRACT));
					Route mRoute = new Route();
					mRoute.IMG = mImg;
					mRoute.NAME = mName;
					mRoute.ABSTRACT = mAbstract;
					mList.add(mRoute);
				}
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			} finally {
				if (mItemCursor.isClosed()) {
					mItemCursor.close();
				}
				TravelApplication.getPoiDB().closeDatabase();
			}
		}
	}

	private void initData() {
		RouteListAdapter mAdapter = new RouteListAdapter(this,
				R.layout.route_row, mList);
		mListView.setAdapter(mAdapter);
	}

	public class Route {
		String NAME;
		String IMG;
		String ABSTRACT;
	}

	public class RouteListAdapter extends ArrayAdapter<Route> {
		private int resourceId;

		public RouteListAdapter(Context context, int textViewResourceId,
				List<Route> objects) {
			super(context, textViewResourceId, objects);
			this.resourceId = textViewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Route mRoute = getItem(position);
			LinearLayout routeListItem = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					inflater);
			vi.inflate(resourceId, routeListItem, true);
			TextView mRouteTitle = (TextView) routeListItem
					.findViewById(R.id.txtRouteRowTitle);
			// TextView mRouteAbastract = (TextView) routeListItem
			// .findViewById(R.id.txtRouteRow);
			// ImageView mRouteImage = (ImageView) routeListItem
			// .findViewById(R.id.imgRouteRow);
			mRouteTitle.setText(mRoute.NAME);
			// mRouteAbastract.setText(mRoute.ABSTRACT);
			// int imgId = mResources.getIdentifier(mRoute.IMG, "drawable",
			// "com.travelapp");
			// Drawable mDrawable = mResources.getDrawable(imgId);
			// mRouteImage.setImageDrawable(mDrawable);
			return routeListItem;
		}
	}
}
