import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
public class Gameplay extends JPanel implements KeyListener,   ActionListener {
    private boolean play =  false;
    private Timer timer;
    private int delay= -10;
    public int score = 10;
    public int finalScore;
    private int PlayerX = 200;
    private int BallXpos=(int)(Math.random()*(500-100+1)+100);
    private int BallYpos=(int)(Math.random()*(360-200+1)+200);
    private int BallXdir= -1;
    private int BallYdir= -4;
    private int fire_downY = -70;
    private int fire_downX = 190;
    private int fire_downYdir = 3;
    private int fire_leftY = -59;
    private int fire_leftX = 130;
    private int fire_leftYdir = 2;
    private int fire_leftXdir = -1;
    private int fire_rightY = -69;
    private int fire_rightX = 210;
     private int fire_rightYdir = 2;
    private int fire_rightXdir = 1;
    private int dragonIntersected = 0;
    private boolean paddleHitByFire=false;
    MapGenerator map = new MapGenerator();
    public RoundRectangle2D roundedRect = new RoundRectangle2D.Double(250, 150, 150, 10, 15, 25);
    public Gameplay() throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException{
        addKeyListener(this);
        setFocusTraversalKeysEnabled(false);
    timer = new Timer(delay,this);
     timer.start(); 
    
     playSound("sounds/audio3.wav");
    }
    
     @Override
    
     public void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        ImageIcon gifIcon = new ImageIcon("gif.gif");
        ImageIcon bg = new ImageIcon("Background.png");
        ImageIcon paddle = new ImageIcon("Paddle.png");
        ImageIcon fire_down = new ImageIcon("fire_down.png");
        ImageIcon fire_left = new ImageIcon("fire_left.png");
        ImageIcon fire_right = new ImageIcon("fire_right.png");
    
        // Draw background
        bg.paintIcon(this, g, 0, 0);
    
        // Draw the borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        g.fillRect(673, 0, 3, 592);
    
