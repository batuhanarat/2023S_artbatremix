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
import java.io.IOException;
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
    
    public void saveGame(GameData gameData, String filename, GameDataAdapter adapter) throws IOException {
        adapter.saveGameData(gameData, filename);
    }

    public GameData loadGame(String filename, List<Player> playerList, GameDataAdapter adapter) throws FileNotFoundException {
        return adapter.loadGameData(filename, playerList);
    } 
    
     
    public void updateGameState(GameState loadedGameState) {
        // Here, add code to update the game state using loadedGameState
    }

    public void updatePlayerDataList(List<PlayerData> loadedPlayerDataList) {
         //code to update the player list using loadedPlayerDataList
        
        
    
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
