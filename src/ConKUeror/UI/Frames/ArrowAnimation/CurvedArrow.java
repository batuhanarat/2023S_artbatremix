package src.ConKUeror.UI.Frames.ArrowAnimation;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class CurvedArrow extends JPanel implements Arrow {
    private float centerX; // Variable to store the center x-coordinate
    private float centerY; // Variable to store the center y-coordinate
    private double angle;


    @Override
    public void paintComponent(Graphics g) {
    super.paintComponent(g);







    }



    public void draw(Graphics2D g, float x, float y, float pathHeight,float pathWidth, float lineHeight,float lineWidth,double angle) {
        centerX = x+10; // Set the center x-coordinate
        centerY = y+20; // Set the center y-coordinate
        this.angle = angle;
        float arrowRatio = 0.4f;
        float arrowLength = 60.0f; // Adjust the arrow length to make it smaller
        float arrowWidth = arrowRatio * arrowLength; // Adjust the arrow width proportionally

        BasicStroke stroke = (BasicStroke) g.getStroke();

        float endX = centerX + 175.0f; // Adjust the x-coordinate of the end point of the arrow

        float veeX = endX - stroke.getLineWidth() * 0.5f / arrowRatio;

        // vee
        Path2D.Float path = new Path2D.Float();

        float waisting = 0.5f;

        float waistX = endX - arrowLength * 0.5f;
        float waistY = arrowRatio * arrowLength * 0.5f * waisting;

        // path.moveTo(veeX - arrowLength * pathHeight-pathWidth, centerY - arrowWidth * pathHeight);
        // path.quadTo(waistX-pathWidth, centerY - waistY, endX-pathWidth, centerY);
        // path.quadTo(waistX-pathWidth, centerY + waistY, veeX - arrowLength * pathHeight-pathWidth, centerY + arrowWidth * pathHeight);

        // // end of arrow is pinched in
        // path.lineTo(veeX - arrowLength * 0.75f * pathHeight-pathWidth, centerY);
        // path.lineTo(veeX - arrowLength * pathHeight-pathWidth, centerY - arrowWidth * pathHeight);

        // Rotate the path by 90 degrees
        Shape rotatedPath = rotateShape(path, this.angle);

        

        // move stem back a bit
        g.setColor(Color.RED);
        float xEnd = veeX - 20 - arrowLength * 0.5f * lineHeight;
        Line2D line = new Line2D.Float(centerX, centerY, lineWidth, centerY);

        // Rotate the line by 90 degrees
        Shape rotatedLine = rotateShape(line, this.angle);

        ((Graphics2D) g).setStroke(new BasicStroke(20.0f * lineHeight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        g.draw(rotatedLine);
     g.setColor(Color.BLUE);

        g.fill(rotatedPath);
    }

     public void move() {
//         // Adjust the movement speed as needed
//         // This method is not relevant to the size of the arrow, so you can leave it as it is
    
// angle+=10;    
// System.out.println(angle);
     }

    public Shape rotateShape(Shape shape, double degrees) {
        rotateAngle(degrees);
        AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(degrees), centerX, centerY);
        return transform.createTransformedShape(shape);
    }

    public void rotateAngle(double angle) {
        

    }
}
