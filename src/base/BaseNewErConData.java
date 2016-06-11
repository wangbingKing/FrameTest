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
public class BaseNewErConData {
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
    public double newPrice;
    
    /**
     * 操作
     */
    public int do_BS;
    
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
    public int han_bi_special;
    
    public double bi_special;
    public double a_special;
    public double b_special;
    
    /**
     * 传当前的价格
     * 获取比较的值
     * @return 
     */
    public double getValue(double v)
    {
        if(han_bi == 1)
        {
            return (v * a + b);
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
            return (v * a_special + b_special);
        }
        else
        {
            return (basePrice_special * bi_special);
        }
    } 
}
