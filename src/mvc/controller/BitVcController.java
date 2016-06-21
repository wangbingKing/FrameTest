/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import base.BaseConfig;
import base.BaseNode;

import com.deal.api.demo.futures.FuturesService;
import com.okcoin.rest.HttpUtilManager;
import com.okcoin.rest.MD5Util;
import com.okcoin.rest.StringUtil;
import com.okcoin.rest.future.IFutureRestApi;
import com.okcoin.rest.future.impl.FutureRestApiV1;

import config.Config;
import config.Config.stateAction;

import java.util.HashMap;
import java.util.Map;

import tools.Tools;

/**
 *
 * @author wuxianshikong
 * BitVc平台控制器
 */
public class BitVcController implements BaseNode{
	
    private final String BASE_URL = "https://api.bitvc.com";
    private final String GET_HOLDORDER_URL = "/futures/holdOrder/list";
    private final String GET_BALANCE = "/futures/balance";
     
    private static String FUTURES_BALANCE_INFO = "https://api.bitvc.com/futures/balance";
    private static String FUTURES_HOLD_ORDER_LIST = "https://api.bitvc.com/futures/holdOrder/list";
    private static String FUTURES_HOLD_ORDER_SUM = "https://api.bitvc.com/futures/holdOrder";
    private static String FUTURES_ORDERS_LIST="https://api.bitvc.com/futures/order/list";
    private static String FUTURES_ORDER_INFO = "https://api.bitvc.com/futures/order";
    private static String FUTURES_ORDER_SAVE="https://api.bitvc.com/futures/order/save";
    private static String FUTURES_CANCEL_ORDER = "https://api.bitvc.com/futures/order/cancel";
    FuturesService service;
    public Controller mainController;
    int index = 0;//限制请求次数
    public double newTrandMoney;
    
    public double newThisWeekMoney;
    public double newNextWeekMoney;
    public double newQuarterMoney;
    
    stateAction state = Config.stateAction.INIT_STATE;//控制状态
    /**
     * 存用户的key
     */
    BaseConfig userKey;

    public BitVcController(Controller con)
    {
    	service = new FuturesService();
        mainController = con;
        userKey = Tools.getUserAccount(Config.BTBVC);
    }
    /**
     * 获得用户key
     */
    public BaseConfig getUserKey()
    {
        return this.userKey;
    }
    public void updateHoldOrder(String type)
    {
        try
        {
            String result = service.getHoldOrderList(1,type, FUTURES_HOLD_ORDER_LIST);
            mainController.setHoldOrderModel(Config.BTBVC,result,Config.THIS_WEEK_FURTURE);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updateBalance()
    {
        try
        {
            String result = service.getBalanceInfo(1,FUTURES_BALANCE_INFO);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void getDepthData()
    {
    	if(index == -1) //网络连接中
        {
                return;
        }
        index ++;
        if(index > 10) //计数到
        {
                state = Config.stateAction.NETING_STATE;
                index = -1;
                Thread thread = new Thread(){
                           public void run(){
                                   try{
                                           HttpUtilManager httpUtil = HttpUtilManager.getInstance();
                                                String result = httpUtil.requestHttpGet("http://market.bitvc.com/futures/depths_btc_week.js","", "");
                                                mainController.model.setFutureData(Config.BTBVC, result,Config.THIS_WEEK_FURTURE);
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
    public void getNewPrice()
    {
      
        Thread thread = new Thread(){
            public void run(){
                    try
                    {
                           
                            
                    }
                    catch(Exception E)
                    {
                    }
            }
         };
        thread.start();
    }
    @Override
    public void update() {
    	// TODO Auto-generated method stub
        getDepthData();

    }
	public void bsFutureRequest(int bs, int type, double value, double num,
			int pt) {
		// TODO Auto-generated method stub
		
	}
}
