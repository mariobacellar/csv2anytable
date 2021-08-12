package br.com.macro.test.loader;

import br.com.macro.datalake.common.ConfigProperties;
import br.com.macro.datalake.loader.Csv2AnyTableFullLoad;

public class Csv2AnyTableFullLoadTester {

	public Csv2AnyTableFullLoadTester() throws Exception {
		ConfigProperties.getInstance();
	}

	public void test() throws Exception {
		Csv2AnyTableFullLoad
		loader = new Csv2AnyTableFullLoad();
		loader.performLoop();
	}
	
	public static void main(String[] args) {
		try {
			
			Csv2AnyTableFullLoadTester 
			tester = new Csv2AnyTableFullLoadTester();
			tester.test();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


