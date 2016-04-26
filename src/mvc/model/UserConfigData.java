package mvc.model;

import java.util.Vector;

import base.UserConBase;

public class UserConfigData {

	/**
	 * 用户检测交易的数据存储
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
