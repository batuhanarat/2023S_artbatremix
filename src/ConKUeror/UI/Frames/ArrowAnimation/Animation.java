package src.ConKUeror.UI.Frames.ArrowAnimation;

import java.awt.*;
import java.awt.geom.*;
import java.io.IOException;

import javax.swing.*;

import src.ConKUeror.UI.HelpScreen.HelpScreen;

public class Animation extends JPanel  {
  
    Arrow arrow = new CurvedArrow();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2d.setColor(Color.WHITE);

       

        g2d.setStroke(new BasicStroke(20.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        g2d.translate(0, 60);
        //  ((Graphics2D) g).setStroke(new BasicStroke(20.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
                //   g.translate(0, 60);
                // arrow.draw(g2d);
                g2d.translate(400, -260);


    }

  


}
