package mvc.model;

import java.util.Vector;

import base.UserConBase;

public class UserConfigData {

	/**
	 * 用户检测交易的数据存储
	 * 以后作为预留扩展写到文件中
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
	public Boolean removeControlData(long u_ID)
	{
		Boolean result = false;
		for(int i = 0;i <this.userControl.size();i++)
		{
			if(userControl.get(i).U_id == u_ID)
			{
				userControl.remove(i);
				result = true;
				break;
			}
		}
		return result;
	}
	
}
