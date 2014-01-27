package com.travelapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RouteDetailActivity extends Activity {
	ImageView mBackImageView;
	TextView mTitleTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routedetail);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mBackImageView = (ImageView) findViewById(R.id.imgListBack);
		mTitleTextView = (TextView) findViewById(R.id.txtListTitle);
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

}
