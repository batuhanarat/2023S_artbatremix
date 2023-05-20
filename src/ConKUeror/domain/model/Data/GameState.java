package src.ConKUeror.domain.model.Data;

public class GameState {
    private String currentTurn;
    private String mapState;

    
    public GameState(String currentTurn, String mapState) {
        this.currentTurn = currentTurn;
        this.mapState = mapState;
    }

    public String getCurrentTurn() {
        return this.currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public String getMapState() {
        return this.mapState;
    }

    public void setMapState(String mapState) {
        this.mapState = mapState;
    }

    @Override
    public String toString() {
        return currentTurn + "," + mapState;
    }

    public static GameState fromString(String str) {
        String[] parts = str.split(",");
        return new GameState(parts[0], parts[1]);
    } 
}
