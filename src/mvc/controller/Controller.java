package mvc.controller;

import config.Config;
import mvc.model.ModelBase;
import mvc.model.ModelMain;
import mvc.view.ViewBase;
import base.BaseList;
import base.BaseNode;

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
	public Controller()
	{
		isOver = false;
		model = new ModelMain();
		view = new ViewBase("姣旂壒甯佷氦鏄撴満鍣ㄤ汉",this);
		
		//娣诲姞ok涓浗鎺у埗鍣�
		okCoinCnController = new OkCoinCnController(this);
		
		huobiController = new HuoBiController(this);
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
	}

}
