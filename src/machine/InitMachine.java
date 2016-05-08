package machine;

import base.UserConBase;
import mvc.controller.ProcessControllerAI;
import mvc.controller.ProcessInterface;
/**
 * 用于初始化状态机
 * 状态机下一状态是监听数据状态
 * 当前切换到下一状态
 * @author dabing
 *
 */
public class InitMachine extends BaseMachine{
	public InitMachine(ProcessControllerAI controller,UserConBase userCheckData)
	{
		super(controller,userCheckData);
	}
	@Override
	public void updata() {
		// TODO Auto-generated method stub
		super.updata();
		this.changeMachine(new CheckMachine(this.controller,this.userCheckData));//切换到监听数据状态
	}
}
