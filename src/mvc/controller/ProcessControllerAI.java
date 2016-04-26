package mvc.controller;

import machine.InitMachine;
import machine.MachineInterface;
import base.BaseNode;
import base.UserConBase;

public class ProcessControllerAI implements ProcessInterface {
	MachineInterface machine;
	UserConBase userCheckData;
	public long U_id = -1;
	public ProcessControllerAI(long U_id,UserConBase userCheckData)
	{
		this.U_id = -1;
		MachineInterface machine = new InitMachine(userCheckData);
		this.setControllerMachine(machine);
	}
	
	@Override
	public void setControllerMachine(MachineInterface machine)
	{
		this.machine = machine;
	}
	@Override
	public void updata() {
		// TODO Auto-generated method stub
		machine.updata();
	}
	
}
