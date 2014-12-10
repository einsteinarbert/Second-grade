// Duy : 
package inout2nd;

public class Bieu_Thuc {
	private String ID;
	private String ID_Nhom_CH;
	private String ID_Danh_Muc;
	private String Giatri_BT;
	private int min_Gt;
	private int max_Gt;
	private String Rang_buoc;
    private String bien_X;

public Bieu_Thuc(String id , String nhomch, String danhmuc, String gt , int ma , int mi , String rb,String x){
	ID = id;
	this.ID_Nhom_CH = nhomch;
	this.ID_Danh_Muc=danhmuc;
	this.Giatri_BT = gt;
	this.max_Gt=ma;
	this.min_Gt= mi;
	this.Rang_buoc = rb;
	this.bien_X = x;
    	
}
public String getID(){
	return ID;
}
public String getNhomCauHoi(){
	return this.ID_Nhom_CH;
}
public String getDanhMuc(){
	return this.ID_Danh_Muc;
}
public String getBieuThuc(){
	return this.Giatri_BT;
}
public String getRangBuoc(){
	return Rang_buoc;
}
public int getMax(){
	return max_Gt;
}
public int getMin(){
	return min_Gt;
}
public String getBienX(){
	return this.bien_X;
	
}
}