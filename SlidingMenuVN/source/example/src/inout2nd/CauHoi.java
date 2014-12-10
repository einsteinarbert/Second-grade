package inout2nd;

public class CauHoi {
  private String cauHoi;
  private String bieuThuc;
  private int ketQua;

public CauHoi(String cauhoi, String bieuthuc, int kq){
    this.cauHoi = cauhoi;
    bieuThuc = bieuthuc;
    ketQua = kq;
}	 
public String getCauHoi(){
	return cauHoi;
}
public String getBieuThuc(){
	return bieuThuc;
	
}
public int  getKetQua() {
	return ketQua;
}

 }
