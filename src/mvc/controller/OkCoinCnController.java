package mvc.controller;

import tools.Tools;

import com.okcoin.rest.HttpUtilManager;

import config.Config;
import config.Config.stateAction;
import base.BaseConfig;
import base.BaseNode;

public class OkCoinCnController implements BaseNode{
	public Controller mainController;
	int index = 0;//限制请求次数
	stateAction state = Config.stateAction.INIT_STATE;//控制状态
	/**
	 * 存用户的key
	 */
	BaseConfig userKey;
	public OkCoinCnController(Controller con)
	{
		mainController = con;
		userKey = Tools.getUserAccount(Config.OKCOINCN);
	}
	/**
	 * 获得用户key
	 */
	public BaseConfig getUserKey()
	{
		return this.userKey;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		switch(state)
		{
		case INIT_STATE:
			getDepthData();
			break;
		case NETING_STATE:
			break;
		case NETOEVR_STATE:
			getDepthData();
			break;
		}
	}
	public void getDepthData()  //获取OKCoin市场深度
	{
		//https://www.okcoin.cn/api/v1/depth.do?symbol=btc_cny
		// TODO Auto-generated method stub
		if(index == -1) //网络连接中
		{
			return;
		}
		index ++;
		if(index > 2) //计数到
		{
			state = Config.stateAction.NETING_STATE;
			index = -1;
			Thread thread = new Thread(){
				   public void run(){
					   try{
						   HttpUtilManager httpUtil = HttpUtilManager.getInstance();
							String result = httpUtil.requestHttpGet("https://www.okcoin.cn/api/v1/depth.do?symbol=btc_cny","", "");
							mainController.model.setTickerData(Config.OKCOINCN,result);
							index = 0;	
					   }
					   catch(Exception E)
					   {
						   index = 0;
					   }
					   state = Config.stateAction.NETOEVR_STATE;
				   }
				};
			thread.start();
		}
	}
}
