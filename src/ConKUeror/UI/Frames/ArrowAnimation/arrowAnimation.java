package ConKUeror.UI.Frames.ArrowAnimation;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Array;
import java.util.Map;

import ConKUeror.domain.controller.ButtonHandler;
import ConKUeror.domain.model.Board.MapConstants;

public class arrowAnimation {

    private Arrow arrow = new Arrow();

    private int territory_id = -1;
    private float arrow_x;
    private float arrow_y;
    float path_width;
    float path_height;
    float line_height;
    float line_width;
    Graphics2D g2d;
    float path_width_end;
    float path_height_end;
    float line_height_end;
    // float line_width_end;
    ButtonHandler buttonHandler;
    double distance;
    
    Integer[][]  line_width_ends = new Integer[43][43];
    Float[][] line_width_neighborTerritories = new Float[43][43];

    
    
    public double getDistance() {


     return distance;

    }
    public void Animation(int territory_id,int i,Graphics2D g2, int line_width_end) { // REQUIRES: Territory_id should be between 0 and 41 && neighborterritorId
        // should have a size of 42. The red line starting from the button territory_id
        // should end to its neighbor buttons territoryId.
    
        MapConstants m = new MapConstants();
        m.fillCoordinates();
       float arrow_x = m.coordinates.get(territory_id).getX();
       float arrow_y = m.coordinates.get(territory_id).getY();
      float  path_width = m.coordinates.get(territory_id).getX();
     float line_h = 0.5f;
      float path_h = 0.8f;

      System.out.println("Arrow X: " + arrow_x);
      System.out.println("Arrow Y: " + arrow_y);
      System.out.println("Line width end: " + line_width_end);
      System.out.println("Territory id: " + territory_id);


           
    float neighbor_x= m.coordinates.get(i).getX();
    
    float neighbor_y= m.coordinates.get(i).getY();
    System.out.println("Neighbor x: " + neighbor_x);
    System.out.println("Neighbor y: " + neighbor_y);

        // buttonHandler = b;
        // arrow_x = x;
        // arrow_y = y;
        line_height  = line_h;
        path_height = path_h;
        // path_width = path_w;
        g2d = g2;
        
        // EFFECTS: Returns a red line object starting from the button territory_id
        // ends to the buttons index of its neighborterritorId  .


        
    
    
    // float neighbor_x= buttonHandler.getBuildMode().getCoordinateList().get(neighborterritorId[i]).getX();
    
    // float neighbor_y= buttonHandler.getBuildMode().getCoordinateList().get(neighborterritorId[i]).getY();


    
    
    
    float distance_neighbor_x = arrow_x - neighbor_x;
    float distance_neighbor_y = arrow_y - neighbor_y;
    
    System.out.println("Distance x: " + distance_neighbor_x);
    System.out.println("Distance y: " + distance_neighbor_y);
    
    double angle =  distance_neighbor_y/distance_neighbor_x;
    
    
    
    double degree =  Math.toDegrees(Math.atan(angle));
    
    
    
    double distance_neighbor_x_square = distance_neighbor_x*distance_neighbor_x;
    double distance_neighbor_y_square = distance_neighbor_y*distance_neighbor_y;
    
    double distance_overall = (float) Math.sqrt(distance_neighbor_x_square+distance_neighbor_y_square);
    
    distance = distance_overall;
    
    System.out.println("Distance x: "+ distance_neighbor_x);
    System.out.println("Distance y: "+ distance_neighbor_y);
    System.out.println(angle);
    System.out.println("Degree: "+ degree);
    System.out.println("Distance overall: "+ distance_overall);
    
    
    if(distance_neighbor_y>0 && distance_neighbor_x<0)
    degree = degree;
    else if(distance_neighbor_y<0 && distance_neighbor_x<0)
    degree = degree;
    else if(distance_neighbor_y<0 && distance_neighbor_x>0)
    degree = degree-180;
    else if(distance_neighbor_y>0 && distance_neighbor_x>0)
    degree =180+ degree;
    

    line_width_end+=2;
    System.out.println("Update Degree: "+ degree);
    if(line_width_end>distance_overall)
    line_width_end = 0;
    System.out.println("Line end: "+ line_width_end);

    
    arrow.draw(g2d, arrow_x, arrow_y, path_height,path_width-arrow_x, line_height, line_width_end+arrow_x,degree);
    
    
    
    
    
    
    }
    





    
}
