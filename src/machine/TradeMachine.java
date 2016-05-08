package machine;

import base.BaseList;
import base.UserConBase;
import config.Config;
import java.util.Arrays;
import java.util.Vector;
import mvc.controller.ProcessControllerAI;
import mvc.controller.ProcessInterface;
import mvc.model.HuoBIData;
import mvc.model.ModelBase;
import mvc.model.OkCoinCnData;
import mvc.model.OkCoinComData;
/**
 * 用于初始化状态机
 * 当前检测数据状态
 * 状态机下一状态是交易状态
 * 当前切换到下一状态
 * @author dabing
 *
 */
public class TradeMachine extends BaseMachine{
    
    public static enum TRADESTATE
    {
        STATE_BEGIN,//开始交易
        STATE_SENDREQUESE,
        STATE_TRADEINT,
        STATE_CHECKRESULT,  
    }
	public TradeMachine(ProcessControllerAI controller,UserConBase userCheckData)
	{
		super(controller,userCheckData);
	}
	@Override
	public void updata() {
		// TODO Auto-generated method stub
		super.updata();
		
	}
}
