package com.shikong.model;


public class BaiduWeatherData {

	String date;
	String dayPictureUrl;
	String nightPictureUrl;
	String weather;
	String wind;
	String temperature;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDayPictureUrl() {
		return dayPictureUrl;
	}
	public void setDayPictureUrl(String dayPictureUrl) {
		this.dayPictureUrl = dayPictureUrl;
	}
	public String getNightPictureUrl() {
		return nightPictureUrl;
	}
	public void setNightPictureUrl(String nightPictureUrl) {
		this.nightPictureUrl = nightPictureUrl;
	}
//	public String getWeather() {
//		return WeatherFilter.filterData(weather);
//	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
//	public String getTemperature() {
//		return WeatherFilter.filterData(temperature);
//	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
}
