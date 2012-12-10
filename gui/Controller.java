
package gui;
import connector.catchConnector;
import gui.component.*;


/**
 *
 * @author Przemo
 */
public class Controller {
    LoginDialog dialog;
    catchConnector connector;
    MainFrame mainFrame;
    
    public Controller() {
        try {
            //ustawienie domyślnego wyglądu okienek
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());     
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        dialog = new LoginDialog(new javax.swing.JFrame(), true, this);
        dialog.setVisible(true);  
        
        if (dialog.getReturnStatus() == 1) {
            startMainFrame();
        } else {
            System.exit(0);
        }
        
    }
    
    public void setCatchConnector(catchConnector connector) {
        this.connector = connector;
    }
    
    public catchConnector getCatchConnector() {
        return connector;
    }

    private void startMainFrame() {
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
    }
}
