package machine;

import mvc.controller.ProcessControllerAI;
/**
 * ����״̬��
 * @author bj
 *
 */
public interface MachineInterface{//����״̬��
	void updata();
	void changeMachine(MachineInterface machine);
	void setController(ProcessControllerAI controller);
}
