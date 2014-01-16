package com.example.travel_app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class ScenicListActivity extends Activity {
	ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mActionBar = getActionBar();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sceniclist);
		mActionBar.setDisplayHomeAsUpEnabled(true);
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
}
