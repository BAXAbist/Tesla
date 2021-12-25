package tesla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author tolik
 */
public class MapGUI extends JFrame {
    
    private TeslaAgent myAgent;
    private final int sizeMapX = 20;
    private final int sizeMapY = 20;
    private JLabel[][] mapLink = new JLabel[sizeMapY][sizeMapX];
    private int[][] mapTypeCell = new int[sizeMapY][sizeMapX];
    private final String res = "src/resources/";
    
//    private class Cell{
//        public JLabel country;
//        public int x,y; 
//        public Offer(JLabel country, int x, int y){
//            this.country = country;
//            this.y = y;
//            this.x = x;
//        }
//    }
//}
    
    public MapGUI(TeslaAgent a) throws Exception{
        
        super(a.getLocalName());
        
        myAgent = a;
        
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        
        JPanel map = new JPanel();
        map.setLayout(new GridLayout(sizeMapY,sizeMapX));
        BufferedReader reader = new BufferedReader(new FileReader(res+"map.txt"));
        for(int i = 0;i<sizeMapY;i++){
            int[] curline = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); 
            for (int j = 0;j<sizeMapX;j++){
                JLabel pix = new JLabel();
                switch(curline[j]){
                    case 0:
                        pix.setIcon(new ImageIcon(res+"pix_not_road.png"));
                        break;
                    case 1:
                        pix.setIcon(new ImageIcon(res+"pix_road.png"));
                        break;
                    case 2:
                        pix.setIcon(new ImageIcon(res+"pix_gas.png"));
                        break;
                }
                mapLink[i][j] = pix;
                map.add(pix);
            }
            System.out.println();
        }
        reader.close();
        p.add(map,BorderLayout.CENTER);
        getContentPane().add(p, BorderLayout.CENTER);
        
    }
    
     public void showGui() {
         pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
         super.setVisible(true);
     }
}
