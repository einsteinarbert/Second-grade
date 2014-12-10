package Expression;

import java.util.Random;


public class Randomize {
	private  int min;
	private  int max;
	private int size;
	private int index = 0;
	int[] randList;
	public Randomize() {
	}


	public int getRandomNumberNomal() {
		Random rand = new Random();
		return rand.nextInt(max-min) + min;
	}
	
	public int getRandomNumber() {
		int rt;
		if(index<size){
			rt =randList[index];
			index++;
		}else{
			createRandomList();
			rt = randList[index];
			index++;
		}
		return rt;
	}
	public void setRangeValue(int minx, int maxx) {
		min = minx;
		max = maxx+1;
		size = max-min;
		index = 0;
		createRandomList();
	}
	private void createRandomList(){
		index = 0;
		randList = new int[size];
		for (int i = 0; i < size; i++) {
			randList[i] = min+i;
		}
		Random rand = new Random();
		int len = size*10;
		int a,b;
		for (int i = 0; i < len; i++) {
			a = rand.nextInt(size);
			b = rand.nextInt(size);
			if(a!=b){
				randList[a] = randList[a]+randList[b];
				randList[b] = randList[a]-randList[b];
				randList[a] = randList[a]- randList[b];
			}
		}
	}
}
