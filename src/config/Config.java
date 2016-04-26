package config;
public class Config {
	public static final int OKCOINCN = 1;  	//ok中国
	public static final int OKCOINCOM = 2; 	//ok国际
	public static final int HUOBI = 3; 		//火币
	public static final int BTBVC = 4; 		//btbvc	
	public static final int BTCC  = 5; 		//���ر��й�
	
	public static enum stateAction
	{
		INIT_STATE,     //init
		NETING_STATE,   //net ing
		NETOEVR_STATE   //net over
		
	}
	public static final String[][]BTCPLAT = {
		{"OKCoin�й�","�ֻ�",""},
		{"OKCoin����","�ֻ�","�ڻ�"},
		{"�����","�ֻ�",""},
		{"btcVc","","�ڻ�"},
		{"���ر��й�","�ֻ�","�ڻ�"}
	}; 
	public static final int MODERN_ID = 0;
	public static final int FURTURE_ID = 1;
	public static String getStringMF(int mf)
	{
		String t[] = {"�ֻ�","�ڻ�"};
		return t[mf];
	}

	public static final int BUY_ID = 0;
	public static final int SELL_ID = 1;
	public static String getStringBS(int mf)
	{
		String t[] = {"���","����"};
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
		String s[] = {"����","����","����"};
		return s[t];
	}
	public static enum TRADESTATE
	{
		CHECKDATA_TRADESTATE,//����������Ƿ�ﵽҪ��
		BUYSELL_TRADESTATE,//����Ҫ����н��ף��µ���
		
		BUYSELLSUCCESS_TRADESTATE//��������˳��������ɽ���
		
	}
}
