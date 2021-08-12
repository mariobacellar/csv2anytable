package br.com.macro.lab.thread;

public class AppThread {

	private MarioThread mario;
	private PabloThread pablo;
	
	private int countId=0;
	

	public AppThread() {

		countId++;	
		this.mario = new MarioThread(countId);

		countId++;	
		this.pablo = new PabloThread(countId);
	}

	public void  start() {

		this.mario.start();
		this.pablo.start();		
	}		

	
	
	public static void main(String args[]){  
		AppThread  app=new AppThread();
		app.start();
	} 

}
