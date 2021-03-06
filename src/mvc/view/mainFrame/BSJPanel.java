/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view.mainFrame;

import base.BaseUserInfo;
import config.Config;

/**
 *
 * @author wuxianshikong
 */
public class BSJPanel extends javax.swing.JPanel {
    double value;
    double num;
    double total;
    PtJPanel superPanle;
    /**
     * 买卖标志
     */
    private int bsflag;
    /**
     * Creates new form BSJPanel
     */
    public BSJPanel() {
        value = 0.0f;
        num = 0.0f;
        total = 0.0f;
        initComponents();
        updateBSUI();
    }
    public void setSuperPanel(PtJPanel superP)
    {
        superPanle = superP;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        assetTitle = new javax.swing.JLabel();
        freeTitile = new javax.swing.JLabel();
        freeValue = new javax.swing.JLabel();
        assetValue = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        bsValue = new javax.swing.JTextField();
        bsNum = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        bsTotal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        bsButton = new javax.swing.JButton();

        assetTitle.setText("人民币余额:");

        freeTitile.setText("人民币余额:");

        freeValue.setText("100000000");

        assetValue.setText("100000000");

        label.setText("价格");

        jLabel6.setText("数量");

        jLabel7.setText("总价");

        bsValue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bsValueFocusLost(evt);
            }
        });
        bsValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsValueActionPerformed(evt);
            }
        });

        bsNum.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bsNumFocusLost(evt);
            }
        });
        bsNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsNumActionPerformed(evt);
            }
        });

        jLabel8.setText("X");

        bsTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsTotalActionPerformed(evt);
            }
        });

        jLabel9.setText("=");

        bsButton.setText("买卖");
        bsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(assetTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(assetValue, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(label)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel6)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(freeTitile, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bsValue, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bsNum, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(freeValue, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(assetTitle)
                    .addComponent(assetValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(freeTitile)
                    .addComponent(freeValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bsNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(bsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bsButton))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bsValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsValueActionPerformed

        // TODO add your handling code here:
        try{
            value = Double.parseDouble(bsValue.getText());
            total = value * num;
        }
        catch(Exception e)
        {
            
        }
        updateBSUI();
        
    }//GEN-LAST:event_bsValueActionPerformed

    private void bsNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsNumActionPerformed
        // TODO add your handling code here:
         try{
            num = Double.parseDouble(bsNum.getText());
            total = value * num;
        }
        catch(Exception e)
        {
            
        }
        updateBSUI();
    }//GEN-LAST:event_bsNumActionPerformed

    private void bsTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsTotalActionPerformed
        // TODO add your handling code here:
    	updateBSUI();
    }//GEN-LAST:event_bsTotalActionPerformed

    private void bsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsButtonActionPerformed
        // TODO add your handling code here:
        bsRequest();
    }//GEN-LAST:event_bsButtonActionPerformed

    private void bsValueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bsValueFocusLost
        // TODO add your handling code here:
        try{
            value = Double.parseDouble(bsValue.getText());
            total = value * num;
        }
        catch(Exception e)
        {
            
        }
        updateBSUI();
    }//GEN-LAST:event_bsValueFocusLost

    private void bsNumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bsNumFocusLost
        // TODO add your handling code here:
        try{
            num = Double.parseDouble(bsNum.getText());
            total = value * num;
        }
        catch(Exception e)
        {
            
        }
        updateBSUI();
    }//GEN-LAST:event_bsNumFocusLost
    public void updateBSUI()
    {
        bsValue.setText(String.format("%.4f",value));
        bsNum.setText(String.format("%.1f",num));
        bsTotal.setText(String.format("%.4f",total));
    }
    public void setBSData(double value,double num)
    {
        this.value = value;
        this.num = num;
        this.total = value * num;
        updateBSUI();
    }
    public void updateUserInfo(BaseUserInfo info)
    {
        assetTitle.setText("账号资产:");
        assetValue.setText(info.asset);
        freeTitile.setText("账号余额:");
        freeValue.setText(info.free);
    }
    public void setBSPanel(int bs)
    {
        bsflag = bs;
        if(bs == Config.BUY_ID)
        {
            bsButton.setText("买入");
        }
        else
        {
            bsButton.setText("卖出");
        }
    }
    public void bsRequest()
    {
        superPanle.bsRequest(bsflag, value, num);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel assetTitle;
    private javax.swing.JLabel assetValue;
    private javax.swing.JButton bsButton;
    private javax.swing.JTextField bsNum;
    private javax.swing.JTextField bsTotal;
    private javax.swing.JTextField bsValue;
    private javax.swing.JLabel freeTitile;
    private javax.swing.JLabel freeValue;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel label;
    // End of variables declaration//GEN-END:variables
}
