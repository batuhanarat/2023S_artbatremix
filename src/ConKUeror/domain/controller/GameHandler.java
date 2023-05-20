package src.ConKUeror.domain.controller;

import java.util.List;
import java.util.Scanner;

import src.ConKUeror.domain.model.Data.GameData;
import src.ConKUeror.domain.model.Data.GameState;
import src.ConKUeror.domain.model.Data.PlayerData;
import src.ConKUeror.domain.model.Player.Player;
import src.ConKUeror.domain.model.Player.PlayerExpert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GameHandler {

    List<String> buttons = new ArrayList<String>();

    private static GameHandler instance;
    private GameHandler() {

    }

    public static GameHandler getInstance() {

        if (instance == null) {
            instance = new GameHandler();
        }
        return instance;
    }
    
    public void saveGame(GameData gameData, String filename) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(gameData.getGameState());
            for (PlayerData playerData : gameData.getPlayerDataList()) {
                out.println(playerData);
            }
        }
    }
     

    public GameData loadGame(String filename, List<Player> playerList) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            GameState gameState = GameState.fromString(scanner.nextLine());
    
            List<PlayerData> playerData = new ArrayList<>();
            while (scanner.hasNextLine()) {
                playerData.add(PlayerData.fromString(scanner.nextLine(), playerList));
            }
            return new GameData(gameState, playerData);
        }
    } 


    public void registerPlayerPanelAsListener(PlayerExpertListener listener) {
        PlayerExpert.addPlayerPanelAsListener(listener);

    }



    public List<String >getButtonNames() {


        buttons.add("Add Connection");
        buttons.add("Remove");
        buttons.add("Next");

        return buttons;




    }




}
