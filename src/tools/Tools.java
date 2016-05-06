/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import base.BaseList;
import base.UserConBase;
import config.Config;
import java.util.Vector;

/**
 *
 * @author wuxianshikong
 */
public class Tools {
    /**
     * 获得比较有差值的数量
     * @param buyListLift
     * @param sellListLift
     * @param buyListRight
     * @param sellListRight
     * @param userCheckData
     * @return 
     */
    public static int getResidual(Vector<BaseList> buyListLift,Vector<BaseList> sellListLift,Vector<BaseList> buyListRight,Vector<BaseList> sellListRight,UserConBase userCheckData)
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
        if(userCheckData.BSStateLeft == Config.BUY_ID)//买最高的
        {
            lift = buyListLift;
            lsize = lift.size();
            foL = -1;
            lmax = 0;
        }
        else //卖最低的
        {
            lift = sellListLift;
            lsize = 0;
            foL = 1;
            lmax = lift.size();
        }
        if(userCheckData.BSStateRight == Config.BUY_ID)
        {
            right = buyListRight;
            rsize = right.size();
            foR = -1;
            rmax = 0;
        }
        else
        {
            right = sellListRight;
            rsize = 0;
            foR = 1;
            rmax = right.size();
        }
        if(rmax == rsize || lmax == lsize)
        {
            return 0;
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
                    >Math.abs(userCheckData.money))
            {
                if(istrue == 0)
                {
                    lmax += dataL.num;
                    rmax += dataR.num;
                }
                else if(istrue >0 && !liftIsMax)
                {
                    lmax += dataL.num;
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
        int result =   lmax > rmax ? rmax : lmax;
        return result;
    }
    
}
