package com.lop2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lop2.main.BaseActivity;

public class Changer1_FragmentChangeActivity extends BaseActivity {
	
	private Fragment mContent;
	
	public Changer1_FragmentChangeActivity() {
		super(R.string.changing_fragments);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		// hieu: fragment first page của hiếu
		if (mContent == null)
			mContent = new F0_BeginFragment();	
		
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new Menu1_ChuongFragment())
		.commit();
		
		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}
	
	public void onBackPressed(){
		String nameFr = mContent.toString();
		Log.e("mContent", mContent.toString());
		if(nameFr.contains("F0_BeginFragment")){
			Log.e("Leu leu","do nothing");
		}else{
			super.onBackPressed();
		}
		//
	}

}
