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
import base.BaseOrder;
import base.BaseUserInfo;

import com.okcoin.rest.stock.IStockRestApi;
import com.okcoin.rest.stock.impl.StockRestApi;
import java.util.Vector;

public class OkCoinCnController implements BaseNode{
    private final String BASE_URL = "https://www.okcoin.cn";
    private final String API_KEY = "d086cba1-b17c-4961-ac17-dbd5ec4a8458";
    private final String SECRET_KEY = "";

    public BaseUserInfo baseUserInfo;
    public Controller mainController;
    
    public double newTrandMoney;
    public Vector<BaseOrder> orderList;
    
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
    /**
     * post请求需发送身份认证，获取用户个人相关信息时，需要指定api_key,与secret_key并与参数进行签名，
     * 此处对构造方法传入api_key与secret_key,在请求用户相关方法时则无需再传入，
     * 发送post请求之前，程序会做自动加密，生成签名。
     * 
    */
        
    int index = 0;//限制请求次数
    int newPriceIndex = 0;
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
        orderList = new Vector<BaseOrder>();
        stockPost = new StockRestApi(BASE_URL, API_KEY, SECRET_KEY);
        stockGet = new StockRestApi(BASE_URL);
        updateUserInfo();
        updateNewTrand();
    }
    public void updateOrderList()
    {
        
        if(!orderList.isEmpty())
        {
            for(int i = 0;i< orderList.size();i++)
            {
                
            }
        }
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
            updateNewTrand();
    }
    public void updateNewTrand()
    {
    	if(newPriceIndex > 2)
    	{
    		newPriceIndex = 0;
    		Thread thread = new Thread(){
    			   public void run(){
    				   try{
    						String result =stockGet.ticker("btc_cny");
    						JSONObject  dataJson = new JSONObject(JSON.parseObject(result));
    	                    JSONObject data = dataJson.getJSONObject("ticker");
    	                    newTrandMoney = data.getDouble("last");
    				   }
    				   catch(Exception E)
    				   {
    					
    				   }
    				 
    			   }
    			};
    			thread.start();
    	}
    	else
    	{
    		newPriceIndex ++;
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
		Thread thread = new Thread(){
			public void run(){
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
	            	mainController.updateAccount(Config.OKCOINCN, baseUserInfo);
	            }   
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
		}};
		thread.start();
	}
//        public void get
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
	public String bsRequest(int bs, double value, double num) {
		// TODO Auto-generated method stub
        String bsStr[] = {"buy","sell"};
        String price = String.format("%.4f", value);
        String amount = String.format("%.4f", num);
        try
        {
            String result = stockPost.trade("btc_cny", bsStr[bs], price, amount);
            JSONObject tradejs = JSONObject.parseObject(result);
            Boolean tresult = tradejs.getBoolean("result");
            if(tresult)
            {
                String tradeOrderV1 = tradejs.getString("order_id");
                return tradeOrderV1;
            }

        }
        catch (Exception e)
        {
            
        }
        return "";
	}
	public String bsRequest(int bs,int xs, double value, double num) {
		// TODO Auto-generated method stub
        String bsStr[][] = {
        		{"buy","sell"},
        		{"buy_market","sell_market"}
					        };
        String price = String.format("%.4f", value);
        String amount = String.format("%.4f", num);
        try
        {
            String result = stockPost.trade("btc_cny", bsStr[xs][bs], price, amount);
            JSONObject tradejs = JSONObject.parseObject(result);
            Boolean tresult = tradejs.getBoolean("result");
            if(tresult)
            {
                String tradeOrderV1 = tradejs.getString("order_id");
                return tradeOrderV1;
            }

        }
        catch (Exception e)
        {
            
        }
        return "";
	}
}
