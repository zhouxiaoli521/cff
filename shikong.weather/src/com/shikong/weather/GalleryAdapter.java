package com.shikong.weather;

import com.example.shikong.weather.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView.ScaleType;

public class GalleryAdapter extends BaseAdapter {

	int[] id={R.drawable.m1,R.drawable.m2,R.drawable.m3,R.drawable.m4,
			R.drawable.m5,R.drawable.m6,R.drawable.m7,R.drawable.m8};
	Context context;
	public GalleryAdapter(Context context){
		this.context=context;
	}
	@Override
	public int getCount() {
		// 2013-8-30 下午4:46:07
		return id.length;
	}

	@Override
	public Object getItem(int position) {
		// 2013-8-30 下午4:46:07
		return position;
	}

	@Override
	public long getItemId(int position) {
		// 2013-8-30 下午4:46:07
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 2013-8-30 下午4:46:07
		if(convertView==null){
			ImageView iv=new ImageView(context);
			Gallery.LayoutParams glp=new Gallery.LayoutParams(-1,-1);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(glp);
			convertView=iv;
		}
		((ImageView)convertView).setImageResource(id[position]);
		return convertView;
	}

}
