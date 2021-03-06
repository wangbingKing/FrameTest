package mvc.controller;

import java.util.Vector;

import config.Config;
import mvc.model.ModelBase;
import mvc.model.ModelMain;
import mvc.model.UserConfigData;
import mvc.view.ViewBase;
import base.BaseConfig;
import base.BaseErAiCheckData;
import base.BaseList;
import base.BaseNewErConData;
import base.BaseNode;
import base.BaseOrder;
import base.BaseUserInfo;
import base.BaseWuAi;
import base.UserConBase;
import mvc.view.mainFrame.MainFrame;

public class Controller implements BaseNode{
	/**
	 * 
	 */
	public ModelMain model;
	/**
	 * 
	 */
	public Boolean isOver;
	/**
	 * view 
	 */
    public MainFrame mainview;
    public OkCoinCnController okCoinCnController; 
    public OkCoinComController okCoinComController;
    public HuoBiController huobiController;
    public BitVcController bitVcController;
    public Vector<ProcessControllerAI> processAI;
    public Vector<ProcessControllerAIEr> processAIEr;
    public Vector<ProcessControllerAIWu> processControllerAIWu;
    public Vector<ProcessControllerAINewER> processControllerAINewER;
	/**
	 * set user config data
	 */
	public UserConfigData userData;
	public Controller()
	{
		isOver = false;
		model = new ModelMain();
		mainview = new MainFrame("比特币交易机器人",this);
		okCoinCnController = new OkCoinCnController(this);
		okCoinComController = new OkCoinComController(this);
		huobiController = new HuoBiController(this);
                bitVcController = new BitVcController(this);
		this.initProcess();
		
                
		 try {
		        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		            if ("Nimbus".equals(info.getName())) {
		                javax.swing.UIManager.setLookAndFeel(info.getClassName());
		                break;
		            }
		        }
		    } catch (ClassNotFoundException ex) {
		        java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		    } catch (InstantiationException ex) {
		        java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		    } catch (IllegalAccessException ex) {
		        java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
		        java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		    }
		    //</editor-fold>
		    //</editor-fold>
		
