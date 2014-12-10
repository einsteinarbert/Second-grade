package com.lop2.fragments;

import inout2nd.Chuong;
import inout2nd.IOData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lop2.anim.ListAdapterCustom;

public class Menu1_ChuongFragment extends ListFragment {
	IOData getData = new IOData();
	int len;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// hieu: lấy Danh sách menu từ array.xml
		// String[] colors = getResources().getStringArray(R.array.color_names);
		// add new:
		
		String[] CacChuong = getData.getChuong();
		len = CacChuong.length;
		// ---------
		Log.e("StringColor", CacChuong.length + "|");
		/*ArrayAdapter<String> ChuongAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1,
				android.R.id.text1, CacChuong);*/
		//setListAdapter(ChuongAdapter);
		ListAdapterCustom DeMucAdapter = new ListAdapterCustom(
				getLayoutInflater(savedInstanceState), CacChuong);
		int iDdr[] = new int[1];
		iDdr[0] = R.drawable.tho1;
		DeMucAdapter.setImageList1(iDdr);
		setListAdapter(DeMucAdapter);
		
	}

	// hieu: xử lý onclick vào mục lục tại đây:
	@Override
	public void onListItemClick(ListView lv, View v, int idChuong, long id) {
		Fragment newContent = null;
		String id_chuong = getData.getListChuong().get(idChuong).getIdChuong();
		newContent = new F1_DeMucFragment(id_chuong);
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		Log.e("Switch", "Switch");
		if (getActivity() == null) {
			Log.e("NULL", "NULL");
			return;
		}
		if (getActivity() instanceof Changer1_FragmentChangeActivity) {
			Log.e("GIua", "Giua");
			Changer1_FragmentChangeActivity fca = (Changer1_FragmentChangeActivity) getActivity();
			fca.switchContent(fragment);
		} else if (getActivity() instanceof ResponsiveUIActivity) {
			Log.e("CUOI", "CUOI");
			ResponsiveUIActivity ra = (ResponsiveUIActivity) getActivity();
			ra.switchContent(fragment);
		}
		Log.e("Switch1", "Switch1");
	}
	
}

	