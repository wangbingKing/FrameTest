package mvc.model;

import config.Config;

public class ModelMain extends ModelBase{
	public OkCoinCnData okcoinCnData;//Ok中国数据缓存
	public HuoBIData huoBIData;//火币现货数据
	public ModelMain()
	{
		okcoinCnData = new OkCoinCnData();
		huoBIData = new HuoBIData();
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
		}
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}
}
