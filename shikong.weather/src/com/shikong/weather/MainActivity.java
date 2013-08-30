package com.shikong.weather;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Gallery;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.shikong.weather.R;
import com.shikong.model.WeatherMap;

public class MainActivity extends FragmentActivity {

	private ArrayList<Wdata> list;
	private AMap aMap;
	private Marker marker;
	private float zoom;
	private Gallery gallery;
	private TextView temp1;
	private TextView temp2;
	private int index;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// aMap.moveCamera(CameraUpdateFactory.scrollBy(15f, 15f));
			location();
			gallery.setSelection(index);
			temp1.setText(list.get(index).temp1);
			temp2.setText(list.get(index).temp2);
			nextBroadcast();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list = new ArrayList<Wdata>();
		setContentView(R.layout.activity_main);
		gallery = (Gallery) findViewById(R.id.city_image);
		temp1=(TextView) findViewById(R.id.temp1);
		temp2=(TextView) findViewById(R.id.temp2);
		gallery.setAdapter(new GalleryAdapter(this));
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (aMap != null) {
				aMap.getUiSettings().setZoomControlsEnabled(false);// 隐藏缩放按钮

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
		new WeatherMap().makeAll();
		for (int i = 0; i < 5; i++) {
			Wdata wdata = new Wdata().getWdata(i);
			list.add(wdata);
		}
		startBroadcast();
	}

	/**
	 * 开始播报
	 */
	public void startBroadcast() {
		// 创建marker
		for (int i = 0; i < list.size(); i++) {
			aMap.addMarker(new MarkerOptions().position(list.get(i).latLng)
					.icon(BitmapDescriptorFactory
							.fromBitmap(getViewBitmap(getView(list.get(i).city,
									list.get(i).temp1)))));
		}
		location();
		nextBroadcast();
	}

	/**
	 * 下一个城市
	 */
	public void nextBroadcast() {
		index++;
		if (index >= list.size()) {
			// 播报完毕
			return;
		}
		// 启动下一次播报
		Message message = handler.obtainMessage();
		message.arg1 = index;
		handler.sendMessageDelayed(message, 2000);
	}

	/**
	 * 定位到城市
	 */
	public void location() {
		// float zoom,float tilt,float bearing
		// 设置位置
		aMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(new CameraPosition(list.get(index).latLng,
						6F, 45f, 10)));
//		aMap.animateCamera(CameraUpdateFactory.newLatLng(list.get(index).latLng));
	}

	/**
	 * 把一个xml布局文件转化成view
	 */
	public View getView(String title, String text) {
		View view = getLayoutInflater().inflate(R.layout.marker, null);
		TextView text_title = (TextView) view.findViewById(R.id.marker_title);
		TextView text_text = (TextView) view.findViewById(R.id.marker_text);
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

}
