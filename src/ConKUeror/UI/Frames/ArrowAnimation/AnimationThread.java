package ConKUeror.UI.Frames.ArrowAnimation;


import java.awt.Graphics2D;
import java.awt.List;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import ConKUeror.UI.Frames.MapView;
import ConKUeror.domain.controller.ArrowAnimationController;
import ConKUeror.domain.enums.GameMode;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Modes.GameLogic;

public class AnimationThread {

  Territory t;
  ArrayList<Integer> territoriesAvailableForAttack;
  MapView map;

ArrowAnimationController ar;
GameLogic gmode;


    public AnimationThread(MapView m,GameLogic gmode) {

    this.t = t;
    this.territoriesAvailableForAttack = territoriesAvailableForAttack;
    map = m;
    this.gmode = gmode;
    // this.ar = ar;
    }
    
  

    Thread animationThread = new Thread(() -> {

      int line_width_end = 0;
            int territory_id = t.getId();
            float line_height = 0.4f;
            float path_height = 0.2f;

    while (true) {
      
          

        if(gmode.currentGameMode == GameMode.ATTACK) {
      for(int i: territoriesAvailableForAttack) {


            
        arrowAnimation a = new arrowAnimation();
         try {
          a.Animation(territory_id,i,map.g2d, line_width_end);

          
          map.mapPanel.repaint();
          
          Thread.sleep(100);

          if(a.getDistance()<line_width_end) line_width_end = 0;
        line_width_end+=2;
        } catch (  InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        

          

      }
      
  
  
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
