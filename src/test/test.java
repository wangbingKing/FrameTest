package test;

import java.io.File;
import java.util.Vector;

import config.Config;
import tools.Tools;

public class test {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Tools.getUserAccount(0);
		System.out.print("dkfjkl\r");
		File directory = new File("");//参数为空 
		try{
			String courseFile = directory.getCanonicalPath(); 
			System.out.println(courseFile);
			System.out.println(courseFile+"\\config.cof");
			System.out.println(Tools.FileInputStreamDemo(courseFile+"\\config.cof"));
		}catch(Exception e)
		{
			
		}
		
//		testClass s = new testClass();
		
	}

}
