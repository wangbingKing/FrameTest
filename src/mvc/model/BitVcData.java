/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model;

import base.BaseVcHoldOrder;
import java.util.Vector;

/**
 *
 * @author wuxianshikong
 * bitvc 数据存放
 */
public class BitVcData extends ModelBase{
    /**
     * 存放仓位信息
     */
    public Vector<BaseVcHoldOrder> bitvcData;
    public BitVcData()
    {
        bitvcData =new Vector<BaseVcHoldOrder>();
    }
    
    
    
    @Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}
}
