package com.travelapp;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISLocalTiledLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Envelope;

public class MapActivity extends Activity {
	ImageView mBackImageView;
	MapView mMap = null;

	ArcGISLocalTiledLayer mLocalTiledLayer;
	ArcGISTiledMapServiceLayer mTiledMapServiceLayer;
	String url = "http://cache1.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		initView();
		new AddMap().execute();
	}

	private void initView() {
		mBackImageView = (ImageView) findViewById(R.id.imgListBack);
		mMap = (MapView) findViewById(R.id.map);
		mBackImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MapActivity.this, HomeActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				MapActivity.this.startActivity(intent);
				MapActivity.this.finish();
				MapActivity.this.overridePendingTransition(
						R.anim.anim_in_left2right, R.anim.anim_out_left2right);
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMap.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMap.unpause();

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
				 * serialVersionUID = 3648431371109601053L;
				 */
				private static final long serialVersionUID = 3648431371109601053L;

				public void preAction(float arg0, float arg1, double arg2) {
					// TODO Auto-generated method stub
				}

				public void postAction(float arg0, float arg1, double arg2) {
					double mapscale = mMap.getScale();
					if (mapscale < 1155581.108577) {
						mMap.setMapBackground(0xffffffff, color.white, 0, 0);
						mMap.addLayer(mLocalTiledLayer);
					}
				}
			});
			mMap.addLayer(mTiledMapServiceLayer);
			mMap.addLayer(mLocalTiledLayer);
			return "ok";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				Toast.makeText(MapActivity.this, "成功获取地图", Toast.LENGTH_LONG)
						.show();

			}

		}
	}
}