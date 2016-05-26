package main;

import base.BaseList;
import base.UserConBase;
import config.Config;
import java.util.Vector;
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
			}
			catch(Exception e)
			{
				
			}
		}
	}

}
