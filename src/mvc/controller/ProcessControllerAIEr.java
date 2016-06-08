package mvc.controller;

import base.BaseErAiCheckData;
import java.util.Vector;

import machine.InitMachine;
import machine.MachineInterface;
import mvc.model.ModelBase;
import base.BaseList;
import base.BaseNewErConData;
import base.BaseNode;
import base.BaseOrder;
import base.UserConBase;

public class ProcessControllerAIEr implements ProcessInterface {
        BaseNewErConData conData;
        
	/**
	 * 运行的状态机
	 */
	MachineInterface machine;
	/**
	 * 用户检测的数据
	 */
	public BaseErAiCheckData baseErAiCheckData;
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
	public ProcessControllerAIEr(long U_id,BaseErAiCheckData userCheckData,Controller mainControl)
	{
		this.U_id = U_id;
		this.mainControl = mainControl;
                baseErAiCheckData = userCheckData;
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
	@Override
	public void updata() {
		// TODO Auto-generated method stub
	}
	@Override
	public ModelBase getModelData(int pt)
	{
		return this.mainControl.getModelData(pt);
	}
}
