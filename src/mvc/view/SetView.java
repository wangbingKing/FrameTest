/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import base.BaseNode;
import config.Config;
import java.awt.Color;
import java.awt.Component;
import java.awt.Panel;
import javax.swing.JComboBox;

/**
 *
 * @author wuxianshikong
 */
public class SetView extends Panel implements BaseNode{

    JComboBox ptLeft;
    JComboBox ptRight;
    public SetView()
    {
        this.setLayout(null);
        setBackground(Color.GREEN);
        setSize(400, 200);
        setLocation(100, 300);
        setVisible(true);
        String nations[]={Config.BTCPLAT[0][0],Config.BTCPLAT[1][0],Config.BTCPLAT[2][0],Config.BTCPLAT[3][0],Config.BTCPLAT[4][0]};
        ptLeft = new JComboBox(nations);
        ptRight = new JComboBox(nations);
        ptLeft.setBounds(0,55, 70, 14);
        ptRight.setBounds(100,55, 70, 14);
        ptLeft.setVisible(true);
        ptRight.setVisible(true);
        this.add(ptLeft);
        this.add(ptRight);
    }
    
    @Override
    public void update() 
    {
        
    }
    
}
