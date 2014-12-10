package inout2nd;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

import Expression.Expression;
import Expression.Randomize;

public class SinhCauHoi {
	IOData getdata;
	private ArrayList<Bieu_Thuc> bieuThuc;
	private ArrayList<CauHoi> cacCauHoi;
	private ArrayList<Trac_Nghiem> thongDiepCauHoi;
	private Expression exp;
	private CauHoi cauHoi;
	private int[] dapAn;
	private int SoCauHoi;
	private Randomize result;
	private Randomize viTriBieuThuc;
	private String limitedExpress;

	public SinhCauHoi() {

	}

	public SinhCauHoi(String idDeMuc, int SoCau) {
		getdata = new IOData();

		bieuThuc = getdata.getListBieuThuc(idDeMuc);

		Log.e("IDDEMUC", idDeMuc + "|" + bieuThuc.size());
		thongDiepCauHoi = getdata.getListTracNghiem((bieuThuc.get(0)
				.getNhomCauHoi()));
		exp = new Expression();
		SoCauHoi = SoCau;
		dapAn = new int[SoCauHoi];
		// sẽ sửa sau:
		result = new Randomize();
		Log.e("Khoi tao size",
				">" + thongDiepCauHoi.size() + "|" + bieuThuc.size());
	}

	public int[] getListDapAn() {
		return dapAn;
	}

	public void setData(int SoCau) {
		this.SoCauHoi = SoCau;
	}

	public ArrayList<String> getListCauHoi() throws InterruptedException {
		cacCauHoi = new ArrayList<CauHoi>();
		int len = SoCauHoi;
		int soBieuThuc = bieuThuc.size();
		String buf = "";
		ArrayList<String> listCh = new ArrayList<String>();
		ArrayList<String> listChTEST = new ArrayList<String>();
		viTriBieuThuc = new Randomize();
		Log.e("SoBT", ">" + soBieuThuc + "|" + SoCauHoi);

		if (soBieuThuc > 1)
			viTriBieuThuc.setRangeValue(0, soBieuThuc - 1);
		int viTri;

		for (int i = 0; i < len; i++) {
			listChTEST.add("TEST " + i);
			if (soBieuThuc > 1)
				viTri = viTriBieuThuc.getRandomNumber();
			else
				viTri = 0;

			// TODO ------------------------------
			Log.e("vitri", ">> " + viTri);
			limitedExpress = bieuThuc.get(viTri).getRangBuoc();

			result.setRangeValue(bieuThuc.get(viTri).getMin() + 1, bieuThuc
					.get(viTri).getMax());

			Log.e("MIN-MAX",
					bieuThuc.get(viTri).getMin() + "|"
							+ bieuThuc.get(viTri).getMax());

			if (limitedExpress != null && limitedExpress.length() > 0) {
				if (limitedExpress.equals("0")) {
					dapAn[i] = 10;
				} else if (limitedExpress.equals("100")) {
					dapAn[i] = 100;
				} else {
					dapAn[i] = result.getRandomNumber();
					Log.e("DA VAO DAY", "XXX");
				}
			} else {
				dapAn[i] = result.getRandomNumber();
			}
			if (dapAn[i] < 5) {
				dapAn[i] = dapAn[i] + 3;// :))
				Log.e("DAPANXXX", ">> " + dapAn[i]);
			}
			Log.e("DAPAN", ">> " + dapAn[i]);
			// -----------------------------------
			thongDiepCauHoi = getdata.getListTracNghiem(bieuThuc.get(0)
					.getNhomCauHoi());
			Log.e("Chay toi day?", "XXXXXXXXXXXXXXXXXXXXXX");

			
		    
			buf="";
		    while(true){
		    	exp.setLimited(bieuThuc.get(viTri).getMin(), bieuThuc.get(viTri)
						.getMax(), dapAn[i]);
		    	Log.e("ThongSoBT",bieuThuc.get(viTri).getBieuThuc()+"|"+bieuThuc.get(viTri).getMin()+"|"+bieuThuc.get(viTri).getMax()+"|"+dapAn[i]);
		    	buf = exp.createExpression(bieuThuc.get(viTri).getBieuThuc());
		    	if(buf.equals("error")){
		    		Log.e("EROOROORORORROOR", "ERRIRIRIRRIRIRI");
		    		if (limitedExpress != null && limitedExpress.length() > 0) {
						if (limitedExpress.equals("0")) {
							dapAn[i] = 10;
						} else if (limitedExpress.equals("100")) {
							dapAn[i] = 100;
						} else {
							dapAn[i] = result.getRandomNumber();
							Log.e("DA VAO DAY", "XXX");
						}
					} else {
						dapAn[i] = result.getRandomNumber();
					}
					if (dapAn[i] < 5) {
						dapAn[i] = dapAn[i] + 3;// :))
						Log.e("DAPANXXX", ">> " + dapAn[i]);
					}
					Log.e("RE gen DAPAN", ">> " + dapAn[i]);
		    	}
		    	else 
		    		break;
		    }

			Log.e("buf", ">> " + buf+"|kqmd: "+dapAn[i]+"|"+bieuThuc.get(viTri).getBieuThuc());
			Log.e("IDNHOM|vitri", ">> " + bieuThuc.get(viTri).getNhomCauHoi()
					+ "|" + viTri);
			if (bieuThuc.get(viTri).getNhomCauHoi().equals("3") == true) {
				Log.e("Gone here", "HAHA");
				cauHoi = getCauHoiLoi(viTri, buf, dapAn[i]);
				Log.e("Pass!", "HAHA");
			} else {
				Log.e("OverHere", buf);
				cauHoi = new CauHoi(thongDiepCauHoi.get(0).getCauHoi(), buf,
						dapAn[i]);
				Log.e("Pass here", buf);
			}
			Log.e("cacCauHoi", ">> " + cacCauHoi.size() + "|" + listCh.size());
			cacCauHoi.add(cauHoi);
			listCh.add("Câu " + (i + 1) + " " + cauHoi.getCauHoi() + "\n   "
					+ cauHoi.getBieuThuc());
			Log.e("VongLapThu", ">>" + i);

		}
		Log.e("SoCau", "> " + +listChTEST.size());
		return listCh;
	}

