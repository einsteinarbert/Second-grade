package inout2nd;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;



public class Database {
	

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "Data_BTL.db";
	private static final int DATABASE_VERSION = 2;
	private static final String CHUONG = "Chuong";
	private static final String BIEU_THUC = "Bieu_Thuc";
	private static final String DE_MUC = "De_Muc";
	private static final String NHOM_CAU_HOI = "Nhom_Cau_Hoi";
	private static final String TRAC_NHIEM = "Trac_Nghiem";
	private static final String THAN_BAI = "than_bai";
	private static final String  DAU_BAI = "Dau_bai";
	private static final String USER = "User";
	private static final String SETTING = "SetTing";
	

	private final Context mCtx;
	// create an empty array list with an initial capacity
	ArrayList<Bieu_Thuc> post = new ArrayList<Bieu_Thuc>();
	ArrayList<Chuong> chuong = new ArrayList<Chuong>();
	ArrayList<De_Muc> de_muc = new ArrayList<De_Muc>();
	ArrayList<Trac_Nghiem> trac_nghiem = new ArrayList<Trac_Nghiem>(); 
	ArrayList<Dau_Bai> dau_bai = new ArrayList<Dau_Bai>();
	ArrayList<Than_Bai> than_bai = new ArrayList<Than_Bai>();
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}

	}

	public void Reset() {
		mDbHelper.onUpgrade(this.mDb, 1, 1);
	}

	public Database(Context ctx) {
		mCtx = ctx;
		mDbHelper = new DatabaseHelper(mCtx);
	}

	public Database open() throws SQLException {
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}


	// To get list of employee details
	public ArrayList<Bieu_Thuc> getall_Bieu_Thuc(String id_demuc) throws SQLException {
		ArrayList<Bieu_Thuc> post = new ArrayList<Bieu_Thuc>();
		Cursor cur = mDb.query(true, BIEU_THUC, new String[] { "ID","ID_Nhom","ID_Danh_Muc","Value","Min_GT","Max_GT","DG_Rang_Buoc","Bien_X" }, null, null, null, null, null, null);
		if (cur.moveToFirst()) {
			do {
				String id = cur.getString(cur.getColumnIndex("ID"));
				int mi = cur.getInt(cur.getColumnIndex("Min_GT"));
				int ma = cur.getInt(cur.getColumnIndex("Max_GT"));
				String id_nhom = cur.getString(cur.getColumnIndex("ID_Nhom"));
				String id_dm  = cur.getString(cur.getColumnIndex("ID_Danh_Muc"));
				String gt  = cur.getString(cur.getColumnIndex("Value"));
				String rb = cur.getString(cur.getColumnIndex("DG_Rang_Buoc"));
				String bienx = cur.getString(cur.getColumnIndex("Bien_X"));
				
				if(id_demuc.equals(id_dm))
					post.add(new Bieu_Thuc(id ,id_nhom,id_dm,gt,ma, mi,rb,bienx));
				
			} while (cur.moveToNext());
		}
		return post;
	}
	public ArrayList<Chuong> getall_Chuong() throws SQLException {
		ArrayList<Chuong> chuong = new ArrayList<Chuong>();
		Cursor cur = mDb.query(true, CHUONG, new String[] { "ID","Noi_Dung"}, null, null, null, null, null, null);
		if (cur.moveToFirst()) {
			do {
				String id = cur.getString(cur.getColumnIndex("ID"));
				String nd = cur.getString(cur.getColumnIndex("Noi_Dung"));
				chuong.add(new Chuong(id ,nd));
			} while (cur.moveToNext());
		}
		return chuong;
	}
	
	public ArrayList<De_Muc> getall_de_muc(String id_chuong) throws SQLException{
		ArrayList<De_Muc> de_muc = new ArrayList<De_Muc>();
		Cursor cur = mDb.query(true, DE_MUC, new String[]{"ID","Noi_Dung","ID_Chuong","Huong_Dan"} ,null, null, null, null, null, null);
		
		if(cur.moveToFirst()){
			do{
			String id = cur.getString(cur.getColumnIndex("ID"));
			String nd = cur.getString(cur.getColumnIndex("Noi_Dung"));
			String id_ch = cur.getString(cur.getColumnIndex("ID_Chuong"));
			//String huong_dan = cur.getString(cur.getColumnIndex("Huong_Dan"));
			Bitmap huong_dan = null;
			//String fk = cur.getString(cur.getColumnIndex("DG_Rang_Buoc"));
			if(id_chuong.equals(id_ch)== true){
				de_muc.add(new De_Muc(id,nd,id_ch,huong_dan));
				
			}
			}while(cur.moveToNext());
			
		}
		
		return de_muc;
		
	}
   	public ArrayList<Trac_Nghiem> getll_trac_nghiem(String nhomCauHoi) throws SQLException{
   		ArrayList<Trac_Nghiem> trac_nghiem = new ArrayList<Trac_Nghiem>(); 
   		Cursor cur = mDb.query(true, TRAC_NHIEM, new String[]{"ID","Noi_Dung","ID_Nhom_Cau_Hoi"} ,null, null, null, null, null, null);
		if(cur.moveToFirst()){
			do{
			String id_ch = cur.getString(cur.getColumnIndex("ID_Nhom_Cau_Hoi"));
			String nd = cur.getString(cur.getColumnIndex("Noi_Dung"));
			if(nhomCauHoi.equals(id_ch))
			trac_nghiem.add(new Trac_Nghiem(id_ch,nd));
				
			}while(cur.moveToNext());
			
		}
		
		return trac_nghiem;
   	}
	
   	public ArrayList<Dau_Bai> getAll_DauBai() throws SQLException{
   		ArrayList<Dau_Bai> dau_bai = new ArrayList<Dau_Bai>();
   		Cursor cur = mDb.query(true, DAU_BAI, new String[]{"ID","Dau_bai"} ,null, null, null, null, null, null);
		if(cur.moveToFirst()){
			do{
			String id = cur.getString(cur.getColumnIndex("ID"));
			String daubai = cur.getString(cur.getColumnIndex("Dau_bai"));
			dau_bai.add(new Dau_Bai(id,daubai));
				
			}while(cur.moveToNext());
			
		}
		
		return dau_bai;
   	 }
	public ArrayList<Than_Bai> get_ThauBai(String id_dau_bai,String bieu_thuc) throws SQLException{
		 than_bai = new ArrayList<Than_Bai>();
   		Cursor cur = mDb.query(true, THAN_BAI, new String[]{"ID","Than_Bai","Bieu_Thuc","ID_Dau_Bai"} ,null, null, null, null, null, null);
		if(cur.moveToFirst()){
			do{
			String id = cur.getString(cur.getColumnIndex("ID"));
			String thanbai = cur.getString(cur.getColumnIndex("Than_Bai"));
			String bieuthuc = cur.getString(cur.getColumnIndex("Bieu_Thuc"));
			String id_daubaubai = cur.getString(cur.getColumnIndex("ID_Dau_Bai"));
			if(id_dau_bai.equals(id_daubaubai)== true && bieu_thuc.equals(bieuthuc)== true)
						than_bai.add(new Than_Bai(thanbai));
				
			}while(cur.moveToNext());
			
		 }
		String size_thanbai = Integer.toString(than_bai.size());
		Log.e("Sicau than bai day nha : ---muc database", size_thanbai);
		return than_bai;
   	 }


	public void InsertUser(User user)throws SQLException{
			ContentValues values=new ContentValues();
			values.put("ID",user.getID());
			values.put("Name",user.getName());
			values.put("dateLamBai",user.getDateLamBai() );
			values.put("deMucLam", user.getDeMucLam());
			values.put("thoiGianLam", user.getThoiGianLam());
			values.put("tongThoiGian",user.getTongThoiGianLam() );
			values.put("DiemSo",user.getDiem() );
			values.put("tongDiem", user.getTongDiem());
			String msg="";
			
			if(mDb.insert(USER, null, values)==-1){
				  msg="Failed to insert record";
			}
			else{
				msg="insert record is successful";
			}	
		  Log.e("ket qua insert cua user",msg);
	}
