package br.com.macro.lab.thread;

public class MarioRunnable implements Runnable{

	private int id;
	
	public MarioRunnable(int i) {
		this.id=i;
	}

	public void run() {
		while (true) {
			System.out.println("MarioRunnable (id=["+id+"])is running...");  
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

	public void setId(int i) {
		this.id=i;
	}

}
	