	public ArrayList<CauHoi> getListCauHoiObj() {
		return cacCauHoi;
	}

	// Duy:
	private CauHoi getCauHoiLoi(int Index_bieu_thuc, String Gia_tri,
			int giatridung) {
		int length = Gia_tri.length();
		int index = 0;
		if (Gia_tri.indexOf("+") >= 0)
			index = Gia_tri.indexOf("+");
		else if (Gia_tri.indexOf("-") >= 0)
			index = Gia_tri.indexOf("-");
		else if (Gia_tri.indexOf("x") >= 0)
			index = Gia_tri.indexOf("x");
		else
			index = Gia_tri.indexOf("/");

		Log.e("xac dinh cong tru nhan chia", Integer.toString(index));

		String A = Gia_tri.substring(0, index); // gia tri A
		String B = Gia_tri.substring(index + 1); // Gia tri B

		IOData io = new IOData();
		ArrayList<Dau_Bai> dau_bai = new ArrayList<Dau_Bai>();
		dau_bai = io.getDauBai();

		Random rand = new Random();
		String sixe = Integer.toString(dau_bai.size());

		int indexDauBai = rand.nextInt(3) + 0; // lấy đâu bài duy:sua

		String id_dau_bai = dau_bai.get(indexDauBai).getId(); // lấy id đâu

		String bieu_thuc = Returndau(bieuThuc.get(Index_bieu_thuc).getBieuThuc()); // lấy
																		// biểu

		ArrayList<Than_Bai> than_bai = new ArrayList<Than_Bai>();
		than_bai = io.getThanBai(id_dau_bai, bieu_thuc);

		String size_thanbai = Integer.toString(than_bai.size());
		Log.e("id_dau_bai", id_dau_bai);
		Log.e("Bieu_thuc", bieu_thuc);
		Log.e("size cua than bai----------", size_thanbai);

		// Log.e("size than bai",size_thanbai);

		int indexThan_bai = rand.nextInt(than_bai.size()) + 0;
		String loiDauBai = dau_bai.get(indexDauBai).getDauBai();
		String loiThanai = than_bai.get(indexThan_bai).getThanBai();
		int indexA = loiThanai.indexOf("A");
		int indexB = loiThanai.indexOf("B");
		String s1 = loiThanai.substring(0, indexA - 1);
		String s2 = loiThanai.substring(indexA + 1, indexB - 1);
		String s3 = loiThanai.substring(indexB + 1);
		loiThanai = s1 +" "+ A + s2 + B + s3;
		String loi_bai = loiDauBai + loiThanai;
		Log.e("loi bai toan", loi_bai);
		CauHoi cauhoi = new CauHoi(loi_bai, " ", giatridung);
		return cauhoi;

	}

	public String toString() {
		String sizes = "bieuThuc:" + bieuThuc.size() + "|" + "cacCauHoi:"
				+ cacCauHoi.size() + "|" + "thongDiepCauHoi:"
				+ thongDiepCauHoi.size() + "|" + "dapAn:" + dapAn.length;
		return sizes;

	}
	
	// hàm hỗ trợ
		  private String Returndau(String a){
			   int index;
			   if(a.indexOf("+")>=0) index = a.indexOf("+");
			    else    if(a.indexOf("-")>=0) 	index = a.indexOf("-");	
			            else if(a.indexOf("x")>=0) 	index = a.indexOf("x");
			                 else 	index = a.indexOf("/");
			    String dau_1 = a.substring(index,index+1);
			    
			       return dau_1;
		   }

}
