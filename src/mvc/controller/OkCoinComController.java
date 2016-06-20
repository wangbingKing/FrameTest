package mvc.controller;

import tools.Tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.rest.HttpUtilManager;

import config.Config;
import config.Config.stateAction;
import base.BaseConfig;
import base.BaseNode;
import base.BaseUserInfo;

import com.okcoin.rest.MD5Util;
import com.okcoin.rest.future.IFutureRestApi;
import com.okcoin.rest.future.impl.FutureRestApiV1;
import com.okcoin.rest.stock.IStockRestApi;
import com.okcoin.rest.stock.impl.StockRestApi;

import java.util.HashMap;
import java.util.Map;

public class OkCoinComController implements BaseNode{
    private final String API_KEY = "7945c0bb-f45e-4af9-b445-f5f71651e1ef";
    private final String SECRET_KEY = "";
    private final String BASE_URL = "https://www.okcoin.com";
    public BaseUserInfo baseUserInfo;
    public double newTrandMoney;
    public double newThisWeekMoney;
    public double newNextWeekMoney;
    public double newMonthMoney;
    public double newQuarterMoney;
     /**
     * get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
     * 
    */
	IStockRestApi stockGet;
    /**
     * post请求需发送身份认证，获取用户个人相关信息时，需要指定api_key,与secret_key并与参数进行签名，
     * 此处对构造方法传入api_key与secret_key,在请求用户相关方法时则无需再传入，
     * 发送post请求之前，程序会做自动加密，生成签名。
     * 
    */
    
    /**
    *  get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
    */
    IFutureRestApi futureGetV1;// = new FutureRestApiV1(url_prex);
    /**
     * post请求需发送身份认证，获取用户个人相关信息时，需要指定api_key,与secret_key并与参数进行签名，
     * 此处对构造方法传入api_key与secret_key,在请求用户相关方法时则无需再传入，
     * 发送post请求之前，程序会做自动加密，生成签名。
     * 
    */
    IFutureRestApi futurePostV1;// = new FutureRestApiV1(url_prex, api_key,secret_key);
	IStockRestApi stockPost;// = new StockRestApi(url_prex, api_key, secret_key);
	public Controller mainController;
	int index = 0;//限制请求次数
	int newPriceIndex = 0;
	stateAction state = Config.stateAction.INIT_STATE;//控制状
	/**
	 * 存用户的key
	 */
	BaseConfig userKey;
	
	public OkCoinComController(Controller con)
	{
		baseUserInfo = new BaseUserInfo();
		mainController = con;
		userKey = Tools.getUserAccount(Config.OKCOINCOM);
        stockPost = new StockRestApi(BASE_URL, API_KEY, SECRET_KEY);
        stockGet = new StockRestApi(BASE_URL);
        futureGetV1 = new FutureRestApiV1(BASE_URL);
        futurePostV1 = new FutureRestApiV1(BASE_URL, API_KEY, SECRET_KEY);
        updateUserInfo();
        updateNewTrand();
	}
	/**
	 * 获得用户key
	 */
	public BaseConfig getUserKey()
	{
        return this.userKey;
	}
    public String updateUserInfoRequest()
    {
        return "";
    }
    
    public String bsRequest(int bs,double value,double num)
    {
        String bsStr[] = {"buy","sell"};
        String price = String.format("%.4f", value);
        String amount = String.format("%.4f", num);
        try
        {
            String result = stockPost.trade("btc_usd", bsStr[bs], price, amount);
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
            String result = stockPost.trade("btc_usd", bsStr[xs][bs], price, amount);
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
	            	mainController.updateAccount(Config.OKCOINCOM, baseUserInfo);
	            }   
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
		}};
		thread.start();
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
	public void updateTickerData()
	{
		Thread thread = new Thread(){
			   public void run(){
				   try{
					   
						String result = stockGet.depth("btc_usd");
						mainController.model.setTickerData(Config.OKCOINCOM,result);
						String futureresult = futureGetV1.future_depth("btc_usd", "this_week");
						mainController.model.setFutureData(Config.OKCOINCOM,futureresult,Config.THIS_WEEK_FURTURE);
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
	public void getDepthData()  //获取OKCoin市场深度
        {
		// TODO Auto-generated method stub
		if(index == -1) //网络连接
		{
			return;
		}
		index ++;
		if(index > 4) //计数
		{
			state = Config.stateAction.NETING_STATE;
			index = -1;
			updateTickerData();
		}
	}
    public void updateHoldOrder()
    {
        Thread thread = new Thread(){
		   public void run(){
                    try
                    {
                        String result = futurePostV1.future_position("btc_usd", "this_week");
                        mainController.setHoldOrderModel(Config.OKCOINCOM, result, Config.THIS_WEEK_FURTURE);
                    }
                    catch(Exception e)
                    {

                    }
		   		}
        };
        thread.start();
    }
    public void updateNewTrand()
    {
    	if(newPriceIndex > 3)
    	{
    		newPriceIndex = 0;
            Thread thread = new Thread(){
     		   public void run(){
     			   try{
     					String result =stockGet.ticker("btc_usd");
     					JSONObject  dataJson = new JSONObject(JSON.parseObject(result));
						JSONObject data = dataJson.getJSONObject("ticker");
						newTrandMoney = data.getDouble("last");
						String thisWeek = futureGetV1.future_ticker("btc_usd", "this_week");
						JSONObject  dataWeekJson = new JSONObject(JSON.parseObject(thisWeek));
						JSONObject weekdata = dataWeekJson.getJSONObject("ticker");
						newThisWeekMoney = weekdata.getDouble("last");
						 
						String next_week = futureGetV1.future_ticker("btc_usd", "next_week");
						JSONObject  datanext_weekJson = new JSONObject(JSON.parseObject(next_week));
						JSONObject next_weekdata = datanext_weekJson.getJSONObject("ticker");
						newNextWeekMoney = next_weekdata.getDouble("last");
						 
						String thismonth = futureGetV1.future_ticker("btc_usd", "month");
						JSONObject  datamonthJson = new JSONObject(JSON.parseObject(thismonth));
						JSONObject monthdata = datamonthJson.getJSONObject("ticker");
						newMonthMoney = monthdata.getDouble("last");
						 
						String thisquarter = futureGetV1.future_ticker("btc_usd", "quarter");
						JSONObject  dataquarterJson = new JSONObject(JSON.parseObject(thisquarter));
						JSONObject quarterdata = dataquarterJson.getJSONObject("ticker");
						newQuarterMoney = quarterdata.getDouble("last");                                              
					}
     			   catch(Exception e)
     			   {
     				   e.printStackTrace();
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
}
