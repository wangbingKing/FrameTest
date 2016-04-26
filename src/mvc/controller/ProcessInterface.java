package mvc.controller;

import machine.MachineInterface;

public interface ProcessInterface {
	void updata();
	/**
	 * …Ë÷√con
	 * @param machine
	 */
	public void setControllerMachine(MachineInterface machine);
}
