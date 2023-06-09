package ConKUeror.UI.Frames;

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

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ConKUeror.UI.Buttons.TerritoryButton;
import ConKUeror.UI.Frames.ArrowAnimation.Arrow;
import ConKUeror.UI.Frames.ArrowAnimation.arrowAnimation;
import ConKUeror.UI.HelpScreen.HelpScreen;
import ConKUeror.UI.Panels.InfoPanel;
import ConKUeror.UI.Panels.PlayerInteractionPanel;
import ConKUeror.UI.Panels.PlayerPanel;
import ConKUeror.UI.PauseScreen.PauseScreen;
import ConKUeror.domain.controller.ButtonHandler;
import ConKUeror.domain.controller.GameHandler;
import ConKUeror.domain.controller.HandlerFactory;

import ConKUeror.domain.controller.MapHandler;
import ConKUeror.domain.controller.MapListener;
import ConKUeror.domain.controller.RollDieListener;
import ConKUeror.domain.controller.StartHandler;
import ConKUeror.domain.controller.TerritoryButtonListener;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Data.GameState;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerExpert;

import java.util.ArrayList;
import java.util.List;


public class MapView extends JFrame implements MapListener ,TerritoryButtonListener,RollDieListener{

    Integer[][]  line_width_ends = new Integer[43][43];
    Float[][] line_width_neighborTerritories = new Float[43][43];
    int line_width_end;
  


    private int count = 0;
    int threadStarter = 1;
    MapHandler mapHandler;
    ButtonHandler buttonHandler;
    StartHandler startHandler;
    GameHandler gameHandler;
    PlayerPanel playerPanel;
    JPanel jPanel = new JPanel();
    JPanel jPanel2 =  new JPanel();

    JButton pauseAndResumeButton;
    
    JButton helpButton;
     GameState gameState;
    List<Player> playerList;

    JButton rollButton;
    JButton executeButton;
    JButton nextButton;
    TerritoryButton selectedButton;
    List<TerritoryButton> territoryButtonsList = new ArrayList<TerritoryButton>();
    Boolean selected = false;
    List<TerritoryButton> buttonHistory = new ArrayList<TerritoryButton>();


    public transient  BufferedImage image;
    JPanel mapPanel;
    String armyNum =String.valueOf(0);
    Boolean disable = false;
    int disable_Num = 0;

