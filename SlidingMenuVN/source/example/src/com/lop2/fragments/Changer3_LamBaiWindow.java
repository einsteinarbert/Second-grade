package com.lop2.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.WindowManager;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lop2.main.BaseActivity;

public class Changer3_LamBaiWindow extends BaseActivity {
	private static Context myContex;
	private String idDeMuc;
	private Fragment mContent;
	private int idOption;
	private Changer3_LamBaiWindow title;
	public Changer3_LamBaiWindow() {
		super(R.string.changing_fragments);// hieu: set title
		idOption = -1;
		title = this;
	}

	public Changer3_LamBaiWindow(int _idOption) {
		super(R.string.changing_fragments);
		idOption = _idOption;
		title = this;
	}

	public void setIdDeMuc(int _idOption) {
		idOption = _idOption;
	}

	public int getIdDemuc() {
		return idOption;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("F3-Option", ">" + F3_LamBaiTap.getIdOption());
		Intent inten = this.getIntent();
		this.idDeMuc = inten.getStringExtra("id_DeMuc");
		myContex = getApplicationContext();
		// set the Above View
		if (F3_LamBaiTap.getIdOption() == -2) {
			mContent = new F3_LamBaiTap(-2);
			title.setTitle("Kết quả        Học sinh: Duy Phan");
		}
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null) {
			mContent = new F3_LamBaiTap(-1);
			title.setTitle("Làm bài tập        Học sinh: Duy Phan");
		}

		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.menu_frame,
						new Menu3_OptionMenuTracNghiem(idDeMuc)).commit();
		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// String idDemuc = mContent.getString("idDeMuc");
		// -----------------------------------
		if (Menu3_OptionMenuTracNghiem.getTestState()) {
			final ProgressDialog ringProgressDialog = ProgressDialog.show(
					Menu3_OptionMenuTracNghiem.getContext(), "Xin chờ ...",
					"Đang tạo bài tập ...", true);
			ringProgressDialog.setCancelable(false);
			new Thread(new Runnable() { // hieu: There code is very unbelievable
										// :))
						@Override
						public void run() {
							try {
								while (true) {
									Thread.sleep(1000);
									Log.e("WAITTTTTT", "...."
											+ F3_LamBaiTap.createdData);
									getSupportFragmentManager()
											.beginTransaction()
											.replace(R.id.content_frame,
													fragment).commit();
									getSlidingMenu().showContent();
									if (F3_LamBaiTap.createdData
											|| Menu3_OptionMenuTracNghiem
													.getTestState() == false) {
										// Menu3_OptionMenuTracNghiem.startTimeCoundown();
										break;
									}
								}
							} catch (Exception e) {

							}
							ringProgressDialog.dismiss();
						}
					}).start();
		} else {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			getSlidingMenu().showContent();
		}
		// -----------------------------------

	}

	public void onBackPressed() {
		if (Menu3_OptionMenuTracNghiem.getCallFinish()==true||Menu3_OptionMenuTracNghiem.getTestState()==true) {
		} else {
			Log.e("BACK BACK BACH", "BHACK");
			super.onBackPressed();
		}
	}

	public static Context getContext() {
		return myContex;
	}
}
