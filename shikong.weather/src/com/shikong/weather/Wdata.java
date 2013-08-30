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

	public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// �����о�γ��
	public static final LatLng SHANGHAI = new LatLng(31.239879, 121.499674);// �Ϻ��о�γ��
	public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// �ɶ��о�γ��
	public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// �����о�γ��
	public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// ֣���о�γ��
	
	LatLng[] ll={BEIJING,SHANGHAI,CHENGDU,XIAN,ZHENGZHOU};
	public Wdata getWdata(int i) {
		city = "city" + i;
		date = "2013��8��" + i + "��";
		week = "����" + i;
		temp1 = "1" + i + "��~2" + i + "��";
		temp2 = "2" + i + "��~3" + i + "��";
		weather1 = "����" + i;
		weather2 = "��" + i;
		latLng=ll[i%ll.length];
		return this;
	}
}
