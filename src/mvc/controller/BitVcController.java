/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import base.BaseConfig;
import base.BaseNode;
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
    public Controller mainController;
    int index = 0;//限制请求次数
    stateAction state = Config.stateAction.INIT_STATE;//控制状态
    /**
     * 存用户的key
     */
    BaseConfig userKey;

    public BitVcController(Controller con)
    {
        mainController = con;
        userKey = Tools.getUserAccount(Config.BTBVC);
        updateHoldOrder("week");
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
        IFutureRestApi futurePostV1 = new FutureRestApiV1(BASE_URL, userKey.api_key,userKey.secret_key);
        long time=System.currentTimeMillis()/1000;
        // 构造参数签名
        Map<String, String> params = new HashMap<String, String>();
        
        params.put("accessKey", userKey.api_key);
        params.put("coinType", "1");
        params.put("created",Long.toString(time));
        String sign = MD5Util.buildMysignV1(params, userKey.secret_key);
        params.put("sign", sign);
        params.put("contarctType", type); 
        // 发送post请求

        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        try
        {
            String result = httpUtil.requestHttpPost(BASE_URL,GET_HOLDORDER_URL, params);
            mainController.setHoldOrderModel(Config.BTBVC,result,Config.THIS_WEEK_FURTURE);
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
		if(index > 2) //计数到
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
}
