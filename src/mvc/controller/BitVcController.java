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
    /**
     * 存用户的key
     */
    BaseConfig userKey;

    public BitVcController(Controller con)
    {
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
    public void updateHoldOrder()
    {
        IFutureRestApi futurePostV1 = new FutureRestApiV1(BASE_URL, userKey.api_key,userKey.secret_key);
        // 构造参数签名
        Map<String, String> params = new HashMap<String, String>();
        
        params.put("symbol", "");
        
        String sign = MD5Util.buildMysignV1(params, userKey.secret_key);
        params.put("sign", sign);
        // 发送post请求

        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        try
        {
            String result = httpUtil.requestHttpPost(BASE_URL,GET_HOLDORDER_URL, params);
        }
        catch(Exception e)
        {
            
        }
    }
    @Override
    public void update() {
       
    }
}
