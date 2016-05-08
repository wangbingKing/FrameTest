package mvc.controller;

import machine.MachineInterface;
import mvc.model.ModelBase;

public interface ProcessInterface {
	void updata();
	/**
	 * ����con
	 * @param machine
	 */
	public ModelBase getModelData(int pt);
}
