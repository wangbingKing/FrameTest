/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

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
        userCheckData.newPrice = this.mainControl.getNewPtPrice(userCheckData.pt_L);
        
        userCheckData.newPrice_special = this.mainControl.getNewPtPrice(userCheckData.pt_R);
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
