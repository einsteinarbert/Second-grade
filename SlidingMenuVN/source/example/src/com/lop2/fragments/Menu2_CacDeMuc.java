package com.lop2.fragments;

import java.util.ArrayList;

import inout2nd.De_Muc;
import inout2nd.IOData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lop2.anim.ListAdapterCustom;

public class Menu2_CacDeMuc extends ListFragment {

	 private String idChuong;
	 private String idDeMuc;
	 ArrayList<De_Muc> de_muc;
	 public Menu2_CacDeMuc(String chuong , String demuc){
		 idChuong = chuong;
		 idDeMuc = demuc;
		 
	 }
	 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// hieu: lấy Danh sách menu từ array.xml
			//String[] colors = getResources().getStringArray(R.array.color_names);
		// add new:
		 
 
		IOData getData = new IOData();
		//String[] CacMuc = getData.getDeMuc();
		de_muc = new ArrayList<De_Muc>();		
	    de_muc = getData.getListDeMuc(this.idChuong);
	    String[] menuDemuc = new String[de_muc.size()];
	    String bbt = Integer.toString(de_muc.size());
	    Log.e("----De muc tromg menu2 :",bbt); 
	    ArrayList< String> de_muc_list = new ArrayList<String>();
	    for(int i= 0; i<de_muc.size();i++){
	    	de_muc_list.add(de_muc.get(i).getND());
	    	menuDemuc[i] = de_muc_list.get(i);
	    }
		//---------
		//Log.e("StringColor", CacMuc.length+"|");
		/*ArrayAdapter<String> DeMucAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1,de_muc_list);
		setListAdapter(DeMucAdapter);*/
	    ListAdapterCustom DeMucAdapter = new ListAdapterCustom(
				getLayoutInflater(savedInstanceState), menuDemuc);
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
		setListAdapter(DeMucAdapter);
	}
	
	// hieu: xử lý onclick vào mục lục tại đây:
	@Override
	public void onListItemClick(ListView lv, View v, int indexIDdemuc, long id) {
		Fragment newContent = null;
		//Log.e("newContent", "option="+idDemuc);
		String _id_demuc = de_muc.get(indexIDdemuc).getID();
		newContent = new F2_NoiDungKienThucFragment(_id_demuc);
		F3_LamBaiTap.setIdDemuc(_id_demuc);
		Log.e("id de muc o day", _id_demuc); // loi
		if (newContent != null){
			Log.e("Ko NULL", "KO NULL");
			switchFragment(newContent);
		}
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		Log.e("Switch", "Switch");
		if (getActivity() == null){
			Log.e("NULL", "NULL");
			return;
		}
		if (getActivity() instanceof Changer2_KienThucDeMucFragment) {
			Log.e("GIua", "Giua");
			Changer2_KienThucDeMucFragment fca = (Changer2_KienThucDeMucFragment) getActivity();
			fca.switchContent(fragment);
		} else if (getActivity() instanceof ResponsiveUIActivity) {
			Log.e("CUOI", "CUOI");
			ResponsiveUIActivity ra = (ResponsiveUIActivity) getActivity();
			ra.switchContent(fragment);
		}
		Log.e("Switch1", "Switch1");
	}


}
