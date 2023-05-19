    package src.ConKUeror.UI.Frames;

    import java.awt.BorderLayout;
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.Font;
    import java.awt.Graphics;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.awt.image.BufferedImage;
    import java.io.IOException;
    import java.util.Map;

    import javax.imageio.ImageIO;
    import javax.sound.midi.MidiDevice.Info;
    import javax.swing.Box;
    import javax.swing.BoxLayout;
    import javax.swing.JButton;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JOptionPane;
    import javax.swing.JPanel;

    import src.ConKUeror.UI.Buttons.TerritoryButton;
import src.ConKUeror.UI.Frames.ArrowAnimation.Animation;
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


    public class MapView extends JFrame implements MapListener ,TerritoryButtonListener,RollDieListener{

        MapHandler mapHandler;
        ButtonHandler buttonHandler;
        StartHandler startHandler;
        GameHandler gameHandler;
        PlayerPanel playerPanel;
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


        public BufferedImage image;
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





    public void initGUI() throws IOException {

        image = ImageIO.read(getClass().getResourceAsStream("/src/images/Map.png"));
        setSize((int) (1.20 * image.getWidth()), image.getHeight());
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


    public void createTerritoryButtons() {

        for(int i= 0 ; i<42 ; i++) {

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
                        buttonHandler.matchButtonWithTerritory(button.getID());
                        buttonHandler.selectButton(button);


                        buttonHandler.addToMemory(button.getID());

                        Territory[] memoryTerritory = buttonHandler.getMemoryList();
                        for (Territory t : memoryTerritory) {
                         
                            if (t != null) {
                                System.out.println(t.getId());
                                Animation animatedObject = new Animation();
                                Thread thread = new Thread(animatedObject);
                                thread.start();
                        
                            }

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
