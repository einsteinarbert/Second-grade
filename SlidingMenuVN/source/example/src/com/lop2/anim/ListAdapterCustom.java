package com.lop2.anim;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.example.R;

public class ListAdapterCustom extends BaseAdapter {
	LayoutInflater lf;
	String[] list;
	int listImg1[];
	int listImg2[];
	int listImg3[];
	int listImg4[];
	int color = 0xff67E3FD;
	public ListAdapterCustom(LayoutInflater ly, String[] list){
		lf = ly;
		this.list = list;
	}
	@Override
	public int getCount() {
		
		return list.length;
	}

	@Override
	public Object getItem(int arg0) {
		
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View v = lf.inflate(R.layout.item, arg2, false);
		
		TextView t = (TextView)v.findViewById(R.id.textView1);
		ImageView img1 = (ImageView)v.findViewById(R.id.imageView1);
		ImageView img2 = (ImageView)v.findViewById(R.id.imageView2);
		ImageView img3 = (ImageView)v.findViewById(R.id.imageView3);
		ImageView img4 = (ImageView)v.findViewById(R.id.imageView4);
		t.setText(list[arg0]);
		if(arg0%2==0){
			v.setBackgroundColor(color);
		}
		if(listImg1!=null){
			int size = listImg1.length;
			if(listImg1[arg0%size]>0)
				img1.setBackgroundResource(listImg1[arg0%size]);
		}
		if(listImg2!=null){
			int size = listImg2.length;
			if(listImg2[arg0%size]>0)
				img2.setBackgroundResource(listImg2[arg0%size]);
		}
		if(listImg3!=null){
			int size = listImg3.length;
			if(listImg3[arg0%size]>0)
				img3.setBackgroundResource(listImg3[arg0%size]);
		}
		if(listImg4!=null){
			int size = listImg4.length;
			if(listImg4[arg0%size]>0)
				img4.setBackgroundResource(listImg4[arg0%size]);
		}
		return v;
	}
	public void setColorLine(int myColor){
		color = myColor;
	}
	public void setImageList1(int IdDrawable[]){
		listImg1 = IdDrawable.clone();
	}
	public void setImageList12(int IdDrawable[]){
		listImg2 = IdDrawable.clone();
	}
	public void setImageList3(int IdDrawable[]){
		listImg3 = IdDrawable.clone();
	}
	public void setImageList4(int IdDrawable[]){
		listImg4 = IdDrawable.clone();
	}
	public void setImageAllLists(int IdDrawable1[],int IdDrawable2[],int IdDrawable3[],int IdDrawable4[]){
		listImg1 = IdDrawable1.clone();
		listImg2 = IdDrawable2.clone();
		listImg3 = IdDrawable3.clone();
		listImg4 = IdDrawable4.clone();
	}
}
