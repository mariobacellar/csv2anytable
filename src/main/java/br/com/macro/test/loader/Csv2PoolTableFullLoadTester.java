package br.com.macro.test.loader;

import br.com.macro.datalake.common.ConfigProperties;
import br.com.macro.datalake.loader.Csv2PoolTableFullLoad;

public class Csv2PoolTableFullLoadTester {

	public Csv2PoolTableFullLoadTester() throws Exception {
		ConfigProperties.getInstance();
	}

	public void test() throws Exception {
		Csv2PoolTableFullLoad 
		loader = new Csv2PoolTableFullLoad();
		loader.performLoop();
	}
	
	public static void main(String[] args) {
		try {
			
			Csv2PoolTableFullLoadTester 
			tester = new Csv2PoolTableFullLoadTester();
			tester.test();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


