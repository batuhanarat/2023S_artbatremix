package ConKUeror.domain.model.Data;


import java.awt.Color;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ConKUeror.domain.model.Board.ArmyCard;
import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Board.ChanceCard;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Board.TerritoryCard;
import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerInventory;

public class PlayerData implements Serializable{
    private String playerName;
    private int cavalryCount;
    private int infantryCount;
    private int artilleryCount;
    private List<ChanceCard> chanceCards;
    private List<TerritoryCard> territoryCards;
    private List<ArmyCard> armyCards;
    private List<Territory> territories;
    private PlayerInventory inventory;
    private String playerType;
    private transient Color playerColor;
    Player player;
    public PlayerData(Player player) {
        this.player = player;
        this.playerName = player.getName();
        this.cavalryCount = player.getInventory().getCavalryCount();
        this.infantryCount = player.getInventory().getInfantryCount();
        this.artilleryCount = player.getInventory().getArtilleryCount();
        this.chanceCards = player.getInventory().getChanceCards();
        this.armyCards = player.getInventory().getArmyCards();
        this.territoryCards= player.getInventory().getTerritoryCards();
        this.territories = player.getInventory().getOwnedTerritories();
        this.inventory = player.getInventory();
        this.playerType = player.getPlayerType();
        this.playerColor =  player.getColor();



    }
    public Color getPlayerColor(){
        return playerColor;
    }
    public String getPlayerName() {
        return this.playerName;
    }
    public String getPlayerType(){
        return playerType;
    }
    public Player getPlayer(){
        return player;
    }


    public List<Territory> getTerritories() {
        return this.territories;
    }

    public int getCavalryCount() {
        return cavalryCount;
    }

    public void setCavalryCount(int cavalryCount) {
        this.cavalryCount = cavalryCount;
    }

    public int getInfantryCount() {
        return infantryCount;
    }

    public void setInfantryCount(int infantryCount) {
        this.infantryCount = infantryCount;
    }

    public int getArtilleryCount() {
        return artilleryCount;
    }

    public void setArtilleryCount(int artilleryCount) {
        this.artilleryCount = artilleryCount;
    }

    public List<ChanceCard> getChanceCards() {
        return chanceCards;
    }

    public void setChanceCards(List<ChanceCard> chanceCards) {
        this.chanceCards = chanceCards;
    }

    public List<TerritoryCard> getTerritoryCards() {
        return territoryCards;
    }

    public void setTerritoryCards(List<TerritoryCard> territoryCards) {
        this.territoryCards = territoryCards;
    }

    public List<ArmyCard> getArmyCards() {
        return armyCards;
    }

    public void setArmyCards(List<ArmyCard> armyCards) {
        this.armyCards = armyCards;
    }



}
