package src.ConKUeror.domain.model.Modes;

import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.util.ArrayList;

import src.ConKUeror.UI.Buttons.TerritoryButton;
import src.ConKUeror.domain.controller.CardController;
import src.ConKUeror.domain.controller.MapListener;
import src.ConKUeror.domain.controller.NextButtonListener;
import src.ConKUeror.domain.controller.RollDieListener;
import src.ConKUeror.domain.controller.TerritoryButtonListener;
import src.ConKUeror.domain.enums.GameMode;
import src.ConKUeror.domain.model.Army.Army;
import src.ConKUeror.domain.model.Board.ArmyCard;
import src.ConKUeror.domain.model.Board.Board;
import src.ConKUeror.domain.model.Board.Card;
import src.ConKUeror.domain.model.Board.DiceRoller;
import src.ConKUeror.domain.model.Board.Territory;
import src.ConKUeror.domain.model.Board.TerritoryCard;
import src.ConKUeror.domain.model.Board.ArmyCard.ArmyType;
import src.ConKUeror.domain.model.Player.Player;
import src.ConKUeror.domain.model.Player.PlayerExpert;
import src.ConKUeror.domain.model.Player.PlayerInventory;


public class GameLogic {

  private Board board;
  private PlayerInventory p;
  private Territory inputTerritory;
  private ArrayList<Territory> inputTerritories = new ArrayList<Territory>();
  private List<MapListener> listeners = new ArrayList<>();
  private List<TerritoryButtonListener> territoryButtonListeners = new ArrayList<>();
  private List<RollDieListener> rollListeners = new ArrayList<>();
  private List<NextButtonListener> nButtonListener = new ArrayList<>();
  private static List<Player> orderedPlayerList;

  DiceRoller diceRoller = DiceRoller.getDiceRollerInstance();
  private Player playerInTurn;

  private int attackingArmyUnit;




  private Player cardPlayer;




  public Boolean selectedButton;

  public GameMode currentGameMode = GameMode.BUILD;
  public static StartMode startMod;

  public Territory[] memory = new Territory[2];
  private int memoryIndex = 0;

  private int phaseIndex= 0;
  private int cardCounter = 0;
  private int armyCardCounter = 0;
  private CardController cardController;

    public GameLogic(Board board,StartMode sMode) {

      this.startMod = sMode;
      this.board = board;
    }

    public void addToMemory(Territory t) {

        if (memory[0] == null) {
            memory[0] = t;
        } else if (memory[1] == null && memory[0] != t) {
            memory[1] = t;
        } else if (memory[0] != t && memory[1] != t) {
            memory[0] = memory[1];
            memory[1] = t;
        }




    }

    public static void setGameOrderList(List<Player> orderList) {
      orderedPlayerList =orderList;

    }


    public void passToNextPlayer(Player p1) {


        int currentIndex = orderedPlayerList.indexOf(p1);
        int newIndex = (currentIndex+1 ) %orderedPlayerList.size();


        playerInTurn= orderedPlayerList.get(newIndex);


        int oldIndex = currentIndex;


        PlayerExpert.publishPlayerInfoEvent(oldIndex, newIndex, playerInTurn.getColor());

    }

    public void setFirstPlayer() {
      System.out.println(orderedPlayerList.get(0).getName() );

      this.playerInTurn = orderedPlayerList.get(0);
    }



    public GameMode getGameMode() {
      return currentGameMode;
    }


    public void setGameMode(GameMode mode ) {
        this.currentGameMode = mode;
    }


    public Territory[] getMemory() {
      return memory;
    }



    /*
    public static List<Player> getPlayerList() {

      orderedPlayerList = startMod.;
      return orderedPlayerList;

    }
*/
    public Board getBoard() {
        return board;
    }

    public void addMapListener(MapListener lis) {
      listeners.add(lis);
  }

  public void addTerritoryButtonListener(TerritoryButtonListener lis) {
    territoryButtonListeners.add(lis);
  }

  public void addRollDieListener(RollDieListener rdlis) {
    rollListeners.add(rdlis);
  }

  public void addNButtonListener(NextButtonListener nbLis) {
    nButtonListener.add(nbLis);
  }





  public void giveNeighborIdsOfSelectedTerritoryButton(List<Integer> neigborIdsList ) {
    for(TerritoryButtonListener l: territoryButtonListeners) {
      l.getButtonList(neigborIdsList);
    }

  }

