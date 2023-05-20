package src.ConKUeror.domain.model.Data;

import java.util.Arrays;
import java.util.List;

import src.ConKUeror.domain.model.Board.Territory;
import src.ConKUeror.domain.model.Player.Player;

public class PlayerData {
    private String playerName;
    private int armySize;
    private List<Territory> territories;

    public PlayerData(Player player) {
        this.playerName = player.getName();
        //this.armySize = player.getInventory().getArmySize();
        this.territories = player.getInventory().getOwnedTerritories();
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getArmySize() {
        return this.armySize;
    }

    public List<Territory> getTerritories() {
        return this.territories;
    }
    public static PlayerData fromString(String str, List<Player> playerList) {
        for (Player player : playerList) {
            if (player.getName().equals(str)) {
                return new PlayerData(player);
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return playerName + "," + armySize + "," + String.join(";", territories.toString());
    } 
}