public User getUserFirt() throws SQLException {
	
  	Cursor cur = mDb.query(true, USER, new String[] { "ID","Name","dateLamBai","deMucLam","thoiGianLam","tongThoiGian","DiemSo","tongDiem"}, null, null, null, null, null, null);
		if (cur.moveToFirst()) {
			
			String id = cur.getString(cur.getColumnIndex("ID"));
			String name = cur.getString(cur.getColumnIndex("Name"));
			String dateLam = cur.getString(cur.getColumnIndex("dateLamBai"));
			String demuc  = cur.getString(cur.getColumnIndex("deMucLam"));
			String timelam  = cur.getString(cur.getColumnIndex("thoiGianLam"));
			String timetong = cur.getString(cur.getColumnIndex("tongThoiGian"));
			String diem = cur.getString(cur.getColumnIndex("DiemSo"));
			String diemTong = cur.getString(cur.getColumnIndex("tongDiem"));
			//int age = cur.getInt(cur.getColumnIndex(EMP_AGE));
			cur.close();
			return new User(id ,name,dateLam,demuc,timelam,timetong,diem,diemTong);
		}

		cur.close();
		return null;
	}
 public void upUerSetTing(SetTing st){
	 ContentValues values=new ContentValues();
		values.put("Dang_Hinh_Hoc", st.getHinhHoc());
		values.put("Dang_Bieu_Thuc",  st.getHinhHoc());
		values.put("Dang_Toan_Do", st.getToanDo());
		values.put("Dang_Tim_X", st.getTimX());
		String msg="";
		int ret = mDb.update(SETTING, values,null,null);
	    Log.e("updatesetting thành cong la 1 else la 0","kq=="+ret);   
 }
 public void updateImageDeMuc(String id_demuc , Bitmap image){
	 	ContentValues values=new ContentValues();
		values.put("Huong_Dan",Untility.getBytes(image));
		String msg="";
		int ret = mDb.update(DE_MUC, values,"ID=?",new String[]{id_demuc});
	    Log.e("update Image de muc thành cong la 1 else la 0","kq=="+ret);   
 }
 
 public De_Muc get_de_muc(String id_demuc) throws SQLException{
		Cursor cur = mDb.query(true, DE_MUC, new String[]{"ID","Noi_Dung","ID_Chuong","Huong_Dan"} ,null, null, null, null, null, null);
		
		if(cur.moveToFirst()){
			do{
			String id = cur.getString(cur.getColumnIndex("ID"));
			String nd = cur.getString(cur.getColumnIndex("Noi_Dung"));
			String id_ch = cur.getString(cur.getColumnIndex("ID_Chuong"));
			 if(cur.getBlob(cur.getColumnIndex("Huong_Dan"))!= null){
		     	byte[] blob = cur.getBlob(cur.getColumnIndex("Huong_Dan"));
		     	Bitmap huong_dan = Untility.getPhoto(blob);
		     	if(id_demuc.equals(id)== true){
					return new De_Muc(id,nd,id_ch,huong_dan);
					
				}
			 }
			//String huong_dan = "Tự mà học đi em ak :p";
			//String fk = cur.getString(cur.getColumnIndex("DG_Rang_Buoc"));
			
			}while(cur.moveToNext());
			
		}
		
		return null;
		
	}
 
}
	
