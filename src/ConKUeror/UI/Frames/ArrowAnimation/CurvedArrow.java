package src.ConKUeror.UI.Frames.ArrowAnimation;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;


public class CurvedArrow implements Arrow {
    // to draw a nice curved arrow, fill a V shape rather than stroking it with lines
    private float x = 90.0f;

    public void draw(Graphics2D g) {
        // as we're filling rather than stroking, control point is at the apex,

        float arrowRatio = 0.4f;
        float arrowLength = 80.0f;

        BasicStroke stroke = (BasicStroke) g.getStroke();

        float endX = 350.0f;

        float veeX = endX - stroke.getLineWidth() * 0.5f / arrowRatio;

        // vee
        Path2D.Float path = new Path2D.Float();

        float waisting = 0.5f;

        float waistX = endX - arrowLength * 0.5f;
        float waistY = arrowRatio * arrowLength * 0.5f * waisting;
        float arrowWidth = arrowRatio * arrowLength;

        path.moveTo(veeX - arrowLength, -arrowWidth);
        path.quadTo(waistX, -waistY, endX, 0.0f);
        path.quadTo(waistX, waistY, veeX - arrowLength, arrowWidth);

        // end of arrow is pinched in
        path.lineTo(veeX - arrowLength * 0.75f, 0.0f);
        path.lineTo(veeX - arrowLength, -arrowWidth);

        g.setColor(Color.BLUE);
        g.fill(path);

        // move stem back a bit
        g.setColor(Color.RED);
        float xEnd = veeX - 20 - arrowLength * 0.5f;
        if(x-xEnd == 0)
        x = 90.0f;
        Line2D line = new Line2D.Float(x, 0, xEnd, 0.0f);
        

        g.draw(line);
    }

    public void move() {
        x += 1.0f; // Adjust the movement speed as needed
    }


    
    
}
