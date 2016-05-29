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

public class OkCoinCnController implements BaseNode{
	private final String BASE_URL = "https://www.okcoin.com";
    private final String GET_HOLDORDER_URL = "/futures/holdOrder/list";
    private final String GET_USERINFO_URL = "/api/v1/userinfo.do";
    
    public BaseUserInfo baseUserInfo;
	public Controller mainController;
	IFutureRestApi futurePostV1;
	HttpUtilManager httpUtil;
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
		futurePostV1 = new FutureRestApiV1(BASE_URL, userKey.api_key,userKey.secret_key);
		httpUtil = HttpUtilManager.getInstance();
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
	/**
	 * 更新账号信息
	 */
	public void updateUserInfo()
	{
        long time=System.currentTimeMillis()/1000;
        // 构造参数签名
        Map<String, String> params = new HashMap<String, String>();
        
        params.put("accessKey",userKey.api_key); //userKey.api_key
        String sign = MD5Util.buildMysignV1(params, userKey.secret_key);//userKey.secret_key
        params.put("sign", sign);
        // 发送post请求
        try
        {
            String result = httpUtil.requestHttpPost(BASE_URL,GET_HOLDORDER_URL, params);
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
