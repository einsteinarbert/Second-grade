package com.lop2.fragments;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lop2.main.Lop2;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

@SuppressLint("InlinedApi")
public class F0_BeginFragment extends Fragment {

	private int idChuong = -1;
	private int w,h;
	// hieu: đây là nơi hiển thị bìa của soft:
	public F0_BeginFragment() {
		idChuong = 0;
		setRetainInstance(true);
	}

	// hieu: sửa ở đây để hiển thị bìa
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null)
			idChuong = savedInstanceState.getInt("mColorRes");
		RelativeLayout v = new RelativeLayout(getActivity());
		if(Lop2.md.isPlaying()==false){
			Lop2.md=MediaPlayer.create(v.getContext(), R.raw.nu_cuoi) ;
			Lop2.md.start();
		}
		// construct the RelativeLayout
		
		ImageView firstPage = new ImageView(v.getContext());
		firstPage.setImageResource(R.drawable.first_page);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		firstPage.setScaleType(ScaleType.CENTER_CROP);
		// param.addRule(RelativeLayout.?);
		v.addView(firstPage, param);
		Toast.makeText(v.getContext(), "Kéo màn hình từ trái qua phải để lựa chọn.",
				Toast.LENGTH_LONG).show();
		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mColorRes", idChuong);
	}
}
