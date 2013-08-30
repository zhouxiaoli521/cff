package com.shikong.model;

import java.util.HashMap;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class WeatherMap {

	public static HashMap<String,BaiduWeather>  weathermap = new HashMap<String,BaiduWeather>();
	
	public static String[] cityv ={"¹þ¶û±õ","³¤´º","ÉòÑô"};
	
	public void makeAll()
	{
		for ( String city : cityv)
		{
			this.makeWeatherbycity(city);
		}
	}
	void makeWeatherbycity(String city)
	{
		String serviceurl =String.format("http://api.map.baidu.com/telematics/v3/weather?location=%s&output=json&ak=4dee58d4f9c17dbbfdf850487b02b36e",city);
		makeRequestcbinjson(serviceurl,city);
	}
	void makeRequestcbinjson(String serviceurl,String city) {
        AsyncHttpClient client = new AsyncHttpClient();

        final String fcity=city;
        client.get(serviceurl
        		, null, 
        		new AsyncHttpResponseHandler() {
          	@Override
              public void onStart() {
          		Log.d("restful","onStart");
              }
              @Override
              public void onSuccess(String response) {
              	   Log.d("Restful","Lanmaouser success");
                  System.out.println(response);
                  BaiduWeather bw = new BaiduWeather();
                  BaiduWeather bw1 = bw.parse(response);
                  System.out.println("bw1 ="+bw1);
                  System.out.println("bw1="+bw1.getDate());
                  for ( BaiduWeatherResults bwr : bw1.getResulsts())
                  {
                	  System.out.println("bwr"+bwr.getCurrentCity());
                	  for ( BaiduWeatherData bwd : bwr.getWeather_data())
                	  {
                		  System.out.println("bwd="+ bwd.getDate()+ " "+ bwd.temperature );
                		  System.out.println("bwd="+ bwd.getDate()+ " "+ bwd.weather );
                		  
                		  
                	  }
                  }
                  weathermap.put(fcity, bw1);
              }
              @Override
              public void onFailure(Throwable e, String response) {
                  // Response failed :(
              	 Log.d("Restful","Failure");
              	 System.out.println("response ="+response);
              }

              @Override
              public void onFinish() {
                  // Completed the request (either success or failure)
              	 Log.d("Restful","Finish");
              }
          });
        		

	}
}
