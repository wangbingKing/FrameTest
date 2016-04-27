package machine;

import base.UserConBase;
import mvc.controller.ProcessControllerAI;
import mvc.controller.ProcessInterface;
import mvc.model.ModelBase;
/**
 * 用于初始化状态机
 * 当前检测数据状态
 * 状态机下一状态是交易状态
 * 当前切换到下一状态
 * @author dabing
 *
 */
public class CheckMachine extends BaseMachine{
	public CheckMachine(ProcessInterface controller,UserConBase userCheckData)
	{
		super(controller,userCheckData);
	}
	@Override
	public void updata() {
		// TODO Auto-generated method stub
		super.updata();
		ModelBase dataLeft = this.controller.getModelData(this.userCheckData.platLeft);
		
	}
}
