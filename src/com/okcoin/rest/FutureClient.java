package com.okcoin.rest;

import java.io.IOException;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.rest.future.IFutureRestApi;
import com.okcoin.rest.future.impl.FutureRestApiV1;

/**
 * 期货 REST API 客户端请求
 * @author zhangchi
 *
 */
public class FutureClient {

	public static void main(String[] args) throws HttpException, IOException {

		
	    String api_key = "";  //OKCoin申请的apiKey
       	String secret_key = "";  //OKCoin申请的secretKey
 	    String url_prex = "https://www.okcoin.cn";  //注意：请求URL 国际站https://www.okcoin.com ; 国内站https://www.okcoin.cn
		/**
		 *  get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
		 */
		IFutureRestApi futureGetV1 = new FutureRestApiV1(url_prex);
 	    //IFutureRestApi futureGetV1 = new FutureRestApiV1(url_prex, api_key,secret_key);
		/**
		 * post请求需发送身份认证，获取用户个人相关信息时，需要指定api_key,与secret_key并与参数进行签名，
		 * 此处对构造方法传入api_key与secret_key,在请求用户相关方法时则无需再传入，
		 * 发送post请求之前，程序会做自动加密，生成签名。
		 * 
		*/
		IFutureRestApi futurePostV1 = new FutureRestApiV1(url_prex, api_key,secret_key);
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
//		String result = httpUtil.requestHttpGet("http://api.huobi.com/staticmarket/detail_btc_json.js","", "");
//		System.out.println(result);
//	    //期货行情信息
		//futureGetV1.future_ticker("btc_usd", "this_week");
		String str = futureGetV1.future_ticker("btc_cny", "this_week");
		System.out.println("url = " + str);
//		String result = httpUtil.requestHttpGet(url_prex,str,"");
//		System.out.println("获得数据 = " + result);
//		//期货指数信息
//		String str1 = futureGetV1.future_index("btc_usd");
//		System.out.println("获得数据1 = " + str1);
// 
//		//期货交易信息
		String str2 = futureGetV1.future_trades("btc_usd", "this_week");
		System.out.println("获得数据2 = " + str2);
//		//期货市场深度
//		String str3 = futureGetV1.future_depth("btc_usd", "this_week");
//		System.out.println("获得数据3 = " + str3);
//		//美元-人民币汇率
//		String str4 = futureGetV1.exchange_rate();
//		System.out.println("获得数据4 = " + str4);
		
		//期货下单
		String tradeResultV1 = futurePostV1.future_trade("btc_usd","this_week", "10.134", "1", "1", "0");
//		JSONObject tradeJSV1 = JSONObject.parseObject(tradeResultV1);
//		String tradeOrderV1 = tradeJSV1.getString("order_id");
//		System.out.println(tradeResultV1);
//
//		//期货用户订单查询
//		futurePostV1.future_order_info("btc_usd", "this_week",tradeOrderV1, "1", "1", "2");
//
//		//取消订单
//		futurePostV1.future_cancel("btc_usd", "this_week",tradeOrderV1);

		//期货账户信息
//		String res = futurePostV1.future_userinfo();
//		System.out.println(res);
//
//		JSONObject  dataJson = new JSONObject(JSON.parseObject(res));
//		String  response = dataJson.getString("result");
//		System.out.println(response);
//		
//		JSONArray data=response.getJSONArray("data");
//		JSONObject info=data.getJSONObject(0);
//        String province=info.getString("province");
//        String city=info.getString("city");
//        String district=info.getString("district");
//        String address=info.getString("address"); 
//      
//        System.out.println(province+city+district+address);
      
		//逐仓期货账户信息
//		futurePostV1.future_userinfo_4fix();
//		
//		//期货用户持仓查询
//		futurePostV1.future_position("btc_usd", "this_week");
//
//		//期货用户逐仓持仓查询
//	    futurePostV1.future_position_4fix("btc_usd", null);


	}
}
