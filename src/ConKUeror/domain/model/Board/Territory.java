package ConKUeror.domain.model.Board;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.awt.Graphics2D;

import ConKUeror.UI.Frames.ArrowAnimation.arrowAnimation;
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
    private Color territoryColor;
   private Graphics2D g2;



    public Territory(int _id) {
        this.id = _id;
        this.isFree = true;
        this.totalArmyUnit = 0;
        army = new Army();

    }

    public void setGraphics(Graphics2D g) {
     g2 = g;
    }

    public Graphics2D getGraphics() {
        return g2;
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

    public void setColor(Color color) {
        this.territoryColor = color;
    }

    public Color getColor() {
        return this.territoryColor;
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
    public void setTotalUnit(int n){
        totalArmyUnit = n;
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
                arrowAnimation a = new arrowAnimation();
                Integer[] b = new Integer[1];
                b[0] = set.getKey();


                
               float line_height = 0.5f;
               float path_height = 0.8f;

          

            //   float path_width_end = path_width;
            //   int line_width_end =  0;

                 a.Animation(id,b, id, totalArmyUnit, id, totalArmyUnit, id, null, null, null);

            //     MapConstants m = new MapConstants();
            //     m.fillCoordinates();
            //    int arrow_x = m.coordinates.get(this.id).getX();
            //    int arrow_y = m.coordinates.get(this.id).getY();
               




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



}
