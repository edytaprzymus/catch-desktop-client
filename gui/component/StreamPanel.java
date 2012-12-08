/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.component;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Prezes
 */
public class StreamPanel extends JPanel{

    public StreamPanel() {
        setLayout( null );
        JLabel label1 = new JLabel( "Username:" );
        label1.setBounds( 10, 15, 150, 20 );
        add( label1 );
    }
    
}
