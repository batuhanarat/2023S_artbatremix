package ConKUeror.domain.model.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ConKUeror.domain.enums.GameMode;
import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Board.Continent;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerExpert;

public class GameState implements Serializable{
    private List<Player> players;
    private List<PlayerData> playerDataList;
    private static List<Continent> continents;


    private List<TerritoryData> territoryDataList;
    private Player playerInTurn;
    private List<Player> orderedList;
    private int phaseIndex;
    private static Map<Integer, Territory> unoccupiedTerritories;



    private GameMode gameMode;
  //  private static List<Continent> continents = new ArrayList<>();
//static Map<Integer, Territory> unoccupiedTerritories = new LinkedHashMap<>();


 //private static Map<Integer, Territory> territories= new HashMap<>();

    public GameState(List<PlayerData> playerDataList, List<TerritoryData> territoryDataList,List<Continent> continents ,int index){
        this.playerDataList = playerDataList;
        this.territoryDataList = territoryDataList;
        this.continents = continents;
        //this.gameMode = mode;
        this.phaseIndex= index;
        this.playerInTurn = PlayerExpert.getPlayerInTurn();
        this.orderedList = PlayerExpert.getPlayersList();
        this.unoccupiedTerritories = Board.getUnoccupiedTerritories();


    }
    public static Map<Integer, Territory> getUnoccupiedTerritories() {
        return unoccupiedTerritories;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
    public int getPhaseIndex() {
        return phaseIndex;
    }

    public List<PlayerData> getPlayerData(){
        return playerDataList;
    }

    public List<TerritoryData> getTerritoryData() {
        return territoryDataList;
    }

    public List<Continent> getContinents() {
        return continents;
    }
/*
        public Map<Integer, Territory> getTerritories() {
            return this.territories;
        }
        public Map<Integer, Territory> getUnoccupiedTerritories() {
            return this.unoccupiedTerritories;
        }
        public List<Continent> getContinents() {
            return this.continents;
        }


*/


    public Player getPlayerInTurn(){
        return playerInTurn;
    }
    public List<Player> getOrderedList(){
        return orderedList;
    }
}
