package com.travelapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends Activity {
	
	private ImageView mMapImageView;
	private ImageView mScenicImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		InitView();
	}

	private void InitView() {
		mMapImageView = (ImageView) findViewById(R.id.imgItemMap);
		mScenicImageView = (ImageView) findViewById(R.id.imgItemScenic);
		mMapImageView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this, MapActivity.class);
				HomeActivity.this.startActivity(intent);
				HomeActivity.this.finish();
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_left2right);
			}
		});
		mScenicImageView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this, ScenicListActivity.class);
				HomeActivity.this.startActivity(intent);
				HomeActivity.this.finish();
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_left2right);
			}
		});
	}

}
