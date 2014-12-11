package com.lop2.fragments;

import inout2nd.Bieu_Thuc;
import inout2nd.CauHoi;
import inout2nd.IOData;
import inout2nd.SinhCauHoi;
import inout2nd.Trac_Nghiem;
import inout2nd.UpdateSuport;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.w3c.dom.Text;

import Expression.Expression;
import Expression.Randomize;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lop2.main.Lop2;
import com.lop2.main.SettingBaiTap;

import android.R.bool;
import android.R.integer;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRouter.VolumeCallback;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class F3_LamBaiTap extends Fragment {

	private static int idOption = -1;
	private static String idDeMuc;
	private static int width = 0;
	private static int height = 0;
	private static int answerRecorder[];
	private int soCauHoi;
	private int dapAnDung;
	private Randomize ABCD;
	private Randomize kqSai;
	private RadioGroup tickDapAn;
	private int thuTuCauHoi;
	private ArrayList<String> cacCauHoi;
	private RelativeLayout v;
	private boolean newQuestion = true;
	private RadioButton dapAn_A;
	private RadioButton dapAn_B;
	private RadioButton dapAn_C;
	private RadioButton dapAn_D;
	private boolean gameover = false;
	public static boolean createdData = false;
	private MediaPlayer tingkerTrue, tingkerFalse;
	private TextView textUser;
	private String userInfor;

	public void setScreenSize(int w, int h) {
		width = w;
		height = h;
	}

	/*
	 * public F3_LamBaiTap(int idOp, String iddemuc) { idOption = idOp; idDeMuc
	 * = iddemuc; }
	 */

	public F3_LamBaiTap() {
		this(R.color.white);
		// hieu: sua sau
		soCauHoi = (int) SettingBaiTap.getMinute();
		// soCauHoi++;
	}

	public F3_LamBaiTap(int option) {
		F3_LamBaiTap.idOption = option;
		setRetainInstance(true);
		// hieu: sua sau
		soCauHoi = (int) SettingBaiTap.getMinute();
		// soCauHoi++;
	}

	public int getIdChuong() {
		return idOption;
	}

	public void setIdChuong(int _idChuong) {
		idOption = _idChuong;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * if (savedInstanceState != null) idOption =
		 * savedInstanceState.getInt("mColorRes");
		 */
		// duy: lâ�?y dũ liệu của duy
		// construct the RelativeLayout hieu: Duy m sửa ở đây để nó ra
		// cái trang tương ứng chứa nội dung các đ�? mục của chương đó dựa vào
		// idChuong
		v = new RelativeLayout(getActivity());
		if (idOption == -1) {
			ImageView img = new ImageView(v.getContext());
			img.setImageResource(R.drawable.kakashi);
			img.setScaleType(ScaleType.CENTER_CROP);
			RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			v.addView(img, param);
			Toast.makeText(getActivity(), "Kéo sang để hiện menu",
					Toast.LENGTH_SHORT).show();
			textUser = new TextView(this.v.getContext());
			textUser.setText("Touch here!");
			textUser.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								userInfor = UpdateSuport
										.getStringContent("http://wireless-vietvudanh.c9.io/public/api/v1/user/");
								Log.e("String", "~> "+ userInfor);
								textUser.setText(userInfor);
							} catch (TimeoutException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).start();
					
				}
			});
			
			v.addView(textUser);
			return v;
		} else
			switch (idOption) {
			case 0:
				if (Lop2.md != null && Lop2.md.isPlaying()) {
					Lop2.md.pause();
				}
				tingkerTrue = MediaPlayer
						.create(getActivity(), R.raw.beep_true);
				tingkerFalse = MediaPlayer.create(getActivity(),
						R.raw.beep_false);
				answerRecorder = new int[soCauHoi];// [tra_loi][dap_an]
				for (int i = 0; i < soCauHoi; i++) {
					answerRecorder[i] = 0;
				}
				// ------------------------------------------------------------------------
				SinhCauHoi sch = new SinhCauHoi(idDeMuc,
						(int) SettingBaiTap.getMinute());
				try {

					createdData = false;
					cacCauHoi = new ArrayList<String>(sch.getListCauHoi());
					createdData = true;
					// cacCauHoi.add("xxx");
					Log.e("cacCauHoi co pt 0", ">>>>" + cacCauHoi.get(0));
				} catch (InterruptedException e) {
					Toast.makeText(v.getContext(),
							"Có lỗi nhỏ, vui lòng chạy lại. ",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(v.getContext(),
							"Có lỗi nhỏ, vui lòng chạy lại. ",
							Toast.LENGTH_SHORT).show();
				}

				final int[] dapAn = sch.getListDapAn();
				// Log.e("cacCauHoi", ">>>>"+);
				ABCD = new Randomize();
				ABCD.setRangeValue(1, 4);
				dapAnDung = ABCD.getRandomNumber();
				kqSai = new Randomize();
				kqSai.setRangeValue(1, 9);
				thuTuCauHoi = 0;
				// -----------------------------------------------------------------------
				ImageView bkgr = new ImageView(v.getContext());
				bkgr.setBackgroundResource(R.drawable.background);
				bkgr.setScaleType(ScaleType.CENTER_CROP);

				v.addView(bkgr, new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.MATCH_PARENT));

				final TextView cauHoi = new TextView(v.getContext());
				if (cacCauHoi != null) {
					cauHoi.setText(cacCauHoi.get(0));
					cauHoi.setTextAppearance(v.getContext(),
							android.R.style.TextAppearance_Medium);
				} else {
					Log.e("LOILOILOLLOL", ">\"<");
				}
				cauHoi.setId(6969);
				RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.setMarginEnd(width / 2);
				v.addView(cauHoi, param);// 1
				dapAn_A = new RadioButton(v.getContext());
				dapAn_B = new RadioButton(v.getContext());
				dapAn_C = new RadioButton(v.getContext());
				dapAn_D = new RadioButton(v.getContext());
				// dapAn_A.setText("A) 6");
				dapAn_A.setId(6970);
				dapAn_A.setTextAppearance(v.getContext(),
						android.R.style.TextAppearance_Medium);
				// dapAn_B.setText("B) 7");
				dapAn_B.setId(6971);
				dapAn_B.setTextAppearance(v.getContext(),
						android.R.style.TextAppearance_Medium);
				// dapAn_C.setText("C) 8");
				dapAn_C.setId(6972);
				dapAn_C.setTextAppearance(v.getContext(),
						android.R.style.TextAppearance_Medium);
				// dapAn_D.setText("D) 9");
				dapAn_D.setId(6973);
				dapAn_D.setTextAppearance(v.getContext(),
						android.R.style.TextAppearance_Medium);
				switch (dapAnDung) {
				case 1:
					dapAn_A.setText("A) " + dapAn[0]);
					dapAn_B.setText("B) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					dapAn_C.setText("C) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					dapAn_D.setText("D) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					break;
				case 2:
					dapAn_A.setText("A) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					dapAn_B.setText("B) " + dapAn[0]);
					dapAn_C.setText("C) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					dapAn_D.setText("D) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					break;
				case 3:
					dapAn_A.setText("A) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					dapAn_B.setText("B) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					dapAn_C.setText("C) " + dapAn[0]);
					dapAn_D.setText("D) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					break;
				case 4:
					dapAn_A.setText("A) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					dapAn_B.setText("B) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					dapAn_C.setText("C) "
							+ (dapAn[0] + kqSai.getRandomNumber()));
					dapAn_D.setText("D) " + dapAn[0]);
					break;
				}
				tickDapAn = new RadioGroup(v.getContext());
				tickDapAn.setVerticalFadingEdgeEnabled(true);
				param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.BELOW, cauHoi.getId());
				tickDapAn.addView(dapAn_A, param);
				param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.BELOW, dapAn_A.getId());
				tickDapAn.addView(dapAn_B, param);
				param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.BELOW, dapAn_B.getId());
				tickDapAn.addView(dapAn_C, param);
				param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.BELOW, dapAn_C.getId());
				tickDapAn.addView(dapAn_D, param);

				param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

				Button answer = new Button(v.getContext());
				answer.setText("OK");
				answer.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						Log.e("XXXXXXXXXX", "XXXXXXXXXXXXX");
						tickDapAn.clearCheck();
						tickDapAn.clearFocus();
						dapAn_A.setClickable(true);
						dapAn_B.setClickable(true);
						dapAn_C.setClickable(true);
						dapAn_D.setClickable(true);
						dapAn_A.setTextColor(Color.BLACK);
						dapAn_A.setChecked(false);
						dapAn_B.setTextColor(Color.BLACK);
						dapAn_B.setChecked(false);
						dapAn_C.setTextColor(Color.BLACK);
						dapAn_C.setChecked(false);
						dapAn_D.setTextColor(Color.BLACK);
						dapAn_D.setChecked(false);
						// Because one of question was used:
						Log.e("thuTuCauHoi", ">" + thuTuCauHoi + "|"
								+ cacCauHoi.size());
						if (thuTuCauHoi < soCauHoi) {
							thuTuCauHoi++;
							if (thuTuCauHoi == soCauHoi) {
								gameover = true;
								Menu3_OptionMenuTracNghiem.callFinish();
								F3_LamBaiTap.setIdOption(-2);
								startActivity(new Intent(v.getContext(),
										Changer3_LamBaiWindow.class));
								return;
							}
							newQuestion = true;
							dapAnDung = ABCD.getRandomNumber();
							cauHoi.setText(cacCauHoi.get(thuTuCauHoi));
							switch (dapAnDung) {
							case 1:
								dapAn_A.setText("A) " + dapAn[thuTuCauHoi]);
								dapAn_B.setText("B) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								dapAn_C.setText("C) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								dapAn_D.setText("D) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								break;
							case 2:
								dapAn_A.setText("A) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								dapAn_B.setText("B) " + dapAn[thuTuCauHoi]);
								dapAn_C.setText("C) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								dapAn_D.setText("D) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								break;
							case 3:
								dapAn_A.setText("A) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								dapAn_B.setText("B) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								dapAn_C.setText("C) " + dapAn[thuTuCauHoi]);
								dapAn_D.setText("D) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								break;
							case 4:
								dapAn_A.setText("A) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								dapAn_B.setText("B) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								dapAn_C.setText("C) "
										+ (dapAn[thuTuCauHoi] + kqSai
												.getRandomNumber()));
								dapAn_D.setText("D) " + dapAn[thuTuCauHoi]);
								break;
							}
						} else {
							Toast.makeText(view.getContext(),
									"Chúc mừng bạn đã hoàn thành bài tập.",
									Toast.LENGTH_SHORT).show();
							v.removeAllViews();
							ImageView img = new ImageView(v.getContext());
							img.setImageResource(R.drawable.background_blue);
							img.setScaleType(ScaleType.CENTER_CROP);
							RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
									LayoutParams.MATCH_PARENT,
									LayoutParams.MATCH_PARENT);
							v.addView(img, param);
							TextView kq = new TextView(getActivity());
							kq.setText("Kết quả làm bài");
							kq.setId(1964);
							kq.setTextAppearance(v.getContext(),
									android.R.style.TextAppearance_Large);

							param = new RelativeLayout.LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT);
							param.addRule(RelativeLayout.CENTER_HORIZONTAL);
							param.addRule(RelativeLayout.ALIGN_PARENT_TOP);
							v.addView(kq, param);
							kq = new TextView(v.getContext());
							int soCauTraLoiDung = 0;
							if (answerRecorder != null)
								for (int i = 0; i <= soCauHoi; i++) {
									if (answerRecorder[i] == 1) {
										soCauTraLoiDung++;
									}
								}
							else
								Log.e("NULL", "NULL");
							kq.setText("Số câu trả lời đúng: "
									+ soCauTraLoiDung + "/" + soCauHoi);
							kq.setTextAppearance(v.getContext(),
									android.R.style.TextAppearance_Medium);
							param = new RelativeLayout.LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT);
							param.addRule(RelativeLayout.CENTER_HORIZONTAL);
							param.addRule(RelativeLayout.BELOW, 1964);
							kq.setId(1969);
							v.addView(kq, param);
							param = new RelativeLayout.LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT);
							param.addRule(RelativeLayout.CENTER_HORIZONTAL);
							param.addRule(RelativeLayout.BELOW, 1969);
							ImageView meo = new ImageView(v.getContext());
							meo.setBackgroundResource(R.drawable.meomeo);
							v.addView(meo, param);
							gameover = true;
							Menu3_OptionMenuTracNghiem.callFinish();
							F3_LamBaiTap.setIdOption(-2);
							createdData = true;
							startActivity(new Intent(v.getContext(),
									Changer3_LamBaiWindow.class));
							return;
						}

					}
				});
				v.addView(answer, param);
				tickDapAn
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(RadioGroup group,
									int checkedId) {
								// new question:
								Log.e("Chon dap an :", ">" + checkedId);
								Log.e("thuTu+size", ">" + thuTuCauHoi + "|"
										+ cacCauHoi.size());
								switch (checkedId) {
								case 6970:
									if (dapAnDung == 1) {
										answerRecorder[thuTuCauHoi] = 1;
									}
									break;
								case 6971:
									if (dapAnDung == 2) {
										answerRecorder[thuTuCauHoi] = 1;
									}
								case 6972:
									if (dapAnDung == 3) {
										answerRecorder[thuTuCauHoi] = 1;
									}
									break;
								case 6973:
									if (dapAnDung == 4) {
										answerRecorder[thuTuCauHoi] = 1;
									}
									break;
								default:
									break;
								}

								for (int i = 0; i < 4; i++) {
									tickDapAn.getChildAt(i).setClickable(false);
								}
								switch (dapAnDung) {
								case 1:
									dapAn_A.setTextColor(Color.GREEN);
									break;
								case 2:
									dapAn_B.setTextColor(Color.GREEN);
									break;
								case 3:
									dapAn_C.setTextColor(Color.GREEN);
									break;
								case 4:
									dapAn_D.setTextColor(Color.GREEN);
									break;

								default:
									break;
								}

								if (answerRecorder[thuTuCauHoi] == 1) {
									tingkerTrue.start();
								} else {
									tingkerFalse.start();
								}
							}
						});
				param = new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				param.addRule(RelativeLayout.BELOW, cauHoi.getId());
				// hieu: xử lý kích thước màn hình sau
				Log.e("width", ">" + width);
				param.setMargins(width / 2, 0, 0, 0);
				RelativeLayout khung_hinh = new RelativeLayout(v.getContext());

				ImageView hinh_minh_hoa = new ImageView(v.getContext());
				hinh_minh_hoa.setImageResource(R.drawable.toucan);
				hinh_minh_hoa.setScaleType(ScaleType.CENTER_INSIDE);
				hinh_minh_hoa.setId(11169);
				khung_hinh.setId(6974);

				khung_hinh.addView(hinh_minh_hoa, param);

				// new
				param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

				v.addView(khung_hinh, param);
				hinh_minh_hoa.setScaleType(ScaleType.CENTER_INSIDE);

				param = new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				param.addRule(RelativeLayout.BELOW, cauHoi.getId());
				param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				param.addRule(RelativeLayout.LEFT_OF, hinh_minh_hoa.getId());
				v.addView(tickDapAn, param);
				return v;
			default:
				ImageView img = new ImageView(v.getContext());
				img.setImageResource(R.drawable.background_blue);
				img.setScaleType(ScaleType.CENTER_CROP);
				param = new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				v.addView(img, param);
				TextView kq = new TextView(getActivity());
				kq.setText("Kết quả làm bài");
				kq.setId(1964);
				kq.setTextAppearance(v.getContext(),
						android.R.style.TextAppearance_Large);

				param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.CENTER_HORIZONTAL);
				param.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				v.addView(kq, param);
				kq = new TextView(v.getContext());
				int soCauTraLoiDung = 0;
				Log.e("DO DAI", ">" + answerRecorder.length + "|" + soCauHoi);
				if (answerRecorder != null)
					for (int i = 0; i < soCauHoi; i++) {
						if (answerRecorder[i] == 1) {
							soCauTraLoiDung++;
						}
					}
				else
					Log.e("NULL", "NULL");
				kq.setText("Số câu trả lời đúng: " + soCauTraLoiDung + "/"
						+ soCauHoi);
				param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.CENTER_HORIZONTAL);
				param.addRule(RelativeLayout.BELOW, 1964);
				kq.setId(1969);
				v.addView(kq, param);
				kq = new TextView(getActivity());
				kq.setTextAppearance(v.getContext(),
						android.R.style.TextAppearance_Medium);
				param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.CENTER_HORIZONTAL);
				param.addRule(RelativeLayout.BELOW, 1969);
				kq.setText("Điểm số: "
						+ Float.toString(10 * ((float) soCauTraLoiDung / soCauHoi)));
				kq.setId(1970);
				param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.CENTER_HORIZONTAL);
				param.addRule(RelativeLayout.BELOW, 1969);
				v.addView(kq, param);

				param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.CENTER_HORIZONTAL);
				param.addRule(RelativeLayout.BELOW, 1970);
				ImageView meo = new ImageView(v.getContext());
				meo.setBackgroundResource(R.drawable.meomeo);
				v.addView(meo, param);
				Menu3_OptionMenuTracNghiem.setTestState(false);
				MediaPlayer bravo = MediaPlayer.create(getActivity(),
						R.raw.kid_bravo);
				bravo.start();
				if (Lop2.md.isPlaying() == false) {
					Lop2.md = MediaPlayer.create(v.getContext(), R.raw.nu_cuoi);
					Lop2.md.start();
				}
				return v;
			}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mColorRes", idOption);
	}

	public static void setIdDemuc(String id) {
		F3_LamBaiTap.idDeMuc = id;

	}

	public static void setIdOption(int idOpt) {
		F3_LamBaiTap.idOption = idOpt;
	}

	public static int getIdOption() {

		return idOption;
	}

}
