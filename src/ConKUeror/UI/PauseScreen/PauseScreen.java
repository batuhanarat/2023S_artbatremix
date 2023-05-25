package src.ConKUeror.UI.PauseScreen;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import src.ConKUeror.domain.controller.GameHandler;
import src.ConKUeror.domain.model.Data.GameData;
import src.ConKUeror.domain.model.Data.GameState;
import src.ConKUeror.domain.model.Data.PlayerData;
import src.ConKUeror.domain.model.Player.Player;
import src.ConKUeror.domain.model.Player.PlayerInventory;
import src.ConKUeror.domain.controller.FileGameDataAdapter;
import src.ConKUeror.domain.controller.GameDataAdapter;

public class PauseScreen extends JDialog {
    private GameDataAdapter localGameDataAdapter;
    private GameDataAdapter onlineGameDataAdapter;
    private GameHandler gameHandler;
    private GameState gameState;
    private List<Player> playerList;

    public PauseScreen(Frame pauseButtonHandler, GameState gameState, List<Player> playerList, GameHandler gameHandler) {
        super(pauseButtonHandler, "Game is Paused", true);
        this.gameState = gameState;
        this.gameHandler = gameHandler;
        this.playerList = playerList;
        this.localGameDataAdapter = new FileGameDataAdapter(gameHandler);

        JLabel label = new JLabel("Game is Paused", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setPreferredSize(new Dimension(300, 100));

        JButton resumeButton = new JButton("Resume");
        resumeButton.addActionListener(e -> dispose());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            Object[] options = {"Local Save", "Online Save"};
            int choice = JOptionPane.showOptionDialog(this,
                    "Where would you like to save your game?",
                    "Save Options",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == 0) {
                saveGame(playerList, gameState, localGameDataAdapter, "local");
            } else if (choice == 1) {
                saveGame(playerList, gameState, onlineGameDataAdapter, "online");
            }
        });

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            try {
                Object[] options = {"Local Load", "Online Load"};
                int choice = JOptionPane.showOptionDialog(this,
                        "Where would you like to load your game from?",
                        "Load Options",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == 0) {
                    GameData loadedData = localGameDataAdapter.loadGameData("gameData.txt", playerList);
                    System.out.println("Game has been loaded from local storage.");
                    updateGame(loadedData);
                } else if (choice == 1) {
                    // Load game data from online storage
                }
            } catch (IOException ex) {
                System.err.println("An error occurred while trying to load the game.");
                ex.printStackTrace();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resumeButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(pauseButtonHandler);
        setResizable(false);
        setModal(true);
    }

    private void saveGame(List<Player> playerList, GameState gameState, GameDataAdapter gameDataAdapter, String saveType) {
        List<PlayerData> playerDataList = new ArrayList<>();
        for (Player player : playerList) {
            playerDataList.add(new PlayerData(player));
        }

        GameData gameData = new GameData(gameState, playerDataList);

        try {
            gameHandler.saveGame(gameData, "gameData.txt", gameDataAdapter);
            System.out.println("Game has been saved " + saveType + ".");
        } catch (IOException ex) {
            System.err.println("An error occurred while trying to save the game " + saveType + ".");
            ex.printStackTrace();
        }
    }

    private void updateGame(GameData loadedGameData) {
        // Update the game state
        GameState loadedGameState = loadedGameData.getGameState();
        gameState.setCurrentTurn(loadedGameState.getCurrentTurn());
       // gameState.setMapState(loadedGameState.getMapState());

        // Update the player data
        List<PlayerData> loadedPlayerDataList = loadedGameData.getPlayerDataList();
        for (PlayerData loadedPlayerData : loadedPlayerDataList) {
            for (Player currentPlayer : playerList) {
                if (currentPlayer.getName().equals(loadedPlayerData.getPlayerName())) {
                    PlayerInventory playerInventory = currentPlayer.getInventory();
                    playerInventory.setInfantryCount(loadedPlayerData.getInfantryCount());
                    playerInventory.setCavalryCount(loadedPlayerData.getCavalryCount());
                    playerInventory.setArtilleryCount(loadedPlayerData.getArtilleryCount());
                    playerInventory.setOwnedTerritories(loadedPlayerData.getTerritories());
                    break;
                }
            }
        }

        // Perform any other necessary updates or actions based on the loaded game data
        // ...
    }
}
