package mvc.controller;

import base.BaseErAiCheckData;
import java.util.Vector;

import machine.InitMachine;
import machine.MachineInterface;
import mvc.model.ModelBase;
import base.BaseList;
import base.BaseNode;
import base.BaseOrder;
import base.BaseWuAi;
import base.UserConBase;
import config.Config;

public class ProcessControllerAIWu implements ProcessInterface {
        public Config.WUSTATE state;
	/**
	 * 运行的状态机
	 */
	MachineInterface machine;
	/**
	 * 用户检测的数据
	 */
	public BaseWuAi baseWuAi;
	/**
	 * 主控制器
	 */
	Controller mainControl;
	/**
	 * 交易中的订单列表
	 */
	/**
	 * 获得要检测左边平台买单数据
	 */
	public Vector<BaseList> buyListLift;
	/**
	* 获得要检测左边平台卖单数据
	*/
	public Vector<BaseList> sellListLift;
	/**
	* 获得要检测右边平台买单数据
	*/
	public Vector<BaseList> buyListRight;
	/**
	* 获得要检测右边平台卖单数据
	*/
	public Vector<BaseList> sellListRight;
	
	/**
	 * 交易中的订单
	 */
	public Vector<BaseOrder> tradeList;
	/**
	 * 交易完成订单（所有数量的都被交易）
	 */
	public Vector<BaseOrder> tradeOverList;
	/**
	 * 正在撤单中的订单（）
	 */
	public Vector<BaseOrder> repealList;
	/**
	 * 撤单完成订单
	 */
	public Vector<BaseOrder> repealOverList;

	public long U_id = -1;
	public ProcessControllerAIWu(long U_id,BaseWuAi baseWuAiData,Controller mainControl)
	{
		this.U_id = U_id;
		this.mainControl = mainControl;
                baseWuAi = baseWuAiData;
		this.setControllerMachine(machine);
		buyListLift = new Vector<BaseList>();
                sellListLift = new Vector<BaseList>();
                buyListRight = new Vector<BaseList>();
                sellListRight = new Vector<BaseList>();
	}
	/**
	 * 清理左右双方检测数据
	 */
	public void clearCheckData()
	{
		buyListLift.clear();
        sellListLift.clear();
        buyListRight.clear();
        sellListRight.clear();
	}
	public void setControllerMachine(MachineInterface machine)
	{
		this.machine = machine;
	}
        public void updateHaveOrder()
        {
            
        }
	@Override
	public void updata() {
           

            
		// TODO Auto-generated method stub
//		machine.updata();
	}
	@Override
	public ModelBase getModelData(int pt)
	{
		return this.mainControl.getModelData(pt);
	}
}