		    /* Create and display the form */
		    java.awt.EventQueue.invokeLater(new Runnable() {
		        public void run() {
		            mainview.setVisible(true);
		        }
		    });
                
	}
	public void setHoldOrderModel(int pt,String result,int type)
	{
		model.setHoldOrderData(pt, result, type);
                updateHoldOrderView();
	}
	public BaseConfig getUserConfig(int pt)
	{
		switch(pt)
		{
		case Config.OKCOINCN:
			return okCoinCnController.getUserKey();
		case Config.OKCOINCOM:
                        return okCoinComController.getUserKey();
		case Config.BTBVC:
			return bitVcController.getUserKey();
		case Config.HUOBI:
			return huobiController.getUserKey();
		default:
			return null;
		}
	}
        public void updateHoldOrderView()
        {
            mainview.updateHoldView();
        }
	/**
	 * 初始化状态机
	 */
	public void initProcess()
	{
		userData = new UserConfigData();
		processAI = new Vector<ProcessControllerAI>();
        processAIEr = new Vector<ProcessControllerAIEr>();
        processControllerAIWu = new Vector<ProcessControllerAIWu>();
        processControllerAINewER=new Vector<ProcessControllerAINewER>();
	}
	/**
	 * 刷新状态机
	 */
	public void updata_Process()
	{
		for(int i = 0;i<processAI.size();i++)
		{
			processAI.get(i).updata();
		}
                for(int i = 0;i<processAIEr.size();i++)
		{
			processAIEr.get(i).updata();
		}
	}
        public void update_WU_Process()
        {
            Vector<BaseOrder> orderLists = new Vector<BaseOrder>();
//            for(int i = 0;i<processControllerAIWu.size();i++)
//            {
//                if(processControllerAIWu.get(i).state != Config.WUSTATE.REQUESTING)
//                {
//                    Vector<BaseOrder> orderlist = processControllerAIWu.get(i).tradeList;
//                    if(!orderlist.isEmpty());
//                    {
//                        BaseOrder list = orderlist.get(0);
//                        orderLists.add(list);
//                    }
//                }    
//            }
            
            
        }
        public void checkOrderData(Vector<BaseOrder> orderLists,int pt,int mf)
        {
            String orders = "";
            for(int i = 0;i<orderLists.size();i++)
            {
                BaseOrder order = orderLists.get(i);
                if(order.pt == pt && order.mf == mf)
                {
                    orders = orders + "," + order.orderID;
                }
            }
            
        }
	public void addProcess(UserConBase data)
	{
		if(data.U_id == -1)
		{
			long t2=System.currentTimeMillis();
			data.U_id = t2;
		}
		userData.addControlData(data);
		ProcessControllerAI proc = new ProcessControllerAI(data.U_id,data,this);
		processAI.add(proc);
		
	}
        public void addProcessEr(BaseErAiCheckData data)
	{
		if(data.U_id == -1)
		{
			long t2=System.currentTimeMillis();
			data.U_id = t2;
		}
		ProcessControllerAIEr proc = new ProcessControllerAIEr(data.U_id,data,this);
		processAIEr.add(proc);
		
	}
    public void addProcessWu(BaseWuAi data)
	{
		if(data.U_id == -1)
		{
			long t2=System.currentTimeMillis();
			data.U_id = t2;
		}
		ProcessControllerAIWu proc = new ProcessControllerAIWu(data.U_id,data,this);
		processControllerAIWu.add(proc);
		
	}
        
    public void addProcessAINewER(BaseNewErConData data)
	{
        long t2=System.currentTimeMillis();
        data.U_id = t2;
        ProcessControllerAINewER proc = new ProcessControllerAINewER(data.U_id,data,this);
        processControllerAINewER.add(proc);
		
	}
    public void updateAiNewER()
    {
    	for(int i = 0;i < processControllerAINewER.size();i++)
    	{
    		processControllerAINewER.get(i).updata();
    	}
    }
	public void updateAccount(int pt,BaseUserInfo baseUserInfo)
	{
		mainview.updateUserInfo(pt, baseUserInfo);
	}
	public Boolean removeProcess(long U_id)
	{
		Boolean result = false;
		for(int i = 0;i < processAI.size();i++)
		{
			if(((ProcessControllerAI)processAI.get(i)).U_id == U_id)
			{
				processAI.remove(i);
				result = true;
				break;
			}
		}
		Boolean dataResult = userData.removeControlData(U_id);
		return result && dataResult;
	}
        public Boolean removeProcessEr(long U_id)
	{
		Boolean result = false;
		for(int i = 0;i < processAIEr.size();i++)
		{
			if(((ProcessControllerAIEr)processAIEr.get(i)).U_id == U_id)
			{
				processAIEr.remove(i);
				result = true;
				break;
			}
		}
		return result;
	}
        public Boolean removeProcessWu(long U_id)
	{
		Boolean result = false;
		for(int i = 0;i < processControllerAIWu.size();i++)
		{
			if(((ProcessControllerAIWu)processControllerAIWu.get(i)).U_id == U_id)
			{
				processControllerAIWu.remove(i);
				result = true;
				break;
			}
		}
		return result;
	}
    public Boolean removeProcessAINewER(long U_id)
	{
		Boolean result = false;
		for(int i = 0;i < processControllerAINewER.size();i++)
		{
			if(((ProcessControllerAINewER)processControllerAINewER.get(i)).U_id == U_id)
			{
				processControllerAINewER.remove(i);
				result = true;
				break;
			}
		}
		return result;
	}
        /**
         * 现货买卖请求
         * @param bs
         * @param value
         * @param num 
         */
        public Boolean bsRequest(int bs,double value,double num,int pt)
        {
            if(pt == Config.OKCOINCN)
            {
            	okCoinCnController.bsRequest(bs,value, num);
            }
            else if(pt == Config.OKCOINCOM)  
            {
                okCoinComController.bsRequest(bs,value, num);
            }
            else if (pt == Config.HUOBI)
            {
            	this.huobiController.bsRequest(bs, value, num);
            }
            return true;
        }
        public Boolean bsRequest(int bs,int xs,double value,double num,int pt)
        {
            if(pt == Config.OKCOINCN)
            {
            	okCoinCnController.bsRequest(bs,xs,value, num);
            }
            else if(pt == Config.OKCOINCOM)  
            {
                okCoinComController.bsRequest(bs,xs,value, num);
            }
            else if (pt == Config.HUOBI)
            {
            	this.huobiController.bsRequest(bs,xs, value, num);
            }
            return true;
        }
        public Boolean bsFutureRequest(int bs,int type,double value,double num,int pt)
        {
        
            if(pt == Config.OKCOINCOM)  
            {
                okCoinComController.bsFutureRequest(bs,type,value, num, pt);
            }
            else if (pt == Config.BTBVC)
            {
            	this.bitVcController.bsFutureRequest(bs,type,value, num, pt);
            }
        	return true;
        }
        
	/**
	 * 浠庣紦瀛樹腑鑾峰彇鏁版嵁
	 */
	public ModelBase getModelData(int pt)
	{
		switch(pt)
		{
		case Config.OKCOINCN:
			return model.okcoinCnData;
		case Config.OKCOINCOM:
			return model.okCoinComData;
		case Config.BTBVC:
			return null;
		case Config.HUOBI:
			return model.huoBIData;
		default:
			return null;
		}
	}
        public double getNewPtPrice(int pt)
        {
            int xp = (pt - 1)/3;
            int ptidx = (pt - 1)%3;
            if(xp == 0)
            {
                switch(pt)
                {
                    case Config.OKCOINCN:
                        return okCoinCnController.newTrandMoney;
                    case Config.OKCOINCOM:
                        return okCoinComController.newTrandMoney;
                    case Config.HUOBI:
                        return huobiController.newTrandMoney;
                    case Config.BTCC:
                        break;
                }
            }
            else
            {
                if(xp == 1)
                {
                    switch(pt)
                    {
                        case Config.THIS_WEEK_FURTURE:
                            return okCoinComController.newThisWeekMoney;
                        case Config.NEXT_WEEK_FURTURE:
                            return okCoinComController.newNextWeekMoney;
                        case Config.NEXT_MONTH_FURTURE:
                            return okCoinComController.newQuarterMoney;
                    }
                }
                else if(xp == 2)
                {
                    switch(pt)
                    {
                        case Config.THIS_WEEK_FURTURE:
                            return bitVcController.newThisWeekMoney;
                        case Config.NEXT_WEEK_FURTURE:
                            return bitVcController.newNextWeekMoney;
                        case Config.NEXT_MONTH_FURTURE:
                            return bitVcController.newQuarterMoney;
                    }
                }
            }
            
            return 0.0f;
        }
	/**
	 * 
	 * @return
	 */
	public Boolean isOver()
	{
		return this.isOver;
	}
	/**
	 * OkCoin
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		okCoinCnController.update();
		huobiController.update();
		okCoinComController.update();
        bitVcController.update();
		updata_Process();//更新状态机
        mainview.update();
        updateAiNewER();
	}

}
