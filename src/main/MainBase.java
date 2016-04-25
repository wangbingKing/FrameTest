package main;

import mvc.controller.Controller;

public class MainBase {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller con = new Controller();
		while(!con.isOver)
		{
			con.update();
			try
			{
				Thread.sleep(50);
				System.out.println("测试");
				System.out.println("测试");
				System.out.println("测试");
			}
			catch(Exception e)
			{
				
			}
		}
	}

}
