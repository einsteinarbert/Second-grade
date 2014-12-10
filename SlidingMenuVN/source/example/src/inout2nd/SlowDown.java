package inout2nd;

import java.util.Calendar;

import Expression.Randomize;


public class SlowDown {
	Thread pause;
	Randomize rd;
	public SlowDown() {
		 pause = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					rd = new Randomize();
					rd.setRangeValue(1, 10);
					Thread.sleep(Calendar.getInstance().getTimeInMillis()+rd.getRandomNumber());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	public void start(){
		pause.start();
	}
}
