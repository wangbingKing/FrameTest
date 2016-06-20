package com.deal.api.demo.huobi;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.deal.api.util.EncryptUtil;
import com.deal.api.util.HttpUtil;


/**
 *
 */
public abstract class Base {
	//火币现货配置信息
	public static String HUOBI_ACCESS_KEY = "95b208ac-9829d7de-5d646ca4-e5423";
	public static String HUOBI_SECRET_KEY = "";
	public static String HUOBI_API_URL = "https://api.huobi.com/apiv3";
	
	//bitvc现货，期货共用accessKey,secretKey配置信息
	public static String BITVC_ACCESS_KEY = "667eb6b5-cffb0d21-15f3e934-2f56e212";
	public static String BITVC_SECRET_KEY = "";
    
    
	
	
	protected static int success = 200;
	
	
	public String post(Map<String, Object> map,String url) throws Exception {
		return HttpUtil.post(url, map, new ResponseHandler<String>() {
			@Override
			public String handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				int code = response.getStatusLine().getStatusCode();
				if(success == code) {
					return EntityUtils.toString(response.getEntity(), "utf-8");
				}
				return null;
			}
			
		});
	}
	
	public long getTimestamp() {
		return System.currentTimeMillis()/1000;
	}
	
	public String sign(TreeMap<String, Object> map) {
		StringBuilder inputStr = new StringBuilder();
		for (Map.Entry<String, Object> me : map.entrySet()) {
            inputStr.append(me.getKey()).append("=").append(me.getValue()).append("&");
        }
        String md5Str = EncryptUtil.MD5(inputStr.substring(0, inputStr.length() - 1)).toLowerCase();
        return md5Str;
	}
	
}
