package mvc.view;
import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.UnsupportedEncodingException;

import mvc.model.HuoBIData;
import mvc.model.ModelBase;
import mvc.model.OkCoinCnData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.rest.HttpUtilManager;

import base.BaseNode;


public class ShowList extends Panel implements BaseNode {

	public ViewBase mainView;
	public float BuyPrice[] = new float[5];
	public float BuyAmount[] = new float[5];
	public float SellPrice[] = new float[5];
	public float SellAmount[] = new float[5];
	int index = 0;  //回调次数计数
	public Button btBuy = null;
	public Button btSell = null;
	
	public Label textDM = null;
	public Label textM = null;
	public Label textDBM = null;
	public Label textBM = null;
	
	public Label labelDes = null;
	public Label labeltSellCost = null;
	public Label labelNum = null;
	public Label labelTotalCost = null;
	public Label labelX = null;
	public Label labelSum = null;
	
	public TextField textSellCost = null;
	public TextField textNum = null;
	public TextField textTotalCost = null;
	
	public Button btOK = null;

	public Label listSellDes[] = new Label[5];
	public Label listSellCost[] = new Label[5];
	public Label listSellNum[] = new Label[5];
	public Label listBuyDes[] = new Label[5];
	public Label listBuyCost[] = new Label[5];
	public Label listBuyNum[] = new Label[5];

	
	public ShowList(ViewBase view)
	{
		mainView = view;
		init();
		
	}
	void init()
	{
		
		this.setLayout(null);
		setBackground(Color.GREEN);
		setSize(400, 200);
		setLocation(100, 100);
		setVisible(true);
		this.initButtonBS();
		this.initLabel();
		this.initSellLayer();
		this.initList();
	}
	/**
	 * 买入卖出按钮
	 */
	void initButtonBS()
	{
		btBuy = new Button("买入");
		btSell = new Button("卖出");
		
		btBuy.setBounds(0, 0, 80, 30);
		btSell.setBounds(100,0, 80, 30);
		btBuy.setVisible(true);
		btSell.setVisible(true);
		this.add(btBuy);
		this.add(btSell);
	}
	void initLabel() 
	{
		textDM = new Label("人民币余额:");
		textDM.setBounds(0,40, 70, 14);
		textDM.setVisible(true);
		this.add(textDM);
		
		textM = new Label("100000");
		textM.setBounds(70, 40, 60, 14);
		textM.setVisible(true);
		this.add(textM);
		
		textDBM = new Label("可买比特币:");
		textDBM.setBounds(0,55, 70, 14);
		textDBM.setVisible(true);
		this.add(textDBM);
		
		textBM = new Label("10000000");
		textBM.setBounds(70,55, 60, 14);
		textBM.setVisible(true);
		this.add(textBM);

	}
	void initSellLayer()
	{//		
//		public Label labelDes = null;
//		public Label labeltSellCost = null;
//		public Label labelNum = null;
//		public Label labelTotalCost = null;
//		public Label labelX = null;
//		public Label labelSum = null;
//		
//		public TextField textSellCost = null;
//		public TextField textNum = null;
//		public TextField textTotalCost = null;
		int ogX = 0;
		int ogY = 80;
		labelDes = new Label("委托类型：");
		labelDes.setBounds(ogX + 14,ogX, 60, 14);
		labelDes.setVisible(true);
		this.add(labelDes);
		
		labeltSellCost = new Label("价格");
		labeltSellCost.setBounds(ogX + 14,ogY + 16, 60, 14);
		labeltSellCost.setVisible(true);
		this.add(labeltSellCost);
		
		labelNum = new Label("数量");
		labelNum.setBounds(ogX + 84,ogY + 16, 60, 14);
		labelNum.setVisible(true);
		this.add(labelNum);
		
		labelTotalCost = new Label("金额");
		labelTotalCost.setBounds(ogX + 154,ogY + 16, 60, 14);
		labelTotalCost.setVisible(true);
		this.add(labelTotalCost);
		
		textSellCost = new TextField("输入单价");
		textSellCost.setBounds(ogX + 0,ogY + 30, 60, 20);
		textSellCost.setVisible(true);
		this.add(textSellCost);
		textSellCost = new TextField("输入数量");
		textSellCost.setBounds(ogX + 70,ogY + 30, 60, 20);
		textSellCost.setVisible(true);
		this.add(textSellCost);
		textSellCost = new TextField("总价");
		textSellCost.setBounds(ogX + 140,ogY + 30, 60, 20);
		textSellCost.setVisible(true);
		this.add(textSellCost);
		
		labelX = new Label("X");
		labelX.setBounds(ogX + 60,ogY + 30, 14, 14);
		labelX.setVisible(true);
		this.add(labelX);
		labelSum = new Label("=");
		labelSum.setBounds(ogX + 130,ogY + 30, 14, 14);
		labelSum.setVisible(true);
		this.add(labelSum);
		
		btOK = new Button("买卖");
		btOK.setBounds(ogX + 10,ogY + 80, 180, 24);
		btOK.setVisible(true);
		this.add(btOK);
	}
	
