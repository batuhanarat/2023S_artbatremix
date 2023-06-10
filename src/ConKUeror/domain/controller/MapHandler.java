package ConKUeror.domain.controller;

import java.awt.Color;
import java.io.Serializable;

import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Modes.GameLogic;

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


    public void updateTerritory(int buttonID , int army) {
        game.updateTerritory(buttonID,army);


    }




}
