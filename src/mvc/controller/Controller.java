package mvc.controller;

import java.util.Vector;

import config.Config;
import mvc.model.ModelBase;
import mvc.model.ModelMain;
import mvc.model.UserConfigData;
import mvc.view.ViewBase;
import base.BaseConfig;
import base.BaseNode;
import base.BaseUserInfo;
import base.UserConBase;
import mvc.view.mainFrame.MainFrame;

public class Controller implements BaseNode{
	/**
	 * 鏁版嵁闆�
	 */
	public ModelMain model;
	/**
	 * 宸ョ▼缁撴潫
	 */
	public Boolean isOver;
	/**
	 * view 涓荤晫闈�
	 */
	public ViewBase view;
    public MainFrame mainview;
	public OkCoinCnController okCoinCnController; //ok涓浗
	public OkCoinComController okCoinComController;
	public HuoBiController huobiController;
	public BitVcController bitVcController;
	public Vector<ProcessControllerAI> processAI;
	/**
	 * set user config data
	 */
	public UserConfigData userData;
	public Controller()
	{
		isOver = false;
		model = new ModelMain();
//		view = new ViewBase("比特币交易机器人",this);
		mainview = new MainFrame("比特币交易机器人",this);
		//娣诲姞ok涓浗鎺у埗鍣�
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
	}
	public BaseConfig getUserConfig(int pt)
	{
		switch(pt)
		{
		case Config.OKCOINCN:
			return okCoinCnController.getUserKey();
		case Config.OKCOINCOM:
			return null;
		case Config.BTBVC:
			return null;
		case Config.HUOBI:
			return huobiController.getUserKey();
		default:
			return null;
		}
	}
	/**
	 * 初始化状态机
	 */
	public void initProcess()
	{
		userData = new UserConfigData();
		processAI = new Vector<ProcessControllerAI>();
        UserConBase data = new UserConBase();
        data.BSStateLeft = Config.BUY_ID;
        data.BSStateRight = Config.SELL_ID;
        
        data.TypeMTLeft = Config.SELL_ID;
        data.TypeMTRight = Config.BUY_ID;
        
        data.platLeft = Config.OKCOINCN;
        data.platRight = Config.OKCOINCN;
        
//      data.ComPareState = Config.
        
//        this.addProcess(data);
		
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
	/**
	 * 鏁翠釜宸ョ▼缁撴潫鏄惁
	 * @return
	 */
	public Boolean isOver()
	{
		return this.isOver;
	}
	/**
	 * OkCoin涓浗鏁版嵁鍒锋柊
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		okCoinCnController.update();//鏇存柊OkCoin涓浗鏁版嵁
		huobiController.update();
		okCoinComController.update();
        bitVcController.update();
		updata_Process();//更新状态机
        mainview.update();
	}

}
