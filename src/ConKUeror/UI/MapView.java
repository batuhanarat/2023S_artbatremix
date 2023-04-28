package src.ConKUeror.UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import src.ConKUeror.domain.controller.ButtonHandler;
import src.ConKUeror.domain.controller.MapHandler;
import src.ConKUeror.domain.controller.MapListener;
import src.ConKUeror.domain.controller.StartHandler;
import src.ConKUeror.domain.controller.TerritoryButtonListener;
import src.ConKUeror.domain.model.Board.Territory;

import java.util.ArrayList;
import java.util.List;


public class MapView extends JFrame implements MapListener , TerritoryButtonListener{

    MapHandler mapHandler;
    ButtonHandler buttonHandler;
    StartHandler startHandler;

    JButton pauseAndResumeButton;
    JButton helpButton; 

    JButton rollButton; 
    JButton executeButton;
    JButton nextButton; 
    TerritoryButton selectedButton;
    List<TerritoryButton> territoryButtonsList = new ArrayList<TerritoryButton>();
    Boolean selected = false;


    public BufferedImage image;
    JPanel mapPanel;
    String armyNum =String.valueOf(0);
    Boolean disable = false;
    int disable_Num = 0;

    


public MapView(MapHandler _mapHandler, ButtonHandler _buttonHandler,StartHandler _startHandler) throws IOException {

    this.mapHandler = _mapHandler;
    this.buttonHandler = _buttonHandler;
    this.startHandler = _startHandler;

    

    initGUI();


    String openingMessage = startHandler.enterMessageString();
    DialogBox box = new DialogBox(openingMessage,"Select territories" );
    addMapFrameAsListener();
    addMapFrameAsListenertoListenTerrittoryButtonInteraction();


    pauseAndResumeButton.addActionListener(new PauseButtonHandler());
    helpButton.addActionListener(new HelpButtonHandler());
    rollButton.addActionListener(new RollButtonHandler());
    executeButton.addActionListener(new ExecuteButtonHandler());
    nextButton.addActionListener(new NextButtonHandler());


}

public void occupyTerritory() {
    
}

public void addMapFrameAsListener() {
    mapHandler.registerAsListener(this);

}

public void addMapFrameAsListenertoListenTerrittoryButtonInteraction() {
    buttonHandler.registerAsListener(this);

}





public void initGUI() throws IOException {

    image = ImageIO.read(getClass().getResourceAsStream("/src/images/Map.png"));
    setSize(image.getWidth(), image.getHeight());
    mapPanel = new JPanel() {
        BufferedImage backgroundImage = image;
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, null); // draw the image
        }
    };
    mapPanel.setOpaque(false);
 
    //mapPanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    mapPanel.setBounds(0,0,image.getWidth(),image.getHeight());
    mapPanel.setLayout(null); // switch to null layout manager

    
    add(mapPanel);
    setResizable(false);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); 
    setLayout(null);

    
    PlayerPanel playerPanel = new PlayerPanel(buttonHandler);
    mapPanel.add(playerPanel);

    setVisible(true);
    createTerritoryButtons();
    createFunctionalityButtons();

} 


public void createTerritoryButtons() {

    for(int i= 0 ; i<42 ; i++) {

        int x = buttonHandler.getXFromList(i);
        int y = buttonHandler.getYFromList(i);

        TerritoryButton button = new TerritoryButton(x,y,i);
        button.setBounds(x, y, 40, 40);
        button.setPreferredSize(new Dimension(40, 40)); 
        territoryButtonsList.add(button);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                 /*if(!selected) {
                    buttonHandler.matchButtonWithTerritory(button.getID());
                    selected = flip(selected);
                 } */{

                 }
                buttonHandler.matchButtonWithTerritory(button.getID());


                //buttonHandler.checkButtonforRemoval(button) ;

                
                
            }
            
        });
        mapPanel.setLayout(null); // switch to null layout manager
        mapPanel.add(button);



    }

}

public Boolean flip(Boolean b) {
    if (b) {
        return false;

    } else {
        return true;
    }
    
    


} 

public void createFunctionalityButtons() {

        pauseAndResumeButton = new JButton("Pause/Resume");
        pauseAndResumeButton.setBounds(buttonHandler.getXFromList(42), buttonHandler.getYFromList(42), 130, 40);
        helpButton = new JButton("Help");
        helpButton.setBounds(buttonHandler.getXFromList(43), buttonHandler.getYFromList(43), 80, 40);
        rollButton = new JButton("Roll");
        rollButton.setBounds(buttonHandler.getXFromList(44), buttonHandler.getYFromList(44), 80, 80);
        executeButton = new JButton("Remove");
        executeButton.setBounds(buttonHandler.getXFromList(45), buttonHandler.getYFromList(45), 80, 80);
        nextButton = new JButton("Next");
        nextButton.setBounds(buttonHandler.getXFromList(46), buttonHandler.getYFromList(46), 80, 80);
        mapPanel.setLayout(null); // switch to null layout manager

        mapPanel.add(pauseAndResumeButton);
        mapPanel.add(helpButton);
        mapPanel.add(rollButton);
        mapPanel.add(executeButton);
        mapPanel.add(nextButton);


    
}

@Override
public void onBoardEvent(TerritoryButton button) {
    // TODO Auto-generated method stub

    System.out.println("Remove executed");
    button.setVisible(false); // set the visibility of the button to false
    revalidate(); // revalidate the frame to update the layout
    repaint();}

    @Override
    public void getButtonList(List<Integer> neigborIdsList) {
        // TODO Auto-generated method stub
    
        System.out.println("Map View classına kadar gelen bir connection methodu var");

        for (int i = 0; i < neigborIdsList.size(); i++) {
            territoryButtonsList.get(neigborIdsList.get(i)).changeColor();

        };
        revalidate(); // revalidate the frame to update the layout
        repaint();
            // buttonHandler.getButton( neigborIdsList.get(i), f)
            // Printing and display the elements in ArrayList
            // System.out.print(numbers.get(i) + " ");       


       // button.setVisible(false); // set the visibility of the button to false
        //revalidate(); // revalidate the frame to update the layout
        //repaint();
    
    
    }


    

private class PauseButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }


    
}

private class HelpButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}

private class RollButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}

private class ExecuteButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
        buttonHandler.executeButton();

    }
    
}

private class NextButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
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

        /* 
        for (Player p :    buttonHandler.getBuildMode().getPlayers())

         {
            System.out.println(p.getName());
            System.out.println(p.getInventory().getTotalArmy());
        }
        */

    }
    
}







}
