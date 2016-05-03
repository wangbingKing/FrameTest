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
        Vector<BaseList> buyListLift;
        Vector<BaseList> sellListLift;
        Vector<BaseList> buyListRight;
        Vector<BaseList> sellListRight;
        public static enum TRADESTATE
        {
            STATE_BEGIN,//开始交易
            STATE_SENDREQUESE,
            STATE_TRADEINT,
            STATE_CHECKRESULT,
            
            
        }
	public TradeMachine(ProcessInterface controller,UserConBase userCheckData)
	{
		super(controller,userCheckData);
                buyListLift = new Vector<BaseList>();
                sellListLift = new Vector<BaseList>();
                buyListRight = new Vector<BaseList>();
                sellListRight = new Vector<BaseList>();
	}
	@Override
	public void updata() {
		// TODO Auto-generated method stub
		super.updata();
                buyListLift.clear();
                sellListLift.clear();
                buyListRight.clear();
                sellListRight.clear();
		ModelBase dataLeft = this.controller.getModelData(this.userCheckData.platLeft);              
                ModelBase dataRight = this.controller.getModelData(this.userCheckData.platRight);
                setDataToLeft(this.userCheckData.platLeft,dataLeft);
                setDataToRight(this.userCheckData.platRight,dataRight);
                
                
                
	}
       public void setDataToLeft(int pt,ModelBase data)
       {
           switch(pt)
            {
                case Config.BTBVC:

                    break;
                case Config.BTCC:
                    break;
                case Config.HUOBI:
                    HuoBIData huoBiData = (HuoBIData)data;  //只有现货
                    sellListRight.addAll(Arrays.asList(huoBiData.tickerSellData));
                    buyListRight.addAll(Arrays.asList(huoBiData.tickerBuyData));
                    break;
                case Config.OKCOINCN:
                    OkCoinCnData okdata = (OkCoinCnData)data;
                    //for(int i = 0;i < okdata)
                    if(this.userCheckData.TypeMTLeft == Config.MODERN_ID)
                    {
                        //现货
                        for(int i = 0;i < okdata.verTickerBuyData.size();i++)
                        {
                            buyListLift.add(okdata.verTickerBuyData.get(i));
                        }
                        for(int i = 0;i < okdata.verTickerSellData.size();i ++ )
                        {
                            sellListLift.add(okdata.verTickerSellData.get(i));
                        }
                    }
                    else if (this.userCheckData.TypeMTLeft == Config.FURTURE_ID)
                    {
                        //期货
                        System.out.println("OK中国没有期货,出错!!!");
                    }
                    break;
                case Config.OKCOINCOM:
                    OkCoinComData okdataCM = (OkCoinComData)data;
                    //for(int i = 0;i < okdata)
                    if(this.userCheckData.TypeMTLeft == Config.MODERN_ID)
                    {
                        //现货
                        for(int i = 0;i < okdataCM.verTickerBuyData.size();i++)
                        {
                            buyListLift.add(okdataCM.verTickerBuyData.get(i));
                        }
                        for(int i = 0;i < okdataCM.verTickerSellData.size();i ++ )
                        {
                            sellListLift.add(okdataCM.verTickerSellData.get(i));
                        }
                    }
                    else if (this.userCheckData.TypeMTLeft == Config.FURTURE_ID)
                    {
                        //期货
                        for(int i = 0;i < okdataCM.verFurtureBuyData.size();i++)
                        {
                            buyListLift.add(okdataCM.verFurtureBuyData.get(i));
                        }
                        for(int i = 0;i < okdataCM.verFurtureSellData.size();i ++ )
                        {
                            sellListLift.add(okdataCM.verFurtureSellData.get(i));
                        }
                    }
                    break;
            } 
       }
       public void setDataToRight(int pt,ModelBase data)
       {
            switch(pt)
            {
                case Config.BTBVC:

                    break;
                case Config.BTCC:
                    break;
                case Config.HUOBI:
                    HuoBIData huoBiData = (HuoBIData)data;  //只有现货
                    sellListRight.addAll(Arrays.asList(huoBiData.tickerSellData));
                    buyListRight.addAll(Arrays.asList(huoBiData.tickerBuyData));
                    break;
                case Config.OKCOINCN:
                    OkCoinCnData okdata = (OkCoinCnData)data;
                    //for(int i = 0;i < okdata)
                    if(this.userCheckData.TypeMTLeft == Config.MODERN_ID)
                    {
                        //现货
                        for(int i = 0;i < okdata.verTickerBuyData.size();i++)
                        {
                            buyListRight.add(okdata.verTickerBuyData.get(i));
                        }
                        for(int i = 0;i < okdata.verTickerSellData.size();i ++ )
                        {
                            sellListRight.add(okdata.verTickerSellData.get(i));
                        }
                    }
                    else if (this.userCheckData.TypeMTLeft == Config.FURTURE_ID)
                    {
                        //期货
                        System.out.println("OK中国没有期货,出错!!!");
                    }
                    break;
                case Config.OKCOINCOM:
                    OkCoinComData okdataCM = (OkCoinComData)data;
                    //for(int i = 0;i < okdata)
                    if(this.userCheckData.TypeMTLeft == Config.MODERN_ID)
                    {
                        //现货
                        for(int i = 0;i < okdataCM.verTickerBuyData.size();i++)
                        {
                            buyListRight.add(okdataCM.verTickerBuyData.get(i));
                        }
                        for(int i = 0;i < okdataCM.verTickerSellData.size();i ++ )
                        {
                            sellListRight.add(okdataCM.verTickerSellData.get(i));
                        }
                    }
                    else if (this.userCheckData.TypeMTLeft == Config.FURTURE_ID)
                    {
                        //期货
                        for(int i = 0;i < okdataCM.verFurtureBuyData.size();i++)
                        {
                            buyListRight.add(okdataCM.verFurtureBuyData.get(i));
                        }
                        for(int i = 0;i < okdataCM.verFurtureSellData.size();i ++ )
                        {
                            sellListRight.add(okdataCM.verFurtureSellData.get(i));
                        }
                    }
                    break;
            } 
       }
}
