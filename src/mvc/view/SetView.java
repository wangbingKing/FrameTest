/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import base.BaseNode;
import config.Config;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import javax.swing.JComboBox;

/**
 *
 * @author wuxianshikong
 */
public class SetView extends Panel implements BaseNode{

    JComboBox ptLeft;
    JComboBox ptRight;
    Choice choice;
    TextField text;
    TextArea area;
    Button add, del;
    public SetView()
    {
        super();
        this.setLayout(null);
        setBackground(Color.RED);
        setSize(400, 200);
        setLocation(100, 300);
        setVisible(true);
//        String nations[]={Config.BTCPLAT[0][0],Config.BTCPLAT[1][0],Config.BTCPLAT[2][0],Config.BTCPLAT[3][0],Config.BTCPLAT[4][0]};
//        ptLeft = new JComboBox(nations);
//        ptRight = new JComboBox(nations);
//        ptLeft.setBounds(0,55, 70, 14);
//        ptRight.setBounds(100,55, 70, 14);
//        ptLeft.setVisible(true);
//        ptRight.setVisible(true);
//        this.add(ptLeft);
//        this.add(ptRight);
//        this.init();
    }
 

 public void init() {
        choice = new Choice();
        text = new TextField(8);
        area = new TextArea(6, 15);
        choice.add("音乐天地");
        choice.add("武术天地");
        choice.add("象棋乐园");
        choice.add("交友聊天");
        add = new Button("添加");
        del = new Button("删除");
//        add.addActionListener(this);
//        del.addActionListener(this);
//        choice.addItemListener(this);
        choice.setVisible(true);
        add(choice);
        add(del);
        add(text);
        add(add);
        add(area);
 }

    @Override
    public void update() 
    {
        
    }
    
}
