/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.component;

/**
 *
 * @author Prezes
 */
public class StreamLinkPanel extends javax.swing.JPanel {

    /**
     * Creates new form StreamLinkPanel
     */
    //StreamLinkPanel(CatchStream stream)
    public StreamLinkPanel(String name) {
        initComponents();
        streamNameLabel.setText(name);
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        streamNameLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setMaximumSize(new java.awt.Dimension(2147483647, 40));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        streamNameLabel.setText("saddasd");
        streamNameLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        add(streamNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 193, 11));
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel streamNameLabel;
    // End of variables declaration//GEN-END:variables
}
