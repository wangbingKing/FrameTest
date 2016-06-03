package config;
public class Config {
        /**
         * ok中国
         */
	public static final int OKCOINCN = 1;  	
       /**
        * ok国际
        */
	public static final int OKCOINCOM = 2;
        /**
         * 火币网
         */
	public static final int HUOBI = 3; 		
        /**
         * btbVc
         */
	public static final int BTBVC = 4; 		//btbvc	
        /**
         * 比特币中国
         */
	public static final int BTCC  = 5; 		//比特币中国
	
        
        /**
         * 网络状态
         */
	public static enum stateAction
	{
		INIT_STATE,     //init
		NETING_STATE,   //net ing
		NETOEVR_STATE   //net over
		
	}
        /**
         * 各个平台状态
         */
	public static final String[][]BTCPLAT = {
		{"OKCoin中国","现货",""},
		{"OKCoin国际","现货","期货"},
		{"火币网","现货",""},
		{"btcVc","","期货"},
		{"比特币中国","现货","期货"}
	}; 
        /**
         * 现货标志
         */
	public static final int MODERN_ID = 0;
        /**
         * 期货标志
         */
	public static final int FURTURE_ID = 1;
        /**
         * 获得现货期货字符串
         * @param 现货期货标志
         * @return 现货期货名
         */
	public static String getStringMF(int mf)
	{
		String t[] = {"现货","期货"};
		return t[mf];
	}
        /**
         * 买价标志
         */
	public static final int BUY_ID = 0;
        /**
         * 卖价标志
         */
	public static final int SELL_ID = 1;
        /**
         * 获得买卖价名称
         * @param 买卖价标志
         * @return 买卖价名称
         */
	public static String getStringBS(int mf)
	{
		String t[] = {"买价","卖价"};
		return t[mf];
	}
        public static String getStringBSDO(int mf)
	{
		String t[] = {"买入","卖出"};
		return t[mf];
	}
        /**
         * 获得平台名称
         * @param 平台标志
         * @return 平台名称
         */
	public static String getNameByPlat(int pt)
	{
		return BTCPLAT[pt-1][0];
	}
        /**
         * 期货当周标志
         */
	public static final int THIS_WEEK_FURTURE = 0;
        /**
         * 期货下周标志
         */
	public static final int NEXT_WEEK_FURTURE = 1;
        /**
         * 期货下月标志
         */
	public static final int NEXT_MONTH_FURTURE = 2;
        /**
         * 获得期货时期名
         * @param 期货时期标志
         * @return 期货时期名
         */
	public static String getStringFurture(int t)
	{
		String s[] = {"当周","下周","下月"};
		return s[t];
	}
        /**
         * 单子交易状态
         */
	public static enum TRADESTATE
	{
		/**
		 * 空状态
		 */
		NULL_TRADESTATE,
		/**
		 * 挂单中(交易中)
		 */
		TRADING_TRADESTATE,
		/**
		 * 撤单中
		 */
		REPEAL_TRADESTATE,
		/**
		 * 撤单完成
		 */
		REPEALOVER_TRADESTATE,
		/**
		 * 单子交易完成
		 */
		BUYSELLSUCCESS_TRADESTATE,
		/**
		 * 撤销失败的订单
		 */
		REPEALERROR_TRADESTATE
	}
        public static final int GAO_PRICE = 0;
        public static final int DI_PRICE = 1;
        public static final String CONPARE_GD[] = {"高于","低于"};
        public static String getHDStr(int gd)
        {
            return Config.CONPARE_GD[gd];
        }
        public static int YHF_ONE = 2;
        public static int YHF_ALL = 1;
        public static int YHF_NULL = 0;
        public static final String CONPARE_STR[] = {"无","都","任一"};
        
        public static final int ds_s = 0;
        public static final int ds_d = 1;
        
        public static String RUN_PT = "windos";
}
