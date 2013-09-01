package com.shikong.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.shikong.weather.R;

public class IndexPageActivity extends Activity{

	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			startActivity(new Intent(IndexPageActivity.this,MainActivity.class));
			finish();
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 2013-8-31 ÏÂÎç1:49:23
		super.onCreate(savedInstanceState);
		ImageView iv=new ImageView(this);
		iv.setImageResource(R.drawable.home);
		iv.setScaleType(ScaleType.FIT_XY);
		setContentView(iv);
		handler.sendMessageDelayed(handler.obtainMessage(), 3000);
	}
}
