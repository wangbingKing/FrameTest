package mvc.model;

import config.Config;

public class ModelMain extends ModelBase{
	public OkCoinCnData okcoinCnData;//Ok中国数据缓存
	public HuoBIData huoBIData;//火币现货数据
	public OkCoinComData okCoinComData;//ok国际
	public BitVcData bitVcData;
	public ModelMain()
	{
		okcoinCnData = new OkCoinCnData();
		huoBIData = new HuoBIData();
		okCoinComData = new OkCoinComData();
		bitVcData = new BitVcData();
	}
	public void setHoldOrderData(int pt,String str,String type)
	{
		switch(pt)
		{
		case Config.OKCOINCOM:
			break;
		case Config.BTBVC:
			bitVcData.setHoldOrderData(str,type);
			break;
		default:
			System.out.println("平台不存在期货");
		}
	}
	public void setTickerData(int pt,String str)
	{
		switch(pt)
		{
		case Config.HUOBI:
			huoBIData.setTickerData(str);
			break;
		case Config.OKCOINCN:
			okcoinCnData.setTickerData(str);
			break;
		case Config.OKCOINCOM:
			okCoinComData.setTickerData(str);
			break;
		}
		
	}
	public void setFutureData(int pt,String str,String type)
	{
		switch(pt)
		{
		case Config.OKCOINCOM:
			okCoinComData.setFutureData(str);
			break;
		case Config.BTBVC:
			
			break;
		}
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}
}