    JFrame frame;


public MapView() throws IOException {

    HandlerFactory controller =HandlerFactory.getInstance();
    this.mapHandler = controller.giveMapHandler();
    this.buttonHandler = controller.giveButtonHandler();
    this.gameHandler = controller.giveGameHandler();
     frame = this;


    initGUI();
    

   // String openingMessage = startHandler.enterMessageString();
    //DialogBox box = new DialogBox(openingMessage,"Select territories" );
    addMapFrameAsListener();
    addMapFrameAsListenertoListenTerrittoryButtonInteraction();
    addMapFrameAsListenerForRollEvent();


    pauseAndResumeButton.addActionListener(new PauseButtonHandler(this));
    helpButton.addActionListener(new HelpButtonHandler());
    /*
    rollButton.addActionListener(new RollButtonHandler());
    executeButton.addActionListener(new ExecuteButtonHandler());
    nextButton.addActionListener(new NextButtonHandler());
*/


}

public void occupyTerritory() {

}



public void addMapFrameAsListener() {
    mapHandler.registerAsListener(this);

}

public void addMapFrameAsListenertoListenTerrittoryButtonInteraction() {
    buttonHandler.registerAsTerritoryListener(this);
    buttonHandler.registerAsTerritoryListenerPINV(this);

}



public void addMapFrameAsListenerForRollEvent(){
    buttonHandler.registerAsRollListener(this);
}

public void setPanels() {
/// may be used.
}
public List<TerritoryButton> getTerritoryButtonsList(){
    return territoryButtonsList;
}


private void displayTerritoryInfo(Territory territory, JPanel panel) {
    if (territory != null ) {
        String name = "Territory " + Integer.toString(territory.getId());

        String description;

        if (territory.getOwner() != null) {
            description= territory.getOwner().getName();
        }
        else {
            description = "No player has conquered this territory!";
        }

        String totalUnit = "Total Army Unit: " + Integer.toString(territory.getTotalUnit());





        JLabel label = new JLabel(name);
        JLabel label2 = new JLabel(description);
        JLabel label3 = new JLabel(totalUnit);

        panel.add(label);
        panel.add(label2);
        panel.add(label3);
    }


}



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


public void animation(int territory_id,Integer [] neighborterritorId) { // REQUIRES: Territory_id should be between 0 and 41 && neighborterritorId
    // should have a size of 42. The red line starting from the button territory_id
    // should end to its neighbor buttons territoryId.
    
    
    // EFFECTS: Returns a red line object starting from the button territory_id
    // ends to the buttons index of its neighborterritorId  .

for(int i =0;i<neighborterritorId.length;i++) {


float neighbor_x= buttonHandler.getBuildMode().getCoordinateList().get(neighborterritorId[i]).getX();
float neighbor_y= buttonHandler.getBuildMode().getCoordinateList().get(neighborterritorId[i]).getY();



float distance_neighbor_x = arrow_x - neighbor_x;
float distance_neighbor_y = arrow_y - neighbor_y;

System.out.println("Distance x: " + distance_neighbor_x);
System.out.println("Distance y: " + distance_neighbor_y);

double angle =  distance_neighbor_y/distance_neighbor_x;



double degree =  Math.toDegrees(Math.atan(angle));



double distance_neighbor_x_square = distance_neighbor_x*distance_neighbor_x;
double distance_neighbor_y_square = distance_neighbor_y*distance_neighbor_y;

double distance_overall = (float) Math.sqrt(distance_neighbor_x_square+distance_neighbor_y_square);

System.out.println("Distance x: "+ distance_neighbor_x);
System.out.println("Distance y: "+ distance_neighbor_y);
System.out.println(angle);
System.out.println("Degree: "+ degree);



if(distance_neighbor_y>0 && distance_neighbor_x<0)
degree = degree;
else if(distance_neighbor_y<0 && distance_neighbor_x<0)
degree = degree;
else if(distance_neighbor_y<0 && distance_neighbor_x>0)
degree = degree-180;
else if(distance_neighbor_y>0 && distance_neighbor_x>0)
degree =180+ degree;

System.out.println("Update Degree: "+ degree);
line_width_ends[0][i] += 2;
if(line_width_ends[0][i]>distance_overall)
line_width_ends[0][i] = 0;
System.out.println("Line endss: " + line_width_ends[0][i]);

arrow.draw(g2d, arrow_x, arrow_y, path_height,path_width-arrow_x, line_height, line_width_ends[0][i]+arrow_x,degree);




}

}





public void initGUI() throws IOException {

        image = ImageIO.read(getClass().getResourceAsStream("/images/Map.png"));
        setSize((int) (1.20 * image.getWidth()), image.getHeight());
        mapPanel = new JPanel() {
            transient BufferedImage backgroundImage = image;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, null); // draw the image
            
                g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
                int width = getWidth();
                int height = getHeight();
             
                
                for(int i=0; i<43;i++) {



                    if(i== territory_id) {
            
            
                        line_height = 0.5f;
                        path_height = 0.8f;
            
            
            
                        // switch (territory_id) {
                        //     case 0:
                        //     arrowAnimation a = new arrowAnimation();
            
                        // Integer [] array = new Integer[2];
                        // array[0] = 1;
                        // array[1] = 5;
                        //     // a.Animation(territory_id,array,buttonHandler,arrow_x,arrow_y,line_height,path_width,path_height,g2d,line_width_ends,line_width_neighborTerritories);
                     
                        //     line_width_end+=2;
                        //     a.Animation(territory_id,array[0],null,g2d, line_width_end);
                        //      if(line_width_end> a.getDistance())
                        //      line_width_end = 0;
                        //         break;
            
            
                        //      case 1:
                        //      Integer [] array1 = new Integer[5];
                        //      array1[0] = 0;
                        //      array1[1] = 5;
                        //      array1[2] = 2;
                        //      array1[3] = 4;
                        //      array1[4] = 3;
            
                        //                      animation(territory_id,array1);
            
            
                            
                        //      break;
            
            
                        //      case 2:
            
                        //      Integer [] array2 = new Integer[5];
                        //      array2[0] = 1;
                        //      array2[1] = 4;
                        //      array2[2] = 3;
                        //      array2[3] = 23;
                        //      array2[4] = 22;
                                           
                        //      animation(territory_id,array2);
            
            
                        //      break;
            
            
            
                        //      case 3:
                              
                        //      Integer [] array3 = new Integer[5];
                        //      array3[0] = 2;
                        //      array3[1] = 4;
                        //      array3[2] = 7;
                        //      array3[3] = 22;
                        //      array3[4] = 1;
                                           
                        //      animation(territory_id,array3);
            
            
                        //      break;
            
            
                        //      case 4:
            
                        //      Integer [] array4 = new Integer[6];
                        //         array4[0] = 5;
                        //       array4[1] = 2;
                        //       array4[2] = 7;
                        //       array4[3] = 6;
                        //       array4[4] = 3;
                        //       array4[5] = 1;
            
                                           
                        //      animation(territory_id,array4);
            
                             
                        //      break;
            
                        //      case 5:
            
                        //      Integer [] array5 = new Integer[4];
                        //       array5[0] = 0;
                        //       array5[1] = 1;
                        //       array5[2] = 4;
                        //       array5[3] = 6;
                          
            
                                           
                        //      animation(territory_id,array5);
            
            
                        //      break;
            
                      
                        //      case 6:
            
                        //      Integer [] array6 = new Integer[4];
                        //       array6[0] = 5;
                        //       array6[1] = 4;
                        //       array6[2] = 7;
                        //       array6[3] = 8;
                          
            
                                           
                        //      animation(territory_id,array6);
            
            
                        //      break;
            
                 
                        //      case 7:
            
                        //      Integer [] array7 = new Integer[7];
                        //       array7[0] = 8;
                        //       array7[1] = 6;
                        //       array7[2] = 4;
                        //       array7[3] = 3;
                        //       array7[4] = 9;
                        //       array7[5] = 22;
                        //       array7[6] = 21;
            
            
                                           
                        //      animation(territory_id,array7);
            
            
                        //      break;
            
            
                 
                        //      case 8:
            
                        //      Integer [] array8 = new Integer[4];
                        //       array8[0] = 6;
                        //       array8[1] = 7;
                        //       array8[2] = 9;
                        //       array8[3] = 21;
                             
            
            
                                           
                        //      animation(territory_id,array8);
                             
            
            
                        //      break;
            
                        //      case 9:
            
                        //      Integer [] array9 = new Integer[5];
                        //       array9[0] = 8;
                        //       array9[1] = 21;
                        //       array9[2] = 12;
                        //       array9[3] = 10;
                        //       array9[4] = 7;
            
            
            
                                           
                        //      animation(territory_id,array9);
            
            
                        //      break;
            
            
            
                        //      case 10:
            
                        //      Integer [] array10 = new Integer[3];
                        //       array10[0] = 9;
                        //       array10[1] = 12;
                        //       array10[2] = 11;
                             
                                           
                        //      animation(territory_id,array10);
            
            
                        //      break;
            
                        //      case 11:
            
                        //      Integer [] array11 = new Integer[3];
                        //       array11[0] = 12;
                        //       array11[1] = 10;
                        //       array11[2] = 15;
            
                                     
                        //      animation(territory_id,array11);
            
            
                        //      break;
            
            
            
                        //      case 12:
            
                        //      Integer [] array12 = new Integer[5];
                        //       array12[0] = 9;
                        //       array12[1] = 10;
                        //       array12[2] = 11;
                        //       array12[3] = 13;
                        //       array12[4] = 21;
            
                      
                        //      animation(territory_id,array12);
            
            
                        //      break;
            
                        //      case 13:
            
                        //      Integer [] array13 = new Integer[6];
                        //       array13[0] = 21;
                        //       array13[1] = 20;
                        //       array13[2] = 18;
                        //       array13[3] = 17;
                        //       array13[4] = 12;
                        //       array13[5] = 14;
            
                      
                        //      animation(territory_id,array13);
            
            
                        //      break;
            
                        //      case 14:
            
                        //      Integer [] array14 = new Integer[4];
                        //       array14[0] = 15;
                        //       array14[1] = 17;
                        //       array14[2] = 18;
                        //       array14[3] = 13;
            
                      
                        //      animation(territory_id,array14);
            
            
                        //      break;
            
            
                             
                        //      case 15:
            
                        //      Integer [] array15 = new Integer[5];
                        //       array15[0] = 11;
                        //       array15[1] = 14;
                        //       array15[2] = 15;
                        //       array15[3] = 17;
                        //       array15[4] = 16;
            
            
                      
                        //      animation(territory_id,array15);
            
            
                        //      break;
            
                        //      case 16:
            
                        //      Integer [] array16 = new Integer[5];
                        //       array16[0] = 15;
                        //       array16[1] = 14;
                        //       array16[2] = 17;
                        //       array16[3] = 17;
                        //       array16[4] = 41;
            
            
                      
                        //      animation(territory_id,array16);
            
            
                        //      break;
            
            
                        //      case 17:
            
                        //      Integer [] array17 = new Integer[5];
                        //       array17[0] = 14;
                        //       array17[1] = 13;
                        //       array17[2] = 15;
                        //       array17[3] = 19;
                        //       array17[4] = 18;
                        //       array17[5] = 38;
            
            
                      
                        //      animation(territory_id,array17);
            
            
                        //      break;
            
            
            
                        //      case 18:
            
                        //      Integer [] array18 = new Integer[4];
                        //       array18[0] = 20;
                        //       array18[1] = 13;
                        //       array18[2] = 17;
                        //       array18[3] = 19;
                           
            
            
                      
                        //      animation(territory_id,array18);
            
            
                        //      break;
            
            
                        //      case 19:
            
                        //      Integer [] array19 = new Integer[6];
                        //       array19[0] = 17;
                        //       array19[1] = 18;
                        //       array19[2] = 20;
                        //       array19[3] = 26;
                        //       array19[4] = 28;
                        //       array19[5] = 29;
                           
                     
                        //      animation(territory_id,array19);
            
            
                        //      break;
            
            

                        //      case 20:
            
                        //      Integer [] array20 = new Integer[7];
                        //       array20[0] = 19;
                        //       array20[1] = 18;
                        //       array20[2] = 21;
                        //       array20[3] = 22;
                        //       array20[4] = 25;
                        //       array20[5] = 26;
                        //       array20[6] = 13;
                           
                     
                        //      animation(territory_id,array20);
            
            
                        //      break;



                             
                        //      case 21:
            


                        //      Integer [] array21 = new Integer[6];
                        //      array21[0] = 12;
                        //      array21[1] = 13;
                        //      array21[2] = 20;
                        //      array21[3] = 25;
                        //      array21[4] = 22;
                        //      array21[5] = 7;
                           
                     
                        //      animation(territory_id,array21);
            
            
                        //      break;




                             
                        //      case 22:
            

                        //      Integer [] array22 = new Integer[7];
                        //      array22[0] = 21;
                        //      array22[1] = 25;
                        //      array22[2] = 24;
                        //      array22[3] = 23;
                        //      array22[4] = 2;
                        //      array22[5] = 3;
                        //      array22[6] = 7;
                           
                     
                        //      animation(territory_id,array22);
            
            
                        //      break;



                             
                        //      case 23:
            

                        //      Integer [] array23 = new Integer[5];
                        //      array23[0] = 2;
                        //      array23[1] = 3;
                        //      array23[2] = 22;
                        //      array23[3] = 25;
                        //      array23[4] = 24;

                           
                     
                        //      animation(territory_id,array23);
            
            
                        //      break;


                             
                        //      case 24:
            

                        //      Integer [] array24 = new Integer[4];
                        //      array24[0] = 22;
                        //      array24[1] = 23;
                        //      array24[2] = 25;
                        //      array24[3] = 26;
                             

                           
                     
                        //      animation(territory_id,array24);
            
            
                        //      break;

                             
                        //      case 25:
            

                        //      Integer [] array25 = new Integer[6];
                        //      array25[0] = 20;
                        //      array25[1] = 21;
                        //      array25[2] = 22;
                        //      array25[3] = 23;
                        //      array25[4] = 24;
                        //      array25[5] = 26;
                           
                     
                        //      animation(territory_id,array25);
            
            
                        //      break;

                             
                        //      case 26:


            
                        //      Integer [] array26 = new Integer[6];
                        //      array26[0] = 24;
                        //      array26[1] = 25;
                        //      array26[2] = 20;
                        //      array26[3] = 19;
                        //      array26[4] = 28;
                        //      array26[5] = 27;
                           
                     
                        //      animation(territory_id,array26);
            
            
                        //      break;

                             
                        //      case 27:
            
                        //      Integer [] array27 = new Integer[4];
                        //      array27[0] = 28;
                        //      array27[1] = 26;
                        //      array27[2] = 34;
                        //      array27[3] = 30;
                           
                     
                        //      animation(territory_id,array27);
            
            
                        //      break;

                             
                        //      case 28:


            
                        //      Integer [] array28 = new Integer[5];
                        //      array28[0] = 19;
                        //      array28[1] = 29;
                        //      array28[2] = 30;
                        //      array28[3] = 27;
                        //      array28[4] = 26;

                           
                     
                        //      animation(territory_id,array28);
            
            
                        //      break;

                             
                        //      case 29:


            
                        //      Integer [] array29 = new Integer[6];
                        //      array29[0] = 19;
                        //      array29[1] = 28;
                        //      array29[2] = 30;
                        //      array29[3] = 37;
                        //      array29[4] = 38;
                        //      array29[5] = 17;
                           
                     
                        //      animation(territory_id,array29);
            
            
                        //      break;

                             
                        //      case 30:


            
                        //      Integer [] array30 = new Integer[8];
                        //      array30[0] = 37;
                        //      array30[1] = 29;
                        //      array30[2] = 28;
                        //      array30[3] = 27;
                        //      array30[4] = 34;
                        //      array30[5] = 33;
                        //      array30[6] = 32;
                        //      array30[7] = 31;
                           
                     
                        //      animation(territory_id,array30);
            
            
                        //      break;

                             
                        //      case 31:


            
                        //      Integer [] array31 = new Integer[4];
                        //      array31[0] = 30;
                        //      array31[1] = 32;
                        //      array31[2] = 33;
                        //      array31[3] = 36;
                       
                           
                     
                        //      animation(territory_id,array31);
            
            
                        //      break;

                             
                        //      case 32:


            
                        //      Integer [] array32 = new Integer[5];
                        //      array32[0] = 30;
                        //      array32[1] = 33;
                        //      array32[2] = 31;
                        //      array32[3] = 36;
                        //      array32[4] = 34;
                             
                           
                     
                        //      animation(territory_id,array32);
            
            
                        //      break;

                             
                        //      case 33:


            
                        //      Integer [] array33 = new Integer[4];
                        //      array33[0] = 32;
                        //      array33[1] = 34;
                        //      array33[2] = 35;
                        //      array33[3] = 36;
                       
                           
                     
                        //      animation(territory_id,array33);
            
            
                        //      break;

                             
                        //      case 34:
            


                        //      Integer [] array34 = new Integer[5];
                        //      array34[0] = 33;
                        //      array34[1] = 27;
                        //      array34[2] = 35;
                        //      array34[3] = 30;
                        //      array34[4] = 32;

                           
                           
                     
                        //      animation(territory_id,array34);
            
            
                        //      break;

                             
                        //      case 35:


            
                        //      Integer [] array35 = new Integer[3];
                        //      array35[0] = 33;
                        //      array35[1] = 34;
                        //      array35[2] = 36;
                           
                     
                        //      animation(territory_id,array35);
            
            
                        //      break;

                             
                        //      case 36:


            
                        //      Integer [] array36 = new Integer[4];
                        //      array36[0] = 31;
                        //      array36[1] = 33;
                        //      array36[2] = 35;
                        //      array36[3] = 32;
                            
                           
                     
                        //      animation(territory_id,array36);
            
            
                        //      break;

                             
                        //      case 37:


            
                        //      Integer [] array37 = new Integer[4];
                        //      array37[0] = 29;
                        //      array37[1] = 30;
                        //      array37[2] = 38;
                        //      array37[3] = 39;
                             
                           
                     
                        //      animation(territory_id,array37);
            
            
                        //      break;

                             
                        //      case 38:


            
                        //      Integer [] array38 = new Integer[5];
                        //      array38[0] = 39;
                        //      array38[1] = 40;
                        //      array38[2] = 41;
                        //      array38[3] = 29;
                        //      array38[4] = 37;
                           
                     
                        //      animation(territory_id,array38);
            
            
                        //      break;

                             
                        //      case 39:


            
                        //      Integer [] array39 = new Integer[4];
                        //      array39[0] = 40;
                        //      array39[1] = 41;
                        //      array39[2] = 37;
                        //      array39[3] = 38;
                          
                           
                     
                        //      animation(territory_id,array39);
            
            
                        //      break;

                             
                        //      case 40:


            
                        //      Integer [] array40 = new Integer[3];
                        //      array40[0] = 41;
                        //      array40[1] = 38;
                        //      array40[2] = 39;
                         
                           
                     
                        //      animation(territory_id,array40);
            
            
                        //      break;

                             
                        //      case 41:
            

                        //      Integer [] array41 = new Integer[4];
                        //      array41[0] = 16;
                        //      array41[1] = 38;
                        //      array41[2] = 39;
                        //      array41[3] = 40;
                         
                           
                     
                        //      animation(territory_id,array41);
            
            
                        //      break;

                             
                        //      case 42:
            
                        //      Integer [] array42 = new Integer[6];
                        //      array42[0] = 17;
                        //      array42[1] = 18;
                        //      array42[2] = 20;
                        //      array42[3] = 26;
                        //      array42[4] = 28;
                        //      array42[5] = 29;
                           
                     
                        //      animation(territory_id,array42);
            
            
                        //      break;







                        //     default:
                        //         break;
                        // }
                       
                       System.out.println("Territory: "+ territory_id +"\n X Coordinate: " + arrow_x + "\nY Coordinate: " + arrow_y);        
                    
                    }
                }            
            
            
            
            
            
            }
        };




        mapPanel.setOpaque(false);

        //mapPanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        mapPanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        mapPanel.setLayout(null);



        PlayerInteractionPanel interactionPanel = new PlayerInteractionPanel(buttonHandler, gameHandler);
        mapPanel.add(interactionPanel);


        InfoPanel infoPanel = new InfoPanel();
        infoPanel.setPreferredSize(new Dimension((int) (0.20 * image.getWidth()), image.getHeight()));
        infoPanel.setBackground(Color.lightGray);
        BoxLayout boxLayout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
        infoPanel.setLayout(boxLayout);
        jPanel = new JPanel();
        jPanel2 =  new JPanel();
        jPanel.setPreferredSize(new Dimension((int) 0.2 * image.getWidth(),(int) 0.3 * image.getHeight()));
        jPanel2.setPreferredSize(new Dimension((int) 0.2 * image.getWidth(),(int) 0.3 * image.getHeight()));
        jPanel.setBackground(Color.ORANGE);
        jPanel2.setBackground(Color.GREEN);
        infoPanel.add(jPanel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(jPanel2);
        infoPanel.add(Box.createVerticalGlue());


        playerPanel = new PlayerPanel(buttonHandler);
        mapPanel.add(playerPanel);

        setLayout(new BorderLayout());
        add(mapPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);



    setVisible(true);
    createTerritoryButtons();
    createFunctionalityButtons();
}



