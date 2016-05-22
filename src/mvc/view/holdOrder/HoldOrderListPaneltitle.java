/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view.holdOrder;

/**
 *
 * @author wuxianshikong
 */
public class HoldOrderListPaneltitle extends javax.swing.JPanel {

    /**
     * Creates new form HoldOrderListPaneltitle
     */
    public HoldOrderListPaneltitle() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        id = new javax.swing.JLabel();
        tradeType = new javax.swing.JLabel();
        liquidatePrice = new javax.swing.JLabel();
        price = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        money = new javax.swing.JLabel();
        closeMoney = new javax.swing.JLabel();
        storeId = new javax.swing.JLabel();

        id.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        id.setText("      仓位id");

        tradeType.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        tradeType.setText("交易类型");

        liquidatePrice.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        liquidatePrice.setText("爆仓价格");

        price.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        price.setText("平均价格");

        status.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        status.setText("仓位状态");

        money.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        money.setText("持仓金额");

        closeMoney.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        closeMoney.setText("可平金额");

        storeId.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        storeId.setText("仓位ID ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tradeType)
                .addGap(18, 18, 18)
                .addComponent(liquidatePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(status)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(money, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(storeId, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(id)
                .addComponent(tradeType)
                .addComponent(liquidatePrice)
                .addComponent(price)
                .addComponent(status)
                .addComponent(money)
                .addComponent(closeMoney)
                .addComponent(storeId))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel closeMoney;
    public javax.swing.JLabel id;
    public javax.swing.JLabel liquidatePrice;
    public javax.swing.JLabel money;
    public javax.swing.JLabel price;
    public javax.swing.JLabel status;
    public javax.swing.JLabel storeId;
    public javax.swing.JLabel tradeType;
    // End of variables declaration//GEN-END:variables
}
