package com.shikong.model;

import java.util.ArrayList;

public class BaiduWeatherResults {
		String currentCity;
		public String getCurrentCity() {
			return currentCity;
		}
		public void setCurrentCity(String currentCity) {
			this.currentCity = currentCity;
		}
		public ArrayList<BaiduWeatherData> getWeather_data() {
			return weather_data;
		}
		public void setWeather_data(ArrayList<BaiduWeatherData> weather_data) {
			this.weather_data = weather_data;
		}
		ArrayList<BaiduWeatherData> weather_data;
}
