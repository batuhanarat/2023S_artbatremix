    package src.ConKUeror.UI.Frames;

    import java.awt.BasicStroke;
import java.awt.BorderLayout;
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.Font;
    import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.awt.image.BufferedImage;
    import java.io.IOException;
    import java.util.Map;

    import javax.imageio.ImageIO;
import javax.lang.model.util.ElementScanner14;
import javax.sound.midi.MidiDevice.Info;
    import javax.swing.Box;
    import javax.swing.BoxLayout;
    import javax.swing.JButton;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JOptionPane;
    import javax.swing.JPanel;
import javax.swing.border.Border;

import src.ConKUeror.UI.Buttons.TerritoryButton;
import src.ConKUeror.UI.Frames.ArrowAnimation.Animation;
import src.ConKUeror.UI.Frames.ArrowAnimation.ArrowMovementThread;
import src.ConKUeror.UI.Frames.ArrowAnimation.CurvedArrow;
import src.ConKUeror.UI.HelpScreen.HelpScreen;
    import src.ConKUeror.UI.Panels.InfoPanel;
    import src.ConKUeror.UI.Panels.PlayerInteractionPanel;
    import src.ConKUeror.UI.Panels.PlayerPanel;
    import src.ConKUeror.UI.PauseScreen.PauseScreen;
    import src.ConKUeror.domain.controller.ButtonHandler;
    import src.ConKUeror.domain.controller.GameHandler;
    import src.ConKUeror.domain.controller.HandlerFactory;
    import src.ConKUeror.domain.controller.MapHandler;
    import src.ConKUeror.domain.controller.MapListener;
    import src.ConKUeror.domain.controller.RollDieListener;
    import src.ConKUeror.domain.controller.StartHandler;
    import src.ConKUeror.domain.controller.TerritoryButtonListener;
    import src.ConKUeror.domain.model.Board.Territory;

    import java.util.ArrayList;
    import java.util.List;

   

    public class MapView extends  JFrame implements MapListener ,TerritoryButtonListener,RollDieListener, Runnable {

        public JPanel mapPanel;
        MapHandler mapHandler;
        ButtonHandler buttonHandler;
        StartHandler startHandler;
        GameHandler gameHandler;
        PlayerPanel playerPanel;

        private float arrow_x;
        private float arrow_y;
        Graphics2D g2d;
        float path_width;
        float path_height;
        float line_height;
        float line_width;
        float path_width_end;
        float path_height_end;
        float line_height_end;
        float line_width_end;
        double line_width_1_2;

        double angle = 0;

        JPanel jPanel = new JPanel();
        JPanel jPanel2 =  new JPanel();
        JPanel jPanel3;

        JButton pauseAndResumeButton;
        JButton helpButton;
        JButton inventoryButton;

        JButton rollButton;
        JButton executeButton;
        JButton nextButton;
        TerritoryButton selectedButton;
        List<TerritoryButton> territoryButtonsList = new ArrayList<TerritoryButton>();
        Boolean selected = false;
        List<TerritoryButton> buttonHistory = new ArrayList<TerritoryButton>();

        Integer[][]  line_width_ends = new Integer[43][43];
        Float[][] line_width_neighborTerritories = new Float[43][43];
        // Integer [] territory_neighbors = new Integer [];

        List<Integer> territory_Y_list = new ArrayList<Integer>();

        public BufferedImage image;
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


        pauseAndResumeButton.addActionListener(new PauseButtonHandler());
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

    }

    public void addMapFrameAsListenerForRollEvent(){
        buttonHandler.registerAsRollListener(this);
    }

    public void setPanels() {
    /// may be used.
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

private float line_scale;

private int territory_id = -1;
private CurvedArrow arrow = new CurvedArrow();


public void animation(int territory_id,Integer [] neighborterritorId) {


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

    arrow.draw(g2d, arrow_x, arrow_y, path_height,path_width-arrow_x, line_height, line_width_ends[0][i]+arrow_x,degree);




    }

}

    public void initGUI() throws IOException {

        image = ImageIO.read(getClass().getResourceAsStream("/src/images/Map.png"));
        setSize((int) (1.20 * image.getWidth()), image.getHeight());
      
    

      
        mapPanel = new JPanel() {
            BufferedImage backgroundImage = image;
            

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



            switch (territory_id) {
                case 0:

Integer [] array = new Integer[2];
array[0] = 1;
array[1] = 5;
                animation(territory_id,array);


            //     float arrow_x_0 = arrow_x;
            //     float arrow_y_0 = arrow_y;
            //     float arrow_x_1= buttonHandler.getBuildMode().getCoordinateList().get(1).getX();
            //     float arrow_y_1= buttonHandler.getBuildMode().getCoordinateList().get(1).getY();
              
                
            //     // path_height_end = path_height;
            //     // line_height_end = line_height;
            //     // path_height_end = 0.8f;
            //     // path_width_end = 60;
            //     // line_height_end = 0.5f;
            //     // line_width_end = arrow_x +86.28f;

            //     double angle_1 = (Math.abs(arrow_y_1 - arrow_y_0)/Math.abs(arrow_x_1-arrow_x_0));
            //     arrow.draw(g2d, arrow_x_0, arrow_y_0, path_height,path_width, line_height, arrow_x_0+line_width_ends[0][1], angle_1);
            //     System.out.println("Path_width: " + path_width_end);
            //     System.out.println("Line_width: " + line_width_end+ "\n");

                
            //     int arrow_x_5 = buttonHandler.getBuildMode().getCoordinateList().get(5).getX();
            //     int arrow_y_5= buttonHandler.getBuildMode().getCoordinateList().get(5).getY();
           
              

            //    double angle_5 = (double)(arrow_y_5 -arrow_y_0)/(double) (arrow_x_5- arrow_x_0); 

            //     double degree_5 = Math.toDegrees(Math.atan(angle_5));

                

                
               
            //     arrow.draw(g2d, arrow_x_0, arrow_y_0, path_height, path_width,line_height, arrow_x_0+line_width_ends[0][5], degree_5);



            //     // arrow.draw(g2d, arrow_x, arrow_y, path_height, path_width, line_height, line_width);
                

                    break;






                 case 1:
                 Integer [] array1 = new Integer[5];
                 array1[0] = 0;
                 array1[1] = 5;
                 array1[2] = 2;
                 array1[3] = 4;
                 array1[4] = 3;

                                 animation(territory_id,array1);


    
            //           double arrow_x_2= buttonHandler.getBuildMode().getCoordinateList().get(2).getX();
            //           double arrow_y_2= buttonHandler.getBuildMode().getCoordinateList().get(2).getY();
                      
            //           double arrow_y1_y2 = (arrow_y_1-arrow_y_2);
            //           double arrow_x1_x2 = (arrow_x_1-arrow_x_2);

            //           System.out.println("Arrow_y1_y2: " +arrow_y1_y2 );
            //           System.out.println("Arrow_x1_x2: "+ arrow_x1_x2 );

            //         double angle_2 = (double) Math.abs(arrow_y_1 - arrow_y_2)/Math.abs(arrow_x_1-arrow_x_2);

                    
            //          double degree_2 = Math.toDegrees(Math.atan(angle_2));


            //         System.out.println("Angle_2: "+ angle_2);
            //         System.out.println("Degree_2: "+ degree_2);
                        
            //         double arrow_y1_y2_square = arrow_y1_y2*arrow_y1_y2;
            //         double arrow_x1_x2_square = arrow_x1_x2*arrow_x1_x2;

            // // line_width = (float) Math.sqrt(arrow_x1_x2_square+arrow_y1_y2_square);





            //          if(arrow_y1_y2>0 && arrow_x1_x2<0)
            //          degree_2 = 360-degree_2;
            //          else if(arrow_y1_y2<0 && arrow_x1_x2<0)
            //          degree_2 = 270 -degree_2;
            //          else if(arrow_y1_y2<0 && arrow_x1_x2>0)
            //          degree_2 = 360 - degree_2;
                     

            //          //  line_width =(float) (Math.sqrt(arrow_y1_y2*arrow_y1_y2 + arrow_x1_x2*arrow_x1_x2));
                      
                    
            //           arrow.draw(g2d, arrow_x_1, arrow_y_1, path_height,path_width-arrow_x_0, line_height, line_width_ends[1][2]+arrow_x_1,degree_2);

    

                    //  arrow.draw(g2d, arrow_x_1, arrow_y_1, path_height,path_width-arrow_x_0, line_height, line_width_end+arrow_x_1,270);




                
                 break;


                 case 2:

                 Integer [] array2 = new Integer[5];
                 array2[0] = 1;
                 array2[1] = 4;
                 array2[2] = 3;
                 array2[3] = 23;
                 array2[4] = 22;
                               
                 animation(territory_id,array2);


                 break;



                 case 3:
                  
                 Integer [] array3 = new Integer[4];
                 array3[0] = 2;
                 array3[1] = 4;
                 array3[2] = 7;
                 array3[3] = 22;
                               
                 animation(territory_id,array3);


                 break;


                 case 4:

                 Integer [] array4 = new Integer[6];
                 array4[0] = 5;
                  array4[1] = 2;
                  array4[2] = 7;
                  array4[3] = 6;
                  array4[4] = 3;
                  array4[5] = 1;

                               
                 animation(territory_id,array4);

                 
                 break;

                 case 5:

                 Integer [] array5 = new Integer[4];
                  array5[0] = 0;
                  array5[1] = 1;
                  array5[2] = 4;
                  array5[3] = 6;
              

                               
                 animation(territory_id,array5);


                 break;




                 
                 case 6:

                 Integer [] array6 = new Integer[4];
                  array6[0] = 5;
                  array6[1] = 4;
                  array6[2] = 7;
                  array6[3] = 8;
              

                               
                 animation(territory_id,array6);


                 break;

     
                 case 7:

                 Integer [] array7 = new Integer[7];
                  array7[0] = 8;
                  array7[1] = 6;
                  array7[2] = 4;
                  array7[3] = 3;
                  array7[4] = 9;
                  array7[5] = 22;
                  array7[6] = 21;


                               
                 animation(territory_id,array7);


                 break;


     
                 case 8:

                 Integer [] array8 = new Integer[4];
                  array8[0] = 6;
                  array8[1] = 7;
                  array8[2] = 9;
                  array8[3] = 21;
                 


                               
                 animation(territory_id,array8);


                 break;

                 case 9:

                 Integer [] array9 = new Integer[5];
                  array9[0] = 8;
                  array9[1] = 21;
                  array9[2] = 12;
                  array9[3] = 10;
                  array9[4] = 7;



                               
                 animation(territory_id,array9);


                 break;



                 case 10:

                 Integer [] array10 = new Integer[3];
                  array10[0] = 9;
                  array10[1] = 12;
                  array10[2] = 11;
                 


                               
                 animation(territory_id,array10);


                 break;

                 case 11:

                 Integer [] array11 = new Integer[3];
                  array11[0] = 12;
                  array11[1] = 10;
                  array11[2] = 15;



                               
                 animation(territory_id,array11);


                 break;



                 case 12:

                 Integer [] array12 = new Integer[5];
                  array12[0] = 9;
                  array12[1] = 10;
                  array12[2] = 11;
                  array12[3] = 13;
                  array12[4] = 21;

          
                 animation(territory_id,array12);


                 break;

                 case 13:

                 Integer [] array13 = new Integer[6];
                  array13[0] = 21;
                  array13[1] = 20;
                  array13[2] = 18;
                  array13[3] = 17;
                  array13[4] = 12;
                  array13[5] = 14;

          
                 animation(territory_id,array13);


                 break;

                 case 14:

                 Integer [] array14 = new Integer[4];
                  array14[0] = 15;
                  array14[1] = 17;
                  array14[2] = 18;
                  array14[3] = 13;

          
                 animation(territory_id,array14);


                 break;


                 
                 case 15:

                 Integer [] array15 = new Integer[5];
                  array15[0] = 11;
                  array15[1] = 14;
                  array15[2] = 15;
                  array15[3] = 17;
                  array15[4] = 16;


          
                 animation(territory_id,array15);


                 break;

                 case 16:

                 Integer [] array16 = new Integer[5];
                  array16[0] = 15;
                  array16[1] = 14;
                  array16[2] = 17;
                  array16[3] = 17;
                  array16[4] = 41;


          
                 animation(territory_id,array16);


                 break;


                 case 17:

                 Integer [] array17 = new Integer[5];
                  array17[0] = 14;
                  array17[1] = 13;
                  array17[2] = 15;
                  array17[3] = 19;
                  array17[4] = 18;


          
                 animation(territory_id,array17);


                 break;



                 case 18:

                 Integer [] array18 = new Integer[4];
                  array18[0] = 20;
                  array18[1] = 13;
                  array18[2] = 17;
                  array18[3] = 19;
               


          
                 animation(territory_id,array18);


                 break;










                default:
                    break;
            }
           
            // arrow.draw(g2d, arrow_x, arrow_y, path_height, path_width, line_height, line_width);
           System.out.println("Territory: "+ territory_id +"\n X Coordinate: " + arrow_x + "\nY Coordinate: " + arrow_y);        
        
        }
    }

