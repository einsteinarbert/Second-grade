package com.lop2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lop2.main.BaseActivity;

public class Changer2_KienThucDeMucFragment extends BaseActivity {
	
	private Fragment mContent;
	private String idDeMuc;
	private String idChuong;
	
	public Changer2_KienThucDeMucFragment() {
		super(R.string.changing_fragments);
		idDeMuc = "";
	}
	public Changer2_KienThucDeMucFragment(String id_chuong, String id_DeMuc) {
		super(R.string.changing_fragments);
		idDeMuc=id_DeMuc;
		idChuong = id_chuong;
		
	}
	public void setIdDeMuc(String _idDeMuc){
		idDeMuc = _idDeMuc;
	}
	public String getIdDemuc(){
		return idDeMuc;
	}
	public String getIdChuong(){
		return idChuong;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = this.getIntent();
		this.idDeMuc = i.getStringExtra("idDemuc");
		this.idChuong = i.getStringExtra("idChuong"); 
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new F2_NoiDungKienThucFragment(idDeMuc);
		
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
        Menu2_CacDeMuc menu2 = new Menu2_CacDeMuc(idChuong,idDeMuc);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, menu2)
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
		//String idDemuc = mContent.getString("idDeMuc");
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}

}
