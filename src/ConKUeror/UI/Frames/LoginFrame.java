package ConKUeror.UI.Frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ConKUeror.UI.Buttons.TerritoryButton;
import ConKUeror.UI.Frames.ArrowAnimation.AnimationThread;
import ConKUeror.domain.controller.BuildHandler;
import ConKUeror.domain.controller.GameHandler;
import ConKUeror.domain.controller.HandlerFactory;
import ConKUeror.domain.controller.SaveLoadHandler;
import ConKUeror.domain.controller.StartHandler;
import ConKUeror.domain.controller.TerritoryButtonListener;
import ConKUeror.domain.enums.GameMode;
import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Data.GameState;
import ConKUeror.domain.model.Data.PlayerData;
import ConKUeror.domain.model.Data.TerritoryData;
import ConKUeror.domain.model.Modes.BuildMode;
import ConKUeror.domain.model.Modes.StartMode;
import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerExpert;
import ConKUeror.domain.model.Player.PlayerFactory;

public class LoginFrame extends JFrame {
    private JButton newGameButton;
    private JButton loadGameButton;
    private GameState gameState;
    private StartMode startMode;
    private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();
    private List<Player> playerList;
    private List<Territory> territoryList;
    private static Map<Integer, Territory> territories= new HashMap<>();

    private MapView map;
    private HandlerFactory controller = HandlerFactory.getInstance();



    private List<TerritoryButtonListener> territoryButtonListeners = new ArrayList<>();


    public LoginFrame() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setBorder(new EmptyBorder(50, 80, 50, 80));

        JLabel titleLabel = new JLabel("ConKuEror");
        Font titleFont = new Font("Monospaced", Font.BOLD, 40);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);

        JLabel madeByLabel = new JLabel("Made by: artbatremix");
        madeByLabel.setFont(new Font("Monospaced", Font.ITALIC, 16));
        madeByLabel.setForeground(Color.LIGHT_GRAY);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(Color.DARK_GRAY);
        headerPanel.add(titleLabel);
        headerPanel.add(madeByLabel);

        this.add(headerPanel, BorderLayout.NORTH);

        this.newGameButton = createButton("New Game");
        this.loadGameButton = createButton("Load Game");

        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    gameState = loadGameState();
                    System.out.println("Loaddan sonra territories");

                    //System.out.println(gameState.getTerritories());
                    System.out.println("player name" +gameState.getPlayerData().get(0).getPlayerName());
                    System.out.println( "owned territories" +gameState.getPlayerData().get(0).getTerritories());


                } catch (ClassNotFoundException | IOException e1) {
                    e1.printStackTrace();
                }

                startLoadedGame(gameState.getPlayerData(),gameState.getTerritoryData());
            }
        });

        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(newGameButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(loadGameButton);

        panel.setBorder(new EmptyBorder(new Insets(50, 80, 50, 80)));
        this.add(panel);

        this.setSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 50));
        button.setFont(new Font("Monospaced", Font.BOLD, 20));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(255, 215, 0));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return button;
    }

    private void startNewGame() {
        BuildHandler buildHandler = controller.giveBuildHandler();
        BuildModeScreen buildModeScreen = new BuildModeScreen(buildHandler);


        this.dispose();
    }







    public void addTerritoryButtonListener(TerritoryButtonListener lis) {
        territoryButtonListeners.add(lis);
      }


    public void startLoadedGame(List<PlayerData> loadedPlayerDataList,List<TerritoryData> loadedTerritoryDataList) {
      //  System.out.println(gameState.getTerritories());

        HandlerFactory controller = HandlerFactory.getInstance();
        playerList = new ArrayList<Player>();
        territoryList = new ArrayList<Territory>();
        //BuildHandler buildHandler = controller.giveBuildHandler();
        int botCount = 0;

        PlayerExpert.setPlayerInTurn(gameState.getPlayerInTurn());
        PlayerFactory playerFactory = PlayerFactory.getInstance();
        StartHandler startHandler = controller.giveStartHandler();



        for(TerritoryData territoryData : loadedTerritoryDataList) {

            Territory territory = territoryData.getTerritory();
            territoryList.add(territory);
            territory.setColor(territoryData.getColor());

            territory.setIsDeleted(territoryData.getIsDeleted());
            territory.setId(territoryData.getId());
            System.out.println("Territory and deletion boolean" +territory.getId() + territory.isDeleted());

            territories.put(territory.getId(), territory);



        }



        Board.setTerritories(territories);
        Board.setContinents(gameState.getContinents());
        controller.getGameLogic().setPlayerInTurn(gameState.getPlayerInTurn());


        // Initialize the players using the loaded player data
        for (PlayerData playerData : loadedPlayerDataList) {
            Player player = playerData.getPlayer();
            playerList.add(player);

            // Set the player's inventory and other attributes based on the loaded data
            player.getInventory().setCavalryCount(playerData.getCavalryCount());
            player.getInventory().setInfantryCount(playerData.getInfantryCount());
            player.getInventory().setArtilleryCount(playerData.getArtilleryCount());

            player.getInventory().setOwnedTerritories(playerData.getTerritories());

           // player.setColor(new Color(playerData.getPlayerColor()));
            player.setName(playerData.getPlayerName());
            Map<Integer, Territory> hashMap = Board.getTerritories();

            if (playerData.getPlayerType().equals("Computer")) {
                 // Initialize bot player.
                  botCount++;
            } else {
               playerFactory.createColoredPlayer(player.getType() + " Player", player.getName(), player.getColor());

            }
        }

        StartMode.setOrderedList(playerList);
        PlayerExpert.setPlayersList(playerList);

        controller.getGameLogic().setGamePhaseIndex(gameState.getPhaseIndex());
        controller.getGameLogic().setGameMode(  gameState.getGameMode());


       

        // Start the game
        try {
             map = new MapView();
           

            


           for(PlayerData playerData: loadedPlayerDataList){
            Player p2 = playerData.getPlayer();
            List<Territory> owned = p2.getInventory().getOwnedTerritories();

            p2.setColor(playerData.getPlayerColor());
            //p2.setColor( new Color(playerData.getPlayerColor()) );


            for(Territory t : owned){
                t.getArmy().addArtilleries(p2.getInventory().getArtilleryCount());
                t.getArmy().addCavalries(p2.getInventory().getCavalryCount());
                t.getArmy().addInfantries(p2.getInventory().getInfantryCount());
                t.setOwner(p2);

               // controller.getGameLogic().setTerritoryInfo(botCount, botCount, color, botCount);
                //controller.giveMapHandler().updateTerritory(botCount, botCount);
              // mapHandler.updateTerritory(buttonId,result);

               //controller.getGameLogic().setTerritoryInfo(t.getId(), t.getTotalUnit(), p2.getColor(), t.getTotalUnit());


               controller.giveMapHandler().updateTerritory(t.getId(),t.getTotalUnit());

              // mapHandler.updateTerritory(buttonId,result,player.getColor());




            }


           }


        } catch (IOException e1) {
            e1.printStackTrace();
        }

       // this.dispose();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }

    public GameState loadGameState() throws ClassNotFoundException, IOException {
        return saveLoadHandler.loadGame("data.txt");
    }
}
