package test;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.rest.HttpUtilManager;
import com.okcoin.rest.MD5Util;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import config.Config;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;

import tools.MailUtil;
import tools.Tools;

public class test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws HttpException 
	 */
//	public final String API_KEY = "7945c0bb-f45e-4af9-b445-f5f71651e1ef";
//        public final String BASE_URL = "https://www.okcoin.com";
//        public final String GET_HOLDORDER_URL = "";
//        public final String GET_UESRINFO = "/api/v1/userinfo.do";
	public static void main(String[] args) throws InterruptedException, HttpException, IOException {
	
		 long time=System.currentTimeMillis()/1000;
         // 构造参数签名
		 String bsStr[] = {"buy","sell"};
         Map<String, String> params = new HashMap<String, String>();
         params.put("method",bsStr[0]);
         params.put("access_key", "419d4c14-3d2a2894-dc96119f-1d091");
         params.put("coin_type","1");
         params.put("price","0.1");
         params.put("amount","0.1");
         params.put("created",Long.toString(time));
         String sign = MD5Util.buildHuoBiSign(params, "");
         params.put("sign", sign);

         // 发送post请求
         HttpUtilManager httpUtil = HttpUtilManager.getInstance();
         String result = httpUtil.requestHttpPost("https://api.huobi.com/apiv3","",
                         params);
         JSONObject tradejs = JSONObject.parseObject(result);
         Boolean tresult = tradejs.getBoolean("result");
         if(tresult)
         {
             String tradeOrderV1 = tradejs.getString("id");
         }

		
	}

}
