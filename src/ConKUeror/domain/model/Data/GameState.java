package src.ConKUeror.domain.model.Data;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    private String turn;
    private Map<String, Integer> territoryArmies;
    private int currentTurn;
    private String mapState;
    public GameState(String turn,String mapState) {
        this.turn = turn;
        this.mapState = mapState;
    }

    public String getTurn() {
        return turn;
    }

    public Map<String, Integer> getTerritoryArmies() {
        return territoryArmies;
    }
    public int getCurrentTurn() {
        return currentTurn;
    }
    
    public void setCurrentTurn(int turn) {
        currentTurn = turn;
    }

    public void setTerritoryArmies(Map<String, Integer> territoryArmies) {
        this.territoryArmies = territoryArmies;
    }

    @Override
    public String toString() {
        return turn + "\n" + mapState; // Only include turn and mapState
    }
    
    public static GameState fromString(String data) {
        String[] lines = data.split("\n");
        if (lines.length < 2) {
            return null;
        }
        String turn = lines[0];
        String mapState = lines[1]; // Add the map state from the data
        return new GameState(turn, mapState);
    } 
}
