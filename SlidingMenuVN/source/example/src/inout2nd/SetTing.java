package inout2nd;

public class SetTing {
	String dangHinhHoc;
	String dangBieuThuc;
	String dangToanDo;
	String dangTimX;
 
public SetTing(String hc ,String dangBT ,String dangTD,String timX){
  	  dangHinhHoc = hc;
  	  dangBieuThuc = dangBT;
  	  dangToanDo	= dangTD;
  	  dangTimX = timX;	
	}
  public String getHinhHoc(){
	  return dangHinhHoc;
	  
  }
  public String getBieuThuc() {
	return dangBieuThuc;
			
}
  public String getToanDo(){
	   return this.dangToanDo;
	   
	   
  } 
  public String getTimX(){
	   return this.dangTimX;
	      
  } 
  
}
