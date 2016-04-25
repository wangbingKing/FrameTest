package mvc.model;

import config.Config;

public class ModelMain extends ModelBase{
	public OkCoinCnData okcoinCnData;//Ok涓浗鏁版嵁缂撳瓨
	public HuoBIData huoBIData;//鐏竵鐜拌揣鏁版嵁
	public OkCoinComData okCoinComData;//ok国际现货
	public ModelMain()
	{
		okcoinCnData = new OkCoinCnData();
		huoBIData = new HuoBIData();
		okCoinComData = new OkCoinComData();
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
	public void setFutureData(int pt,String str)
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
