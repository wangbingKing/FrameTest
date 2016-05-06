package main;

import base.BaseList;
import base.UserConBase;
import config.Config;
import java.util.Vector;
import mvc.controller.Controller;

public class MainBase {
//        UserConBase userCheckData;
//        Vector<BaseList> buyListLift;
//        Vector<BaseList> sellListLift;
//        Vector<BaseList> buyListRight;
//        Vector<BaseList> sellListRight;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
                
//                
//            Vector<BaseList> lift;
//            Vector<BaseList> right;
//            int idxL = 0;
//            int idxR = 0;
//            int foL = 1;
//            int foR = 1;
//            int lsize = 0;
//            int rsize = 0;
//            
//            int lmax = 0;
//            int rmax = 0;
//            
//            int numL = 0;
//            int numR = 0;
//            if(this.userCheckData.BSStateLeft == Config.BUY_ID)//买最高的
//            {
//                lift = this.buyListLift;
//                lsize = lift.size();
//                foL = -1;
//                lmax = 0;
//            }
//            else //卖最低的
//            {
//                lift = this.sellListLift;
//                lsize = 0;
//                foL = 1;
//                lmax = lift.size();
//            }
//            if(this.userCheckData.BSStateRight == Config.BUY_ID)
//            {
//                right = this.buyListRight;
//                rsize = right.size();
//                foR = -1;
//                rmax = 0;
//            }
//            else
//            {
//                right = this.sellListRight;
//                rsize = 0;
//                foR = 1;
//                rmax = right.size();
//            }
//            if(rmax == rsize || lmax == lsize)
//            {
//                return;
//            }
//            Boolean istrue = true;// true lift ++   false Right++
//            Boolean liftIsMax = false;
//            Boolean rightIsMax = false;
//            while(true)
//            {
////                rsize = right.size();
////                foR = -1;
//                int l = lsize + foL * idxL;
//                int r = rsize + foR * idxR;
//                BaseList dataL = lift.get(l);
//                BaseList dataR = right.get(r);
//                if(Math.abs(Math.abs(dataL.value) - Math.abs(dataR.value))
//                        >Math.abs(this.userCheckData.money))
//                {
//                    if(istrue && !liftIsMax)
//                    {
//                        rmax += dataL.num;
//                    }
//                    else if(!istrue && !rightIsMax)
//                    {
//                        rmax += dataR.num;
//                    }
//                }
//                if(istrue)
//                {
//                    if(idxL < lift.size()-1)
//                    {
//                        idxL++;
//                    }
//                    else
//                    {
//                        liftIsMax = true;
//                    }
//                }
//                else
//                {
//                    if(idxR < right.size()-1)
//                    {
//                        idxR++;
//                    }
//                    else
//                    {
//                        rightIsMax = true;
//                    }
//                } 
//                if(liftIsMax && rightIsMax)
//                {
//                    break;
//                }
//                istrue = !istrue;
//            }
//                
//                
		Controller con = new Controller();
		while(!con.isOver)
		{
			con.update();
			try
			{
				Thread.sleep(50);
			}
			catch(Exception e)
			{
				
			}
		}
	}

}
