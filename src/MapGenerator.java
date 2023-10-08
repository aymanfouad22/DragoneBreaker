import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapGenerator extends JPanel {

    public void GameOver(Graphics g,int score){
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //text
        g.setColor(Color.yellow);
        g.setFont(new Font("serif",Font.BOLD,70));
        g.drawString("Game Over",150,320);

        //score
       
        g.setColor(Color.yellow);
        g.setFont(new Font("serif", Font.BOLD, 15));
        g.drawString("Your Score :"+score, 280, 360);
   
        //replay
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Press Enter To replay", 210, 450);

    }
    public void winGame(Graphics g,int score){
          // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //text
        g.setColor(Color.yellow);
        g.setFont(new Font("serif",Font.BOLD,70));
        g.drawString("You Won!",150,320);

        //score
       
        g.setColor(Color.yellow);
        g.setFont(new Font("serif", Font.BOLD, 15));
        g.drawString("Your Score :"+score, 280, 360);

        //replay
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Press Enter To replay", 210, 450);
   

    }
    //fire draw
    public void fireDraw(Graphics g,ImageIcon fire_down,ImageIcon fire_left,ImageIcon fire_right,
    int fire_downX,int fire_downY,int fire_leftX,int fire_leftY,int fire_rightX,int fire_rightY,int score){
        fire_down.paintIcon(this,g,fire_downX,fire_downY);
        fire_left.paintIcon(this,g,fire_leftX,fire_leftY);
        fire_right.paintIcon(this,g,fire_rightX,fire_rightY);
    }
   
}
