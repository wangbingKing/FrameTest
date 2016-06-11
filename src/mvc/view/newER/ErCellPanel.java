/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view.newER;

import base.BaseNewErConData;
import base.BaseNode;
import config.Config;
import mvc.controller.Controller;

/**
 *
 * @author wuxianshikong
 */
public class ErCellPanel extends javax.swing.JPanel implements BaseNode {
    Controller mainController;
    ErFrame superFrame;
    public BaseNewErConData userCheckData;
    /**
     * Creates new form ErCellPanel
     */
    public ErCellPanel() {
        initComponents();
    }
    public void setMainController(Controller mainController,ErFrame superFrame)
    {
        this.mainController = mainController;
        this.superFrame = superFrame;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pt_L = new javax.swing.JLabel();
        conPrice = new javax.swing.JLabel();
        bs_L = new javax.swing.JLabel();
        price = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        amount = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        pt_R = new javax.swing.JLabel();
        conValue_R = new javax.swing.JLabel();

        pt_L.setBackground(new java.awt.Color(0, 255, 204));
        pt_L.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pt_L.setText("jLabel1");

        conPrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        conPrice.setText("999");

        bs_L.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bs_L.setText("买入");

        price.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        price.setText("999");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("X");

        amount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        amount.setText("999");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("=");

        total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total.setText("999");

        jButton1.setText("撤销");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        pt_R.setText("ok国际");

        conValue_R.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pt_L, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bs_L, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pt_R)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conValue_R, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(pt_L)
                .addComponent(conPrice)
                .addComponent(bs_L)
                .addComponent(price)
                .addComponent(jLabel5)
                .addComponent(amount)
                .addComponent(jLabel7)
                .addComponent(total)
                .addComponent(jButton1)
                .addComponent(pt_R)
                .addComponent(conValue_R))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.mainController.removeProcessAINewER(this.userCheckData.U_id);
        this.superFrame.upDateListData();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amount;
    private javax.swing.JLabel bs_L;
    private javax.swing.JLabel conPrice;
    private javax.swing.JLabel conValue_R;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel price;
    private javax.swing.JLabel pt_L;
    private javax.swing.JLabel pt_R;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables

    public void updateUIData(BaseNewErConData userCheckData)
    {
        this.userCheckData = userCheckData;
        if(userCheckData == null)
        {
            this.setVisible(false);
        }
        else
        {
            pt_L.setText(Config.getNameByPlat(this.userCheckData.pt_L));
            conPrice.setText("");
            bs_L.setText(Config.getStringBS(this.userCheckData.do_BS));
            price.setText(""+ String.format("%.4f",this.userCheckData.price));
            amount.setText(String.format("%.4f", this.userCheckData.amount));
            total.setText(String.format("%.4f", (this.userCheckData.amount * this.userCheckData.price)));
            pt_R.setText("");
            if(this.userCheckData.special == Config.YHF_NULL)
            {
                conValue_R.setText("");
            }
            else
            {
                conValue_R.setText(Config.getNameByPlat(this.userCheckData.pt_R));
            }
            this.setVisible(true);
        }
    }
    public void updateNewTrandUIData()
    {
        
    }
    @Override
    public void update() {
        
    }
}