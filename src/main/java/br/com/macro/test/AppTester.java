package br.com.macro.test;

import br.com.macro.datalake.App;

public class AppTester {

	public AppTester() {
		// TODO Auto-generated constructor stub
	}
	
	public void perform(String[] args) throws Exception {
		App
		app = new App();
		app.perform(args);
	}
	
	public static void main(String[] args) {
		try {
			
			//System.setProperty("OPTION","1");

			AppTester 
			tester = new AppTester();
			tester.perform(args);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
