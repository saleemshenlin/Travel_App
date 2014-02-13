package com.travelapp;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;

public class SocialActivity extends Activity implements OnClickListener {
	ImageView mBackImageView;
	TextView mTitleTextView;
	static ImageView mCameraImageView;
	ImageView mLeft;
	ImageView mRight;
	String imageFilePath;
	static Bitmap mCamera;
	EditText mEditText;
	LinearLayout mLinearLayout;
	Button mShareButton;

	int CAMERA_RESULT = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social);
		initView();
		initCamera();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// 如果拍照成功
		if (resultCode == RESULT_OK) {
			// 取得屏幕的显示大小
			Display currentDisplay = getWindowManager().getDefaultDisplay();
			int dw = currentDisplay.getWidth();
			int dh = currentDisplay.getHeight();

			// 对拍出的照片进行缩放
			BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
			bmpFactoryOptions.inJustDecodeBounds = true;
			mCamera = BitmapFactory
					.decodeFile(imageFilePath, bmpFactoryOptions);
			int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
					/ (float) dh);
			int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
					/ (float) dw);

			if (heightRatio > 1 && widthRatio > 1) {

				if (heightRatio > widthRatio) {

					bmpFactoryOptions.inSampleSize = heightRatio;
				} else {
					bmpFactoryOptions.inSampleSize = widthRatio;
				}

			}

			bmpFactoryOptions.inJustDecodeBounds = false;
			mCamera = BitmapFactory
					.decodeFile(imageFilePath, bmpFactoryOptions);

			mCameraImageView.setImageBitmap(mCamera);
		} else if ((resultCode == RESULT_CANCELED)) {
			Intent intent = new Intent(SocialActivity.this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			SocialActivity.this.startActivity(intent);
			SocialActivity.this.finish();
			SocialActivity.this.overridePendingTransition(
					R.anim.anim_in_left2right, R.anim.anim_out_left2right);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.linContent) {
			InputMethodManager imm = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			mEditText.setCursorVisible(false);// 失去光标
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	private void initCamera() {
		// TODO Auto-generated method stub
		imageFilePath = SocialActivity.this.getExternalFilesDir(null)
				+ "/mypicture.jpg";
		File imageFile = new File(imageFilePath);
		Uri imageFileUri = Uri.fromFile(imageFile);

		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);

		startActivityForResult(i, CAMERA_RESULT);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mBackImageView = (ImageView) findViewById(R.id.imgListBack);
		mTitleTextView = (TextView) findViewById(R.id.txtListTitle);
		mLeft = (ImageView) findViewById(R.id.imgLeft);
		mRight = (ImageView) findViewById(R.id.imgRight);
		mCameraImageView = (ImageView) findViewById(R.id.imgCamera);
		mEditText = (EditText) findViewById(R.id.editMeg);
		mLinearLayout = (LinearLayout) findViewById(R.id.linContent);
		mLinearLayout.setOnClickListener(this);
		mTitleTextView.setText("分享");
		mShareButton = (Button) findViewById(R.id.btnShare);
		mBackImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mCamera != null) {
					mCamera.recycle();
				}
				Intent intent = new Intent(SocialActivity.this,
						HomeActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				SocialActivity.this.startActivity(intent);
				SocialActivity.this.finish();
				SocialActivity.this.overridePendingTransition(
						R.anim.anim_in_left2right, R.anim.anim_out_left2right);
			}
		});
		mRight.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCameraImageView.setDrawingCacheEnabled(true);
				Bitmap mBitmap = mCameraImageView.getDrawingCache();
				if (mBitmap != null) {
					mCameraImageView.setImageBitmap(rotateImage(mBitmap, 0));
				}
				mBitmap.recycle();
				mCameraImageView.setDrawingCacheEnabled(false);
			}
		});
		mLeft.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCameraImageView.setDrawingCacheEnabled(true);
				Bitmap mBitmap = mCameraImageView.getDrawingCache();
				if (mBitmap != null) {
					mCameraImageView.setImageBitmap(rotateImage(mBitmap, 1));
				}
				mBitmap.recycle();
				mCameraImageView.setDrawingCacheEnabled(false);
			}
		});
		mShareButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendWXMeg();
				Toast.makeText(SocialActivity.this, "分享成功", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	public static Bitmap rotateImage(Bitmap bgimage, int direction) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 旋转图片
		switch (direction) {
		case 0:
			matrix.postRotate(90);
			break;
		case 1:
			matrix.postRotate(-90);
		default:
			break;
		}
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		mCamera = Bitmap.createBitmap(mCamera, 0, 0, (int) mCamera.getWidth(),
				(int) mCamera.getHeight(), matrix, true);
		return bitmap;
	}

	public void sendWXMeg() {
		String text = mEditText.getText().toString();
		if (text == null || text.equals("")) {
			text = mEditText.getHint().toString();
		}
		// 初始化一个WXImageObject对象
		mCameraImageView.setDrawingCacheEnabled(true);

		WXImageObject imgObj = new WXImageObject(mCamera);

		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;
		msg.title = "Trvael_app";
		msg.thumbData = getBitmapBytes(mCamera, false); // 设置缩略图

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;

		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		// 调用api接口发送数据到微信
		boolean isSend = TravelApplication.mIwxapi.sendReq(req);
		if (isSend) {
			Log.e("WX", "OK");
		} else {
			Log.e("WX", "Error");
		}
		mCameraImageView.setDrawingCacheEnabled(false);
	}

	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
		Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
		Canvas localCanvas = new Canvas(localBitmap);
		int i;
		int j;
		if (bitmap.getHeight() > bitmap.getWidth()) {
			i = bitmap.getWidth();
			j = bitmap.getWidth();
		} else {
			i = bitmap.getHeight();
			j = bitmap.getHeight();
		}
		mCameraImageView.setDrawingCacheEnabled(true);
		while (true) {
			localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,
					80, 80), null);
			if (paramBoolean)
				bitmap.recycle();
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					localByteArrayOutputStream);
			localBitmap.recycle();
			byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
			try {
				localByteArrayOutputStream.close();
				return arrayOfByte;
			} catch (Exception e) {
				Log.e("SendWX", e.toString());
			}
			i = bitmap.getHeight();
			j = bitmap.getHeight();
		}
	}
}
