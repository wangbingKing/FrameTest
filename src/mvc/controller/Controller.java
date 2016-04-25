package mvc.controller;

import config.Config;
import mvc.model.ModelBase;
import mvc.model.ModelMain;
import mvc.view.ViewBase;
import base.BaseList;
import base.BaseNode;

public class Controller implements BaseNode{
	/**
	 * 数据集
	 */
	public ModelMain model;
	/**
	 * 工程结束
	 */
	public Boolean isOver;
	/**
	 * view 主界面
	 */
	public ViewBase view;
	public OkCoinCnController okCoinCnController; //ok中国
	public HuoBiController huobiController;
	public Controller()
	{
		isOver = false;
		model = new ModelMain();
		view = new ViewBase("比特币交易机器人",this);
		
		//添加ok中国控制器
		okCoinCnController = new OkCoinCnController(this);
		
		huobiController = new HuoBiController(this);
	}
	/**
	 * 从缓存中获取数据
	 */
	public ModelBase getModelData(int pt)
	{
		switch(pt)
		{
		case Config.OKCOINCN:
			return model.okcoinCnData;
		case Config.OKCOINCOM:
			return null;
		case Config.BITVC:
			return null;
		case Config.HUOBI:
			return model.huoBIData;
		default:
			return null;
		}
	}
	/**
	 * 整个工程结束是否
	 * @return
	 */
	public Boolean isOver()
	{
		return this.isOver;
	}
	/**
	 * OkCoin中国数据刷新
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		okCoinCnController.update();//更新OkCoin中国数据
		huobiController.update();
		view.update();
	}

}
