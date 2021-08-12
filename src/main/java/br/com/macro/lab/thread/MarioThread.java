package br.com.macro.lab.thread;

public class MarioThread extends Thread{

	private int count;
	
	public MarioThread(int i) {
		setCount(i);
	}
	
	public void setCount(int i) {
		this.count=i;
	}

	public int getCount() {
		return this.count;
	}

	public void run() {
		while (true) {
			System.out.println("MarioThread (count=["+count+"])is running..."); 
			count++;
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

}
	
