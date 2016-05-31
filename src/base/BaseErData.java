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
public class BaseErData {
    public int Pt_L;
    public int bs_L;
    /**
     * 0 gao
     * 1 di
     */
    public int gd;
    public int Pt_R;
    public int bs_R;
    /**
     * 操作
     */
    public int do_BS;
    /**
     * 比例 0
     * 函数 1
     */
    public int han_bi;
    
    public double bi;
    public double a;
    public double b;
    
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
            return (v * bi);
        }
    }
}
