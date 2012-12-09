/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.component;

import connector.Database;
import connector.StreamFromLocal;
import connector.Synchronizer;
import connector.catchConnector;
import connector.miniStream;
import gui.Controller;
import java.awt.Component;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Przemo
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame(Controller controller) {
        this.controller = controller;
        initComponents();
        createNotePanels();
        createAddStreamPanel();
        createAllStreamsPanel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sideTabbedPane = new javax.swing.JTabbedPane();
        allStreamsPanel = new javax.swing.JPanel();
        privateStreamsPanel = new javax.swing.JPanel();
        sharedStreamsPanel = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        mainHeaderPanel = new javax.swing.JPanel();
        DisplayedStreamName = new javax.swing.JTextField();
        shareNotePanel = new javax.swing.JPanel();
        inviteButton = new javax.swing.JToggleButton();
        editStreamButton = new javax.swing.JToggleButton();
        editStreamPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        newStreamNameText = new javax.swing.JTextField();
        okButton = new javax.swing.JButton();
        doneButton = new javax.swing.JButton();
        deleteStreamButton = new javax.swing.JButton();
        notesOptionsPanel = new javax.swing.JPanel();
        newNoteButton = new javax.swing.JButton();
        editNotesCombo = new javax.swing.JComboBox();
        editNotesLabel = new javax.swing.JLabel();
        sortNotesCombo = new javax.swing.JComboBox();
        sortNotesLabel = new javax.swing.JLabel();
        collapseAllButton = new javax.swing.JButton();
        expandAllButton = new javax.swing.JButton();
        createStreamPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        synchronizeButton = new javax.swing.JButton();
        synchroInfoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        allStreamsPanel.setLayout(new javax.swing.BoxLayout(allStreamsPanel, javax.swing.BoxLayout.PAGE_AXIS));
        sideTabbedPane.addTab("Wszystkie Przestrzenie", allStreamsPanel);

        javax.swing.GroupLayout privateStreamsPanelLayout = new javax.swing.GroupLayout(privateStreamsPanel);
        privateStreamsPanel.setLayout(privateStreamsPanelLayout);
        privateStreamsPanelLayout.setHorizontalGroup(
            privateStreamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );
        privateStreamsPanelLayout.setVerticalGroup(
            privateStreamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
        );

        sideTabbedPane.addTab("prywatne", privateStreamsPanel);

        javax.swing.GroupLayout sharedStreamsPanelLayout = new javax.swing.GroupLayout(sharedStreamsPanel);
        sharedStreamsPanel.setLayout(sharedStreamsPanelLayout);
        sharedStreamsPanelLayout.setHorizontalGroup(
            sharedStreamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );
        sharedStreamsPanelLayout.setVerticalGroup(
            sharedStreamsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
        );

        sideTabbedPane.addTab("Współdzielone ", sharedStreamsPanel);

        mainPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        mainPanel.setMinimumSize(new java.awt.Dimension(200, 0));
        mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel, javax.swing.BoxLayout.PAGE_AXIS));

        mainHeaderPanel.setBackground(new java.awt.Color(153, 255, 153));
        mainHeaderPanel.setAutoscrolls(true);
        mainHeaderPanel.setMinimumSize(new java.awt.Dimension(200, 0));

        DisplayedStreamName.setEditable(false);
        DisplayedStreamName.setBackground(mainHeaderPanel.getBackground());
        DisplayedStreamName.setText("Nazwa Przestrzeni");
        DisplayedStreamName.setToolTipText("");
        DisplayedStreamName.setBorder(null);
        DisplayedStreamName.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        shareNotePanel.setBackground(new java.awt.Color(51, 153, 255));
        shareNotePanel.setAlignmentX(0.0F);
        shareNotePanel.setAlignmentY(0.0F);
        shareNotePanel.setVisible(false);

        javax.swing.GroupLayout shareNotePanelLayout = new javax.swing.GroupLayout(shareNotePanel);
        shareNotePanel.setLayout(shareNotePanelLayout);
        shareNotePanelLayout.setHorizontalGroup(
            shareNotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        shareNotePanelLayout.setVerticalGroup(
            shareNotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        inviteButton.setText("Zaproś znajomych");
        inviteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inviteButtonHandler(evt);
            }
        });

        editStreamButton.setText("Edytuj Przestrzeń");
        editStreamButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editStreamButtonMouseClicked(evt);
            }
        });
        editStreamButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editStreamButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainHeaderPanelLayout = new javax.swing.GroupLayout(mainHeaderPanel);
        mainHeaderPanel.setLayout(mainHeaderPanelLayout);
        mainHeaderPanelLayout.setHorizontalGroup(
            mainHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainHeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DisplayedStreamName, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editStreamButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 399, Short.MAX_VALUE)
                .addComponent(inviteButton)
                .addContainerGap())
            .addComponent(shareNotePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainHeaderPanelLayout.setVerticalGroup(
            mainHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainHeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DisplayedStreamName, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(inviteButton)
                    .addComponent(editStreamButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(shareNotePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        mainPanel.add(mainHeaderPanel);

        editStreamPanel.setBackground(new java.awt.Color(255, 204, 51));

        jLabel1.setText("Zmień nazwę Przestrzeni: ");

        newStreamNameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newStreamNameTextActionPerformed(evt);
            }
        });

        okButton.setBackground(getBackground());
        okButton.setText("OK");

        doneButton.setBackground(getBackground());
        doneButton.setText("Gotowe");
        doneButton.setToolTipText("");

        deleteStreamButton.setBackground(new java.awt.Color(255, 51, 51));
        deleteStreamButton.setText("Usuń Przestrzeń");

        javax.swing.GroupLayout editStreamPanelLayout = new javax.swing.GroupLayout(editStreamPanel);
        editStreamPanel.setLayout(editStreamPanelLayout);
        editStreamPanelLayout.setHorizontalGroup(
            editStreamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editStreamPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editStreamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editStreamPanelLayout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(okButton)
                        .addGap(18, 18, 18)
                        .addComponent(doneButton)
                        .addGap(79, 79, 79)
                        .addComponent(deleteStreamButton))
                    .addGroup(editStreamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(newStreamNameText)))
                .addContainerGap(248, Short.MAX_VALUE))
        );
        editStreamPanelLayout.setVerticalGroup(
            editStreamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editStreamPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newStreamNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editStreamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(doneButton)
                    .addComponent(deleteStreamButton))
                .addGap(10, 10, 10))
        );

        mainPanel.add(editStreamPanel);
        editStreamPanel.setVisible(false);

        notesOptionsPanel.setBackground(new java.awt.Color(153, 255, 204));
        notesOptionsPanel.setPreferredSize(new java.awt.Dimension(700, 84));

        newNoteButton.setText("Nowa Notatka");
        newNoteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newNoteButtonActionPerformed(evt);
            }
        });

        editNotesCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Dodaj/odejmij gwiazdkę", "Usuń", "Zmień Przestrzeń" }));
        editNotesCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editNotesComboActionPerformed(evt);
            }
        });

        editNotesLabel.setText("Edytuj zaznaczone:");

        sortNotesCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "wg. nazwy rosnąco", "wg. nazwy malejąco", "wg. daty utworzenia rosnąco", "wg. daty utworzenia malejąco" }));
        sortNotesCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortNotesComboActionPerformed(evt);
            }
        });

        sortNotesLabel.setText("Sortuj:");

        collapseAllButton.setText("zwiń wszystko");

        expandAllButton.setText("rozwiń wszystko");
        expandAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expandAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout notesOptionsPanelLayout = new javax.swing.GroupLayout(notesOptionsPanel);
        notesOptionsPanel.setLayout(notesOptionsPanelLayout);
        notesOptionsPanelLayout.setHorizontalGroup(
            notesOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notesOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newNoteButton)
                .addGap(53, 53, 53)
                .addGroup(notesOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editNotesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editNotesLabel))
                .addGap(51, 51, 51)
                .addGroup(notesOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(notesOptionsPanelLayout.createSequentialGroup()
                        .addComponent(sortNotesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addComponent(collapseAllButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(expandAllButton))
                    .addGroup(notesOptionsPanelLayout.createSequentialGroup()
                        .addComponent(sortNotesLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        notesOptionsPanelLayout.setVerticalGroup(
            notesOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notesOptionsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(notesOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sortNotesLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editNotesLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(1, 1, 1)
                .addGroup(notesOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editNotesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newNoteButton)
                    .addComponent(sortNotesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(collapseAllButton)
                    .addComponent(expandAllButton))
                .addGap(111, 111, 111))
        );

        mainPanel.add(notesOptionsPanel);

        createStreamPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        createStreamPanel.setLayout(new javax.swing.BoxLayout(createStreamPanel, javax.swing.BoxLayout.LINE_AXIS));
        createStreamPanel.add(jSeparator1);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("zalogowany jako: ");

        synchronizeButton.setBackground(new java.awt.Color(0, 51, 255));
        synchronizeButton.setText("synchronizacja");
        synchronizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                synchronizeButtonMouseClicked(evt);
            }
        });
        synchronizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                synchronizeButtonActionPerformed(evt);
            }
        });

        synchroInfoLabel.setText("Trwa synchronizacja....");
        synchroInfoLabel.setVisible(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(210, 210, 210)
                .addComponent(synchronizeButton)
                .addGap(26, 26, 26)
                .addComponent(synchroInfoLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(synchronizeButton)
                    .addComponent(synchroInfoLabel))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(createStreamPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sideTabbedPane))
                        .addGap(18, 18, 18)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sideTabbedPane)
                        .addGap(0, 0, 0)
                        .addComponent(createStreamPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(76, 76, 76))
        );

        sideTabbedPane.getAccessibleContext().setAccessibleName("Wszystki Przestrzenie");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inviteButtonHandler(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inviteButtonHandler
        shareNotePanel.setVisible(!shareNotePanel.isVisible());
    }//GEN-LAST:event_inviteButtonHandler

    private void newNoteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newNoteButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newNoteButtonActionPerformed

    private void editNotesComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editNotesComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editNotesComboActionPerformed

    private void sortNotesComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortNotesComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sortNotesComboActionPerformed

    private void expandAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expandAllButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expandAllButtonActionPerformed

    private void newStreamNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newStreamNameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newStreamNameTextActionPerformed

    private void editStreamButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editStreamButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editStreamButtonActionPerformed

    private void editStreamButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editStreamButtonMouseClicked
        editStreamPanel.setVisible(!editStreamPanel.isVisible());
    }//GEN-LAST:event_editStreamButtonMouseClicked

    private void synchronizeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_synchronizeButtonMouseClicked
        catchConnector connector = Database.getUser();
        synchroInfoLabel.setVisible(true);
        synchronizer = new Synchronizer();
        synchronizer.run(connector);
        synchroInfoLabel.setText("Ostatnia synchronizacja: "+new Date());
        
        refreshAllStreamsPanel();
    }//GEN-LAST:event_synchronizeButtonMouseClicked

    private void synchronizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_synchronizeButtonActionPerformed
        
    }//GEN-LAST:event_synchronizeButtonActionPerformed

    private void createNotePanels() {
       NotePanel np = new NotePanel(this);
       np.setVisible(true);
       mainPanel.add(np);
       pack();
    }
    
    private void createAllStreamsPanel() {
       List<miniStream> allStreams = streamFromLocal.getStreams();
       for (miniStream mini : allStreams) {
           StreamLinkPanel streamLink = new StreamLinkPanel(mini.getName());
           allStreamsPanel.add(streamLink);
           streamLinks.add(streamLink);
       }       
    }
  
    private void createAddStreamPanel() {
        AddStreamPanel addStreamPanel = new AddStreamPanel();
        createStreamPanel.add(addStreamPanel);
    }
    
    private void refreshAllStreamsPanel() {
       for (Component comp : allStreamsPanel.getComponents() ) {
           allStreamsPanel.remove(comp);
       }
       createAllStreamsPanel();
       allStreamsPanel.validate();
       allStreamsPanel.update(allStreamsPanel.getGraphics());
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                
            } catch (    ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
        
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
              //      new MainFrame().setVisible(true);
                }
            });
       
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField DisplayedStreamName;
    private javax.swing.JPanel allStreamsPanel;
    private javax.swing.JButton collapseAllButton;
    private javax.swing.JPanel createStreamPanel;
    private javax.swing.JButton deleteStreamButton;
    private javax.swing.JButton doneButton;
    private javax.swing.JComboBox editNotesCombo;
    private javax.swing.JLabel editNotesLabel;
    private javax.swing.JToggleButton editStreamButton;
    private javax.swing.JPanel editStreamPanel;
    private javax.swing.JButton expandAllButton;
    private javax.swing.JToggleButton inviteButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainHeaderPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton newNoteButton;
    private javax.swing.JTextField newStreamNameText;
    private javax.swing.JPanel notesOptionsPanel;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel privateStreamsPanel;
    private javax.swing.JPanel shareNotePanel;
    private javax.swing.JPanel sharedStreamsPanel;
    private javax.swing.JTabbedPane sideTabbedPane;
    private javax.swing.JComboBox sortNotesCombo;
    private javax.swing.JLabel sortNotesLabel;
    private javax.swing.JLabel synchroInfoLabel;
    private javax.swing.JButton synchronizeButton;
    // End of variables declaration//GEN-END:variables
    private LinkedList<NotePanel> notePanels;
    private LinkedList<String> streamList = new LinkedList<>();
    private LinkedList<StreamLinkPanel> streamLinks = new LinkedList<>();
    private Controller controller;
    private StreamFromLocal streamFromLocal = new StreamFromLocal();
    private Synchronizer synchronizer;

}
