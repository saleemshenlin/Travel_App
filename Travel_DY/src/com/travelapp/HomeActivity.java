package com.travelapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends Activity {

	private ImageView mMapImageView;
	private ImageView mScenicImageView;
	private ImageView mHotelImageView;
	private ImageView mRestImageView;
	private ImageView mFunImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		InitView();
	}

	private void InitView() {
		mMapImageView = (ImageView) findViewById(R.id.imgItemMap);
		mScenicImageView = (ImageView) findViewById(R.id.imgItemScenic);
		mHotelImageView = (ImageView) findViewById(R.id.imgItemHotel);
		mFunImageView = (ImageView) findViewById(R.id.imgItemFun);
		mRestImageView = (ImageView) findViewById(R.id.imgItemRest);
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
				Intent intent = new Intent(HomeActivity.this,
						ScenicListActivity.class);
				HomeActivity.this.startActivity(intent);
				HomeActivity.this.finish();
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
		});
		mHotelImageView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this,
						HotelListActivity.class);
				HomeActivity.this.startActivity(intent);
				HomeActivity.this.finish();
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
		});
		mRestImageView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this,
						RestListActivity.class);
				HomeActivity.this.startActivity(intent);
				HomeActivity.this.finish();
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
		});
		mFunImageView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this,
						FunListActivity.class);
				HomeActivity.this.startActivity(intent);
				HomeActivity.this.finish();
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
		});
	}

}
