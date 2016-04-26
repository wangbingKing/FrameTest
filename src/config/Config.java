package config;
public class Config {
	public static final int OKCOINCN = 1;  	//ok涓浗
	public static final int OKCOINCOM = 2; 	//ok鍥介檯
	public static final int HUOBI = 3; 		//鐏竵
	public static final int BTBVC = 4; 		//btbvc	
	public static final int BTCC  = 5; 		//比特币中国
	
	public static enum stateAction
	{
		INIT_STATE,     //init
		NETING_STATE,   //net ing
		NETOEVR_STATE   //net over
		
	}
	public static final String[][]BTCPLAT = {
		{"OKCoin中国","现货",""},
		{"OKCoin国际","现货","期货"},
		{"火币网","现货",""},
		{"btcVc","","期货"},
		{"比特币中国","现货","期货"}
	}; 
	public static final int MODERN_ID = 0;
	public static final int FURTURE_ID = 1;
	public static String getStringMF(int mf)
	{
		String t[] = {"现货","期货"};
		return t[mf];
	}

	public static final int BUY_ID = 0;
	public static final int SELL_ID = 1;
	public static String getStringBS(int mf)
	{
		String t[] = {"买价","卖价"};
		return t[mf];
	}
	public static String getNameByPlat(int pt)
	{
		return BTCPLAT[pt-1][0];
	}
	public static final int THIS_WEEK_FURTURE = 0;
	public static final int NEXT_WEEK_FURTURE = 1;
	public static final int NEXT_MONTH_FURTURE = 2;
	public static String getStringFurture(int t)
	{
		String s[] = {"当周","下周","下月"};
		return s[t];
	}
	public static enum TRADESTATE
	{
		CHECKDATA_TRADESTATE,//检查检测数据是否达到要求
		BUYSELL_TRADESTATE,//满足要求进行交易（下单）
		
		BUYSELLSUCCESS_TRADESTATE//两方交易顺利，都完成交易
		
	}
}
