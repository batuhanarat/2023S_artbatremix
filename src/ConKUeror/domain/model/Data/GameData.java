package src.ConKUeror.domain.model.Data;

import java.util.List;

public class GameData {
    private GameState gameState;
    private List<PlayerData> playerDataList;

    
    public GameData(GameState gameState, List<PlayerData> playerDataList) {
        this.gameState = gameState;
        this.playerDataList = playerDataList;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public List<PlayerData> getPlayerDataList() {
        return this.playerDataList;
    }
}
