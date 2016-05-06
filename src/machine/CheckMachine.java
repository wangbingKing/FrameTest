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
public class CheckMachine extends BaseMachine{
        Vector<BaseList> buyListLift;
        Vector<BaseList> sellListLift;
        Vector<BaseList> buyListRight;
        Vector<BaseList> sellListRight;
        
	public CheckMachine(ProcessInterface controller,UserConBase userCheckData)
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
                compareData();
                
	}
        public void compareData()
        {
            Vector<BaseList> lift;
            Vector<BaseList> right;
            int idxL = 0;
            int idxR = 0;
            int foL = 1;
            int foR = 1;
            int lsize = 0;
            int rsize = 0;
            
            int lmax = 0;
            int rmax = 0;
            
            int numL = 0;
            int numR = 0;
            if(this.userCheckData.BSStateLeft == Config.BUY_ID)//买最高的
            {
                lift = this.buyListLift;
                lsize = lift.size();
                foL = -1;
                lmax = 0;
            }
            else //卖最低的
            {
                lift = this.sellListLift;
                lsize = 0;
                foL = 1;
                lmax = lift.size();
            }
            if(this.userCheckData.BSStateRight == Config.BUY_ID)
            {
                right = this.buyListRight;
                rsize = right.size();
                foR = -1;
                rmax = 0;
            }
            else
            {
                right = this.sellListRight;
                rsize = 0;
                foR = 1;
                rmax = right.size();
            }
            if(rmax == rsize || lmax == lsize)
            {
                return;
            }
            int istrue = 0;// true lift ++   false Right++
            Boolean liftIsMax = false;
            Boolean rightIsMax = false;
            while(true)
            {
//                rsize = right.size();
//                foR = -1;
                int l = lsize + foL * idxL;
                int r = rsize + foR * idxR;
                BaseList dataL = lift.get(l);
                BaseList dataR = right.get(r);
                if(Math.abs(Math.abs(dataL.value) - Math.abs(dataR.value))
                        >Math.abs(this.userCheckData.money))
                {
                    if(istrue == 0)
                    {
                        rmax += dataL.num;
                        rmax += dataR.num;
                    }
                    else if(istrue >0 && !liftIsMax)
                    {
                        rmax += dataL.num;
                    }
                    else if(istrue < 0 && !rightIsMax)
                    {
                        rmax += dataR.num;
                    }
                }
                if(istrue >= 0)
                {
                    if(idxL < lift.size()-1)
                    {
                        idxL++;
                    }
                    else
                    {
                        liftIsMax = true;
                    }
                    istrue = -1;
                }
                else if(istrue < 0)
                {
                    if(idxR < right.size()-1)
                    {
                        idxR++;
                    }
                    else
                    {
                        rightIsMax = true;
                    }
                    istrue = 1;
                } 
                if(liftIsMax && rightIsMax)
                {
                    break;
                }
            }
//            if()
//            for(int i = 0;i< lift.size();i++)
//            {
//                for(int j = 0;j < right.size();j++)
//                {
//                    
//                }
//            }
//             if(Math.abs(Math.abs(this.userCheckData.BSMoneyLeft) - Math.abs(this.userCheckData.BSMoneyRight))>Math.abs(this.userCheckData.money))
//                {
//                    //满足条件  切换到交易状态
//                    TradeMachine machine = new TradeMachine(this.controller,this.userCheckData);
//                    this.changeMachine(machine);//切换到监听数据状态
//                    machine.updata();
//                }
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