//  switch (territory_id) {
//     case 0:
//     path_height = 0.8f;
//     path_width = 60;
//     line_height = 0.5f;
//     line_width = arrow_x +86.28f;
//     arrow.draw(g2d, arrow_x, arrow_y, path_height, path_width, line_height, line_width);

//         //  arrow.rotateAngle(angle);
//     //  ArrowMovementThread thread = new ArrowMovementThread(arrow_x,arrow);
//     //  thread.start();
//         // arrow.rotateAngle(angle);
//         // arrow.draw((Graphics2D)g, arrow_x, arrow_y, path_height, path_width, line_height, line_width);


//         // arrow.draw((Graphics2D) g, arrow_x, arrow_y,0.8f,50,0.5f,arrow_x+86.28f);
//         break;
        
//         case 1:
//         path_height = 0.8f;
//     path_width = 60;
//     line_height = 0.5f;
//     line_width = arrow_x +86.28f;
//         arrow.draw((Graphics2D) g, arrow_x, arrow_y,path_height, path_width, line_height, line_width);

//         // arrow.rotateAngle(180);
//         // arrow.draw((Graphics2D) g, arrow_x, arrow_y,0.8f,90,0.5f,arrow_x+56.28f);
    
//         // arrow.rotateAngle(45);
//         // arrow.draw((Graphics2D) g, arrow_x+10, arrow_y,0.8f,50,0.5f,arrow_x+106.28f);
        
