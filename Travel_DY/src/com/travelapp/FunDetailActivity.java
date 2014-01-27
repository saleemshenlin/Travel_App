package com.travelapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FunDetailActivity extends Activity {
	ImageView mBackImageView;
	ImageView mItemImageView;
	TextView mTitleTextView;
	TextView mItemPrice;
	LinearLayout mLinearLayout;
	TextView mItemAddress;
	TextView mItemTele;
	TextView mItemAbstract;
	ImageView mMapImageView;
	Cursor mItemCursor = null;
	PoiProvider mPoiProvider;
	Intent mIntent;
	Bundle mBundle;
	String mPoiId;
	String mFrom;
	Resources mResources;
	Query mQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		mIntent = getIntent();
		mBundle = mIntent.getExtras();
		mPoiId = String.valueOf(mBundle.getLong("ID"));
		mFrom = "Detail";
		mResources = this.getResources();
		initView();
		getPOI(mPoiId);
	}

	private void initView() {
		mBackImageView = (ImageView) findViewById(R.id.imgListBack);
		mMapImageView = (ImageView) findViewById(R.id.imgListMap);
		mItemImageView = (ImageView) findViewById(R.id.imgItem);
		mTitleTextView = (TextView) findViewById(R.id.txtListTitle);
		mItemPrice = (TextView) findViewById(R.id.txtItemPrice);
		mLinearLayout = (LinearLayout) findViewById(R.id.linTime);
		mLinearLayout.setVisibility(View.GONE);
		mItemAddress = (TextView) findViewById(R.id.txtItemAddress);
		mItemTele = (TextView) findViewById(R.id.txtItemTele);
		mItemAbstract = (TextView) findViewById(R.id.txtItemAbstract);
		mPoiProvider = new PoiProvider();
		if (mBundle.getString("FROM") != null) {
			mFrom = mBundle.getString("FROM");
		}
		if (mFrom.equals("MapActivity")) {
			mBackImageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(FunDetailActivity.this,
							MapActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					FunDetailActivity.this.startActivity(intent);
					FunDetailActivity.this.finish();
					FunDetailActivity.this.overridePendingTransition(
							R.anim.anim_in_left2right,
							R.anim.anim_out_left2right);
				}
			});
		} else if (mFrom.equals("ListActivity")) {
			mBackImageView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(FunDetailActivity.this,
							MapActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("FUNCTION", "POIS");
					intent.putExtra("TYPE", 4);
					FunDetailActivity.this.startActivity(intent);
					FunDetailActivity.this.finish();
					FunDetailActivity.this.overridePendingTransition(
							R.anim.anim_in_left2right,
							R.anim.anim_out_left2right);
				}
			});
		} else {
			mBackImageView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(FunDetailActivity.this,
							FunListActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					FunDetailActivity.this.startActivity(intent);
					FunDetailActivity.this.finish();
					FunDetailActivity.this.overridePendingTransition(
							R.anim.anim_in_left2right,
							R.anim.anim_out_left2right);
				}
			});
		}
		mMapImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FunDetailActivity.this,
						MapActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("FUNCTION", "POI");
				intent.putExtra("TYPE", Integer.parseInt(mPoiId));
				FunDetailActivity.this.startActivity(intent);
				FunDetailActivity.this.finish();
				FunDetailActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
		});
		if (TravelApplication.isFromMap) {
			mMapImageView.setVisibility(View.GONE);
			TravelApplication.isFromMap = false;
		}
	}

	private void getPOI(String id) {
		final Uri queryUri = Uri.parse(PoiProvider.CONTENT_URI.toString() + "/"
				+ id);
		mItemCursor = mPoiProvider.query(queryUri, null, null, null, null);
		try {
			if (mItemCursor.moveToFirst()) {
				mTitleTextView.setText(mItemCursor.getString(mItemCursor
						.getColumnIndex(PoiDB.C_NAME)));
				mItemPrice.setText("时间："
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

}
