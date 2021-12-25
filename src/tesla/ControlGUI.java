/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tesla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author tolik
 */
public class ControlGUI extends JFrame {
    
    private JLabel left;
    private JLabel right;
    private JLabel gas;
    private JLabel brake;
    private final String res = "src/resources/";
    private ElonMuskAgent myAgent;
    
    public ControlGUI(ElonMuskAgent a){
        
        super(a.getLocalName());
        
        myAgent = a;
        
        JPanel p_bottom = new JPanel(new BorderLayout());
        
        JPanel control = new JPanel();
        control.setLayout(new GridLayout(2,2));
        
        left = new JLabel();
        left.setIcon(new ImageIcon(res + "red_l.png"));
        control.add(left);
        
        right = new JLabel();
        right.setIcon(new ImageIcon(res + "red_r.png"));
        control.add(right);
        
        gas = new JLabel();
        gas.setText("Gas");
        gas.setForeground(Color.red);
        control.add(gas);
        
        brake = new JLabel();
        brake.setText("Brake");
        control.add(brake);
        
        p_bottom.add(control,BorderLayout.CENTER);
    }
    
    public void showGui() {
         pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() - 200;
        int centerY = (int)screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
         super.setVisible(true);
     }
            
}
