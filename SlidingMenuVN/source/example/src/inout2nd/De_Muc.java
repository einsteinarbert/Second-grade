package inout2nd;

public class De_Muc {
	String ID;
	String ND;
	String ID_chuong;
	String Huong_Dan;
	
	public De_Muc(String id ,String nd,String id_ch,String huongdan){
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
	public String getHuongDan(){
		return Huong_Dan;
	}

}
