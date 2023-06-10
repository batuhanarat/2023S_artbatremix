package ConKUeror.UI.Frames.ArrowAnimation;


import java.awt.Graphics2D;
import java.awt.List;
import java.util.ArrayList;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ConKUeror.domain.model.Board.Territory;

public class AnimationThread {

  private ActionListener actionListener;

//  int territory_id;
// ArrayList<Integer> territoriesAvailableForAttack = new ArrayList<Integer>();  
// JPanel map;
//  Graphics2D g2d;
// int line_width_end;
    public  AnimationThread(ActionListener l) {


actionListener = l;
    //     this.territory_id = territory_id;
    //     this.territoriesAvailableForAttack = territoriesAvailableForAttack;
    //     this.map = map;
    //     this.g2d = g2d;
    //     this.line_width_end = line_width_end;
    // // animationThread.start();
    
    
    
    }
    
    public void start() {
     
      AnimationThread thread = new AnimationThread(actionListener);
      thread.start();

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
