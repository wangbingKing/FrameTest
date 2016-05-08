package base;

import config.Config;

public class BaseOrder {
	/**
	 * 订单id
	 */
	public String orderID;
	/**
	 * 订单状态
	 * Config.TRADESTATE
	 * 
	 */
	public Config.TRADESTATE state;
	/**
	 * 完成数
	 */
	public float finishNum;
	/**
	 * 剩余数量
	 */
	public float remainNum;
	public BaseOrder()
	{
		this.orderID = "";
		this.state = Config.TRADESTATE.NULL_TRADESTATE;
		this.finishNum = 0.0f;
		this.remainNum = 0.0f;
	}
}
