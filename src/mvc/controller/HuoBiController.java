package mvc.controller;

import tools.Tools;

import com.okcoin.rest.HttpUtilManager;

import config.Config;
import config.Config.stateAction;
import base.BaseConfig;
import base.BaseNode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deal.api.demo.huobi.HuobiService;
import com.okcoin.rest.MD5Util;
import com.okcoin.rest.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class HuoBiController implements BaseNode{
	HuobiService service;
	
	private static String BUY = "buy";
    private static String BUY_MARKET = "buy_market";
    private static String CANCEL_ORDER = "cancel_order";
    private static String ACCOUNT_INFO = "get_account_info";
    private static String NEW_DEAL_ORDERS = "get_new_deal_orders";
    private static String ORDER_ID_BY_TRADE_ID = "get_order_id_by_trade_id";
    private static String GET_ORDERS = "get_orders";
    private static String ORDER_INFO = "order_info";
    private static String SELL = "sell";
    private static String SELL_MARKET = "sell_market";
    
	public Controller mainController;
	/**
	 * 存用户的key
	 */
	BaseConfig userKey;
	int index = 0;//限制请求次数
    public double newTrandMoney;
	stateAction state = Config.stateAction.INIT_STATE;//控制状态
	
	HttpUtilManager httpUtil;
	public HuoBiController(Controller con)
	{
		service = new HuobiService();
		mainController = con;
		userKey = Tools.getUserAccount(Config.HUOBI);
		httpUtil = HttpUtilManager.getInstance();
		this.bsRequest(0, 1, 0.1);
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
	public void updateTickerData()
	{
		Thread thread = new Thread(){
			   public void run(){
				   try{
					   
                        String result = httpUtil.requestHttpGet("http://api.huobi.com/staticmarket/detail_btc_json.js","", "");
                        JSONObject  dataJson = new JSONObject(JSON.parseObject(result));
                        double newPrece = dataJson.getDouble("p_new");
                        newTrandMoney = newPrece;
                        mainController.model.setTickerData(Config.HUOBI,result);
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
        String price = String.format("%.2f", value);
        String amount = String.format("%.2f", num);
        String result = "";
        try
        {
        	if(bs == Config.BUY_ID)
        	{
        		result = service.buy(1, price, amount, null, null, BUY);
        	}
        	else
        	{
        		result = service.sell(1, price, amount, null, null, SELL);
        	}
        	System.out.println(result);
            JSONObject tradejs = JSONObject.parseObject(result);
            String tresult = tradejs.getString("result");
            if(tresult.equals("success"))
            {
                String tradeOrderV1 = tradejs.getString("id");
                return tradeOrderV1;
            }

        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        return "";
	}
    
    public String bsRequest(int bs,int xs, double value, double num) {
    	// TODO Auto-generated method stub
        String price = String.format("%.2f", value);
        String amount = String.format("%.2f", num);
        String result = "";
        try
        {
        	if(bs == Config.BUY_ID && xs == Config.TYPE_BS_XIAN)
        	{
        		result = service.buy(1, price, amount, null, null, BUY);
        	}
        	else if(bs == Config.SELL_ID && xs == Config.TYPE_BS_XIAN)
        	{
        		result = service.sell(1, price, amount, null, null, SELL);
        	}
        	else if(bs == Config.BUY_ID && xs == Config.TYPE_BS_SHI)
        	{
        		result = service.buyMarket(1, amount, null, null, BUY_MARKET);
        	}
        	else if(bs == Config.SELL_ID && xs == Config.TYPE_BS_SHI)
        	{
        		result = service.sellMarket(1, amount, null, null, SELL_MARKET);
        	}
        	System.out.println(result);
            JSONObject tradejs = JSONObject.parseObject(result);
            String tresult = tradejs.getString("result");
            if(tresult.equals("success"))
            {
                String tradeOrderV1 = tradejs.getString("id");
                return tradeOrderV1;
            }

        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        return "";
	}
    
}
