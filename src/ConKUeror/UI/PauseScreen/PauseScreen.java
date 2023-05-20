package src.ConKUeror.UI.PauseScreen;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

import src.ConKUeror.domain.controller.GameHandler;
import src.ConKUeror.domain.model.Data.GameData;
import src.ConKUeror.domain.model.Data.GameState;
import src.ConKUeror.domain.model.Data.PlayerData;
import src.ConKUeror.domain.model.Player.Player;

import java.awt.*;
import java.io.IOException;

public class PauseScreen extends JDialog {
    GameHandler gameHandler;
    public PauseScreen(Frame pauseButtonHandler,GameState gameState,List<Player> playerList,GameHandler gameHandler) {
        super(pauseButtonHandler, "Game is Paused", true);
        JLabel label = new JLabel("Game is Paused", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setPreferredSize(new Dimension(300, 100));
        this.gameHandler = gameHandler;
        JButton resumeButton = new JButton("Resume");
        resumeButton.addActionListener(e -> dispose());
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            // Add your code to save the game here...
            List<PlayerData> playerDataList = new ArrayList<>();
            for (Player player : playerList) {
                playerDataList.add(new PlayerData(player));
            }
            // Create game data
            GameData gameData = new GameData(gameState, playerDataList);
            try {
               gameHandler.saveGame(gameData,"gameData.txt");
                System.out.println("Game has been saved.");
            } catch (IOException ex) {
                System.err.println("An error occurred while trying to save the game.");
                ex.printStackTrace();
            }
            // Print a message to the terminal indicating that the game has been saved
            System.out.println("Game has been saved.");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resumeButton);
        buttonPanel.add(saveButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(pauseButtonHandler);
        setResizable(false);
        setModal(true);
    }
}