  private void showAvailableAttacks(List<Integer> territoriesAvailableForAttack)
    {
      for(TerritoryButtonListener l: territoryButtonListeners) {
        l.getButtonList(territoriesAvailableForAttack);
      }
    }


  public void setTerritoryInfo(int ID, int armyUnit, Color color,int territoryArmy) {
    for(TerritoryButtonListener l: territoryButtonListeners) {
      l.setTerritoryButtonInfo(ID, armyUnit, color,territoryArmy);
    }
  }

  public void publishRemoveEvent(TerritoryButton button) {
      for(MapListener l: listeners){
          l.removeOnboardEvent(button);

      }

  }

  public void publishArmyIncreasedEvent(int i)
  {
    for(TerritoryButtonListener l: territoryButtonListeners) {
      l.setArmyCount(i);
    }
  }




  public void prepareTerritory(Territory t) {
    board.setTerritory(inputTerritory);

  }
   //this will be changed later as observer pattern

  public void giveFirstPlayer(String playerName) {
    for (RollDieListener l: rollListeners) {
        l.getRollEvent(playerName);
    }
  }

  public void addTerritoryCard() {
      //  cardPlayer = playerInTurn;
        CardController cc  = CardController.getInstance();
        TerritoryCard tCard = cc.drawTerritoryCard(playerInTurn);
        if (tCard != null) {
        System.out.println(tCard.getName());

        playerInTurn.inv.addTerritoryCard(tCard);
        }
  }

  public void useTerritoryCard() {

        playerInTurn.inv.useTerritoryCards();

  }



  public void addArmyCard() {
        CardController cc = CardController.getInstance();
        ArmyCard aCard = cc.drawArmyCard(playerInTurn);
        if (aCard != null) {
            playerInTurn.inv.addArmyCard(aCard);
            System.out.println(aCard.getName());


        }




       //  ArmyCard iArmyCard = new ArmyCard("Infantry Card",ArmyType.INFANTRY);
        // ArmyCard aArmyCard = new ArmyCard("Artillery Card",ArmyType.ARTILLERY);
        // ArmyCard cArmyCard = new ArmyCard("Cavalry Card",ArmyType.CAVALRY);
        // armyCardCounter += 1;




        // if (armyCardCounter == 1) {
        //     playerInTurn.inv.addArmyCard(iArmyCard);
        //     playerInTurn.inv.addArmyCard(iArmyCard);
        //     playerInTurn.inv.addArmyCard(iArmyCard);
        //     System.out.println("3 Infantry is Added - Case 1");
        // }

        // else if (armyCardCounter == 2) {
        //     playerInTurn.inv.addArmyCard(iArmyCard);
        //     playerInTurn.inv.addArmyCard(iArmyCard);
        //     playerInTurn.inv.addArmyCard(cArmyCard);
        //     System.out.println("2 I + 1 C is added - Case 2 ");
        // }

        // else if (armyCardCounter == 3) {
        //     playerInTurn.inv.addArmyCard(iArmyCard);
        //     playerInTurn.inv.addArmyCard(iArmyCard);
        //     playerInTurn.inv.addArmyCard(aArmyCard);
        //     System.out.println("2 I + 1 A is added - Case 3");
        // }

        // else if (armyCardCounter == 4) {
        //     playerInTurn.inv.addArmyCard(iArmyCard);
        //     playerInTurn.inv.addArmyCard(cArmyCard);
        //     playerInTurn.inv.addArmyCard(cArmyCard);

        //     System.out.println("1 I + 2 C is added - Case 4");
        // }

        // else if (armyCardCounter == 5) {
        //     playerInTurn.inv.addArmyCard(aArmyCard);
        //     playerInTurn.inv.addArmyCard(cArmyCard);
        //     playerInTurn.inv.addArmyCard(cArmyCard);

        //     System.out.println("1 A + 2 C is added - Case 5");
        // }
        // else {
        //     System.out.println("Use Cases are done, thus no more adding for current moment for simplify tests.");
        // }
  }


  public void useArmyCards() {
    playerInTurn.inv.useArmyCards();
    //System.out.println("After Using!");

  }


  public void roll() {

    //Rollayarak ilk playera karar veriyor
    Player player = diceRoller.getFirstPlayer();
    //bu playerın solundaki playerlari listeye atıyor ve orderli bir player listesi oluşturuyor
    startMod.setOrderedAfterRoll(player);
    //ilk ismi döndürüyor
    giveFirstPlayer(player.getName());

}

