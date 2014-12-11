package inout2nd;

public class User {
   String  id;  
   String name;
   String dateLambai;
   String deMucLam;
   String thoiGianLam ;
   String tongThoiGian;
   String soDiem;
   String TongDiem;
 
   
   public User (String id ,String name ,String date ,String demuclam 
		   		,String timelam,String tongtime ,String diem ,String tongdiem ){
	   this.id = id;
	   this.name = name;
	   this.dateLambai = date;
	   this.deMucLam = demuclam;
	   this.thoiGianLam = timelam;
	   this.tongThoiGian = tongtime;
	   this.soDiem = diem;
	   this.TongDiem = tongdiem;   
   }
   public String getThoiGianLam(){
	   return this.thoiGianLam;
   } 
   public String getTongThoiGianLam(){
	   return this.tongThoiGian;
   } 
   public String getName(){
	   return this.name;
   } 
   public String getDateLamBai(){
	   return this.dateLambai;
   } 
   public String getID(){
	   return this.id;
   } 
   public String dateLamBai(){
	   return this.dateLambai;
	   
   } 
   public String getDiem(){
	   return this.soDiem;
	   
	   
   } 
   public String getTongDiem(){
	   return this.TongDiem;
	   
	   
   } 
   public String getDeMucLam(){
	   return this.deMucLam;
	   
	   
   } 
}
