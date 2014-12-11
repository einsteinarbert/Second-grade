package com.lop2.fragments;

import java.util.ArrayList;

import inout2nd.De_Muc;
import inout2nd.IOData;
import inout2nd.SetTing;
import inout2nd.User;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lop2.anim.ListAdapterCustom;

import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class F1_DeMucFragment extends Fragment {

	private String idChuong = "";
	// bă�?t sự kiện click vào item
	private ArrayList<De_Muc> de_muc;

	public F1_DeMucFragment() {

	}

	public F1_DeMucFragment(String idChuong) {
		this.idChuong = idChuong;
		setRetainInstance(true);
	}

	public String getIdChuong() {
		return idChuong;
	}

	public void setIdChuong(String _idChuong) {
		idChuong = _idChuong;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null)
			idChuong = savedInstanceState.getString("mColorRes");
		RelativeLayout v = new RelativeLayout(getActivity());


		// construct the RelativeLayout hieu: Duy m sửa ở đây để nó ra
		// cái trang tương ứng chứa nội dung các đ�? mục của chương đó dựa vào
		// idChuong

		ListView Lv = new ListView(v.getContext());
		// TODO txt sẽ là listView Adapter
		IOData getData = new IOData();
		// duy test
		User user = new User("1","duy","30","1","2","3","4","5");
		getData.inserUser(user);
		SetTing st= new SetTing("0", "0", "1", "1") ;
		getData.updateSetting(st);
		////////// end tets
		de_muc = getData.getListDeMuc(idChuong);
         
		String[] menuDemuc = new String[de_muc.size()];
		ArrayList<String> list_de_muc = new ArrayList<String>();
		for (int i = 0; i < de_muc.size(); i++) {
			list_de_muc.add(de_muc.get(i).getND());
			menuDemuc[i] ="  "+ list_de_muc.get(i);
		}

		/*
		 * ArrayAdapter<String> DemucAdapter = new ArrayAdapter<String>(
		 * v.getContext(), android.R.layout.simple_list_item_1,
		 * android.R.id.text1, list_de_muc); Log.e("???", "??? " + idChuong);
		 * //Lv.setAdapter(DemucAdapter);
		 */
		ListAdapterCustom DeMucAdapter = new ListAdapterCustom(
				getLayoutInflater(savedInstanceState), menuDemuc);
		//int iDdr[];
		int len = de_muc.size();
		int idImg1[] = new int[len];
		int idImg2[] = new int[len];
		int idImg3[] = new int[len];
		int idImg4[] = new int[len];
		String deMucNum;
		int lengthString=0,firstId = R.drawable.n0;
		for (int i = 0; i < len; i++) {
			deMucNum = de_muc.get(i).getID();
			lengthString = deMucNum.length();
			switch (lengthString) {
			case 3:
				idImg1[i] = firstId+Integer.parseInt(deMucNum.charAt(0)+"");
				idImg2[i] = R.drawable.dot;
				idImg3[i] = firstId+Integer.parseInt(deMucNum.charAt(2)+"");
				idImg4[i] = 0;
				break;
			case 4:
				idImg1[i] = firstId+Integer.parseInt(deMucNum.charAt(0)+"");
				idImg2[i] = R.drawable.dot;
				idImg3[i] = firstId+Integer.parseInt(deMucNum.charAt(2)+"");
				idImg4[i] = firstId+Integer.parseInt(deMucNum.charAt(3)+"");
			default:
				break;
			}
		}
		DeMucAdapter.setImageAllLists(idImg1, idImg2, idImg3, idImg4);
		Lv.setAdapter(DeMucAdapter);

		Lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int id_de_muc, long arg3) {
				
				// TODO Auto-generated method stub
				Intent intent = new Intent(arg0.getContext(),
						Changer2_KienThucDeMucFragment.class);
				//test duy 
				Bitmap imag =  BitmapFactory.decodeResource(getResources(), R.drawable.so8);
				IOData getData = new IOData();
				getData.updateImageDeMuc(de_muc.get(id_de_muc).getID(), imag);
				//
				intent.putExtra("idChuong", idChuong);
				intent.putExtra("idDemuc", de_muc.get(id_de_muc).getID());
				F3_LamBaiTap.setIdDemuc(de_muc.get(id_de_muc).getID());
				Log.e("iD_Demuc", de_muc.get(id_de_muc).getID());

				startActivity(intent);

			}
		});

		/*
		 * .setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { //TODO hieu: next sang trang
		 * hướng dẫn kiến thức Intent intent = new Intent(v.getContext(),
		 * Changer2_KienThucDeMucFragment.class); startActivity(intent); } });
		 */
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		v.addView(Lv, param);
		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("mColorRes", idChuong);
	}

}
