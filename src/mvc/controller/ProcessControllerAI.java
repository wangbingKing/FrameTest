package mvc.controller;

import machine.MachineInterface;
import base.BaseNode;

public class ProcessControllerAI implements ProcessInterface {
	MachineInterface machine;
	public ProcessControllerAI()
	{

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
