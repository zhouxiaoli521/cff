package com.shikong.model;


import java.util.ArrayList;

import com.google.gson.Gson;


public class BaiduWeather {
   int error;
   String status;
   String date;
   ArrayList<BaiduWeatherResults> results;
   
   
   public int getError() {
	return error;
}


public void setError(int error) {
	this.error = error;
}


public String getStatus() {
	return status;
}


public void setStatus(String status) {
	this.status = status;
}


public String getDate() {
	return date;
}


public void setDate(String date) {
	this.date = date;
}


public ArrayList<BaiduWeatherResults> getResulsts() {
	return results;
}


public void setResulsts(ArrayList<BaiduWeatherResults> resulsts) {
	this.results = resulsts;
}


public BaiduWeather parse(String response)
  {
	   Gson gson = new Gson();
	   BaiduWeather bw = (BaiduWeather) gson.fromJson(response,BaiduWeather.class);
		 return bw;
   }
}
