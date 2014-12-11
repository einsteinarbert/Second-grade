package inout2nd;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.util.Log;

import com.lop2.main.Lop2;

public class IOData {
	
	//duy:
		private Database DbHelper =Lop2.DbHelper;
		//public Context contx = ExampleListActivity;
		 // các biến để để lưu tạm thời các dữ liệu trong csdl để sử lý
		
	    private ArrayList<Chuong> chuong_list ;
	    private ArrayList<De_Muc> de_muc_list ;
	    private ArrayList<Bieu_Thuc> bieu_thuc_list;
	    private ArrayList<Dau_Bai> dau_bai;
	    private ArrayList<Than_Bai> than_bai;
	    private ArrayList<Trac_Nghiem> trac_nghiem_list ;
	    // biến lắp đc sử dụng để lặp các biểu thức thành 1 vong trong dự vào danh mục
	 // duy: khởi tạo 1 làn để lấy dữ tất cả dữ liệu sẽ được lưu trong stactic này
	    public static IOData iodata;
	
	// hieu: Duy sửa data trong này để get data từ Sqlite
	    private String[] chuong;
	    private String[] de_muc;
	    private String[] bieu_thuc;// use to randrom expression
	    private String[] trac_nghiem;
	    private String[] menu_trac_nghiem;
	public IOData() {
		//bieu_thuc_list = new ArrayList<Bieu_Thuc>();
	    chuong_list = new ArrayList<Chuong>();
	   // de_muc_list = new ArrayList<De_Muc>();
	    trac_nghiem_list = new ArrayList<Trac_Nghiem>();
	    dau_bai  =  new ArrayList<Dau_Bai>();
	    than_bai = new ArrayList<Than_Bai>();
	    
	    Log.e("xxx", "xxx");
		chuong_list = DbHelper.getall_Chuong(); 
		chuong = new String[chuong_list.size()];
		for(int i=0; i<chuong_list.size(); i++){
			chuong[i] = new String("Chương "+chuong_list.get(i).ID+": "+chuong_list.get(i).Nd);
		}
		
		de_muc = new String[5];
		for(int i=0; i<5; i++){
			de_muc[i] = new String("Mục "+(i+1)+ ". Phép cộng với " + (i+2));
		}
		menu_trac_nghiem = new String[6];
		menu_trac_nghiem[0] = "Bắt đầu làm bài";
		menu_trac_nghiem[1] = "Kết thúc";
		menu_trac_nghiem[2] = "Xem điểm";
		menu_trac_nghiem[3] = "Chơi game";
		menu_trac_nghiem[4] = "Chọn hình thức bài tập";
		menu_trac_nghiem[5] = "Quay lại mục lục";
	}
	public void setChuong(String[] data_Chuong){
		chuong = data_Chuong;
	}
	
	public void setDeMuc(String[] data_DeMuc){
		de_muc = data_DeMuc;
	}
	
	public void setBieuThuc(String[] data_BieuThuc){
		bieu_thuc = data_BieuThuc;
	}
	
	public void setTracNghiem(String[] data_TracNghiem){
		trac_nghiem = data_TracNghiem;
	}
	
	public void setMenuTracNghiem(String[] menuTracNghiem){
		menu_trac_nghiem = menuTracNghiem;
	}
	//-------------
	public String[] getChuong(){
		return chuong;
	}
	
	public String[] getDeMuc(){
		return de_muc;
	}
	
	public String[] getBieuThuc(){
		return bieu_thuc;
	}
	
	public String[] getTracNghiem(){
		
		return trac_nghiem;
	}
	
	public String[] getMenuTracNghiem(){
		return menu_trac_nghiem;
	}
	public  ArrayList<Chuong> getListChuong(){
		return chuong_list;	
	}
	public ArrayList<Trac_Nghiem> getListTracNghiem(String nhomCauHoi){
		trac_nghiem_list = new ArrayList<Trac_Nghiem>();
		trac_nghiem_list = DbHelper.getll_trac_nghiem(nhomCauHoi) ;
		return trac_nghiem_list;
		
	}
	public ArrayList<De_Muc> getListDeMuc(String id_chuong){
		de_muc_list = new ArrayList<De_Muc>();
		de_muc_list = DbHelper.getall_de_muc(id_chuong);
		return de_muc_list;
	}
	public ArrayList<Bieu_Thuc> getListBieuThuc(String id_demuc){
		bieu_thuc_list = new ArrayList<Bieu_Thuc>();
		bieu_thuc_list = DbHelper.getall_Bieu_Thuc(id_demuc);
		return bieu_thuc_list;
			
	}
	public ArrayList<Dau_Bai> getDauBai(){
		 dau_bai  =  new ArrayList<Dau_Bai>();
		 dau_bai = DbHelper.getAll_DauBai();
		return dau_bai;
	}
	public ArrayList<Than_Bai> getThanBai(String id_dau_bai ,String bieu_thuc ){
		than_bai = new ArrayList<Than_Bai>();
		than_bai = DbHelper.get_ThauBai(id_dau_bai, bieu_thuc);
		//String size_thanbai = Integer.toString(than_bai.size());
		//Log.e("Sicau than bai day xem no nhu", size_thanbai);
		return than_bai;
		
	}
	public void inserUser(User user){
		 DbHelper.InsertUser(user);
		
	}
	public void updateSetting(SetTing st){
	  DbHelper.upUerSetTing(st);
		
	}
	public void updateImageDeMuc(String idDeMuc , Bitmap image){
		  DbHelper.updateImageDeMuc(idDeMuc, image);
			
		}
	public De_Muc getde_muc(String idDeMuc){
		 return DbHelper.get_de_muc(idDeMuc);
		}
}
