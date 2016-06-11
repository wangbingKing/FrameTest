/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import config.Config;
import base.BaseErAiCheckData;
import base.BaseNewErConData;
import mvc.model.ModelBase;

/**
 *
 * @author wuxianshikong
 */
public class ProcessControllerAINewER implements ProcessInterface{
    public BaseNewErConData userCheckData;
    /**
    * 主控制器
    */
    public Controller mainControl;
    public long U_id;
    public ProcessControllerAINewER(long U_id,BaseNewErConData userCheckData,Controller mainControl)

    {
        this.userCheckData = userCheckData;
        this.U_id = U_id;
        this.mainControl = mainControl;
    }
    public void updateCheckData()
    {
    	if(userCheckData.state == Config.ER_AI_STATE.NULLSTATE || userCheckData.state == Config.ER_AI_STATE.CHECKING)
    	{
    		userCheckData.newPrice = this.mainControl.getNewPtPrice(userCheckData.pt_L);
            userCheckData.newPrice_special = this.mainControl.getNewPtPrice(userCheckData.pt_R);
            double checkValue_L = userCheckData.getValue(userCheckData.newPrice);
            double checkValue_R = userCheckData.getSpecialValue(userCheckData.newPrice);
            Boolean istrue_Left = false;
            Boolean istrue_Right = false;
            if(userCheckData.gd_L == Config.GAO_PRICE && userCheckData.newPrice >= checkValue_L)
            {
            	istrue_Left = true;
            }
            else if(userCheckData.gd_L == Config.DI_PRICE && userCheckData.newPrice <= checkValue_L)
            {
            	istrue_Left = true;
            }
            if(userCheckData.special == Config.YHF_NULL && istrue_Left)
            {
            	userCheckData.state = Config.ER_AI_STATE.REQUESTING;
                this.mainControl.bsRequest(userCheckData.do_BS,userCheckData.xs_L, userCheckData.price, userCheckData.amount, userCheckData.pt_L);
                userCheckData.state = Config.ER_AI_STATE.OVER;
            }
            else
            {
            	if(userCheckData.gd_R == Config.GAO_PRICE && userCheckData.newPrice_special >= checkValue_R)
                {
                	istrue_Right = true;
                }
                else if(userCheckData.gd_R == Config.DI_PRICE && userCheckData.newPrice_special <= checkValue_R)
                {
                	istrue_Right = true;
                }
            	if(userCheckData.special == Config.YHF_ALL && (istrue_Right && istrue_Left))
            	{
            		userCheckData.state = Config.ER_AI_STATE.REQUESTING;
                    this.mainControl.bsRequest(userCheckData.do_BS,userCheckData.xs_L, userCheckData.price, userCheckData.amount, userCheckData.pt_L);
                    userCheckData.state = Config.ER_AI_STATE.OVER;
            	}
            	else if(userCheckData.special == Config.YHF_ONE && (istrue_Right || istrue_Left))
            	{
            		userCheckData.state = Config.ER_AI_STATE.REQUESTING;
                    this.mainControl.bsRequest(userCheckData.do_BS,userCheckData.xs_L, userCheckData.price, userCheckData.amount, userCheckData.pt_L);
                    userCheckData.state = Config.ER_AI_STATE.OVER;
            	}
            }
            
            
    	}
    }
    @Override
    public void updata() {
        updateCheckData();
    }

    @Override
    public ModelBase getModelData(int pt) {
        return null;
    }
    
}
