/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.component;

/**
 *
 * @author Prezes
 */
public class AddStreamPanel extends javax.swing.JPanel {

    /**
     * Creates new form AddStreamPanel
     */
    public AddStreamPanel() {
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

        streamNameText = new javax.swing.JTextField();
        createStreamButton = new javax.swing.JButton();

        streamNameText.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        streamNameText.setText("nazwa streamu");

        createStreamButton.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        createStreamButton.setText("utwórz");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(streamNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(createStreamButton)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(streamNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createStreamButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createStreamButton;
    private javax.swing.JTextField streamNameText;
    // End of variables declaration//GEN-END:variables
}