Thread animationThread = new Thread(() -> {

    // if(!pauseScreen.isVisible()) threader = true; 
 while (true) {

        mapPanel.repaint();

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
 });


//  public void stop() {
//     threadStarter = false;
//  }

public void createTerritoryButtons() {

    for(int i= 0 ; i<42 ; i++) {

        for(int j= 0 ; j<42 ; j++) {
            line_width_ends[i][j] = 0;
            line_width_neighborTerritories[i][j] = 0.f;
            line_width_end = 0;
}



        int x = buttonHandler.getXFromList(i);
        int y = buttonHandler.getYFromList(i);

        TerritoryButton button = new TerritoryButton(x,y,i);
        button.setBounds(x, y, 40, 40);
        button.setPreferredSize(new Dimension(40, 40));
        territoryButtonsList.add(button);

    
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()== MouseEvent.BUTTON1) {
                    System.out.println("MOUSE CLICKED TO TERRITORY");
               
                
                 
                    try {
                        buttonHandler.matchButtonWithTerritory(button.getID());
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    buttonHandler.selectButton(button);


                    buttonHandler.addToMemory(button.getID());

                    Territory[] memoryTerritory = buttonHandler.getMemoryList();
                    for (Territory t : memoryTerritory) {
                        System.out.println("Hello World");
                        if (t != null) {

                            t.setGraphics(g2d);

                            System.out.println(t.getId());


                            territory_id = t.getId();
                            arrow_x= buttonHandler.getBuildMode().getCoordinateList().get(territory_id).getX();
                            arrow_y= buttonHandler.getBuildMode().getCoordinateList().get(territory_id).getY();
                          line_width =buttonHandler.getBuildMode().getCoordinateList().get(territory_id).getY();
                          path_width = buttonHandler.getBuildMode().getCoordinateList().get(territory_id).getX();

                        //   path_width_end = path_width;
                        //   line_width_end =  0;
                          

                          try {
                                    


                            

                                    animationThread.start();
                                    
             
                 
                } catch (Exception v) {
                    // TODO: handle exception
                }
                                    
                                  
                           

                            






                        }

                        System.out.println("Bye World!");
                    }

                    jPanel.removeAll();
                    jPanel2.removeAll();
                    if (memoryTerritory.length == 1) {

                        Territory t1 = memoryTerritory[1];
                        displayTerritoryInfo(t1, jPanel);
                    }
                    else if (memoryTerritory.length == 2) {
                        Territory t1 = memoryTerritory[1];
                        displayTerritoryInfo(t1, jPanel);
                        Territory t2 = memoryTerritory[0];
                        displayTerritoryInfo(t2, jPanel2);

                    }

                    jPanel.revalidate();
                    jPanel.repaint();
                    jPanel2.revalidate();
                    jPanel2.repaint();







                }
                else if (e.getButton() == MouseEvent.BUTTON3) {
                  ButtonHandler bHandler = HandlerFactory.getInstance().giveButtonHandler();

                    for (TerritoryButton b: buttonHistory) {

                        bHandler.resetColorOfTerritoryButton(b);
                        }
                    buttonHistory.clear();

                }


            }

        });
        mapPanel.setLayout(null); // switch to null layout manager
        mapPanel.add(button);



    }

}

