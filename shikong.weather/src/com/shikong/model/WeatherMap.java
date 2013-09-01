package com.shikong.model;

import java.util.HashMap;

import android.R.integer;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.example.shikong.weather.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shikong.weather.Wdata;

public class WeatherMap {
	public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
	public static final LatLng SHANGHAI = new LatLng(31.239879, 121.499674);// 上海市经纬度
	public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
	public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
	public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度
	public static final LatLng guangzhou = new LatLng(23.1, 113.2);// 郑州市经纬度
	public static final LatLng changsha = new LatLng(28.2, 112.9);// 郑州市经纬度
	public static final LatLng yingchuan = new LatLng(38.4, 106.2);// 郑州市经纬度

	public static HashMap<String, Wdata> weathermap = new HashMap<String, Wdata>();

	public static HashMap<String, LatLng> cityLatLng = new HashMap<String, LatLng>();
	public static String[] cityv = { "北京", "上海", "成都", "西安", "郑州","广州","长沙","银川" };
	public static LatLng[] latlngs = { BEIJING, SHANGHAI, CHENGDU, XIAN,
			ZHENGZHOU,guangzhou,changsha ,yingchuan};

	public static HashMap<String, Integer> weathers = new HashMap<String, Integer>();
	public static String[] weathersTra = { "晴", "多云", "阴", "阵雨", "雷阵雨", "小雨",
			"中雨", "大雨", "雾" };
	public static int[] weathersid = { R.drawable.w1, R.drawable.w2,
			R.drawable.w3, R.drawable.w4, R.drawable.w5, R.drawable.w6,
			R.drawable.w7, R.drawable.w8, R.drawable.w9 };

	private InformCallback listening;

	static {
		for (int i = 0; i < cityv.length; i++)
			cityLatLng.put(cityv[i], latlngs[i]);

		for (int i = 0; i < weathersTra.length; i++)
			weathers.put(weathersTra[i], weathersid[i]);

	}

	public static int getResId(String weather) {
		Integer request = weathers.get(weather);
		if (request == null) {
			for (int i = 0; i < weathersTra.length; i++) {
				if (weathersTra[i].startsWith(weather)) {
					return weathers.get(weathersTra[i]);
				}
			}
			return weathers.get(weathersTra[0]);
		}
		return request;
	}

	public void makeAll(InformCallback informCallback) {
		listening = informCallback;
		for (String city : cityv) {
			this.makeWeatherbycity(city);
		}
	}

	public void makeWeatherbycity(String city) {
		String serviceurl = String
				.format("http://api.map.baidu.com/telematics/v3/weather?location=%s&output=json&ak=4dee58d4f9c17dbbfdf850487b02b36e",
						city);
		makeRequestcbinjson(serviceurl, city);
	}

	void makeRequestcbinjson(String serviceurl, String city) {
		AsyncHttpClient client = new AsyncHttpClient();

		final String fcity = city;
		client.get(serviceurl, null, new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				Log.d("restful", "onStart");
			}

			@Override
			public void onSuccess(String response) {
				Log.d("Restful", "Lanmaouser success");
				System.out.println(response);
				Wdata wdata = new Wdata();
				BaiduWeather bw = new BaiduWeather();
				wdata.date = bw.date;
				BaiduWeather bw1 = bw.parse(response);
				System.out.println("bw1 =" + bw1);
				System.out.println("bw1=" + bw1.getDate());
				for (BaiduWeatherResults bwr : bw1.getResulsts()) {
					System.out.println("bwr" + bwr.getCurrentCity());
					wdata.city = bwr.currentCity;
					wdata.setLatlng(cityLatLng.get(bwr.currentCity));
					int i = 0;
					for (BaiduWeatherData bwd : bwr.getWeather_data()) {
						switch (i) {
						case 0:
							wdata.temp0 = bwd.temperature;
							wdata.weather0 = bwd.weather;
							wdata.week0=bwd.getDate();
							break;
						case 1:
							wdata.temp1 = bwd.temperature;
							wdata.weather1 = bwd.weather;
							wdata.week1=bwd.getDate();

							break;
						case 2:
							wdata.temp2 = bwd.temperature;
							wdata.weather2 = bwd.weather;
							wdata.week2=bwd.getDate();

							break;
						}
						i++;
						System.out.println("bwd=" + bwd.getDate() + " "
								+ bwd.temperature);
						System.out.println("bwd=" + bwd.getDate() + " "
								+ bwd.weather);

					}
				}
				weathermap.put(fcity, wdata);
				if (listening != null) {
					listening.onFinish(wdata);
				}
			}

			@Override
			public void onFailure(Throwable e, String response) {
				// Response failed :(
				Log.d("Restful", "Failure");
				System.out.println("response =" + response);
			}

			@Override
			public void onFinish() {
				// Completed the request (either success or failure)
				Log.d("Restful", "Finish");
			}
		});

	}
}
