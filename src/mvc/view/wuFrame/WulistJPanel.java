package mvc.view.wuFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import base.BaseErAiCheckData;
import base.BaseWuAi;
import config.Config;
import mvc.view.setERFrame.OneDirectionFrame;

/**
 *
 * @author wuxianshikong
 */
public class WulistJPanel extends javax.swing.JPanel {
    long udid;
    WuJFrame superView;
    /**
     * Creates new form ErlistJPanel
     */
    public WulistJPanel() {
        initComponents();
    }
    public void updateListUI(BaseWuAi baseData,long udid,WuJFrame superView)
    {
        this.superView = superView;
        this.udid = udid;
        String typebs[] = {"现货","当周","次周","季度"};
        pt_L1.setText(Config.BTCPLAT[baseData.pt_L][0]);
        bs_L1.setText(typebs[baseData.type_L] + Config.getStringBS(baseData.bs_L));
        compare_L1.setText(Config.getHDStr(baseData.gd_L));
        compareValue.setText("当前价*"+baseData.value_L);
        do_bs_Str.setText("挂"+Config.getStringBSDO(baseData.do_bs)+"单");
        bs_num.setText(""+ String.format("%.4f", baseData.bs_num));
        pt_R.setText(Config.BTCPLAT[baseData.pt_R][0]);
        bs_R.setText(typebs[baseData.type_R]);
        compareValue_R.setText(""+baseData.value_R);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pt_L1 = new javax.swing.JLabel();
        bs_L1 = new javax.swing.JLabel();
        compare_L1 = new javax.swing.JLabel();
        compareValue = new javax.swing.JLabel();
        do_bs_Str = new javax.swing.JLabel();
        bs_num = new javax.swing.JLabel();
        com = new javax.swing.JLabel();
        pt_R = new javax.swing.JLabel();
        bs_R = new javax.swing.JLabel();
        compareStr = new javax.swing.JLabel();
        compare_L2 = new javax.swing.JLabel();
        compareValue_R = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        pt_L1.setText("OKCoin中国");

        bs_L1.setText("期货现价");

        compare_L1.setText("高于");

        compareValue.setText("当前价*100");

        do_bs_Str.setText("挂买卖单");

        bs_num.setText("1000");

        com.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        com.setText("对手盘:");

        pt_R.setText("OKCoin中国");

        bs_R.setText("期货现价");

        compareStr.setText("当前最新价*");

        compare_L2.setText("撤单条件:");

        compareValue_R.setText("1000");

        jButton1.setText("撤销");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pt_L1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bs_L1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(compare_L1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(compareValue, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(do_bs_Str)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bs_num, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(com, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pt_R, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bs_R)
                .addGap(18, 18, 18)
                .addComponent(compare_L2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(compareStr, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(compareValue_R)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pt_L1)
                    .addComponent(bs_L1)
                    .addComponent(compare_L1)
                    .addComponent(compareValue)
                    .addComponent(do_bs_Str)
                    .addComponent(bs_num)
                    .addComponent(com, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pt_R)
                    .addComponent(bs_R)
                    .addComponent(compare_L2)
                    .addComponent(compareStr)
                    .addComponent(compareValue_R)
                    .addComponent(jButton1))
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        superView.mainController.removeProcessWu(this.udid);
        superView.updateListUI();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bs_L1;
    private javax.swing.JLabel bs_R;
    private javax.swing.JLabel bs_num;
    private javax.swing.JLabel com;
    private javax.swing.JLabel compareStr;
    private javax.swing.JLabel compareValue;
    private javax.swing.JLabel compareValue_R;
    private javax.swing.JLabel compare_L1;
    private javax.swing.JLabel compare_L2;
    private javax.swing.JLabel do_bs_Str;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel pt_L1;
    private javax.swing.JLabel pt_R;
    // End of variables declaration//GEN-END:variables
}
