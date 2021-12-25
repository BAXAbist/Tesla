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
 * @author Ivan + Tolik = ♥
 */
public class MapGUI extends JFrame {
    
    private TeslaAgent myAgent;
    private final int sizeMapX = 20;
    private final int sizeMapY = 20;
    private JLabel[][] mapLink = new JLabel[sizeMapY][sizeMapX];
    private int[][] mapTypeCell = new int[sizeMapY][sizeMapX];
    private final String res = "src/resources/";
    private Cell cell;
    
    private class Cell{
        public JLabel car;
        public int x,y; 
        public Cell(JLabel car, int x, int y){
            this.car = car;
            this.x = x;
            this.y = y;
            
        }
    }
    

    
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
        
        JLabel car = new JLabel();
        car.setIcon(new ImageIcon(res+"pix_car_right.png"));
        cell = new Cell(car,1,1);
        
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
     public void turn(int direction){
         switch (direction){
             case(0):{
                 cell.car.setIcon(new ImageIcon(res+"pix_car_up.png"));
             }
             break;
             
             case(1):{
                 cell.car.setIcon(new ImageIcon(res+"pix_car_right.png"));
             }
             break;
             
             case(2):{
                 cell.car.setIcon(new ImageIcon(res+"pix_car_down.png"));
             }
             break;
             
             case(3):{
                 cell.car.setIcon(new ImageIcon(res+"pix_car_left.png"));
             }
             break;
             }
            }
     
     public boolean isWall(int direction){
        switch (direction){
             case(0):{
                 return mapTypeCell[cell.x][cell.y-1] !=0;
             }             
             case(1):{
                 return mapTypeCell[cell.x+1][cell.y] !=0;
             }
             case(2):{
                 return mapTypeCell[cell.x][cell.y+1] !=0;
             }             
             case(3):{
                 return mapTypeCell[cell.x-1][cell.y] !=0;
             }
             } 
        return true;
     }
     
     public void movement(){
     
     }
             
     }
}