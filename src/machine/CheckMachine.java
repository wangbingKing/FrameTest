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

        
	public CheckMachine(ProcessControllerAI controller,UserConBase userCheckData)
	{
		super(controller,userCheckData);
	}
	@Override
	public void updata() {
		// TODO Auto-generated method stub
		super.updata();
		this.controller.clearCheckData();
        ModelBase dataLeft = this.controller.getModelData(this.userCheckData.platLeft);              
        ModelBase dataRight = this.controller.getModelData(this.userCheckData.platRight);
        Tools.setDataTo(this.controller.buyListLift, this.controller.sellListLift, this.userCheckData.platLeft, dataLeft, this.userCheckData);
        Tools.setDataTo(this.controller.buyListRight, this.controller.sellListRight, this.userCheckData.platRight, dataRight, this.userCheckData);
        float num = Tools.getResidual(this.controller.buyListLift, this.controller.sellListLift, this.controller.buyListRight, this.controller.sellListRight, this.userCheckData);
        if(num > 0)
        {
        	//满足条件  切换到交易状态
              TradeMachine machine = new TradeMachine(this.controller,this.userCheckData);
              this.changeMachine(machine);//切换到监听数据状态
              machine.updata();
        }
                
	}
}