public void createFunctionalityButtons() {

        pauseAndResumeButton = new JButton("Pause/Resume");
        pauseAndResumeButton.setBounds(buttonHandler.getXFromList(42), buttonHandler.getYFromList(42), 130, 40);
        helpButton = new JButton("Help");
        helpButton.setBounds(buttonHandler.getXFromList(43), buttonHandler.getYFromList(43), 80, 40);
/*
       rollButton = new JButton("Roll");
        rollButton.setBounds(buttonHandler.getXFromList(44), buttonHandler.getYFromList(44), 80, 80);
        executeButton = new JButton("Remove");
        executeButton.setBounds(buttonHandler.getXFromList(45), buttonHandler.getYFromList(45), 80, 80);
        nextButton = new JButton("Next");
        nextButton.setBounds(buttonHandler.getXFromList(46), buttonHandler.getYFromList(46), 80, 80);
        mapPanel.setLayout(null); // switch to null layout manager
*/
        mapPanel.add(pauseAndResumeButton);
        mapPanel.add(helpButton);
       // mapPanel.add(rollButton);
        //mapPanel.add(executeButton);
        //mapPanel.add(nextButton);



}

@Override
public void removeOnboardEvent(TerritoryButton button) {
    // TODO Auto-generated method stub

    System.out.println("Remove executed");
    button.setVisible(false); // set the visibility of the button to false
    revalidate(); // revalidate the frame to update the layout
    repaint();}

    @Override
    public void getButtonList(List<Integer> neigborIdsList) {
        // TODO Auto-generated method stub


        for (int i = 0; i < neigborIdsList.size(); i++) {
            TerritoryButton button = territoryButtonsList.get(neigborIdsList.get(i));
            button.changeColor();
            buttonHistory.add(button);

        };

        revalidate();
        repaint();
        for (int i = 0; i < neigborIdsList.size(); i++) {

            // Print all elements of List
            System.out.println(neigborIdsList.get(i));
        }


    }

    @Override
    public void getRollEvent(String message) {
        // TODO Auto-generated method stub
        JOptionPane.showMessageDialog(MapView.this, message);
        updatePlayerPanel();


    }

    public void updatePlayerPanel() {
        playerPanel.clearPlayerInfos();
        playerPanel.revalidate();
        playerPanel.repaint();

        mapPanel.revalidate();
        mapPanel.repaint();

    }
    
    private class ResumeButtonHandler implements ActionListener {

        private List<Player> playerList;
        private MapView mapView;
    
        public ResumeButtonHandler(MapView mapView) {  // Modified constructor
            this.mapView = mapView;
       
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
           // String turn = PlayerExpert.getPlayerInTurn().getName();
    
    
    
           
    
            this.playerList = PlayerExpert.getPlayersList();
        
    
           PauseScreen pauseScreen = new PauseScreen(frame, playerList, gameHandler);       
    
    
    
        threadStarter = 1;
           
    
       
    
    
            
    
    
    
           
            
         
        }
    
    
    
    }
    


