package test;

import com.okcoin.rest.HttpUtilManager;
import com.okcoin.rest.MD5Util;

import java.io.File;
import java.util.Vector;

import config.Config;

import java.util.HashMap;
import java.util.Map;

import tools.MailUtil;
import tools.Tools;

public class test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
//	public final String API_KEY = "7945c0bb-f45e-4af9-b445-f5f71651e1ef";
//        public final String BASE_URL = "https://www.okcoin.com";
//        public final String GET_HOLDORDER_URL = "";
//        public final String GET_UESRINFO = "/api/v1/userinfo.do";
	public static void main(String[] args) throws InterruptedException {
		long t1=System.currentTimeMillis();
		System.out.println(t1);
		Thread.sleep(360000);
		long t2=System.currentTimeMillis();
		System.out.println(t2);
		long t3 = t2-t1;
		
		System.out.println(t3);
//                MailUtil.sendMail("wangbing0108@163.com", "机器人功能", "测试一下邮件功能cxgfdhfhfhgfhfgdhgfhdf");
//		HtmlEmail email = new HtmlEmail();  
//		testClass s = new testClass();
		
	}

}
