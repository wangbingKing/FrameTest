/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model;

import base.BaseList;
import base.BaseVcHoldOrder;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author wuxianshikong
 * bitvc 数据存放
 */
public class BitVcData extends ModelBase{
	
	public Vector<BaseList> verFurtureSellData;
	public Vector<BaseList> verFurtureBuyData;
	
	public Vector<BaseList> verFurtureSellDataWeek;
	public Vector<BaseList> verFurtureBuyDataWeek;
	
	public Vector<BaseList> verFurtureSellDataNextWeek;
	public Vector<BaseList> verFurtureBuyDataNextWeek;
	
	public Vector<BaseList> verFurtureSellDataMonth;
	public Vector<BaseList> verFurtureBuyDataMonth;
    /**
     * 存放仓位信息
     */
    public Vector<BaseVcHoldOrder> bitvcData;
    public BitVcData()
    {
        bitvcData =new Vector<BaseVcHoldOrder>();
        verFurtureSellData = new Vector<BaseList>();
		verFurtureBuyData = new Vector<BaseList>();
		
		verFurtureSellDataWeek = new Vector<BaseList>();
		verFurtureBuyDataNextWeek = new Vector<BaseList>();
		verFurtureSellDataWeek = new Vector<BaseList>();
		verFurtureBuyDataWeek = new Vector<BaseList>();
		verFurtureSellDataMonth = new Vector<BaseList>();
		verFurtureSellDataMonth = new Vector<BaseList>();
    }
    /**
     * 设置仓位
     * @param result
     * @param type
     */
    public void setHoldOrderData(String result,int type)
    {
    	synchronized(this)
		{
            if(result == null || result.equals(""))
			{
				return;
			}
//            {
//                week :
//                [
//                {
//                    id : 908,            // Order ID
//                    fee : 0,             // Fee
//                    tradeType : 1,      // Transaction type （1、buy 2、sell）
//                    price : 1063,       // Price
//                    status : 0,         // Status
//                    orderType : 1,      // Order type （1、Open order 2、Close order）
//                    lastTime : 1421221147,
//                    amount : 0.08130081,    // Order BTC value
//                    money : 1100,       // Order value
//                    orderTime : 1421221147, // Order time
//                    lever : 5,
//                    storeId : 0,        // Store ID
//                    processedMoney : 0,
//                    processedAmount : 0,
//                    margin : 0.008130081,   // Order frozen margin
//                    processedPrice : 0
//                }
//                ]
//            }

            String funtureName[] = {"week","nextweek","month"};
			JSONObject  dataJson = new JSONObject(JSON.parseObject(result));
		
			try
			{
				int errorid = dataJson.getInteger("code");
				if(errorid > 0)
				{
					return;//后期添加邮件提醒
				}
				JSONArray data=dataJson.getJSONArray(funtureName[type]);
				bitvcData.clear();
				for(int i = 0;i < data.size();i++)
				{
					JSONObject arr = data.getJSONObject(i);
					BaseVcHoldOrder child = new BaseVcHoldOrder();
					child.id = arr.getString("id");
					child.tradeType = arr.getIntValue("tradeType");
					child.price = arr.getDoubleValue("price");
					child.status = arr.getIntValue("status");
					child.lastTime = arr.getDoubleValue("lastTime");
					child.money = arr.getDoubleValue("money");
					child.closeMoney = arr.getDoubleValue("closeMoney");
					child.holdProfitLoss = arr.getDoubleValue("holdProfitLoss");
					child.dynamicRights = arr.getDoubleValue("dynamicRights");
					child.liquidatePrice = arr.getDoubleValue("liquidatePrice");
					child.riskRate = arr.getDoubleValue("riskRate");
					child.storeId = arr.getIntValue("storeId");
					child.usedMargin = arr.getDoubleValue("usedMargin");
					bitvcData.add(child);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
    }
    /**
     * 保存期货数据
     * @param result
     */
	public void setFutureData(String result,int FURTUREType)//保存期货数据
	{
		synchronized(this)
		{
//			{
//			"asks" : [[ // 卖单委托
//			    		"27.64",    // 价格
//			    		"21.7076",  // 委托数量
//			    		"600"       // 委托金额
//			    	]],
//			    	"bids" : [[ // 卖单委托
//			    		"27.57",
//			    		"7.2542",
//			    		"200"
//			    	]]
//			    }


			if(result == null || result.equals(""))
			{
				return;
			}
			JSONObject  dataJson = new JSONObject(JSON.parseObject(result));
			try{
				int errorid = dataJson.getInteger("error_code");
				if(errorid > 0)
				{
					return;//后期添加邮件提醒
				}
			}catch(Exception e)
			{
				
			}
			
			
			verFurtureSellData.clear();
			verFurtureBuyData.clear();
			JSONArray data=dataJson.getJSONArray("asks");
			for(int i = 0;i < data.size();i++)
			{
				JSONArray arr = data.getJSONArray(i);
				float num = arr.getFloatValue(1);
				float value = arr.getFloatValue(0);
				BaseList child = new BaseList(num,value);
				verFurtureSellData.add(child);
			}
			data=dataJson.getJSONArray("bids");
			for(int i = 0;i < data.size();i++)
			{
				JSONArray arr = data.getJSONArray(i);
				float num = arr.getFloatValue(1);
				float value = arr.getFloatValue(0);
				BaseList child = new BaseList(num,value);
				verFurtureBuyData.add(child);
			}
			
			Collections.sort(verFurtureSellData,new Comparator<BaseList>() {

                    @Override
	            public int compare(BaseList left, BaseList right) {
	            	if(left.value < right.value)
	            	{
	            		return 1;
	            	}
	            	else
	            	{
	            		return 0;
	            	}
	            }
	        });
			
			Collections.sort(verFurtureBuyData,new Comparator<BaseList>() {

                    @Override
	            public int compare(BaseList left, BaseList right) {
	            	if(left.value < right.value)
	            	{
	            		return 1;
	            	}
	            	else
	            	{
	            		return 0;
	            	}
	            }
	        });
		}
		
		
	}
    @Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}
}
