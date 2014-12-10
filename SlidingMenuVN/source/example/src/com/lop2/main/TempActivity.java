package com.lop2.main;


import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.jeremyfeinstein.slidingmenu.example.R;
import com.lop2.fragments.Changer1_FragmentChangeActivity;

public class TempActivity extends SherlockPreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
		
//		Crittercism.init(getApplicationContext(), "508ab27601ed857a20000003");
		this.addPreferencesFromResource(R.xml.main);
		Class<?> cls = Changer1_FragmentChangeActivity.class;
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	/*@Override
	public boolean onPreferenceTreeClick(PreferenceScreen screen, Preference pref) {
		Class<?> cls = null;
		String title = pref.getTitle().toString();
		if (title.equals(getString(R.string.properties))) {
			cls = PropertiesActivity.class;	
		} else if (title.equals(getString(R.string.attach))) {
			cls = AttachExample.class;
		} else if (title.equals(getString(R.string.changing_fragments))) {
			cls = FragmentChangeActivity.class;
		} else if (title.equals(getString(R.string.left_and_right))) {
			cls = LeftAndRightActivity.class;
		} else if (title.equals(getString(R.string.responsive_ui))) {
			cls = ResponsiveUIActivity.class;
		} else if (title.equals(getString(R.string.viewpager))) {
			cls = ViewPagerActivity.class;
		} else if (title.equals(getString(R.string.title_bar_slide))) {
			cls = SlidingTitleBar.class;
		} else if (title.equals(getString(R.string.title_bar_content))) {
			cls = SlidingContent.class;
		} else if (title.equals(getString(R.string.anim_zoom))) {
			cls = CustomZoomAnimation.class;
		} else if (title.equals(getString(R.string.anim_scale))) {
			cls = CustomScaleAnimation.class;
		} else if (title.equals(getString(R.string.anim_slide))) {
			cls = CustomSlideAnimation.class;
		}
		Intent intent = new Intent(this, cls);
		startActivity(intent);
		return true;
	}*/

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.github) {
			Util.goToGitHub(this);
			return true;
		} else if (itemId == R.id.about) {
			new AlertDialog.Builder(this)
			.setTitle(R.string.about)
			.setMessage(Html.fromHtml(getString(R.string.about_msg)))
			.show();
		} else if (itemId == R.id.licenses) {
			new AlertDialog.Builder(this)
			.setTitle(R.string.licenses)
			.setMessage(Html.fromHtml(getString(R.string.apache_license)))
			.show();
		} else if (itemId == R.id.contact) {
			final Intent email = new Intent(android.content.Intent.ACTION_SENDTO);
			String uriText = "mailto:jfeinstein10@gmail.com" +
					"?subject=" + "SlidingMenu Demos Feedback";
			email.setData(Uri.parse(uriText));
			try {
				startActivity(email);
			} catch (Exception e) {
				Toast.makeText(this, R.string.no_email, Toast.LENGTH_SHORT).show();
			}
		}
		return super.onOptionsItemSelected(item);
	}*/

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.example_list, menu);
		return true;
	}*/
	
}
