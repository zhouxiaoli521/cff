package com.shikong.weather;

import com.amap.api.maps.model.LatLng;

public class Wdata {

	public int lat;
	public float lng;
	public LatLng latLng;
	public String city;
	public String date;
	public String week;
	public String temp1;
	public String temp2;
	public String weather1;
	public String weather2;

	public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
	public static final LatLng SHANGHAI = new LatLng(31.239879, 121.499674);// 上海市经纬度
	public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
	public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
	public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度
	
	LatLng[] ll={BEIJING,SHANGHAI,CHENGDU,XIAN,ZHENGZHOU};
	public Wdata getWdata(int i) {
		city = "city" + i;
		date = "2013年8月" + i + "日";
		week = "星期" + i;
		temp1 = "1" + i + "℃~2" + i + "℃";
		temp2 = "2" + i + "℃~3" + i + "℃";
		weather1 = "多云" + i;
		weather2 = "晴" + i;
		latLng=ll[i%ll.length];
		return this;
	}
}
