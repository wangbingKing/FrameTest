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
public class BaseErAiCheckData {
    /**
    * U_id
    * 唯一id
    */
    public long U_id = -1;
    public BaseErData leftCheckData;
    public BaseErData rightCheckData;
    public int compare;
    public double maxNum;
}
