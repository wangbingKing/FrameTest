package mvc.model;

import base.BaseList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HuoBIData extends ModelBase{
	public BaseList tickerSellData[];//现货卖数据
	public BaseList tickerBuyData[];//现货买数据
	public HuoBIData()
	{
		tickerSellData = new BaseList[5];
		tickerBuyData = new BaseList[5];
		for(int i = 0;i < 5;i++)
		{
			tickerSellData[i] = new BaseList();
			tickerBuyData[i] = new BaseList();
		}
	}
	public void setTickerData(String result)
	{
		JSONObject  dataJson = new JSONObject(JSON.parseObject(result));	
		JSONArray data=dataJson.getJSONArray("top_buy");
		for(int i = 0;i < 5; i++)
		{
			JSONObject info=data.getJSONObject(i);
			tickerBuyData[i].num=info.getFloat("amount");
			tickerBuyData[i].value=info.getFloat("price");
		}
		data=dataJson.getJSONArray("top_sell");
		for(int i = 0;i < 5; i++)
		{
			JSONObject info=data.getJSONObject(i);
			tickerSellData[i].num=info.getFloat("amount");
			tickerSellData[i].value=info.getFloat("price");
		}
		sort();
	}
	public BaseList[] getTickerSellData()
	{
		return tickerSellData;
	}
	public BaseList[] getTickerBuyData()
	{
		return tickerBuyData;
	}
	public void sort()//排序
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