        // Draw the blood
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.green);
        g2d.fill(roundedRect);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.red);
        g2d.drawRoundRect(250, 150, 150, 10, 15, 25);
        g2d.draw(roundedRect);
    
        // Draw the score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);
    
        // Draw the dragon 
            gifIcon.paintIcon(this, g, 210, -1);
        
    
        // Draw the paddle
        paddle.paintIcon(this, g, PlayerX, 540);
    
        // Draw the ball
        g.setColor(Color.yellow);
        g.fillOval(BallXpos, BallYpos, 20, 20);
    
        // Draw fire if dragonIntersected is positive
    Rectangle paddleBound = new Rectangle(PlayerX,550,100,8);
    Rectangle fireDownBound = new Rectangle(fire_downX,fire_downY,210,220);
    Rectangle fireLeftBound = new Rectangle(fire_leftX,fire_leftY,210,210);
    Rectangle firerightBound = new Rectangle(fire_rightX,fire_rightY,210,210);
       
        if (dragonIntersected > 0 & !paddleBound.intersects(firerightBound)  ) {        
            fire_right.paintIcon(this, g, fire_rightX, fire_rightY); 
            
          
              
        }
        if(dragonIntersected > 0 & !paddleBound.intersects(fireLeftBound)){
          fire_left.paintIcon(this, g, fire_leftX, fire_leftY);
       
              
          
        }
        if( dragonIntersected > 0 & !paddleBound.intersects(fireDownBound)){
              fire_down.paintIcon(this, g, fire_downX, fire_downY);
            
        }
        if(fire_downY > 592){
            dragonIntersected=0;
        }
        //Check if fire hit the paddle
    Rectangle paddleHitBound = new Rectangle(PlayerX,548,80,8);
    Rectangle fireDownHitBound = new Rectangle(fire_downX+140,fire_downY+160,30,50);
    Rectangle fireLeftHitBound = new Rectangle(fire_leftX+165,fire_leftY+160,30,50);
    Rectangle firerightHitBound = new Rectangle(fire_rightX+150,fire_rightY+165,10,50);
             
             if (paddleHitBound.intersects(fireLeftHitBound) || paddleHitBound.intersects(fireDownHitBound) || paddleHitBound.intersects(firerightHitBound)) {
            if (!paddleHitByFire) {
                // Decrement the score by 10 only once
                score -= 10;
                paddleHitByFire = true; // Mark that the paddle has been hit by fire
                try {
                    playSound("sounds/fire.wav");
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        }else{paddleHitByFire = false;}
        if (score < 0){score = 0;}   
        // Check for game over
       Rectangle rec1 = new Rectangle(0, 592, 692, 3);
     if (new Rectangle(BallXpos, BallYpos, 20, 20).intersects(rec1) || score == 0) {
            map.GameOver((Graphics) g, score);
            play = false;
        } 
    
        // Check for win game
        if (roundedRect.getWidth()<=0) {
            map.winGame((Graphics) g, score);
            play = false;
        }
    
        g.dispose();
    }    
    public void actionPerformed(ActionEvent e) {
        if (play) {
            
            if(new Rectangle(BallXpos,BallYpos,20,20).intersects(new Rectangle(PlayerX, 550, 100, 8))){
                BallYdir=-BallYdir;
            }
            // Colision of the ball with the dragon
            Rectangle dragonBounds = new Rectangle(210, -1, 270, 120);
        Rectangle ballBounds = new Rectangle(BallXpos, BallYpos, 20, 20);

        if (ballBounds.intersects(dragonBounds)) {
            // Check which side of the dragon was hit
            dragonIntersected =100;
           //left side and right side
            if(BallYpos < dragonBounds.getMaxY() & BallXpos <= dragonBounds.getMinX()){
                BallXdir = - BallXdir;
            }
            if(BallYpos < dragonBounds.getMaxY() & BallXpos == dragonBounds.getMaxX()){
                BallXdir = - BallXdir;
            }
                 
            else            
              {  
                BallYdir = -BallYdir; 
            }
            
            
            score+=5;
            roundedRect.setRoundRect(roundedRect.getX(), roundedRect.getY(), roundedRect.getWidth()-20,roundedRect.getHeight(), roundedRect.getArcWidth(), roundedRect.getArcHeight());
            
            try {
                playSound("sounds/audio2.wav");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
                
                e1.printStackTrace();
           }
            
        }
        
        
        // Update the ball's position
        BallXpos += BallXdir;
        BallYpos += BallYdir;
        
        // Check for collisions with the walls
        if (BallXpos <= 0 || BallXpos >= 655) {
            BallXdir = -BallXdir;
        }
        if (BallYpos <= 0) {
            BallYdir = -BallYdir;
        }
   
        //update the fire's position
  
        if(dragonIntersected > 0)  {
        fire_downY += fire_downYdir;
        fire_leftY += fire_leftYdir;
        fire_leftX += fire_leftXdir;
        fire_rightY += fire_rightYdir;
        fire_rightX += fire_rightXdir;
    }
        if (fire_downY > 592) {
            // Reset the fire's position when it reaches the bottom
            fire_downY = -70;
            fire_leftY = -59;
            fire_leftX = 130;
            fire_rightY = -69;
            fire_rightX = 210;
        }     
        // Repaint the panel to update the ball's position
        repaint(); 
       
        } 
}
            
    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if (PlayerX >= 580){
                PlayerX =580;
            }else{
                MoveRight();}
           
        }
    else  if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if (PlayerX <= 10){
                PlayerX =10;
            }else {
                MoveLeft();
            }}
           else if(e.getKeyCode()==KeyEvent.VK_ENTER){
               resetGame();
            }  
      
    }

    
    public void keyReleased(KeyEvent e) {
    }
     public void MoveRight(){
        play=true;
        PlayerX+=20;
     }
     public void MoveLeft(){
        play=true;
        PlayerX-=20;
     }
     public void resetGame() {
        score = 10;
        PlayerX = 310;
        BallXpos = (int)(Math.random()*(500-100+1)+100);
        BallYpos = (int)(Math.random()*(360-200+1)+200);
        BallXdir = -1;
        BallYdir = -4;
       fire_downY = -70;
    fire_downX = 190;
   fire_downYdir = 3;
     fire_leftY = -59;
   fire_leftX = 130;
     fire_leftYdir = 2;
     fire_leftXdir = -1;
     fire_rightY = -69;
     fire_rightX = 210;
      fire_rightYdir = 2;
     fire_rightXdir = 1;
        roundedRect.setRoundRect(roundedRect.getX(), roundedRect.getY(),150,roundedRect.getHeight(), roundedRect.getArcWidth(), roundedRect.getArcHeight());
        play = false;
        dragonIntersected = 0;
        repaint();
    }
     void playSound(String soundFile) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
    File f = new File("./" + soundFile);
    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
    Clip clip = AudioSystem.getClip();
    clip.open(audioIn);
    clip.start();
}
 
    
}
