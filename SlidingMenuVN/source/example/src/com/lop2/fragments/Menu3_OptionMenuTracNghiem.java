package com.lop2.fragments;

import inout2nd.IOData;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lop2.anim.ListAdapterCustom;
import com.lop2.main.Lop2;
import com.lop2.main.SettingBaiTap;

public class Menu3_OptionMenuTracNghiem extends ListFragment {
	private String idDemuc = "";
	private static boolean onTesting = false;
	private static CountDownTimer cd;
	private static boolean callFinish = false;
	private static Context myContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public Menu3_OptionMenuTracNghiem() {

	}

	public Menu3_OptionMenuTracNghiem(String _idDemuc) {
		idDemuc = _idDemuc;
	}

	public static boolean getTestState() {
		return onTesting;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// hieu: lấy Danh sách menu từ array.xml
		// String[] colors = getResources().getStringArray(R.array.color_names);
		// add new:
		IOData getData = new IOData();
		String[] menuTracNghiem = getData.getMenuTracNghiem();
		// ---------
		Log.e("StringColor", menuTracNghiem.length + "|");
		/*
		 * ArrayAdapter<String> DeMucAdapter = new ArrayAdapter<String>(
		 * getActivity(), android.R.layout.simple_list_item_1,
		 * android.R.id.text1, menuTracNghiem);
		 */
		ListAdapterCustom DeMucAdapter = new ListAdapterCustom(
				getLayoutInflater(savedInstanceState), menuTracNghiem);
		int iDdr[] = new int[8];
		int firstId = R.drawable.m1;
		iDdr[0] = firstId;
		for (int i = 1; i < 8; i++) {
			iDdr[i] = i+firstId;
		}
		DeMucAdapter.setImageList1(iDdr);
		setListAdapter(DeMucAdapter);
	}

	// hieu: xử lý onclick vào mục lục tại đây:
	// hieu: TODO Xử lý các chưng năng trong cửa sổ làm bt:
	@Override
	public void onListItemClick(ListView lv, View v, int options, long id) {
		if (onTesting) {
			for (int i = 1; i < 5; i++) {
				lv.getChildAt(i).setFocusable(false);
			}
		}
		switch (options) {
		case 0:
			if (onTesting == false) {
				Menu3_OptionMenuTracNghiem.myContext = v.getContext();
				AlertDialog.Builder AreUReady = new AlertDialog.Builder(
						v.getContext());
				AreUReady.setTitle("Bạn đã sẵn sàng?");
				AreUReady
						.setMessage("Khi làm bài tập, điểm của bạn sẽ được lưu lại để đánh giá.");
				AreUReady.setPositiveButton("Sẵn sàng",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								F3_LamBaiTap f3 = new F3_LamBaiTap(0);
								final Fragment newContent = f3;
								if (newContent != null) {
									Menu3_OptionMenuTracNghiem.onTesting = true;
									Menu3_OptionMenuTracNghiem.resetState();
									F3_LamBaiTap.setIdOption(0);
									switchFragment(newContent);
								}
							}
						});
				AreUReady.setNegativeButton("Quay lại",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				AreUReady.create().show();
				

			}
			break;
		case 1:
			AlertDialog.Builder AreUReady = new AlertDialog.Builder(
					v.getContext());
			AreUReady.setTitle("Bạn có chắc?");
			AreUReady
					.setMessage("Nếu chưa hết thời gian, kết quả sẽ không được ghi nhận.");
			AreUReady.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							F3_LamBaiTap f3 = new F3_LamBaiTap(-1);
							onTesting = false;
							if(Lop2.md.isPlaying()==false){
								Lop2.md=MediaPlayer.create(getContext(), R.raw.nu_cuoi) ;
								Lop2.md.start();
							}
							switchFragment(f3);
						}
					});
			AreUReady.setNegativeButton("Làm tiếp",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			AreUReady.create().show();
			break;
		case 2:
			if (this.onTesting)
				break;
			break;
		case 3:
			if (this.onTesting)
				break;
			break;
		case 4:
			if (this.onTesting)
				break;
			Intent i = new Intent(getActivity(), SettingBaiTap.class);
			startActivity(i);
			break;
		case 5:
			if (this.onTesting)
				break;
			F3_LamBaiTap.setIdOption(0);
			Intent j = new Intent(getActivity(),
					Changer1_FragmentChangeActivity.class);
			startActivity(j);
			break;
		}

	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null) {
			Log.e("XXX1", "XXX1" + onTesting);
			return;
		}

		if (getActivity() instanceof Changer3_LamBaiWindow) {
			Log.e("XXX2", "XXX2" + onTesting);
			final Changer3_LamBaiWindow fca = (Changer3_LamBaiWindow) getActivity();
			if (callFinish) {
				Log.e("XXX3", "XXX3" + onTesting);
				fca.setTitle("Hết giờ làm bài");
				Menu3_OptionMenuTracNghiem.setTestState(false);
				fca.switchContent(new F3_LamBaiTap(-2));
				return;
			}
			Log.e("XXX4", "XXX4" + onTesting);
			// set time to testing
			if (Menu3_OptionMenuTracNghiem.onTesting) {
				Log.e("XXX5", "XXX5" + onTesting);
				cd = new CountDownTimer(60 * 1000 * SettingBaiTap.getMinute(),1000) {
					// cd = new CountDownTimer(5000, 1000) {
					@Override
					public void onTick(long millis) {
						int seconds = (int) (millis / 1000) % 60;
						int minutes = (int) ((millis / (1000 * 60)) % 60);
						int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
						// String text =
						// String.format("%02d hours, %02d minutes, %02d seconds",hours,minutes,seconds);
						String text = String
								.format("Kiểm tra        Học sinh: Duy Phan			Thời gian còn lại: %02d:%02d:%02d",
										hours, minutes, seconds);
						fca.setTitle(text);
					}

					@Override
					public void onFinish() {
						fca.setTitle("Hết giờ làm bài");
						Menu3_OptionMenuTracNghiem.setTestState(false);
						Menu3_OptionMenuTracNghiem.callFinish();
						fca.switchContent(new F3_LamBaiTap(-2));
						// TODO
					}
				};
				cd.start();
			} else {
				if (cd != null) {
					cd.cancel();
				}
				fca.setTitle("Làm bài tập        Học sinh: Duy Phan");
			}
			Log.e("XXX6", "XXX6" + onTesting);
			fca.switchContent(fragment);
		} else if (getActivity() instanceof ResponsiveUIActivity) {
			Log.e("XXX7", "XXX7" + onTesting);
			ResponsiveUIActivity ra = (ResponsiveUIActivity) getActivity();
			ra.setTitle("Làm bài tập        Học sinh: Duy Phan");
			ra.switchContent(fragment);
		}
	}

	public static void setTestState(boolean gameOver) {
		onTesting = gameOver;// false
		callFinish = !gameOver;
	}

	public static void callFinish() {
		callFinish = true;
		onTesting = false;
	}

	public static void resetState() {
		callFinish = false;
	}
	public static boolean getCallFinish(){
		return callFinish;
	}
	public static Context getContext() {
		return myContext;
	}
	public static void startTimeCoundown(){
		cd.start();
	}
	
}
