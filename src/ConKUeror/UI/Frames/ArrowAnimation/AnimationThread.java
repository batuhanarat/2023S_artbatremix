package ConKUeror.UI.Frames.ArrowAnimation;


import java.awt.Graphics2D;
import java.awt.List;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import ConKUeror.domain.controller.ArrowAnimationController;
import ConKUeror.domain.model.Board.Territory;

public class AnimationThread {

  Territory t;
  ArrayList<Integer> territoriesAvailableForAttack;

ArrowAnimationController ar;


    public AnimationThread(ArrayList<Integer> territoriesAvailableForAttack,Territory t,ArrowAnimationController ar) {

    this.t = t;
    this.territoriesAvailableForAttack = territoriesAvailableForAttack;
    this.ar = ar;
    }
    
  

    Thread animationThread = new Thread(() -> {

      int line_width_end = 0;
            int territory_id = t.getId();
            float line_height = 0.4f;
            float path_height = 0.2f;

    while (true) {
      
          
      for(int i: territoriesAvailableForAttack) {


            
        arrowAnimation a = new arrowAnimation();
         try {
          a.Animation(territory_id,i,ar.getGraphics(), line_width_end);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        if(a.getDistance()<line_width_end) line_width_end = 0;
        line_width_end+=2;


      }
      
  
  
       
  
  
       }
    });


    













    public void start() {
     
      // AnimationThread thread = new AnimationThread(actionListener);
      animationThread.start();

    }

    // Thread animationThread = new Thread(() -> {

    //     // if(!pauseScreen.isVisible()) threader = true; 
    //     int threadStarter = 1;
    //     int line_width_end = 0;
    
    //     float line_height = 0.5f;
    //     float path_height = 0.8f;

     
     
     
    //     while (true) {
    
    
    //       System.out.println("Territory id: " + territory_id);

   
    //   for(int i: territoriesAvailableForAttack) {
    
    //     arrowAnimation a = new arrowAnimation();
    //     // line_width_end+=2;


    //     a.Animation(territory_id,i,g2d, line_width_end);
    //     //  if(line_width_end> a.getDistance())
    //     //  line_width_end = 0;

    //      map.repaint();
    
    //       if(threadStarter == 0) {
    //         try {
    //             Thread.sleep(5000);
                
    //         } catch (Exception e) {
    //             // TODO: handle exception
    //         }
    //       } else {
    
    
    //         try {
          
    //         } catch (Exception b) {
    //             // TODO: handle exception
               
    //         }
    
            
    //       }
    
    
    
    //     }
    //  }});
    












}
