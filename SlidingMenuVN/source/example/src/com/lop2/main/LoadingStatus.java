package com.lop2.main;

import com.jeremyfeinstein.slidingmenu.example.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class LoadingStatus extends Activity{

	ProgressBar load;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		load = (ProgressBar) findViewById(R.id.progressBar1);
		load.setIndeterminate(true);
		load.scrollTo(5, 10);

		Log.e("time:", "running");
		//final Intent i = new Intent(LoadDing.this, MainActivity.class);
		//startActivity(i);
		final int totalProgressTime = 100;

		final Thread t = new Thread() {

			@Override
			public void run() {

				int jumpTime = 0;

				while (jumpTime < totalProgressTime) {
					try {
						sleep(200);
						jumpTime += 5;
						load.setProgress(jumpTime);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				final Intent i = new Intent(LoadingStatus.this, SettingBaiTap.class);
				startActivity(i);

			}
		};
		t.start();
	}
}
