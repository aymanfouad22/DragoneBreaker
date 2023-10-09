import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class  App {
    public static void main(String[] args) throws Exception {
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        obj.setBounds(10, 10, 692, 600);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setTitle("Dragon breaker");
        obj.add(gameplay);
        obj.setVisible(true);
        obj.setResizable(false);       
          
         obj.addKeyListener(gameplay);
         Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
         obj.setIconImage(icon);
    }
}