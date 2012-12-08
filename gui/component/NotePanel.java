/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.component;

import java.awt.Component;

/**
 *
 * @author Prezes
 */
public class NotePanel extends javax.swing.JPanel {

    /**
     * Creates new form NotePanel
     */
    public NotePanel(MainFrame parent) {
        this.parent = parent;
        initComponents();
        addTasksPanel();
        addCommentsPanel();
        addNotesBottomPanel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        noteCheckBox = new javax.swing.JCheckBox();
        dateText = new javax.swing.JTextField();
        notePane = new javax.swing.JScrollPane();
        noteText = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        tasksHeaderPanel = new javax.swing.JPanel();
        checkedTasksCheckBox = new javax.swing.JTextField();

        setBackground(new java.awt.Color(0, 153, 153));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        headerPanel.setBackground(getBackground());
        headerPanel.setPreferredSize(new java.awt.Dimension(769, 39));
        headerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                headerPanelMouseClicked(evt);
            }
        });

        noteCheckBox.setBackground(getBackground());
        noteCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noteCheckBoxActionPerformed(evt);
            }
        });

        dateText.setEditable(false);
        dateText.setBackground(getBackground());
        dateText.setText("data");
        dateText.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(noteCheckBox)
                .addGap(18, 18, 18)
                .addComponent(dateText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(598, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(noteCheckBox))
                .addContainerGap())
        );

        add(headerPanel);

        noteText.setColumns(20);
        noteText.setRows(5);
        notePane.setViewportView(noteText);

        add(notePane);

        tasksHeaderPanel.setBackground(new java.awt.Color(51, 255, 255));
        tasksHeaderPanel.setPreferredSize(new java.awt.Dimension(769, 20));
        tasksHeaderPanel.setVisible(false);

        checkedTasksCheckBox.setEditable(false);
        checkedTasksCheckBox.setBackground(tasksHeaderPanel.getBackground());
        checkedTasksCheckBox.setText("zaznaczone zadania: ");
        checkedTasksCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout tasksHeaderPanelLayout = new javax.swing.GroupLayout(tasksHeaderPanel);
        tasksHeaderPanel.setLayout(tasksHeaderPanelLayout);
        tasksHeaderPanelLayout.setHorizontalGroup(
            tasksHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tasksHeaderPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(checkedTasksCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(475, Short.MAX_VALUE))
        );
        tasksHeaderPanelLayout.setVerticalGroup(
            tasksHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tasksHeaderPanelLayout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addComponent(checkedTasksCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tasksHeaderPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tasksHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void noteCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noteCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noteCheckBoxActionPerformed

    private void headerPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerPanelMouseClicked
        expandCollapse(!expanded);
    }//GEN-LAST:event_headerPanelMouseClicked

    private void addTasksPanel() {
        taskPanel = new TaskPanel();
        add(taskPanel);
        taskPanel.setVisible(false);
    }
    
    private void addCommentsPanel() {
        commentsPanel = new CommentsPanel();
        add(commentsPanel);
        commentsPanel.setVisible(false);

    }
    
    private void addNotesBottomPanel() {
        notesBottomPanel = new NotesBottomPanel();
        add(notesBottomPanel);
        notesBottomPanel.setVisible(false);

    }
    
    private void expandCollapse(boolean expandCollapse){
        tasksHeaderPanel.setVisible(expandCollapse);
        taskPanel.setVisible(expandCollapse);
        commentsPanel.setVisible(expandCollapse);
        notesBottomPanel.setVisible(expandCollapse);
        parent.pack();
        expanded = expandCollapse;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField checkedTasksCheckBox;
    private javax.swing.JTextField dateText;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBox noteCheckBox;
    private javax.swing.JScrollPane notePane;
    private javax.swing.JTextArea noteText;
    private javax.swing.JPanel tasksHeaderPanel;
    // End of variables declaration//GEN-END:variables
    NotesBottomPanel notesBottomPanel;
    CommentsPanel commentsPanel;
    TaskPanel taskPanel;
    boolean expanded = false;
    MainFrame parent;
    
}