
package tesla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author tolik
 */
public class ControlGUI extends JFrame {
    
    private JLabel left;
    private JLabel right;
    private JLabel gas;
    private JLabel brake;
    private JProgressBar fuel;
    private final String res = "src/resources/";
    private ElonMuskAgent myAgent;

    
    public ControlGUI(ElonMuskAgent a){
        
        super(a.getLocalName());
        
        myAgent = a;
        
        setPreferredSize(new Dimension(250, 150));
        
        JPanel p = new JPanel(new BorderLayout());
        
        left = new JLabel();
        left.setIcon(new ImageIcon(res + "red_l.png"));
        p.add(left,BorderLayout.WEST);
        
        right = new JLabel();
        right.setIcon(new ImageIcon(res + "red_r.png"));
        p.add(right,BorderLayout.CENTER);
        
        JPanel control = new JPanel();
        control.setLayout(new GridLayout(1,2));
        
        gas = new JLabel();
        gas.setText("Gas");
        gas.setFont(new Font(gas.getName(),Font.PLAIN,20));
        control.add(gas);
        
        brake = new JLabel();
        brake.setText("Brake");
        brake.setFont(new Font(gas.getName(),Font.PLAIN,20));
        brake.setForeground(Color.red);
        control.add(brake);
        p.add(control,BorderLayout.SOUTH);

        fuel = new JProgressBar(JProgressBar.VERTICAL);
        fuel.setStringPainted(true);
        fuel.setMinimum(0);
        fuel.setMaximum(100);
        //fuel.setLayout(new GridLayout(0 , 4,10,10));
        fuel.setValue(100);

        p.add(fuel,BorderLayout.EAST);
        
        p.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e){
                switch(e.getExtendedKeyCode()){
                    case(KeyEvent.VK_RIGHT):
                        right.setIcon(new ImageIcon(res + "gr_r.png"));
                        a.updateMes(1);
                        break;
                    case(KeyEvent.VK_LEFT):
                        left.setIcon(new ImageIcon(res + "gr_l.png"));
                        a.updateMes(2);
                        break;
                    case(KeyEvent.VK_UP):
                        gas.setForeground(Color.red);
                        brake.setForeground(Color.black);
                        a.updateMes(3);
                        break;
                    case(KeyEvent.VK_DOWN):
                        gas.setForeground(Color.black);
                        brake.setForeground(Color.red);
                        a.updateMes(4);
                        break;
                    case(KeyEvent.VK_CONTROL):
                        gas.setForeground(Color.black);
                        brake.setForeground(Color.red);
                        a.updateMes(5);
                        break;
                    case(KeyEvent.VK_F1):
                        a.updateMes(6);
                        break;
                        
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e){
                switch(e.getExtendedKeyCode()){
                    case(KeyEvent.VK_RIGHT):
                        right.setIcon(new ImageIcon(res + "red_r.png"));
                    case(KeyEvent.VK_LEFT):
                        left.setIcon(new ImageIcon(res + "red_l.png"));
                }
            }
            
            @Override
            public void keyTyped(KeyEvent e){
            }
        });
        p.setFocusable(true);
        getContentPane().add(p, BorderLayout.CENTER);
        
        
    }
    
    public void stop(){
        gas.setForeground(Color.black);
        brake.setForeground(Color.red);
    }
    
    public void fuel(int cnt_fuel){
        fuel.setValue(cnt_fuel);
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
