package machine;

import base.UserConBase;
import mvc.controller.ProcessControllerAI;
import mvc.controller.ProcessInterface;

public class BaseMachine implements MachineInterface{
	UserConBase userCheckData;
	ProcessInterface controller;
	public BaseMachine(UserConBase userCheckData)
	{
		this.userCheckData = userCheckData;
	}
	@Override
	public void updata() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeMachine(MachineInterface machine) {
		// TODO Auto-generated method stub
		controller.setControllerMachine(machine);
	}

	@Override
	public void setController(ProcessInterface controller) {
		// TODO Auto-generated method stub
		this.controller = controller;
	}

}