    public void increasePhaseIndex() {
        phaseIndex += 1;
        for (NextButtonListener l : nButtonListener ) {
            l.nextPhaseEvent(phaseIndex);
        }
    }

    public int getGamePhaseAsIndex() {
         return phaseIndex;

}
public void moveToOtherPhase() {
        if(currentGameMode== GameMode.BUILD) {
            setGameMode(GameMode.CONNECTION);
        }
        else if (currentGameMode == GameMode.CONNECTION) {
            setGameMode(GameMode.START);

        }else if (currentGameMode == GameMode.START) {
            setGameMode(GameMode.CHANCECARD);

        }
        else if (currentGameMode == GameMode.CHANCECARD)
        {
            setGameMode(GameMode.DEPLOY);
        }
        else if (currentGameMode == GameMode.DEPLOY)
        {
            setGameMode(GameMode.ATTACK);
        }

        else if (currentGameMode == GameMode.ATTACK)
        {
            setGameMode(GameMode.FORTIFY);
        }
        else if (currentGameMode == GameMode.FORTIFY)
        {
            setGameMode(GameMode.TERRITORYCARD);
        }

        else if (currentGameMode == GameMode.TERRITORYCARD)
        {
            setGameMode(GameMode.ARMYCARD);
        }
}


    public void prepareButton(Territory t,GameMode gameMode) {

      switch(gameMode) {

        case BUILD:

        this.inputTerritory = t;
        this.phaseIndex=0;
        prepareTerritory(t);
        addToMemory(t);


        break;

        case CONNECTION:
        this.inputTerritory=t;
        this.phaseIndex=1;
        Map<Integer,Territory>  adjList = t.getAdjacencyList();
        List<Integer> neighborIds = new ArrayList<Integer>();
        for (Map.Entry<Integer, Territory> set : adjList.entrySet()) {
          int territoryId =set.getKey();
          neighborIds.add(territoryId);
        }
        giveNeighborIdsOfSelectedTerritoryButton(neighborIds);

        break;

        case START:
        this.inputTerritory = t;
        this.phaseIndex=2;

        if(t.getOwner()==null || t.getOwner() == playerInTurn) {
          if(playerInTurn.getInventory().getTotalArmy()>0) {

         //inventorydeki asker sayısını değiştirecek
          playerInTurn.getInventory().removeInfantries(1);
          t.setOwner(playerInTurn);
          t.addInfantries(1);

          playerInTurn.getInventory().addTerritory(t);
           //uidaki territory buttonını değiştirecek ve rengi değiştirecek
           setTerritoryInfo(t.getId(),playerInTurn.getInventory().getTotalArmy(),playerInTurn.getColor(),t.getTotalUnit());
        }
        passToNextPlayer(playerInTurn);

      }




          break;

        case CHANCECARD:
          System.out.println("Card");
          this.phaseIndex=2;

          break;

        case DEPLOY:
          System.out.println("Deploy");
          this.phaseIndex=3;

          break;

        case ATTACK:
           System.out.println("Attack");
           this.phaseIndex=4;
           this.inputTerritory=t;

           //Map<Integer,Territory>  adjacentList = t.getAdjacencyList();
           List<Integer> territoriesAvailableForAttack = new ArrayList<Integer>();
           t.checkAvailableAttacks(territoriesAvailableForAttack);
           showAvailableAttacks(territoriesAvailableForAttack);
           addToMemory(t);
          break;

        case FORTIFY:
          System.out.println("Fortify");
          this.phaseIndex=5;

          break;

        case TERRITORYCARD:
            System.out.println("Territory Card Phase test");
            this.phaseIndex = 6;
            break;



        case ARMYCARD:
            System.out.println("Army Card Phase test");
            this.phaseIndex = 7;
            break;


      }



    }




    public int getAttackingArmyUnit() {
      return attackingArmyUnit;
    }

    public void setAttackingArmyUnit(int attackingArmyUnit) {
      this.attackingArmyUnit = attackingArmyUnit;
    }

    public void setForAttack()
    {
      int attackingArmy = attackingArmyUnit;
      try {
          int defendingArmy = memory[1].getTotalUnit();
          playerInTurn.attack(attackingArmy, defendingArmy);
      }
      catch (NullPointerException e)
      {
          System.out.println("Please choose an attack target");
      }

    }






























}
