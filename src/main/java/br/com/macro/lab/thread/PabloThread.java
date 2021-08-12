package br.com.macro.lab.thread;

public class PabloThread extends Thread {
	
	private int count;
	
	public PabloThread(int i) {
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
			System.out.println("PabloThread (count=["+count+"])is running...");  
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
}
