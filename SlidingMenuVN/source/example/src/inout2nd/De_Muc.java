package inout2nd;

import android.graphics.Bitmap;

public class De_Muc {
	String ID;
	String ND;
	String ID_chuong;
	Bitmap Huong_Dan;
	
	public De_Muc(String id ,String nd,String id_ch,Bitmap huongdan){
		ID = id;
		ND = nd;
		ID_chuong = id_ch;
		Huong_Dan  = huongdan;
	
	}
	public String getID(){
		return ID;
		
	}
	public String getND(){
		return ND;
	}
	public String geIdChuong(){
		return ID_chuong;
		
	}
	public Bitmap getHuongDan(){
		return Huong_Dan;
	}

}
