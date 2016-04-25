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
	public BaseList tickerSellData[];//鐜拌揣鍗栨暟鎹�
	public BaseList tickerBuyData[];//鐜拌揣涔版暟鎹�
	 public static final Comparator<Vector<String>> VECTOR_COMPARATOR = new Comparator<Vector<String>>() {
		  @Override
		  public int compare(Vector<String> v1, Vector<String> v2) {
		   if (v1 == null || v1.size() < 2) {
		    return -1;
		   }
		   if (v2 == null || v2.size() < 2) {
		    return 1;
		   }
		   return Integer.valueOf(v2.get(1)) - Integer.valueOf(v1.get(1));
		  }
		 };
	public OkCoinComData()
	{
		tickerSellData = new BaseList[5];
		tickerBuyData = new BaseList[5];
		verTickerSellData = new Vector<BaseList>();
		verTickerBuyData = new Vector<BaseList>();
	}
	public void setTickerData(String result)
	{
		JSONObject  dataJson = new JSONObject(JSON.parseObject(result));	
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

            public int compare(BaseList left, BaseList right) {
            	if(left.value > right.value)
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

            public int compare(BaseList left, BaseList right) {
            	if(left.value > right.value)
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
	public BaseList[] getTickerSellData()
	{
		return tickerSellData;
	}
	public BaseList[] getTickerBuyData()
	{
		return tickerBuyData;
	}
	public void sort()//鎺掑簭
	{
		for(int i = 0;i < tickerSellData.length;i++)
		{
			for(int j = i;j < tickerSellData.length;j++)
			{
				if(tickerSellData[j].value < tickerSellData[i].value)
				{
					float value;
					value = tickerSellData[j].value;
					tickerSellData[j].value = tickerSellData[i].value;
					tickerSellData[i].value = value;
					
					value = tickerSellData[j].num;
					tickerSellData[j].num = tickerSellData[i].num;
					tickerSellData[i].num = value;
					
				}
			}
		}
		for(int i = 0;i < tickerBuyData.length;i++)
		{
			for(int j = i;j < tickerBuyData.length;j++)
			{
				if(tickerBuyData[j].value < tickerBuyData[i].value)
				{
					float value;
					value = tickerBuyData[j].value;
					tickerBuyData[j].value = tickerBuyData[i].value;
					tickerBuyData[i].value = value;
					
					value = tickerBuyData[j].num;
					tickerBuyData[j].num = tickerBuyData[i].num;
					tickerBuyData[i].num = value;
					
				}
			}
		}
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}
}
