package mvc.model;

import java.util.Vector;

import base.UserConBase;

public class UserConfigData {

	/**
	 * �û���⽻�׵����ݴ洢
	 */
	public Vector<UserConBase> userControl;
	public UserConfigData()
	{
		userControl = new Vector<UserConBase>();
	}
	public void addControlData(UserConBase data)
	{
		userControl.add(data);
	}
	
}
