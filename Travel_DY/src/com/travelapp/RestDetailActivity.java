package com.travelapp;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISLocalTiledLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Envelope;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RestDetailActivity extends Activity {
	ImageView mBackImageView;
	ImageView mItemImageView;
	TextView mTitleTextView;
	TextView mItemPrice;
	TextView mItemTime;
	TextView mItemAddress;
	TextView mItemTele;
	TextView mItemAbstract;
	ImageView mMapImageView;
	Cursor mItemCursor = null;
	PoiProvider mPoiProvider;
	Intent mIntent;
	Bundle mBundle;
	String mPoiId;
	Resources mResources;
	RelativeLayout mRelativeLayout;
	MapView mMap = null;
	GraphicsLayer mGraphicsLayer;
	ArcGISLocalTiledLayer mLocalTiledLayer;
	ArcGISTiledMapServiceLayer mTiledMapServiceLayer;
	String url = "http://cache1.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer";
	private static boolean isMap = false;
	Query mQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		mIntent = getIntent();
		mBundle = mIntent.getExtras();
		mPoiId = String.valueOf(mBundle.getLong("ID"));
		mResources = this.getResources();
		initView();
		getPOI(mPoiId);
		new AddMap().execute();
	}

	private void initView() {
		mBackImageView = (ImageView) findViewById(R.id.imgListBack);
		mMapImageView = (ImageView) findViewById(R.id.imgListMap);
		mItemImageView = (ImageView) findViewById(R.id.imgItem);
		mTitleTextView = (TextView) findViewById(R.id.txtListTitle);
		mItemPrice = (TextView) findViewById(R.id.txtItemPrice);
		mItemTime = (TextView) findViewById(R.id.txtItemTime);
		mItemAddress = (TextView) findViewById(R.id.txtItemAddress);
		mItemTele = (TextView) findViewById(R.id.txtItemTele);
		mItemAbstract = (TextView) findViewById(R.id.txtItemAbstract);
		mRelativeLayout = (RelativeLayout) findViewById(R.id.relMapView);
		mRelativeLayout.setVisibility(View.GONE);
		mMap = (MapView) findViewById(R.id.mapDetailView);
		mPoiProvider = new PoiProvider();
		mBackImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RestDetailActivity.this,
						RestListActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				RestDetailActivity.this.startActivity(intent);
				RestDetailActivity.this.finish();
				RestDetailActivity.this.overridePendingTransition(
						R.anim.anim_in_left2right, R.anim.anim_out_left2right);
			}
		});
		mMapImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isMap) {
					mRelativeLayout.setVisibility(View.VISIBLE);
					isMap = true;
				} else {
					mRelativeLayout.setVisibility(View.GONE);
					isMap = false;
				}
			}
		});
	}

	private void getPOI(String id) {
		final Uri queryUri = Uri.parse(PoiProvider.CONTENT_URI.toString() + "/"
				+ id);
		mItemCursor = mPoiProvider.query(queryUri, null, null, null, null);
		try {
			if (mItemCursor.moveToFirst()) {
				mTitleTextView.setText(mItemCursor.getString(mItemCursor
						.getColumnIndex(PoiDB.C_NAME)));
				mItemPrice.setText("人均消费："
						+ mItemCursor.getString(mItemCursor
								.getColumnIndex(PoiDB.C_PRICE)));
				mItemTime.setText("时间："
						+ mItemCursor.getString(mItemCursor
								.getColumnIndex(PoiDB.C_TIME)));
				mItemAddress.setText("地址："
						+ mItemCursor.getString(mItemCursor
								.getColumnIndex(PoiDB.C_ADDRESS)));
				mItemTele.setText("电话："
						+ mItemCursor.getString(mItemCursor
								.getColumnIndex(PoiDB.C_TELE)));
				mItemAbstract.setText("简介："
						+ mItemCursor.getString(mItemCursor
								.getColumnIndex(PoiDB.C_ABSTRACT)));
				String name = "img_0" + id;

				int imgId = mResources.getIdentifier(name, "drawable",
						"com.travelapp");
				Drawable mDrawable = mResources.getDrawable(imgId);
				mItemImageView.setImageDrawable(mDrawable);
			}
		} catch (Exception e) {
			Log.e("Detail", e.toString());
		} finally {
			if (mItemCursor != null) {
				mItemCursor.close();
			}
			TravelApplication.getPoiDB().closeDatabase();
		}

	}

	public GraphicsLayer addGraphicToLayer() {
		GraphicsLayer mLayer = new GraphicsLayer();
		// create a simple marker symbol to be used by our graphic
		mQuery = new Query();
		mLayer = mQuery.getPoisById(TravelApplication.getContext(), mPoiId);
		return mLayer;

	}

	class AddMap extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			mTiledMapServiceLayer = new ArcGISTiledMapServiceLayer(url);
			mLocalTiledLayer = new ArcGISLocalTiledLayer(
					"file:///mnt/sdcard/mnt/sdcard/daqingcache/Layers");
			mMap.setExtent(new Envelope(13570407.0434979, 5681967.05272005,
					14203165.9874021, 6017039.55107995), 0);
			mMap.setScale(2311162.217155);
			mMap.setMaxResolution(611.49622628138);
			mMap.setMinResolution(9.55462853563415);
			mMap.setOnZoomListener(new OnZoomListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void preAction(float pivotX, float pivotY, double factor) {
					// TODO Auto-generated method stub

				}

				@Override
				public void postAction(float pivotX, float pivotY, double factor) {
					// TODO Auto-generated method stub
					double mapscale = mMap.getScale();
					if (mapscale < 1155581.108577) {
						mMap.setMapBackground(0xffffffff, Color.WHITE, 0, 0);
						mMap.addLayer(mLocalTiledLayer);
						mMap.addLayer(mGraphicsLayer);
					}
				}
			});
			mMap.addLayer(mTiledMapServiceLayer);
			mMap.addLayer(mLocalTiledLayer);
			mGraphicsLayer = addGraphicToLayer();
			mMap.addLayer(mGraphicsLayer);
			return "ok";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				Log.d("ScenicDetail", "Map ok!");
			}

		}
	}
}
