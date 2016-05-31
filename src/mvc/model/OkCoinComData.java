package mvc.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import base.BaseList;
import base.BaseOKHoldOrder;
import base.BaseVcHoldOrder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class OkCoinComData extends ModelBase{

	public Vector<BaseList> verTickerSellData;
	public Vector<BaseList> verTickerBuyData;
	public Vector<BaseList> verFurtureSellData;
	public Vector<BaseList> verFurtureBuyData;
        /**
        * 存放仓位信息
        */
        public Vector<BaseOKHoldOrder> holdOrderData;
	public OkCoinComData()
	{
		verTickerSellData = new Vector<BaseList>();
		verTickerBuyData = new Vector<BaseList>();
		verFurtureSellData = new Vector<BaseList>();
		verFurtureBuyData = new Vector<BaseList>();
                holdOrderData =new Vector<BaseOKHoldOrder>();
	}
	public void setFutureData(String result,int rutureType)//保存期货数据
	{
		synchronized(this)
		{
			//{"asks":[[461.33,24],[461.31,12],[461.3,10],[461.23,3],[461.22,1]],"bids":[[461,3],[460.99,1],[460.68,53],[460.59,26],[460.54,59]]}
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
        //保存现货
	public void setTickerData(String result)
	{
		synchronized(this)
		{
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
			//{"error_code":20006,"result":false}
			
			/*
			 * # Response
	{
	 "asks": [
	  [792, 5],
	  [789.68, 0.018],
	  [788.99, 0.042],
	  [788.43, 0.036],
	  [787.27, 0.02]
	 ],
	 "bids": [
	  [787.1, 0.35],
	  [787, 12.071],
	  [786.5, 0.014],
	  [786.2, 0.38],
	  [786, 3.217],
	  [785.3, 5.322],
	  [785.04, 5.04]
	 ]
	}
	*/		verTickerSellData.clear();
			verTickerBuyData.clear();
			JSONArray data=dataJson.getJSONArray("asks");
			for(int i = 0;i < data.size();i++)
			{
				JSONArray arr = data.getJSONArray(i);
				float num = arr.getFloatValue(1);
				float value = arr.getFloatValue(0);
				BaseList child = new BaseList(num,value);
				verTickerSellData.add(child);
			}
			data=dataJson.getJSONArray("bids");
			for(int i = 0;i < data.size();i++)
			{
				JSONArray arr = data.getJSONArray(i);
				float num = arr.getFloatValue(1);
				float value = arr.getFloatValue(0);
				BaseList child = new BaseList(num,value);
				verTickerBuyData.add(child);
			}
			
			Collections.sort(verTickerSellData,new Comparator<BaseList>() {
	
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
			
			Collections.sort(verTickerBuyData,new Comparator<BaseList>() {
	
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
        /**
         * 设置仓位
         * @param result
         * @param type
         */
        public void setHoldOrderData(String result,int type)
        {
            synchronized(this)
            {
//{
//    "force_liqu_price": "0.07",
//    "holding": [
//        {
//            "buy_amount": 1,
//            "buy_available": 0,
//            "buy_price_avg": 422.78,
//            "buy_price_cost": 422.78,
//            "buy_profit_real": -0.00007096,
//            "contract_id": 20141219012,
//            "contract_type": "this_week",
//            "create_date": 1418113356000,
//            "lever_rate": 10,
//            "sell_amount": 0,
//            "sell_available": 0,
//            "sell_price_avg": 0,
//            "sell_price_cost": 0,
//            "sell_profit_real": 0,
//            "symbol": "btc_usd"
//        }
//    ],
//    "result": true
//}
// buy_amount(double):多仓数量
//buy_available:多仓可平仓数量
//buy_price_avg(double):开仓平均价
//buy_price_cost(double):结算基准价
//buy_profit_real(double):多仓已实现盈余
//contract_id(long):合约id
//create_date(long):创建日期
//lever_rate:杠杆倍数
//sell_amount(double):空仓数量
//sell_available:空仓可平仓数量
//sell_price_avg(double):开仓平均价
//sell_price_cost(double):结算基准价
//sell_profit_real(double):空仓已实现盈余
//symbol:btc_usd:比特币,ltc_usd:莱特币
//contract_type:合约类型
//force_liqu_price:预估爆仓价               
                if(result == null || result.equals(""))
                {
                    return;
                }
                    JSONObject  dataJson = new JSONObject(JSON.parseObject(result));
                    Boolean resultTrue = dataJson.getBoolean("result");
                    if(resultTrue)
                    {
                        try
                        {
                            JSONArray data=dataJson.getJSONArray("holding");
                            holdOrderData.clear();
                            for(int i = 0;i < data.size();i++)
                            {
                                    JSONObject arr = data.getJSONObject(i);
                                    BaseOKHoldOrder child = new BaseOKHoldOrder();
                                    child.contract_id = arr.getLong("contract_id");// arr.getString("id");
                                    child.buy_amount = arr.getDouble("buy_amount");
                                    child.buy_available = arr.getDouble("buy_available");
                                    child.buy_price_avg = arr.getDouble("buy_price_avg");
                                    child.buy_profit_real = arr.getDouble("buy_profit_real");
                                    child.contract_type = arr.getIntValue("contract_type");
                                    child.create_date = arr.getLong("create_date");
                                    child.force_liqu_price = arr.getDouble("force_liqu_price");
                                    child.lever_rate = arr.getDouble("lever_rate");
                                    child.sell_amount = arr.getDouble("sell_amount");
                                    child.sell_available = arr.getDouble("sell_available");
                                    child.sell_price_avg = arr.getDouble("sell_price_avg");
                                    child.sell_price_cost = arr.getDouble("sell_price_cost");
                                    child.sell_profit_real = arr.getDouble("sell_profit_real");
                                    holdOrderData.add(child);
                            }
                        }
                        catch(Exception e)
                        {
                                e.printStackTrace();
                        }
                    }
                    else
                    {

                    }
                            
//
                    
            }
        }
	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}
}
