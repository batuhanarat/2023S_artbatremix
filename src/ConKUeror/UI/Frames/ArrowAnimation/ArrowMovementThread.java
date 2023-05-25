package src.ConKUeror.UI.Frames.ArrowAnimation;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.ConKUeror.UI.Frames.MapView;

public class ArrowMovementThread extends Thread {
    private Animation Arrow;
    private boolean running;
    private int x;
    MapView p;
    CurvedArrow arrow;
    Graphics2D g;
    int arrow_x;
    int arrow_y;
    float pathHeight;
    float pathWidth;
    float lineHeight;
    float lineWidth;
    public ArrowMovementThread(int arrow_x,CurvedArrow arrow) {
        this.running = true;
        // this.x = x;
        // p = panel;
        this.arrow = arrow;
        this.arrow_x = arrow_x;
    

    }

    public void run() {
        while (running) {

//             arrow_x+=10;
//       arrow.repaint();
// System.out.println("hi");
            


            try {
                Thread.sleep(10); // Adjust the delay between movements as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            arrow.repaint();

        }
    }

    public void stopThread() {
        running = false;
    }
}