	void initList()
	{
//		public Label listSellDes[] = new Label[5];
//		public Label listSellCost[] = new Label[5];
//		public Label listSellNum[] = new Label[5];
//		public Label listBuyDes[] = new Label[5];
//		public Label listBuyCost[] = new Label[5];
//		public Label listBuyNum[] = new Label[5];
		int ogX = 210;
		int ogY = 30;
		for(int i = 0;i < 5;i++)
		{
			listSellDes[i] = new Label("卖"+ (5-i));
			listSellDes[i].setBounds(ogX,ogY + 16 * i, 50, 14);
			listSellDes[i].setVisible(true);
			this.add(listSellDes[i]);
			
			listSellCost[i] = new Label("");
			listSellCost[i].setBounds(ogX + 50,ogY + 16 * i, 50, 14);
			listSellCost[i].setVisible(true);
			this.add(listSellCost[i]);
			listSellNum[i] = new Label("");
			listSellNum[i].setBounds(ogX + 20+ 100,ogY + 16 * i, 60, 14);
			listSellNum[i].setVisible(true);
			this.add(listSellNum[i]);
			listBuyDes[4-i] = new Label("买"+ (i+1));
			listBuyDes[4-i].setText("买"+ (i+1));
			listBuyDes[4-i].setBounds(ogX,ogY + 16 * (5+i) , 50, 14);
			listBuyDes[4-i].setVisible(true);
			this.add(listBuyDes[4-i]);
			listBuyCost[4-i] = new Label("");
			listBuyCost[4-i].setBounds(ogX + 50,ogY + 16 * (5+i), 50, 14);
			listBuyCost[4-i].setVisible(true);
			this.add(listBuyCost[4-i]);
			listBuyNum[4-i] = new Label("");
			listBuyNum[4-i].setBounds(ogX+ 20 + 100,ogY + 16 * (5+i), 60, 14);
			listBuyNum[4-i].setVisible(true);
			this.add(listBuyNum[4-i]);
			
		}
		
	}
	@Override
	public void update() {
		
	}
	
	public void checkData(String result)
	{
		JSONObject  dataJson = new JSONObject(JSON.parseObject(result));	
		JSONArray data=dataJson.getJSONArray("top_buy");
		for(int i = 0;i < 5; i++)
		{
			JSONObject info=data.getJSONObject(i);
			BuyAmount[i]=info.getFloat("amount");
			BuyPrice[i]=info.getFloat("price");
//			listBuyCost[i].setText(BuyPrice[i]+"");
//			listBuyNum[i].setText(BuyAmount[i]+"");
		}
		data=dataJson.getJSONArray("top_sell");
		for(int i = 0;i < 5; i++)
		{
			JSONObject info=data.getJSONObject(i);
			SellAmount[i]=info.getFloat("amount");
			SellPrice[i]=info.getFloat("price");
//			listSellCost[i].setText(SellPrice[i]+"");
//			listSellNum[i].setText(SellAmount[i]+"");
		}
		sort();
	}
	public void sort()
	{
		for(int i = 0; i < 5;i++)
		{
			for(int j = i;j < 5;j++)
			{
				if(SellPrice[i] < SellPrice[j])
				{
					float k = SellPrice[i];
					SellPrice[i] = SellPrice[j];
					SellPrice[j] = k;
					k = SellAmount[i];
					SellAmount[i] = SellAmount[j];
					SellAmount[j] = k;
				}
				if(BuyPrice[i] < BuyPrice[j])
				{
					float k = BuyPrice[i];
					BuyPrice[i] = BuyPrice[j];
					BuyPrice[j] = k;
					k = SellAmount[i];
					BuyAmount[i] = BuyAmount[j];
					BuyAmount[j] = k;
				}
			}
		}
	}
	public void updateUI(ModelBase medol)
	{
		HuoBIData medelData = (HuoBIData)medol;
		
		for(int i = 0;i < 5;i++)
		{
			listBuyCost[i].setText(medelData.tickerBuyData[i].value+"");
			listBuyNum[i].setText(medelData.tickerBuyData[i].num+"");
			listSellCost[i].setText(medelData.tickerSellData[i].value+"");
			listSellNum[i].setText(medelData.tickerSellData[i].num+"");
		}
	}
}
