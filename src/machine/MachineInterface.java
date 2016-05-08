package machine;

import mvc.controller.ProcessControllerAI;
import mvc.controller.ProcessInterface;
/**
 * 有限状态机
 * @author bj
 *
 */
public interface MachineInterface{//有限状态机
	void updata();
	void changeMachine(MachineInterface machine);
	void setController(ProcessControllerAI controller);
}
