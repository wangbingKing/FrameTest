package base;

public class UserConBase {

	/**
	 * 平台
	 */
	public int platLeft = -1;
	/**
	 * 现货 期货
	 */
	public int TypeMTLeft = -1;
	
	/**
	 * 当周 下周 下月
	 */
	public int furtureLeft = -1;
	
	/**
	 * 买卖
	 */
	public int BSStateLeft = -1;
    /**
     * 买卖价
     */
    public float BSMoneyLeft = 0;
	/**
	 * 大于 等于 小于
	 */
	public int ComPareState = -1;
	/**
	 * 当周 下周 下月
	 */
	public int furtureRight = -1;
	/**
	 * 现货 期货
	 */
	public int TypeMTRight = -1;
	/**
	 * 平台
	 */
	public int platRight = -1;
	/**
	 * 买卖
	 */
	public int BSStateRight = -1;
    /**
     * 买卖价
     */
    public float BSMoneyRight = 0;
	/**
	 * 交易状态
	 */
	public int tradeState = -1;
	/**
	 * U_id
	 * 唯一id
	 */
	public long U_id = -1;
    /**
     * 差价
     */
    public int money = 0;
    
    /**
     * 符合条件的买卖价数量
     */
    public float checkNum = 0.0f;
}
