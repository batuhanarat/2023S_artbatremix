package ConKUeror.domain.model.Board;
import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ConKUeror.domain.model.Army.Army;
import ConKUeror.domain.model.Player.Player;



public class Territory implements Serializable {

    private Map<Integer, Territory> adjacencyList = new  HashMap<>();
    private int id;

    private int totalArmyUnit;
    private String name;
    private Player owner;
    private Army army;
    private boolean isFree;
    private boolean isDeleted;
    private transient Color territoryColor;



    public Territory(int _id) {
        this.id = _id;
        this.isFree = true;
        this.totalArmyUnit = 0;
        army = new Army();
        this.isDeleted=false;

    }

    public void setId(int _id) {
        this.id= _id;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player newOwner) {
        this.owner = newOwner;
    }

    public String getName() {
        return name;
    }
      //If i remove a territory from the map, i should also remove its existence  from its neighbor's adjacency list.
         //Map is initialized at the beginning so connections are set at the start of the game.
         //With this method we can update its connections.
         public void removeExistenceInNeighbors() {
            for (Territory t : adjacencyList.values()) {
                searchInAdjacencyList(t.adjacencyList);
            }
        }

        public void searchInAdjacencyList(Map<Integer, Territory> adj) {
            Iterator<Map.Entry<Integer, Territory>> iterator = adj.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<Integer, Territory> entry = iterator.next();
                if (entry.getValue().id == this.id) {
                    iterator.remove();
                }
            }
        }
        public void setDeleted() {
            this.isDeleted = true;
        }

        public void setIsDeleted(Boolean d) {
            this.isDeleted = d;

        }

        public Boolean isDeleted() {
            return this.isDeleted;
        }

    public int getId() {
        return id;
    }

    public void setColor(Color color) {
        this.territoryColor = color;
    }

    public Color getColor() {
        return this.territoryColor;
    }




    public void addInfantries(int n) {
       army.addInfantries(n);

 }

    public void removeInfantries(int n) {
        army.removeInfantries(n);
    }

    public int getTotalUnit() {
       return army.getTotalArmyUnit();

    }

    public Map<Integer, Territory> getAdjacencyList() {
        return adjacencyList;
    }

    public void addConnectionDual(Territory neighbor) {

       int neighborId = neighbor.getId();

        if(this.adjacencyList.get(neighborId) == null) {
            this.adjacencyList.put(neighborId, neighbor);
            neighbor.getAdjacencyList().put(this.getId(),this);
        }

    }

    public void checkAvailableAttacks(List<Integer> territoriesAvailableForAttack)
    {
        for (Map.Entry<Integer, Territory> set : this.adjacencyList.entrySet())
        {
            if (canAttackTerritory(this, Board.getTerritories().get(set.getKey())))
            {
                territoriesAvailableForAttack.add(set.getKey());
            }
        }
    }

    private boolean canAttackTerritory(Territory attacker, Territory defender)
    {
        boolean canAttack = false;
        if (attacker.adjacencyList.containsValue(defender) && attacker.getTotalUnit() > defender.getTotalUnit() && defender.getOwner() != attacker.getOwner())
        {
            canAttack = true;
        }

        return canAttack;
    }


    @Override
    public String toString() {
        return Integer.toString(this.id);
    }

    public Army getArmy() {
        return this.army;
    }

    public void removeAllArmy() {
        int infantry = this.army.getInfantries();
        int artillery = this.army.getArtilleries();
        int cavalry = this.army.getCavalries();

        this.army.removeArtilleries(artillery);
        this.army.removeCavalries(cavalry);
        this.army.removeInfantries(infantry);
    }



}