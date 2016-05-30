package mvc.controller;

import java.util.HashMap;
import java.util.Map;

import tools.Tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.rest.HttpUtilManager;
import com.okcoin.rest.MD5Util;
import com.okcoin.rest.future.IFutureRestApi;
import com.okcoin.rest.future.impl.FutureRestApiV1;

import config.Config;
import config.Config.stateAction;
import base.BaseConfig;
import base.BaseNode;
import base.BaseUserInfo;
import com.okcoin.rest.stock.IStockRestApi;
import com.okcoin.rest.stock.impl.StockRestApi;

public class OkCoinCnController implements BaseNode{
        private final String BASE_URL = "https://www.okcoin.cn";
        private final String API_KEY = "7945c0bb-f45e-4af9-b445-f5f71651e1ef";
        private final String SECRET_KEY = "";

        public BaseUserInfo baseUserInfo;
        public Controller mainController;
         /**
         * get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
         * 
        */
        IStockRestApi stockGet;
        IStockRestApi stockPost;
        /**
             * post请求需发送身份认证，获取用户个人相关信息时，需要指定api_key,与secret_key并与参数进行签名，
             * 此处对构造方法传入api_key与secret_key,在请求用户相关方法时则无需再传入，
             * 发送post请求之前，程序会做自动加密，生成签名。
             * 
            */

        /**
        *  get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
        */
//        IFutureRestApi futureGetV1;
        /**
         * post请求需发送身份认证，获取用户个人相关信息时，需要指定api_key,与secret_key并与参数进行签名，
         * 此处对构造方法传入api_key与secret_key,在请求用户相关方法时则无需再传入，
         * 发送post请求之前，程序会做自动加密，生成签名。
         * 
        */
//        IFutureRestApi futurePostV1;
        
	int index = 0;//限制请求次数
	stateAction state = Config.stateAction.INIT_STATE;//控制状态
	/**
	 * 存用户的key
	 */
	BaseConfig userKey;
	public OkCoinCnController(Controller con)
	{
		baseUserInfo = new BaseUserInfo();
		mainController = con;
		userKey = Tools.getUserAccount(Config.OKCOINCN);
                stockPost = new StockRestApi(BASE_URL, API_KEY, SECRET_KEY);
                stockGet = new StockRestApi(BASE_URL);
//                futureGetV1 = new FutureRestApiV1(BASE_URL);
//                futurePostV1 = new FutureRestApiV1(BASE_URL, API_KEY, SECRET_KEY);
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
	/**
	 * 更新现货深度
	 */
	public void updateTickerData()
	{
		Thread thread = new Thread(){
			   public void run(){
				   try{
						String result =stockGet.depth("btc_cny");
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
	/**
	 * 更新账号信息
	 */
	public void updateUserInfo()
	{
        // 发送post请求
        try
        {
            String result = stockPost.userinfo();
            System.out.println(result);
            JSONObject  dataJson = new JSONObject(JSON.parseObject(result));
            Boolean resultInfo =  dataJson.getBoolean("result");
            if(resultInfo)
            {
            	JSONObject infoData = dataJson.getJSONObject("info");
            	JSONObject fundsData = infoData.getJSONObject("funds");
            	JSONObject assetData = fundsData.getJSONObject("asset");
            	/**
            	 * 净资产
            	 */
            	String netData = assetData.getString("net");
            	
            	JSONObject freeData = fundsData.getJSONObject("free");
            	
            	String btcData = freeData.getString("btc");
            	baseUserInfo.asset = netData;
            	baseUserInfo.free = btcData;
            	
            }   
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
			updateTickerData();
		}
	}
}
