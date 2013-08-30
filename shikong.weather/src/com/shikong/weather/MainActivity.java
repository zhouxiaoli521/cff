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
				aMap.getUiSettings().setZoomControlsEnabled(false);// �������Ű�ť

				// aMap.setOnMarkerClickListener(this);// ���õ��marker�¼�������
				// aMap.setOnInfoWindowClickListener(this);//
				// ���õ��infoWindow�¼�������
				// aMap.setInfoWindowAdapter(this);// �����Զ���InfoWindow��ʽ
				// aMap.setOnMarkerDragListener(this);// ����marker����ק�¼�������
				// aMap.setOnMapLoadedListener(this);// ����amap���سɹ��¼�������
				getData();
			}
		}
	}

	/**
	 * ��ȡ����
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
	 * ��ʼ����
	 */
	public void startBroadcast() {
		// ����marker
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
	 * ��һ������
	 */
	public void nextBroadcast() {
		index++;
		if (index >= list.size()) {
			// �������
			return;
		}
		// ������һ�β���
		Message message = handler.obtainMessage();
		message.arg1 = index;
		handler.sendMessageDelayed(message, 2000);
	}

	/**
	 * ��λ������
	 */
	public void location() {
		// float zoom,float tilt,float bearing
		// ����λ��
		aMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(new CameraPosition(list.get(index).latLng,
						6F, 45f, 10)));
//		aMap.animateCamera(CameraUpdateFactory.newLatLng(list.get(index).latLng));
	}

	/**
	 * ��һ��xml�����ļ�ת����view
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
	 * ��һ��viewת����bitmap����
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
