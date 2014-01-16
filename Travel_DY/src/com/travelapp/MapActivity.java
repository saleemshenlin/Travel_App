package com.travelapp;

import android.R.color;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISLocalTiledLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Envelope;

public class MapActivity extends Activity {
	ActionBar mActionBar;
	private MapView mMap = null;
	ArcGISLocalTiledLayer mLocalTiledLayer;
	ArcGISTiledMapServiceLayer mTiledMapServiceLayer;
	
	String url="http://cache1.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
        mMap = (MapView)findViewById(R.id.map);
        mTiledMapServiceLayer= new ArcGISTiledMapServiceLayer(url);
        mLocalTiledLayer = new ArcGISLocalTiledLayer("file:///mnt/sdcard/mnt/sdcard/daqingcache/Layers");
        mMap.addLayer(mTiledMapServiceLayer);
        mMap.addLayer(mLocalTiledLayer);
        mMap.setExtent(new Envelope(13570407.0434979, 5681967.05272005, 14203165.9874021, 6017039.55107995),0);
        mMap.setScale(2311162.217155);
        mMap.setMaxResolution(611.49622628138);
        mMap.setMinResolution(9.55462853563415);
        mMap.setOnZoomListener(new OnZoomListener() {
			public void preAction(float arg0, float arg1, double arg2) {
				// TODO Auto-generated method stub	
			}	
			public void postAction(float arg0, float arg1, double arg2) {
				double mapscale=mMap.getScale();
				if (mapscale < 1155581.108577) {
                  mMap.setMapBackground(0xffffffff, color.white, 0, 0);
                  mMap.addLayer(mLocalTiledLayer);
				}
			}
		});      
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.anim_out_left2right,
					R.anim.anim_in_right2left);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
		@Override
		protected void onPause() {
			super.onPause();
			mMap.pause();
	 }
		@Override
		protected void onResume(){
			super.onResume();
			mMap.unpause();
			
		}
     }
