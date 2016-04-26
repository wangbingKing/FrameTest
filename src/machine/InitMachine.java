package machine;

import base.UserConBase;
import mvc.controller.ProcessControllerAI;
/**
 * 用于初始化状态机
 * 状态机下一状态是监听数据状态
 * 当前切换到下一状态
 * @author Dolomo
 *
 */
public class InitMachine extends BaseMachine{
	public InitMachine(UserConBase userCheckData)
	{
		super(userCheckData);
	}
	@Override
	public void updata() {
		// TODO Auto-generated method stub
		super.updata();
		
		
	}
}
