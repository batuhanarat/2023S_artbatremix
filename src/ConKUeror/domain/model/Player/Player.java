package ConKUeror.domain.model.Player;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;

import ConKUeror.domain.model.Army.Army;
import ConKUeror.domain.model.Army.Artillery;
import ConKUeror.domain.model.Army.Cavalry;
import ConKUeror.domain.model.Army.Infantry;
import ConKUeror.domain.model.Board.Card;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Player.Strategies.IAttackBehaviour;
import ConKUeror.domain.model.Player.Strategies.IDeployBehaviour;
import ConKUeror.domain.model.Player.Strategies.IFortifyBehaviour;

public class Player implements Serializable {

    public PlayerInventory inv = new PlayerInventory();
    // private Army deployedArmy = new Army();
    private String type;
    private transient Color playerColor;

    IDeployBehaviour db;
    IAttackBehaviour ab;
    IFortifyBehaviour fb;
    private String playerType;

    private String name;
    // private List<Army> armies;
    // private List<Territory> territories;
    private List<Card> cards;

    public Player(String name,
            IDeployBehaviour db,
            IAttackBehaviour ab,
            IFortifyBehaviour fb,
            PlayerInventory inv, String playerType) {

        this.name = name;
        this.db = db;
        this.ab = ab;
        this.fb = fb;
        this.inv = inv;

        this.playerType = playerType;
    }

    public String getPlayerType() {
        return this.playerType;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setColor(Color color) {
        this.playerColor = color;

    }

    public Color getColor() {
        return playerColor;
    }

    /**
     * Deploy Method
     *
     * Requires: (about domain range of values) -> Düzelt
     * Modifies: Territory.army and Player.playerInventory
     * Effects: Armies are deployed to territory's army,
     * and from player's inventory army is removed.
     * </p>
     */
    public void deploy(Territory t, int army) {
        db.deploy(t, army);
    }

    public boolean attack(List<Infantry> attackingInfantries, List<Cavalry> attackingCavalries,
            List<Artillery> attackingArtilleries, Army defendingArmy) {
        boolean attackResult = ab.attack(attackingInfantries, attackingCavalries, attackingArtilleries, defendingArmy);
        return attackResult;
    }

    public void fortify(Territory fortifyFrom, Territory fortifyTo, int army) {
        fb.fortify(fortifyFrom, fortifyTo, army);
    }

    public String getName() {

        return this.name;
    }

    public List<Card> getCards() {

        return this.cards;
    }

    public PlayerInventory getInventory() {
        return inv;

    }

    public void setName(String playerName) {
    }

    public void removeTerritory(Territory territory) {
        this.inv.removeTerritoryFromList(territory);
    }

}
