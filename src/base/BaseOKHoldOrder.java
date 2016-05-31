/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

/**
 *
 * @author wuxianshikong
 */
public class BaseOKHoldOrder {
    /**
     * (double):多仓数量
     */
    public double buy_amount;
    /**
     * 多仓可平仓数量
     */
    public double buy_available;
    /**
     * (double):开仓平均价
     */
    public double buy_price_avg;
    /**
     * (double):结算基准价
     */
    public double buy_price_cost;
    /**
     * (double):多仓已实现盈余
     */
    public double buy_profit_real;
    /**
     * (long):合约id
     */
    public long contract_id;
    /**
     * (long):创建日期
     */
    public long create_date;
    /**
     * :杠杆倍数
     */
    public double lever_rate;
    /**
     * (double):空仓数量
     */
    public double sell_amount;
    /**
     * :空仓可平仓数量
     */
    public double sell_available;
    /**
     * (double):开仓平均价
     */
    public double sell_price_avg;
    /**
     * (double):结算基准价
     */
    public double sell_price_cost;
    /**
     * (double):空仓已实现盈余
     */
    public double sell_profit_real;
    /**
     * :btc_usd:比特币,ltc_usd:莱特币
     */
    public String symbol = "btc_usd";
    /**
     * :合约类型
     */
    public int contract_type;
    /**
     * :预估爆仓价
     */
    public double force_liqu_price;
}
