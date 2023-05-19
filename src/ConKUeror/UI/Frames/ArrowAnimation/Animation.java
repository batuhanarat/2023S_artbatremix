package src.ConKUeror.UI.Frames.ArrowAnimation;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Animation extends JPanel implements Runnable {
  
    Arrow arrow = new CurvedArrow();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

       

        g2d.setStroke(new BasicStroke(20.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        g2d.translate(0, 60);
        arrow.draw(g2d);

        g2d.translate(400, -260);
    }

  
    @Override
    public void run() {
                 Animation arrow = new Animation();

        JFrame frame = new JFrame("Bevel Arrows");

        frame.add(arrow, BorderLayout.CENTER);

        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // Create and start the arrow movement thread
        ArrowMovementThread movementThread = new ArrowMovementThread(arrow);
        movementThread.start();
    }


}
