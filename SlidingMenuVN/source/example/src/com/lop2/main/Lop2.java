package com.lop2.main;

import java.io.File;

import inout2nd.Database;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.jeremyfeinstein.slidingmenu.example.R;
import com.lop2.fragments.Changer1_FragmentChangeActivity;
import com.lop2.fragments.F3_LamBaiTap;

public class Lop2 extends SherlockPreferenceActivity {
	// duy:
	public static Database DbHelper;
	private static Context myContex;
	public static MediaPlayer md;
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
		md = MediaPlayer.create(getApplicationContext(), R.raw.nu_cuoi);
		md.setLooping(true);
		md.start();
		DbHelper = new Database(this); // strat database
		DbHelper.open();
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		F3_LamBaiTap sizeScreen = new F3_LamBaiTap();
		sizeScreen.setScreenSize(width, height);
		// Crittercism.init(getApplicationContext(),
		// "508ab27601ed857a20000003");
		this.addPreferencesFromResource(R.xml.main);
		
		myContex = this.getApplicationContext();
		
		Class<?> cls = Changer1_FragmentChangeActivity.class;
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	public void onBackPressed() {
		Class<?> cls = Changer1_FragmentChangeActivity.class;
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
		Class<?> cls = Changer1_FragmentChangeActivity.class;
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	@Override
	protected void onTitleChanged(CharSequence title, int color) {
		// TODO Auto-generated method stub
		super.onTitleChanged(title, color);
		Class<?> cls = Changer1_FragmentChangeActivity.class;
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		Class<?> cls = Changer1_FragmentChangeActivity.class;
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}
	
	public static Context getContext(){
		return myContex;
	}

}
