package com.travelapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

public class HomeActivity extends Activity {

	private ImageView mMapImageView;
	private ImageView mScenicImageView;
	private ImageView mHotelImageView;
	private ImageView mRestImageView;
	private ImageView mFunImageView;
	private ImageView mRouteImageView;
	private ImageView mSocialImageView;
	private IWXAPI mIwxapi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		initWX();
	}

	private void initView() {
		mMapImageView = (ImageView) findViewById(R.id.imgItemMap);
		mScenicImageView = (ImageView) findViewById(R.id.imgItemScenic);
		mHotelImageView = (ImageView) findViewById(R.id.imgItemHotel);
		mFunImageView = (ImageView) findViewById(R.id.imgItemFun);
		mRestImageView = (ImageView) findViewById(R.id.imgItemRest);
		mRouteImageView = (ImageView) findViewById(R.id.imgItemRoute);
		mSocialImageView = (ImageView) findViewById(R.id.imgItemSocial);
		mMapImageView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this, MapActivity.class);
				HomeActivity.this.startActivity(intent);
				HomeActivity.this.finish();
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
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
		mRouteImageView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this,
						RouteListActivity.class);
				HomeActivity.this.startActivity(intent);
				HomeActivity.this.finish();
				HomeActivity.this.overridePendingTransition(
						R.anim.anim_in_right2left, R.anim.anim_out_right2left);
			}
		});
		mSocialImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final EditText editor = new EditText(HomeActivity.this);
				editor.setLayoutParams(new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.FILL_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
				editor.setText(R.string.app_name);
				String text = "����";

				// ��ʼ��һ��WXTextObject����
				WXTextObject textObj = new WXTextObject();
				textObj.text = text;

				// ��WXTextObject�����ʼ��һ��WXMediaMessage����
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = textObj;
				// �����ı����͵���Ϣʱ��title�ֶβ�������
				// msg.title = "Will be ignored";
				msg.description = text;

				// ����һ��Req
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = buildTransaction("text"); // transaction�ֶ�����Ψһ��ʶһ������
				req.message = msg;
				// req.scene = isTimelineCb.isChecked() ?
				// SendMessageToWX.Req.WXSceneTimeline
				// : SendMessageToWX.Req.WXSceneSession;
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				// ����api�ӿڷ������ݵ�΢��
				boolean isSend = mIwxapi.sendReq(req);
				if (isSend) {
					Log.e("WX", "OK");
				} else {
					Log.e("WX", "Error");
				}
			}
		});
	}

	public void initWX() {
		mIwxapi = WXAPIFactory.createWXAPI(HomeActivity.this,
				HomeActivity.this.getString(R.string.wx_app_id), false);
		boolean isRegist = mIwxapi.registerApp(HomeActivity.this
				.getString(R.string.wx_app_id));
		if (isRegist) {
			Log.e("WX", "OK");
		} else {
			Log.e("WX", "Error");
		}
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}
}
