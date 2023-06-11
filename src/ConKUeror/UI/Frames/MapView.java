package ConKUeror.UI.Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import ConKUeror.UI.HelpScreen.HelpScreen;
import ConKUeror.UI.Panels.InfoPanel;
import ConKUeror.UI.Panels.PlayerInteractionPanel;
import ConKUeror.UI.Panels.PlayerPanel;
import ConKUeror.UI.PauseScreen.PauseScreen;
import ConKUeror.domain.controller.ButtonHandler;
import ConKUeror.domain.controller.GameHandler;
import ConKUeror.domain.controller.HandlerFactory;
import ConKUeror.domain.controller.IUIRefreshListener;
import ConKUeror.domain.controller.MapHandler;
import ConKUeror.domain.controller.MapListener;
import ConKUeror.domain.controller.RollDieListener;
import ConKUeror.domain.controller.StartHandler;
import ConKUeror.domain.controller.TerritoryButtonListener;
import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Data.GameState;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerExpert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapView extends JFrame implements MapListener ,TerritoryButtonListener,RollDieListener,IUIRefreshListener{

    MapHandler mapHandler;
    ButtonHandler buttonHandler;
    StartHandler startHandler;
    GameHandler gameHandler;
    PlayerPanel playerPanel;
    JPanel jPanel = new JPanel();
    JPanel jPanel2 =  new JPanel();

   public static Graphics2D g2d;

   public static JPanel mapPanell;
    JButton pauseAndResumeButton;
    JButton helpButton;
    GameState gameState;
    List<Player> playerList;

    JButton rollButton;
    JButton executeButton;
    JButton nextButton;
    TerritoryButton selectedButton;
    private static Map<Integer, TerritoryButton> territoryButtonsList= new HashMap<>();

    Boolean selected = false;
    List<TerritoryButton> buttonHistory = new ArrayList<TerritoryButton>();


    public BufferedImage image;
   public JPanel mapPanel;
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





public void initGUI() throws IOException {

        image = ImageIO.read(getClass().getResourceAsStream("/images/Map.png"));
        setSize((int) (1.20 * image.getWidth()), image.getHeight());
        mapPanel = new JPanel() {
            BufferedImage backgroundImage = image;

            @Override
            public void paintComponent(Graphics g) {
                 g2d =  (Graphics2D) g;
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, null); // draw the image
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
        
        mapPanell = mapPanel;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);



    setVisible(true);
    createTerritoryButtons();
    createFunctionalityButtons();

}


public void createTerritoryButtons() {

    for(int i= 0 ; i<42 ; i++) {
        if(!Board.getTerritories().get(i).isDeleted()) {


        int x = buttonHandler.getXFromList(i);
        int y = buttonHandler.getYFromList(i);

         TerritoryButton button = new TerritoryButton(x,y,i);


        System.out.println("ne bunun rengi" + Board.getTerritories().get(i).getColor());
        if(Board.getTerritories().get(i).getColor() !=null) {
            button.setColor(Board.getTerritories().get(i).getColor());
        }
        button.setBounds(x, y, 40, 40);
        button.setPreferredSize(new Dimension(40, 40));
       // territoryButtonsList.add(button);
        territoryButtonsList.put(i, button);
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()== MouseEvent.BUTTON1) {
                    System.out.println("MOUSE CLICKED TO TERRITORY");
                    try {
                        try {
                            buttonHandler.matchButtonWithTerritory(button.getID());
                            
                            
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
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
                            System.out.println(t.getId());
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

                    for (TerritoryButton b: buttonHistory) {
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


        PauseScreen pauseScreen = new PauseScreen(mapView, frame, playerList, Board.getTerritories(),gameHandler);

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

        public void refreshUI() {
            // TODO: Add code here to refresh the UI.
            // This might include repainting certain components, revalidating panels, updating labels, etc.
            // Without specifics of what components are in your UI and how they need to be refreshed, this is just a placeholder method.

            // Revalidate and repaint panels and frame, this will reflect the changes in the UI
            mapPanel.revalidate();
            mapPanel.repaint();
            updatePlayerPanel();
            revalidate();
            repaint();
        }

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

    revalidate();
    repaint();



}

@Override
    public void onUIRefreshRequested() {
        System.out.println("refresh requested");
        refreshUI();

    }











}
