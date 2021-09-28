package test;

public class MyTimer {
	long startTime;
	long timeBeforePause = 0;
	public MyTimer() {
		startTime = System.nanoTime();
	}
	//print time elapsed and reset
	public void lap() {
		long endTime = System.nanoTime();
		long durationMs = (endTime - startTime)/1000000;
		System.out.println(durationMs + "ms");
		
		startTime = System.nanoTime();
	}
	//lap, but with a string label
	public void lap(String s) {
		long endTime = System.nanoTime();
		long durationMs = (endTime - startTime)/1000000;
		System.out.println(s + ": " + durationMs + "ms");
		
		startTime = System.nanoTime();
	}
	
	public void lapMicro() {
		long endTime = System.nanoTime();
		long durationMicro = (endTime - startTime)/1000;
		System.out.println(durationMicro + "us");
		
		startTime = System.nanoTime();
	}
	
	public void pause() {
		timeBeforePause = System.nanoTime() - startTime;
	}
	public void resume() {
		startTime = System.nanoTime() - timeBeforePause;
	}
	
	public void startInterval() {
		System.out.println("starting interval");
		(new Thread() {
			public void run() {
				while(true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("-");
				}
			}
		}).start();
	}
}
