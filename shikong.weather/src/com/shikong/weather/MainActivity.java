package com.shikong.weather;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.Projection;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.shikong.weather.R;
import com.shikong.model.InformCallback;
import com.shikong.model.WeatherMap;

public class MainActivity extends FragmentActivity implements InformCallback {

	private AMap aMap;
	private HashMap<String ,Marker> markers=new HashMap<String, Marker>();
	private LinearLayout mywindows;
	private Gallery gallery;
	private TextView week1;
	private TextView week2;
	private TextView temp1;
	private TextView temp2;
	private ImageView weather1;
	private ImageView weather2;
	private int index;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// aMap.moveCamera(CameraUpdateFactory.scrollBy(15f, 15f));
			// if(WeatherMap.weathermap.get(WeatherMap.cityv[index])==null)
			// return;
			Wdata wdata2 = WeatherMap.weathermap.get(WeatherMap.cityv[index]);
			if (wdata2 != null) {
				nextBroadcast(wdata2);

			}
			if(!mywindows.isShown()){
				mywindows.setVisibility(View.VISIBLE);
			}
			Wdata wdata = (Wdata) msg.obj;
			System.out.println("MainActivity.enclosing_method()" + index+" "+markers.get(wdata.city));
			location(wdata);
			if (index < gallery.getCount())
				gallery.setSelection(index);
			week1.setText(wdata.week1);
			week2.setText(wdata.week2);
			weather1.setImageResource(WeatherMap.getResId(wdata.weather1));
			weather2.setImageResource(WeatherMap.getResId(wdata.weather2));
			temp1.setText(wdata.temp1);
			temp2.setText(wdata.temp2);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mywindows=(LinearLayout) findViewById(R.id.widows);
		gallery = (Gallery) findViewById(R.id.city_image);
		week1=(TextView) findViewById(R.id.week1);
		week2=(TextView) findViewById(R.id.week2);
		weather1=(ImageView) findViewById(R.id.weather1);
		weather2=(ImageView) findViewById(R.id.weather2);
		temp1 = (TextView) findViewById(R.id.temp1);
		temp2 = (TextView) findViewById(R.id.temp2);
		gallery.setAdapter(new GalleryAdapter(this));
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (aMap != null) {
				aMap.getUiSettings().setZoomControlsEnabled(false);// 隐藏缩放按钮
				aMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(new CameraPosition(Constants.BEIJING, 6F, 45f, 10)));
				// aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
				// aMap.setOnInfoWindowClickListener(this);//
				// 设置点击infoWindow事件监听器
				// aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
				// aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
				// aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
				getData();
			}
		}
	}

	/**
	 * 获取数据
	 */
	public void getData() {
		new WeatherMap().makeAll(this);
		new Thread() {
			@Override
			public void run() {
				// 2013-8-31 上午11:55:46
				try {
					int i = 0;
					while (true) {
						Thread.sleep(3000);
						Wdata wdata = WeatherMap.weathermap
								.get(WeatherMap.cityv[index]);
						if (wdata != null) {
							nextBroadcast(wdata);
							return;
						}
						if (i >= 5) {
							return;
						}
						i++;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 开始播报
	 */
//	public void startBroadcast() {
//		// 创建marker
//		for (int i = 0; i < WeatherMap.cityv.length; i++) {
//			Wdata wdata = WeatherMap.weathermap.get(WeatherMap.cityv[i]);
//			if (wdata == null)
//				continue;
//			aMap.addMarker(new MarkerOptions().position(wdata.latLng).icon(
//					BitmapDescriptorFactory.fromBitmap(getViewBitmap(getView(
//							wdata.city, wdata.temp0)))));
//		}
//		if (WeatherMap.weathermap.get(WeatherMap.cityv[index]) == null)
//			return;
		// location();
		// nextBroadcast();
//	}

	/**
	 * 下一个城市
	 */
	public void nextBroadcast(Wdata wdata) {
		index++;
		if (index >= WeatherMap.cityv.length) {
			// 播报完毕
			return;
		}
		System.out.println("MainActivity.nextBroadcast()" + index);
		// 启动下一次播报
		Message message = handler.obtainMessage();
		message.arg1 = index;
		message.obj = wdata;
		handler.sendMessageDelayed(message, 5000);
	}

	/**
	 * 定位到城市
	 */
	public void location(Wdata wdata) {
		// float zoom,float tilt,float bearing
		// 设置位置
		aMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(new CameraPosition(wdata.latLng, 6F, 45f, 10)));
		// aMap.animateCamera(CameraUpdateFactory.newLatLng(list.get(index).latLng));
		jumpPoint(markers.get(wdata.city),wdata.latLng);
	}

	/**
	 * 把一个xml布局文件转化成view
	 */
	public View getView(String title, String text,int id) {
		View view = getLayoutInflater().inflate(R.layout.marker, null);
		TextView text_title = (TextView) view.findViewById(R.id.marker_title);
		TextView text_text = (TextView) view.findViewById(R.id.marker_text);
		ImageView iv=(ImageView) view.findViewById(R.id.marker_iv);
		iv.setImageResource(id);
		text_title.setText(title);
		text_text.setText(text);
		return view;
	}

	/**
	 * 把一个view转化成bitmap对象
	 */
	public static Bitmap getViewBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	boolean isFinish;

	@Override
	public void onFinish(Wdata wdata) {
		// 2013-8-31 上午9:41:26
		if (isFinish) {
			// return;
		}
		isFinish = true;

		// for (int i = 0; i < 5; i++) {
		// Wdata wdata = new Wdata().getWdata(i);
		// list.add(wdata);
		// }
		// startBroadcast();
		if (wdata == null)
			return;
		
		Marker marker= aMap.addMarker(new MarkerOptions().position(wdata.latLng).icon(
				BitmapDescriptorFactory.fromBitmap(getViewBitmap(getView(
						wdata.city, wdata.temp0,WeatherMap.getResId(wdata.weather0))))));
		markers.put(wdata.city,marker);
		// nextBroadcast(wdata);
	}
	

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * marker点击时跳动一下
	 */
	public void jumpPoint(final Marker marker,final LatLng latLng) {

		final Handler handler = new Handler();
		final long start = SystemClock.uptimeMillis();
		Projection proj = aMap.getProjection();
		Point startPoint = proj.toScreenLocation(latLng);
		startPoint.offset(0, -100);
		final LatLng startLatLng = proj.fromScreenLocation(startPoint);
		final long duration = 1500;
System.out.println("MainActivity.jumpPoint()"+marker);
		final Interpolator interpolator = new BounceInterpolator();
		handler.post(new Runnable() {
			@Override
			public void run() {
				System.out
						.println("MainActivity.jumpPoint(...).new Runnable() {...}.run()"+marker);
				long elapsed = SystemClock.uptimeMillis() - start;
				float t = interpolator.getInterpolation((float) elapsed
						/ duration);
				double lng = t * latLng.longitude + (1 - t)
						* startLatLng.longitude;
				double lat = t * latLng.latitude + (1 - t)
						* startLatLng.latitude;
				if(marker==null)
					return;
				marker.setPosition(new LatLng(lat, lng));
				if (t < 1.0) {
					handler.postDelayed(this, 16);
				}
			}
		});
	}

}
