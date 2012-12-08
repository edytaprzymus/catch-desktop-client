/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.component;

/**
 *
 * @author Prezes
 */
public class NotesBottomPanel extends javax.swing.JPanel {

    /**
     * Creates new form NoteBottomPanel
     */
    public NotesBottomPanel() {
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

        jSeparator1 = new javax.swing.JSeparator();
        addTaskButton = new javax.swing.JButton();
        addCommentButton = new javax.swing.JButton();
        addFileButton = new javax.swing.JButton();
        organizeButton = new javax.swing.JButton();
        deleteNoteButton = new javax.swing.JButton();
        addLocationButton = new javax.swing.JButton();

        addTaskButton.setText("dodaj zadanie");

        addCommentButton.setText("dodaj komentarz");

        addFileButton.setText("załącz plik");

        organizeButton.setText("organizuj");
        organizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                organizeButtonMouseClicked(evt);
            }
        });

        deleteNoteButton.setText("usuń");

        addLocationButton.setText("dodaj lokalizację");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(addTaskButton)
                .addGap(18, 18, 18)
                .addComponent(addCommentButton)
                .addGap(18, 18, 18)
                .addComponent(addFileButton)
                .addGap(18, 18, 18)
                .addComponent(organizeButton)
                .addGap(18, 18, 18)
                .addComponent(deleteNoteButton)
                .addGap(18, 18, 18)
                .addComponent(addLocationButton)
                .addGap(0, 126, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addTaskButton)
                    .addComponent(addCommentButton)
                    .addComponent(addFileButton)
                    .addComponent(organizeButton)
                    .addComponent(deleteNoteButton)
                    .addComponent(addLocationButton)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void organizeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_organizeButtonMouseClicked
        OrganizeNoteWindow organizeWindow = new OrganizeNoteWindow(null, true);
        organizeWindow.setVisible(true);
        
    }//GEN-LAST:event_organizeButtonMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCommentButton;
    private javax.swing.JButton addFileButton;
    private javax.swing.JButton addLocationButton;
    private javax.swing.JButton addTaskButton;
    private javax.swing.JButton deleteNoteButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton organizeButton;
    // End of variables declaration//GEN-END:variables
}