//         // arrow.rotateAngle(80);
//         // arrow.draw((Graphics2D) g, arrow_x+10, arrow_y,0.5f,100,0.5f,arrow_x+50f);

//         break;

//         case 2: 

//         path_height = 0.8f;
//         path_width = 60;
//         line_height = 0.5f;
//         line_width = arrow_x +86.28f;
//             arrow.draw((Graphics2D) g, arrow_x, arrow_y,path_height, path_width, line_height, line_width);
//             break;


//         // arrow.rotateAngle(35);
//         // arrow.draw((Graphics2D) g, arrow_x+10, arrow_y,0.8f,50,0.5f,arrow_x+106.28f);
       
//         // arrow.rotateAngle(120);
//         // arrow.draw((Graphics2D) g, arrow_x+10, arrow_y,0.8f,50,0.5f,arrow_x+106.28f);
       
//         // arrow.rotateAngle(140);
//         // arrow.draw((Graphics2D) g, arrow_x+10, arrow_y,0.8f,20,0.5f,arrow_x+146.28f);
       

//         case 3: 

//         path_height = 0.8f;
//         path_width = 60;
//         line_height = 0.5f;
//         line_width = arrow_x +86.28f;
//             arrow.draw((Graphics2D) g, arrow_x, arrow_y,path_height, path_width, line_height, line_width);
//             break;


