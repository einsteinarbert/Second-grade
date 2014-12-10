package com.lop2.main;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lop2.fragments.Changer3_LamBaiWindow;
import com.lop2.fragments.F3_LamBaiTap;
import com.lop2.fragments.Menu3_OptionMenuTracNghiem;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class SettingBaiTap extends Activity {
	// Thời gian làm bài:
	private static int minute = 5;
	private String[] nums = { "5", "10", "15", "30", "45", "60", "90", "120" };
	private static int numberPickerState = 0;
	private CheckBox checkBox[];
	private static boolean[] checkTable = {true,false,false,false};
	int viewWidth, viewHeight;
	// 1 2 3 4 5... tương ứng với các hình thức được chọn
	// vd: 1: biểu thức trắc nghiệm, 2: hình vẽ ..., => level = 12
	private NumberPicker np;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// this.setContentView(R.layout.chon_bai_kiemtra);
		np = new NumberPicker(getApplicationContext());
		np.setMaxValue(nums.length - 1);
		np.setMinValue(0);
		np.setWrapSelectorWheel(false);
		np.setDisplayedValues(nums);
		np.setBackgroundColor(Color.BLACK);
		np.setId(1992);
		np.setValue(numberPickerState);
		np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		np.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				minute = Integer.parseInt(nums[view.getValue()]);
				numberPickerState = view.getValue();
			}
		});
		checkBox = new CheckBox[4];
		checkBox[0] = new CheckBox(getApplicationContext());
		checkBox[0].setText("Biểu thức");
		checkBox[1] = new CheckBox(getApplicationContext());
		checkBox[1].setText("Tìm x");
		checkBox[2] = new CheckBox(getApplicationContext());
		checkBox[2].setText("Hình học");// vẽ hình, ...
		checkBox[3] = new CheckBox(getApplicationContext());
		checkBox[3].setText("Toán đố");
		// Title Thời gian làm bài
		TextView title = new TextView(getApplicationContext());
		title.setText("Thời lượng");
		title.setTextColor(Color.BLACK);
		
		RelativeLayout rlt = new RelativeLayout(this);

		ImageView bkr = new ImageView(getApplicationContext());
		bkr.setBackgroundResource(R.drawable.background);
		bkr.setScaleType(ScaleType.CENTER_CROP);
		bkr.setImageAlpha(100);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		
		rlt.addView(bkr,params);

	    params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		rlt.addView(np, params);
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.BELOW, np.getId());
		rlt.addView(title, params);
		Log.e("IDnp", np.getId() + "?");
		// checkbox
		
		int id = Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
		
		for (int i = 0; i < 4; i++) {
			checkBox[i].setId(1993 + i);
			checkBox[i].setTextColor(Color.BLACK);
			checkBox[i]
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							checkTable[buttonView.getId()-1993]=isChecked;
						}
					});

			checkBox[i].setChecked(checkTable[i]);
			checkBox[i].setButtonDrawable(id);
		}
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.RIGHT_OF, np.getId());
		rlt.addView(checkBox[0], params);
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.RIGHT_OF, 1993);
		rlt.addView(checkBox[1], params);
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.BELOW, 1994);
		params.addRule(RelativeLayout.RIGHT_OF, np.getId());
		rlt.addView(checkBox[2], params);
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.RIGHT_OF, 1995);
		params.addRule(RelativeLayout.BELOW, 1994);
		rlt.addView(checkBox[3], params);
		TextView minLable = new TextView(getApplicationContext());
		// Phút
		minLable.setText("Phút");
		minLable.setId(2000);
		minLable.setTextColor(Color.BLACK);
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.RIGHT_OF, np.getId());
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		rlt.addView(minLable, params);

		TextView title1 = new TextView(getApplicationContext());
		title1.setText("Nội dung:");
		title1.setTextColor(Color.BLACK);
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		rlt.addView(title1, params);

		Button ok = new Button(getApplicationContext());
		ok.setText("OK");
		ok.setBackgroundColor(Color.LTGRAY);
		ok.setTextColor(Color.BLACK);
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.addRule(RelativeLayout.BELOW, minLable.getId());
		rlt.addView(ok, params);

		setContentView(rlt);

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(),
						Changer3_LamBaiWindow.class);
				Menu3_OptionMenuTracNghiem.resetState();
				Menu3_OptionMenuTracNghiem.setTestState(false);
				F3_LamBaiTap.setIdOption(-1);
				startActivity(i);
			}
		});
	}

	public static long getMinute() {

		return minute;
		//return 1;
	}
}
