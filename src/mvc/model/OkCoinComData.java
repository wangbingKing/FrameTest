package mvc.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import base.BaseList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class OkCoinComData extends ModelBase{

	public Vector<BaseList> verTickerSellData;
	public Vector<BaseList> verTickerBuyData;
	public Vector<BaseList> verFurtureSellData;
	public Vector<BaseList> verFurtureBuyData;
	public OkCoinComData()
	{
		verTickerSellData = new Vector<BaseList>();
		verTickerBuyData = new Vector<BaseList>();
		verFurtureSellData = new Vector<BaseList>();
		verFurtureBuyData = new Vector<BaseList>();
	}
	public void setFutureData(String result)//保存期货数据
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
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}
}
