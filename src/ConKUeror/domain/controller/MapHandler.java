package ConKUeror.domain.controller;

import java.awt.Color;
import java.util.List;
import java.io.Serializable;

import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerExpert;

public class MapHandler implements Serializable {
    GameLogic game;
    private static MapHandler instance ;

    private MapHandler(GameLogic _game, Board board) {
        this.game = _game;

    }

    public static MapHandler getInstance(GameLogic _game, Board board) {
        if (instance == null) {
            instance = new MapHandler(_game, board);
        }
        return instance;
    }


    public void registerAsListener(MapListener listener) {
        game.addMapListener(listener);


    }

    public void registerAsChanceObserver(ChanceObserverListener coListener) {
        // List<Player> listOfPlayers = PlayerExpert.getPlayersList();
        // for (Player p : listOfPlayers) {
        //     game.addCOListener(coListener, p);
        // }
       // game.addCOListener(coListener);
    }



    public void updateTerritory(int buttonID , int army) {
        game.updateTerritory(buttonID,army);


    }




}
