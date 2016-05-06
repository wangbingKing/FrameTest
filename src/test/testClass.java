package test;

import java.util.Vector;

public class testClass {

	/**
	 * @param args
	 */
	Vector<String > data;
	public void setData(Vector<String > data)
	{
		data.add("11111");
		data.add("22222");
	}
	public testClass()
	{
		this.data = new Vector<String>();
		this.setData(this.data);
		for(String s : this.data)
		{
			System.out.println(s);
		}
	}

}
