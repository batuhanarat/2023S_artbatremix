package ConKUeror.UI.Frames.ArrowAnimation;


import java.awt.List;
import java.util.ArrayList;

import ConKUeror.domain.model.Board.Territory;

public class AnimationThread {

   
public Territory t;
ArrayList<Integer> territoriesAvailableForAttack = new ArrayList<Integer>();   

    public AnimationThread(Territory t, ArrayList<Integer> territoriesAvailableForAttack) {

        this.t = t;
        this.territoriesAvailableForAttack = territoriesAvailableForAttack;
    animationThread.start();
    
    
    
    }
    

    Thread animationThread = new Thread(() -> {

        // if(!pauseScreen.isVisible()) threader = true; 
        int threadStarter = 1;
        int line_width_end = 0;
        int territory_id = t.getId();
        float line_height = 0.4f;
        float path_height = 0.2f;
     while (true) {
    
    
      
    
      for(int i: territoriesAvailableForAttack) {
    
    
        arrowAnimation a = new arrowAnimation();
        line_width_end+=2;
        a.Animation(territory_id,i,null,t.getGraphics(), line_width_end);
         if(line_width_end> a.getDistance())
         line_width_end = 0;
    
          if(threadStarter == 0) {
            try {
                Thread.sleep(5000);
                
            } catch (Exception e) {
                // TODO: handle exception
            }
          } else {
    
    
            try {
          
            } catch (Exception b) {
                // TODO: handle exception
               
            }
    
            
          }
    
    
    
        }
     }});
    












}
