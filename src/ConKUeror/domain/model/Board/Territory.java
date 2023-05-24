package src.ConKUeror.domain.model.Board;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.ConKUeror.domain.model.Army.Army;
import src.ConKUeror.domain.model.Player.Player;



public class Territory {

    private Map<Integer, Territory> adjacencyList = new  HashMap<>();
    private int id;

    private int totalArmyUnit;
    private String name;
    private Player owner;
    private Army army;
    private boolean isFree;
    private int locationX;
    private int locationY;



    public Territory(int _id) {
        this.id = _id;
        this.isFree = true;
        this.totalArmyUnit = 0;
        army = new Army();

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



    public int getId() {
        return id;
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



}
