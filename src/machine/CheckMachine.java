package machine;

import base.BaseList;
import base.UserConBase;
import config.Config;
import java.util.Arrays;
import java.util.Vector;

import tools.Tools;
import mvc.controller.ProcessControllerAI;
import mvc.controller.ProcessInterface;
import mvc.model.HuoBIData;
import mvc.model.ModelBase;
import mvc.model.OkCoinCnData;
import mvc.model.OkCoinComData;
/**
 * 用于初始化状态机
 * 当前检测数据状态
 * 状态机下一状态是交易状态
 * 当前切换到下一状态
 * @author dabing
 *
 */
public class CheckMachine extends BaseMachine{
        Vector<BaseList> buyListLift;
        Vector<BaseList> sellListLift;
        Vector<BaseList> buyListRight;
        Vector<BaseList> sellListRight;
        
	public CheckMachine(ProcessInterface controller,UserConBase userCheckData)
	{
		super(controller,userCheckData);
                buyListLift = new Vector<BaseList>();
                sellListLift = new Vector<BaseList>();
                buyListRight = new Vector<BaseList>();
                sellListRight = new Vector<BaseList>();
	}
	@Override
	public void updata() {
		// TODO Auto-generated method stub
		super.updata();
                buyListLift.clear();
                sellListLift.clear();
                buyListRight.clear();
                sellListRight.clear();
                ModelBase dataLeft = this.controller.getModelData(this.userCheckData.platLeft);              
                ModelBase dataRight = this.controller.getModelData(this.userCheckData.platRight);
                Tools.setDataTo(buyListLift, sellListLift, this.userCheckData.platLeft, dataLeft, this.userCheckData);
                Tools.setDataTo(buyListRight, sellListRight, this.userCheckData.platRight, dataRight, this.userCheckData);
                float num = Tools.getResidual(this.buyListLift, this.sellListLift, this.buyListRight, this.sellListRight, this.userCheckData);
                if(num > 0)
                {
                	//满足条件  切换到交易状态
	                  TradeMachine machine = new TradeMachine(this.controller,this.userCheckData);
	                  this.changeMachine(machine);//切换到监听数据状态
	                  machine.updata();
                }
                
	}
}
