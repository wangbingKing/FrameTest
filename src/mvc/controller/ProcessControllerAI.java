package mvc.controller;

import machine.InitMachine;
import machine.MachineInterface;
import mvc.model.ModelBase;
import base.BaseNode;
import base.UserConBase;

public class ProcessControllerAI implements ProcessInterface {
	MachineInterface machine;
	UserConBase userCheckData;
	Controller mainControl;
	public long U_id = -1;
	public ProcessControllerAI(long U_id,UserConBase userCheckData,Controller mainControl)
	{
		this.U_id = -1;
		this.mainControl = mainControl;
		MachineInterface machine = new InitMachine(this,userCheckData);
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
	@Override
	public ModelBase getModelData(int pt)
	{
		return this.mainControl.getModelData(pt);
	}
}
