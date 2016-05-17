package mvc.view;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import config.Config;
import mvc.controller.Controller;
import base.BaseNode;

public class ViewBase extends Frame implements BaseNode{
	public Controller mainController;
	public ShowList list = null;
        public SetView setView = null;
	public ViewBase(String str,Controller con)
	{
		super(str);
		mainController = con;
		this.setLayout(null);
		setSize(1000, 600);
		setLocation(100, 0);
		setVisible(true);
		list = new ShowList(this);
		add(list);
                
                setView = new SetView();
                add(setView);
                
		this.setBackground(Color.gray);
		addWindowListener(new WinAdp());
	}
	public class WinAdp extends WindowAdapter
	{
		//重写窗口关闭事件
        @Override
        public void windowClosing(WindowEvent arg0) {
           System.exit(0);
        }
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		list.updateUI(this.mainController.getModelData(Config.HUOBI));
	}
	
}
