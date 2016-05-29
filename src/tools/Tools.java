/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import base.BaseConfig;
import base.BaseList;
import base.UserConBase;
import config.Config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Vector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import mvc.model.HuoBIData;
import mvc.model.ModelBase;
import mvc.model.OkCoinCnData;
import mvc.model.OkCoinComData;

/**
 *
 * @author wuxianshikong
 */
public class Tools {
	
	 public static String FileInputStreamDemo(String path){
		 try{
			 
			 File file=new File("F:\\Github\\config.cof");
	         if(!file.exists()||file.isDirectory())
	         {
	        	 return "";
	         }
	         FileInputStream fis=new FileInputStream(file);
	         byte[] buf = new byte[1024];
	         StringBuffer sb=new StringBuffer();
	         while((fis.read(buf))!=-1){
	             sb.append(new String(buf));
	             buf=new byte[1024];//重新生成，避免和上次读取的数据重复
	         }
	         return sb.toString();
		 }catch(Exception e)
		 {
			 return ""; 
		 }
     }
	
	public static String getConfigFile()
	{
		File directory = new File("");//参数为空 
		try{
			String courseFile = directory.getCanonicalPath();
			String filePath = Tools.FileInputStreamDemo(courseFile+"/config.cof");
			return Tools.FileInputStreamDemo(filePath);
			
		}catch(Exception e)
		{
			System.out.println("配置文件打开失败");
			return "";
		}
	}
	public static BaseConfig getUserAccount(int pt)
	{
		BaseConfig conObj = new BaseConfig();
		String configStr = Tools.getConfigFile();
		if(configStr == "" || configStr.equals(null) )
		{
			return null;
		}
		configStr = configStr.trim();
		JSONObject  dataJson = new JSONObject(JSON.parseObject(configStr));	
		JSONArray data=dataJson.getJSONArray("data");
		JSONObject datapt = data.getJSONObject(pt);
		conObj.ptName = datapt.getString("ptName");
		conObj.api_key = datapt.getString("api_key");
		conObj.secret_key = datapt.getString("secret_key");
        conObj.baseUrl = datapt.getString("base_url");
		return conObj;
	}
    /**
     * 获得比较有差值的数量
     * @param buyListLift
     * @param sellListLift
     * @param buyListRight
     * @param sellListRight
     * @param userCheckData
     * @return 
     */
    public static float getResidual(Vector<BaseList> buyListLift,Vector<BaseList> sellListLift,Vector<BaseList> buyListRight,Vector<BaseList> sellListRight,UserConBase userCheckData)
    {
        Vector<BaseList> lift;
        Vector<BaseList> right;
        int idxL = 0;
        int idxR = 0;
        int foL = 1;
        int foR = 1;
        int lsize = 0;
        int rsize = 0;

        int lmax = 0;
        int rmax = 0;

        float numL = 0;
        float numR = 0;
        userCheckData.BSMoneyLeft = 0.0f;
        userCheckData.BSMoneyRight = 0.0f;
        userCheckData.checkNum = 0.0f;
        if(userCheckData.BSStateLeft == Config.BUY_ID)//买最高的
        {
            lift = buyListLift;
            lsize = lift.size();
            foL = -1;
            lmax = 0;
        }
        else //卖最低的
        {
            lift = sellListLift;
            lsize = 0;
            foL = 1;
            lmax = lift.size();
        }
        if(userCheckData.BSStateRight == Config.BUY_ID)
        {
            right = buyListRight;
            rsize = right.size();
            foR = -1;
            rmax = 0;
        }
        else
        {
            right = sellListRight;
            rsize = 0;
            foR = 1;
            rmax = right.size();
        }
        if(rmax == rsize || lmax == lsize)
        {
            return 0;
        }
        int istrue = 0;// true lift ++   false Right++
        Boolean liftIsMax = false;
        Boolean rightIsMax = false;
        while(true)
        {
            int l = lsize + foL * idxL;
            int r = rsize + foR * idxR;
            BaseList dataL = lift.get(l);
            BaseList dataR = right.get(r);
            if(Math.abs(Math.abs(dataL.value) - Math.abs(dataR.value))
                    >Math.abs(userCheckData.money))
            {
                if(istrue == 0)
                {
                	userCheckData.BSMoneyLeft = dataL.value;
                	userCheckData.BSMoneyRight = dataR.value;
                	numL += dataL.num;
                	numR += dataR.num;
                }
                else if(istrue >0 && !liftIsMax)
                {
                	userCheckData.BSMoneyLeft = dataL.value;
                	numL += dataL.num;
                }
                else if(istrue < 0 && !rightIsMax)
                {
                	userCheckData.BSMoneyRight = dataR.value;
                	numR += dataR.num;
                }
            }
            if(istrue >= 0)
            {
                if(idxL < lift.size()-1)
                {
                    idxL++;
                }
                else
                {
                    liftIsMax = true;
                }
                istrue = -1;
            }
            else if(istrue < 0)
            {
                if(idxR < right.size()-1)
                {
                    idxR++;
                }
                else
                {
                    rightIsMax = true;
                }
                istrue = 1;
            } 
            if(liftIsMax && rightIsMax)
            {
                break;
            }
        }
        float result =   numL > numR ? numR : numL;
        userCheckData.checkNum = result;
        return result;
    }
    /**
     * 处理从model中获得的数据 解析保存到列表中
     * @param buyList 保存买数据
     * @param sellList 保存卖数据
     * @param pt 平台标志id
     * @param data 获得的数据
     * @param userCheckData  个人设置的检测数据
     */
    public static void setDataTo( Vector<BaseList> buyList,Vector<BaseList> sellList,int pt,ModelBase data,UserConBase userCheckData)
    {
        switch(pt)
         {
             case Config.BTBVC:

                 break;
             case Config.BTCC:
                 break;
             case Config.HUOBI:
             	 if(userCheckData.TypeMTLeft == Config.MODERN_ID)
                  {
						HuoBIData huoBiData = (HuoBIData)data;  //只有现货
						sellList.addAll(Arrays.asList(huoBiData.tickerSellData));
						buyList.addAll(Arrays.asList(huoBiData.tickerBuyData));
						break;
                  }
             	 else if (userCheckData.TypeMTLeft == Config.FURTURE_ID)
             	 {
             		 System.out.println("火币没有期货,出错!!!");
             	 }
             case Config.OKCOINCN:
                 OkCoinCnData okdata = (OkCoinCnData)data;
                 //for(int i = 0;i < okdata)
                 if(userCheckData.TypeMTLeft == Config.MODERN_ID)
                 {
                     //现货
                     for(int i = 0;i < okdata.verTickerBuyData.size();i++)
                     {
                         buyList.add(okdata.verTickerBuyData.get(i));
                     }
                     for(int i = 0;i < okdata.verTickerSellData.size();i ++ )
                     {
                         sellList.add(okdata.verTickerSellData.get(i));
                     }
                 }
                 else if (userCheckData.TypeMTLeft == Config.FURTURE_ID)
                 {
                     //期货
                     System.out.println("OK中国没有期货,出错!!!");
                 }
                 break;
             case Config.OKCOINCOM:
                 OkCoinComData okdataCM = (OkCoinComData)data;
                 //for(int i = 0;i < okdata)
                 if(userCheckData.TypeMTLeft == Config.MODERN_ID)
                 {
                     //现货
                     for(int i = 0;i < okdataCM.verTickerBuyData.size();i++)
                     {
                         buyList.add(okdataCM.verTickerBuyData.get(i));
                     }
                     for(int i = 0;i < okdataCM.verTickerSellData.size();i ++ )
                     {
                         sellList.add(okdataCM.verTickerSellData.get(i));
                     }
                 }
                 else if (userCheckData.TypeMTLeft == Config.FURTURE_ID)
                 {
                     //期货
                     for(int i = 0;i < okdataCM.verFurtureBuyData.size();i++)
                     {
                         buyList.add(okdataCM.verFurtureBuyData.get(i));
                     }
                     for(int i = 0;i < okdataCM.verFurtureSellData.size();i ++ )
                     {
                         sellList.add(okdataCM.verFurtureSellData.get(i));
                     }
                 }
                 break;
         } 
    }
    
}
