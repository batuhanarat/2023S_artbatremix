package src.ConKUeror.UI.Frames.ArrowAnimation;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.ConKUeror.UI.Frames.MapView;

public class ArrowMovementThread extends Thread {
    private Animation Arrow;
    private boolean running;
   
    public ArrowMovementThread(Animation Arrow) {
        this.Arrow = Arrow;
        this.running = true;
    }

    public void run() {
     
        
        while (running) {
            Arrow.arrow.move();
            Arrow.repaint();
           
           
           
        

            try {
                Thread.sleep(10); // Adjust the delay between movements as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        running = false;
    }
}