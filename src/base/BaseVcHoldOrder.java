/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

/**
 *
 * @author wuxianshikong
 * bitvc 仓位基本类型
 */
public class BaseVcHoldOrder {
    /**
     * 持仓ID
     */
    public String id;//: 1721,  
    /**
     * 交易类型  (1、买多 2、卖空）
     */
    public int tradeType;// : 2,              
    /**
     * 分仓爆仓价格
     */
    public double liquidatePrice;// : 2246.85
    /**
     * 仓位平均价格
     */
    public double price;// : 1881.8564094093,
    /**
     * 风险率
     */
    public double riskRate;// : 0,               
    /**
     * 仓位状态（0 正常 1 爆仓）
     */
    public int status;// : 0,                  
    /**
     * 分仓动态权益
     */
    public double dynamicRights;// : 0.1394,    
    /**
     * 持仓金额
     */
    public double money;// : 500,                
    /**
     * 可平金额
     */
    public double closeMoney;// : 400,           
    /**
     * 持仓盈亏
     */
    public double holdProfitLoss;// : 0.0914,     
    /**
     * 仓位占用保证金
     */
    public double usedMargin;// : 0.0479,          
    /**
     * 仓位ID （0 全仓 1-10 分仓）
     */
    public int storeId;// : 1,                
    /**
     * 最后操作时间
     */
    public double lastTime;// : 1427354462 
}