//             case 4: 

//             path_height = 0.8f;
//             path_width = 60;
//             line_height = 0.5f;
//             line_width = arrow_x +86.28f;
//                 arrow.draw((Graphics2D) g, arrow_x, arrow_y,path_height, path_width, line_height, line_width);
//                 break;




//         default:


//  }
        
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
        jPanel3 =  new JPanel();
        jPanel.setPreferredSize(new Dimension((int) 0.2 * image.getWidth(),(int) 0.3 * image.getHeight()));
        jPanel2.setPreferredSize(new Dimension((int) 0.2 * image.getWidth(),(int) 0.3 * image.getHeight()));
        jPanel.setBackground(Color.ORANGE);
        jPanel2.setBackground(Color.GREEN);
        inventoryButton = new JButton("Inventory");
         inventoryButton.setPreferredSize(new Dimension((int) 0.2 * image.getWidth(),(int) 0.3 * image.getHeight()));
        jPanel3.setPreferredSize(new Dimension((int) 0.2 * image.getWidth(),(int) 0.2 * image.getHeight()));
      //   jPanel3.setLayout(new BorderLayout());
        //jPanel3.add(inventoryButton, BorderLayout.CENTER);
        jPanel3.setBackground(Color.RED);
      //  inventoryButton.setBackground(Color.RED);
        
        infoPanel.add(jPanel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(jPanel2);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(jPanel3);

        inventoryButton.setVisible(true);
        jPanel3.add(inventoryButton);

        infoPanel.add(Box.createVerticalGlue());


            playerPanel = new PlayerPanel(buttonHandler);
            mapPanel.add(playerPanel);

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
        while (true) {

//             for (int i=0;i<43;i++){

//                 for(int j =0;j<43&& i!=j;j++) {

//                     // if(i == j || i!= territory_id) break;



//                     if(i == territory_id && i!=j) {
//                         line_width_ends[i][j] +=2;
            
                        
//             if(line_width_ends[i][j]>line_width_neighborTerritories[i][j])
//             line_width_ends[i][j] = 0;
            



//                 }

//             }

//         }
            

// System.out.println("LineWidth: " + line_width_end);






// if(line_width_end>68)
// line_width_end = line_width;
// System.out.println("Pathwidth: " + path_width);
// System.out.println("Pathwidth: " + line_width);





            

            
            mapPanel.repaint();
            try {
                Thread.sleep(60);
             } catch (InterruptedException e) {
                 e.printStackTrace();
            }
        }
     });
    
    @Override
    public void run() {
      
// while (true) {
    

    // try {
    //     Thread.sleep(10); // Adjust the delay between movements as needed
    // } catch (InterruptedException e) {
    //      e.printStackTrace();
    //  }

// }        // Create and start the arrow movement thread
        //  ArrowMovementThread movementThread = new ArrowMovementThread(arrow_x,this);
        
     
 
    
    }
    


    public void createTerritoryButtons() {

      
        for(int i= 0 ; i<42 ; i++) {

for(int j= 0 ; j<42 ; j++) {
            line_width_ends[i][j] = 0;
            line_width_neighborTerritories[i][j] = 0.f;
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
                    
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        System.out.println("MOUSE CLICKED TO TERRITORY");
                        buttonHandler.matchButtonWithTerritory(button.getID());
                        buttonHandler.selectButton(button);
            
                        buttonHandler.addToMemory(button.getID());
            
                        Territory[] memoryTerritory = buttonHandler.getMemoryList();
                        for (Territory t : memoryTerritory) {
                            if (t != null) {
                                System.out.println(t.getId());
                                territory_id = t.getId();
                                arrow_x= buttonHandler.getBuildMode().getCoordinateList().get(territory_id).getX();
                                arrow_y= buttonHandler.getBuildMode().getCoordinateList().get(territory_id).getY();
                              line_width =buttonHandler.getBuildMode().getCoordinateList().get(territory_id).getY();
                              path_width = buttonHandler.getBuildMode().getCoordinateList().get(territory_id).getX();
                                 
                            // path_width = buttonHandler.getBuildMode().getCoordinateList().get(0).getX();

                            path_width_end = path_width;
                            line_width_end =  0;
                                  try {
                                      animationThread.start();

                                  } catch (Exception b) {
                                       // TODO: handle exception
                                   }
                              
                                    










                                
              
                                mapPanel.repaint();
                               
                             
                            }
                        }
            
                        jPanel.removeAll();
                        jPanel2.removeAll();
                        if (memoryTerritory.length == 1) {
                            Territory t1 = memoryTerritory[1];
                            displayTerritoryInfo(t1, jPanel);
                        } else if (memoryTerritory.length == 2) {
                            Territory t1 = memoryTerritory[1];
                            displayTerritoryInfo(t1, jPanel);
                            Territory t2 = memoryTerritory[0];
                            displayTerritoryInfo(t2, jPanel2);
                        }
            
                        jPanel.revalidate();
                        jPanel.repaint();
                        jPanel2.revalidate();
                        jPanel2.repaint();
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        for (TerritoryButton b : buttonHistory) {
                            b.resetColor();
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
            mapPanel.add(inventoryButton);
        // mapPanel.add(rollButton);
            //mapPanel.add(executeButton);
            //mapPanel.add(nextButton);



    }

    @Override
    public void removeOnboardEvent(TerritoryButton button) {
        // TODO Auto-generated method stub

        System.out.println("Remove executed");
        button.setVisible(false); // set the visibility of the button xto false
        revalidate(); // revalidate the frame to update the layout
        repaint();}

        @Override
        public void getButtonList(List<Integer> neigborIdsList) {
            // TODO Auto-generated method stub

            System.out.println("Map View classına kadar gelen bir connection methodu var");

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




    private class PauseButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            PauseScreen pauseScreen = new PauseScreen(frame);
            pauseScreen.setVisible(true);
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
