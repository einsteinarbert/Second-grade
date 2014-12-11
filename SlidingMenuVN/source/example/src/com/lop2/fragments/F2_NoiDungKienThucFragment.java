package com.lop2.fragments;

import inout2nd.De_Muc;
import inout2nd.IOData;

import com.jeremyfeinstein.slidingmenu.example.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

@SuppressLint("InlinedApi")
public class F2_NoiDungKienThucFragment extends Fragment {

	private String idDeMuc = "";
	//private MediaPlayer backgroundSound;
	public F2_NoiDungKienThucFragment(String _idDeMuc) {
		idDeMuc = _idDeMuc;
		setRetainInstance(true);
	}
	public void setIdDeMuc(String _idDeMuc){
		idDeMuc = _idDeMuc;
	}
	public String getIdDemuc(){
		return idDeMuc;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null){
			idDeMuc = savedInstanceState.getString("mColorRes");
		}
		//backgroundSound = MediaPlayer.create(container.getContext(),R.raw.autumn);
		
		// construct the RelativeLayout
		RelativeLayout v = new RelativeLayout(getActivity());
		ImageView backGround = new ImageView(v.getContext());
		//lay out hinh con cong duy test
		IOData data = new IOData();
		De_Muc demuc_1 = data.getde_muc(idDeMuc);
		Log.e("id de muc hinh con cong", idDeMuc);
		//backGround.setImageResource(R.drawable.peacock);
		
		// ảnh hướng dẫn có set ảnh  // ko set hình con công
		if(demuc_1.getHuongDan()==null)
				backGround.setImageBitmap(demuc_1.getHuongDan());
		else   backGround.setImageResource(R.drawable.peacock); 
		
		Log.e("Changer2_KienThucDeMucFragment", "NoiDungKienThuc~ Hình Con công: " +idDeMuc);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		backGround.setScaleType(ScaleType.CENTER_CROP);
		v.addView(backGround, param);
		param = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		Button lamBaiTap = new Button(v.getContext());
		lamBaiTap.setText("Làm bài tập >>");
		lamBaiTap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), Changer3_LamBaiWindow.class);
				intent.putExtra("id_DeMuc",idDeMuc);
				if (idDeMuc==null) Log.e("iD_Demuc", "null");
				else  Log.e("iD_Demuc", "ko null");
				//backgroundSound.start();
				startActivity(intent);
			}
		});
		v.addView(lamBaiTap,param);
		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("mColorRes", idDeMuc);
	}

}
