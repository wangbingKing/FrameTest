package mvc.controller;

import java.util.Vector;

import config.Config;
import mvc.model.ModelBase;
import mvc.model.ModelMain;
import mvc.model.UserConfigData;
import mvc.view.ViewBase;
import base.BaseNode;
import base.UserConBase;

public class Controller implements BaseNode{
	/**
	 * 鏁版嵁闆�
	 */
	public ModelMain model;
	/**
	 * 宸ョ▼缁撴潫
	 */
	public Boolean isOver;
	/**
	 * view 涓荤晫闈�
	 */
	public ViewBase view;
	public OkCoinCnController okCoinCnController; //ok涓浗
	public HuoBiController huobiController;
	public Vector<ProcessControllerAI> processAI;
	/**
	 * set user config data
	 */
	public UserConfigData userData;
	public Controller()
	{
		isOver = false;
		model = new ModelMain();
		view = new ViewBase("比特币交易机器人",this);
		
		//娣诲姞ok涓浗鎺у埗鍣�
		okCoinCnController = new OkCoinCnController(this);
		
		huobiController = new HuoBiController(this);
		this.initProcess();
		
	}
	/**
	 * 初始化状态机
	 */
	public void initProcess()
	{
		userData = new UserConfigData();
		processAI = new Vector<ProcessControllerAI>();
		
	}
	/**
	 * 刷新状态机
	 */
	public void updata_Process()
	{
		for(int i = 0;i<processAI.size();i++)
		{
			processAI.get(i).updata();
		}
	}
	public void addProcess(UserConBase data)
	{
		if(data.U_id == -1)
		{
			long t2=System.currentTimeMillis();
			data.U_id = t2;
		}
		userData.addControlData(data);
		ProcessControllerAI proc = new ProcessControllerAI(data.U_id,data,this);
		processAI.add(proc);
		
	}
	public Boolean removeProcess(long U_id)
	{
		Boolean result = false;
		for(int i = 0;i < processAI.size();i++)
		{
			if(((ProcessControllerAI)processAI.get(i)).U_id == U_id)
			{
				processAI.remove(i);
				result = true;
				break;
			}
		}
		Boolean dataResult = userData.removeControlData(U_id);
		return result && dataResult;
	}
	/**
	 * 浠庣紦瀛樹腑鑾峰彇鏁版嵁
	 */
	public ModelBase getModelData(int pt)
	{
		switch(pt)
		{
		case Config.OKCOINCN:
			return model.okcoinCnData;
		case Config.OKCOINCOM:
			return null;
		case Config.BTBVC:
			return null;
		case Config.HUOBI:
			return model.huoBIData;
		default:
			return null;
		}
	}
	/**
	 * 鏁翠釜宸ョ▼缁撴潫鏄惁
	 * @return
	 */
	public Boolean isOver()
	{
		return this.isOver;
	}
	/**
	 * OkCoin涓浗鏁版嵁鍒锋柊
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		okCoinCnController.update();//鏇存柊OkCoin涓浗鏁版嵁
		huobiController.update();
		view.update();
		updata_Process();//更新状态机
	}

}
