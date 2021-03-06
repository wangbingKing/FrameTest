/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import config.Config;
/**
 *
 * @author wuxianshikong
 */
public class BaseNewErConData {
    public Config.ER_AI_STATE state = Config.ER_AI_STATE.NULLSTATE;
    public long U_id;
    public int pt_L;
    public int xs_L;
    
    
    
    public double price;
    public double amount;
    public double total;
    /**
     * 比例 0
     * 函数 1
     */
    public int han_bi;
    
    public double bi;
    public double a;
    public double b;
    /**
     * 最新价
     */
    public double newPrice;
    
    /**
     * 操作
     */
    public int do_BS;
    public int gd_L;
    /**
     * 初始设置最新价  不变
     */
    public double basePrice;
    public double basePrice_special;
    public int pt_R;
    public int special;
    public double newPrice_special;
    /**
     * 比例 0
     * 函数 1
     */
    public int gd_R;
    public int han_bi_special;
    
    public double bi_special;
    public double a_special;
    public double b_special;
    /**
     * 期货
     */
    public int current_future;
    public int future_type;
    public int isDuishou;
    /**
     * 传当前的价格
     * 获取比较的值
     * @return 
     */
    public double getValue(double v)
    {
        if(han_bi == 1)
        {
        	long t1=System.currentTimeMillis();
        	int x = (int) ((t1 - U_id)/360000);
            return (x * 0.1 * a + b);
        }
        else
        {
            return (basePrice * bi);
        }
    }
    public double getSpecialValue(double v)
    {
        if(han_bi_special == 1)
        {
        	long t1=System.currentTimeMillis();
        	int x = (int) ((t1 - U_id)/360000);
            return (x * 0.1 * a_special + b_special);
        }
        else
        {
            return (basePrice_special * bi_special);
        }
    } 
}
