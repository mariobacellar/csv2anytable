package br.com.macro.lab.thread;

public class AppRunnable {

	private MarioRunnable mario;
	private PabloRunnable pablo;
	private Thread 		t1;
	private Thread 		t2;
	
	public AppRunnable() {
		this.mario = new MarioRunnable(1);
		
		this.pablo = new PabloRunnable(2);		

		t1 =new Thread(mario);
		t2 =new Thread(pablo);
	}

	public void  start() {
		
		this.t1.start();
		this.t2.start();
		
		try {
		
			Thread.sleep(5000);
			
			int id=pablo.getId();
			id++;
			pablo.setId(id++);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	public static void main(String args[]){  
		AppRunnable  app=new AppRunnable();
		app.start();
	} 

}