private class PauseButtonHandler implements ActionListener {

    private List<Player> playerList;
    private MapView mapView;

    public PauseButtonHandler(MapView mapView) {  // Modified constructor
        this.mapView = mapView;
   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
       // String turn = PlayerExpert.getPlayerInTurn().getName();



       

        this.playerList = PlayerExpert.getPlayersList();
    

       PauseScreen pauseScreen = new PauseScreen(frame, playerList, gameHandler);       



    threadStarter = 0;
       

        pauseScreen.setVisible(true);

        

threadStarter = 0;
        if(pauseScreen.isVisible() == false) threadStarter = 1;


       
        
     
    }



}


private class HelpButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        new HelpScreen();
        
    }

}
/*


        //TESTING

         System.out.println("Territory List after removal");
         Map<Integer, Territory> territoryMap  =buttonHandler.getBoard().getTerritories();
        int counter = 0;


         for (Map.Entry<Integer, Territory> entry : territoryMap.entrySet()) {
            Integer id = entry.getKey();
            Territory territory = entry.getValue();
           // System.out.println("Key=" + id + ", Value=" + territory.getId());
            Map<Integer,Territory> adjList = entry.getValue().getAdjacencyList() ;

              System.out.println("This is the " +counter+". territory");
            System.out.println(adjList);
            counter++;

        }

        System.out.println("Player list");


        for (Player p :    buttonHandler.getBuildMode().getPlayers())

         {
            System.out.println(p.getName());
            System.out.println(p.getInventory().getTotalArmy());
        }
        */
      

@Override
public void setTerritoryButtonInfo(int buttonId,int armyUnit, Color color,int territoryArmy) {

    System.out.print("ŞU AN TESTTEYİM");
    TerritoryButton button = territoryButtonsList.get(buttonId);
    
    button.setColor(color);
     Font labelFont = new Font("Arial", Font.PLAIN, 11);
     button.setFont(labelFont);
     button.setArmyValue(territoryArmy);
     System.out.print(territoryArmy);

     System.out.println(armyUnit);
     revalidate();
     repaint();


}




//attack sonrası map updatei olacak
@Override
public void setArmyCount(int armyCount) {
    // TODO Auto-generated method stub

}

@Override
public void updateTerritory(int buttonID, int deployedArmy) {
    // TODO Auto-generated method stub
    TerritoryButton button = territoryButtonsList.get(buttonID);
    button.setArmyValue(deployedArmy);





}












}
