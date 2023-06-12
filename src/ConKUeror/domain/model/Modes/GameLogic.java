package ConKUeror.domain.model.Modes;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

import ConKUeror.UI.Buttons.TerritoryButton;
import ConKUeror.UI.Panels.ChanceCardWindow;
import ConKUeror.domain.controller.CardController;
import ConKUeror.domain.controller.ChanceObserverListener;
import ConKUeror.domain.controller.EndOfTheGameListener;
import ConKUeror.domain.controller.MapListener;
import ConKUeror.domain.controller.NextButtonListener;
import ConKUeror.domain.controller.RollDieListener;
import ConKUeror.domain.controller.TerritoryButtonListener;
import ConKUeror.domain.enums.GameMode;
import ConKUeror.domain.model.Army.Army;
import ConKUeror.domain.model.Army.Artillery;
import ConKUeror.domain.model.Army.Cavalry;
import ConKUeror.domain.model.Army.Infantry;
import ConKUeror.domain.model.Board.ArmyCard;
import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Board.Card;
import ConKUeror.domain.model.Board.ChanceCard;
import ConKUeror.domain.model.Board.DiceRoller;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Board.TerritoryCard;
import ConKUeror.domain.model.Board.ArmyCard.ArmyType;
import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerExpert;
import ConKUeror.domain.model.Player.PlayerInventory;

public class GameLogic implements Serializable {

  private Board board;
  private PlayerInventory p;
  private Territory inputTerritory;
  private ArrayList<Territory> inputTerritories = new ArrayList<Territory>();
  private List<MapListener> listeners = new ArrayList<>();
  private List<TerritoryButtonListener> territoryButtonListeners = new ArrayList<>();
  private List<RollDieListener> rollListeners = new ArrayList<>();
  private List<NextButtonListener> nButtonListener = new ArrayList<>();
  private static List<Player> orderedPlayerList;
  private Set<Integer> accessibleTerritoryIds = new HashSet<>();
  public static Map<Integer, Territory> unoccupiedTerritories = new LinkedHashMap<>();
  private Random rand;
  private int territoryOrArmyCard;

  DiceRoller diceRoller = DiceRoller.getDiceRollerInstance();
  private Player playerInTurn;

  private int attackingArmyUnit;
  private EndOfTheGameListener endScreenListener;

  private Player cardPlayer;
  public Boolean selectedButton;
  public GameMode currentGameMode = GameMode.BUILD;
  public static StartMode startMod;

  public Territory[] memory = new Territory[2];
  private int memoryIndex = 0;

  private int phaseIndex = 0;
  private int cardCounter = 0;
  private int armyCardCounter = 0;
  private CardController cardController;

  private static final int UNOCCUPIED_FIXED_SIZE = 42;

  public GameLogic(Board board, StartMode sMode) {

    this.startMod = sMode;
    this.board = board;
    rand = new Random();

  }

  public void waitAndExecute(Runnable action) {
    int delay = 1000; // delay for 1 second.
    Timer timer = new Timer(delay, null);
    ActionListener taskPerformer = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        SwingUtilities.invokeLater(action);
        timer.stop(); // stop the timer after it fires
      }
    };
    timer.addActionListener(taskPerformer);
    timer.start();
  }

  public void executeComputerLoop() {
    System.out.println("Computer LOOP");

    waitAndExecute(() -> {
      computerChanceCard();
    });

    waitAndExecute(() -> {
      computerDeploy();
    });

  }

  public void computerChanceCard() {
    System.out.println("Chance Card methodu");

    increasePhaseIndex();
    moveToOtherPhase();

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
    orderedPlayerList = orderList;
    PlayerExpert.setPlayersList(orderedPlayerList);

  }

  public void setForMapInitalization() throws InterruptedException {
    System.out.println("set For Map Initalization");
    String playerType = this.playerInTurn.getType();
    System.out.println("player Type" + playerType);
    playerInTurn = PlayerExpert.getPlayerInTurn();
    if (playerType.equals("Computer")) {
      computerChoosesTerritory();
    }

  }

  public void computerDeploy() {

    List<Territory> ownedTerritories = playerInTurn.inv.getOwnedTerritories();
    int botDeployIndex = rand.nextInt(ownedTerritories.size());
    Territory deployedTerritory = ownedTerritories.get(botDeployIndex);
    int army = playerInTurn.inv.getTotalArmy();
    playerInTurn.deploy(deployedTerritory, army);
  }

  public void computerChoosesTerritory() throws InterruptedException {

    if (playerInTurn.getInventory().getTotalArmy() > 0 && playerInTurn.getType().equals("Computer")) {

      if (!unoccupiedTerritories.isEmpty()) {

        int size = unoccupiedTerritories.size();

        // int randomTerritoryId = rand.nextInt(UNOCCUPIED_FIXED_SIZE);

        int randomTerritoryId = getRandomTerritoryId();

        Territory t = unoccupiedTerritories.get(randomTerritoryId);

        while (t == null || t.isDeleted()) {

          randomTerritoryId = rand.nextInt(unoccupiedTerritories.size());
          t = unoccupiedTerritories.get(randomTerritoryId);
        }

        unoccupiedTerritories.remove(randomTerritoryId);

        playerInTurn.getInventory().removeInfantries(1);
        t.setOwner(playerInTurn);
        t.setColor(playerInTurn.getColor());
        t.addInfantries(1);
        playerInTurn.getInventory().addTerritory(t);
        setTerritoryInfo(t.getId(), playerInTurn.getInventory().getTotalArmy(), playerInTurn.getColor(),
            t.getTotalUnit());

        passToNextPlayer(playerInTurn);

      } else {

        List<Territory> ownedTerritories = playerInTurn.inv.getOwnedTerritories();
        int randomTerritoryId = getRandomTerritoryId2(ownedTerritories);

        Territory t = Board.getTerritoryWithIndex(randomTerritoryId);

        // randomTerritoryId = rand.nextInt(size);
        // t = ownedTerritories.get(randomTerritoryId);

        playerInTurn.getInventory().removeInfantries(1);
        t.addInfantries(1);
        setTerritoryInfo(t.getId(), playerInTurn.getInventory().getTotalArmy(), playerInTurn.getColor(),
            t.getTotalUnit());
        passToNextPlayer(playerInTurn);

      }
      if (playerInTurn.getType().equals("Real")) {
        System.out.println("This is Real Player. Something is not right.");
      } else if (playerInTurn.getInventory().getTotalArmy() <= 0) {
        System.out.println("Computer Player doesnt have army to choose a territory.");
      }

    }

  }

  public void passToNextPlayer(Player p1) throws InterruptedException {
    orderedPlayerList = PlayerExpert.getPlayersList();
    int currentIndex = orderedPlayerList.indexOf(p1);
    PlayerExpert.updatePlayerCount(currentIndex);
    int newIndex = (currentIndex + 1) % orderedPlayerList.size();
    playerInTurn = orderedPlayerList.get(newIndex);
    PlayerExpert.setPlayerInTurn(playerInTurn);

    int oldIndex = currentIndex;
    PlayerExpert.publishPlayerInfoEvent(oldIndex, newIndex, playerInTurn.getColor());
    if (playerInTurn.getType().equals("Computer")) {
      waitAndExecute(() -> {
        try {
          computerChoosesTerritory();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }
  }

  public static int getRandomTerritoryId() {
    List<Integer> territoryIds = new ArrayList<>(unoccupiedTerritories.keySet());

    if (territoryIds.isEmpty()) {
      return -1; // Handle the case when the map is empty
    }

    Random rand = new Random();
    int randomIndex = rand.nextInt(territoryIds.size());
    return territoryIds.get(randomIndex);
  }

  public static int getRandomTerritoryId2(List<Territory> ownedTerritories) {
    // Get the list of territories owned by the player

    if (ownedTerritories.isEmpty()) {
      return -1; // Handle the case when the list is empty
    }

    // Extract IDs from the territories
    List<Integer> territoryIds = new ArrayList<>();
    for (Territory t : ownedTerritories) {
      territoryIds.add(t.getId());
    }

    Random rand = new Random();
    int randomIndex = rand.nextInt(territoryIds.size());
    return territoryIds.get(randomIndex);
  }

  public void setFirstPlayer() {
    this.playerInTurn = orderedPlayerList.get(0);
  }

  public void setPlayerInTurn(Player player) {

    this.playerInTurn = player;
  }

  public GameMode getGameMode() {
    return currentGameMode;
  }

  public void setGameMode(GameMode mode) {
    this.currentGameMode = mode;

  }

  public Territory[] getMemory() {
    return memory;
  }

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

  public void giveNeighborIdsOfSelectedTerritoryButton(List<Integer> neigborIdsList) {
    for (TerritoryButtonListener l : territoryButtonListeners) {
      l.getButtonList(neigborIdsList);
    }

  }

  private void showAvailableAttacks(List<Integer> territoriesAvailableForAttack) {
    for (TerritoryButtonListener l : territoryButtonListeners) {
      l.getButtonList(territoriesAvailableForAttack);
    }
  }

  public void updateTerritory(int buttonID, int army) {
    for (TerritoryButtonListener l : territoryButtonListeners) {
      l.updateTerritory(buttonID, army);
    }
  }

  public void setTerritoryInfo(int ID, int armyUnit, Color color, int territoryArmy) {
    for (TerritoryButtonListener l : territoryButtonListeners) {
      l.setTerritoryButtonInfo(ID, armyUnit, color, territoryArmy);
    }
  }

  public void publishRemoveEvent(TerritoryButton button) {
    for (MapListener l : listeners) {
      l.removeOnboardEvent(button);

    }

  }

  public void publishArmyIncreasedEvent(int i) {
    for (TerritoryButtonListener l : territoryButtonListeners) {
      l.setArmyCount(i);
    }
  }

  public void prepareTerritory(Territory t) {
    board.setTerritory(inputTerritory);

  }
  // this will be changed later as observer pattern

  public void giveFirstPlayer(String playerName) {
    for (RollDieListener l : rollListeners) {
      l.getRollEvent(playerName);
    }
  }

  public void addChanceCard() {
        CardController cc= CardController.getInstance();
    ChanceCard cCard = cc.drawChanceCard(playerInTurn);

    int numberOfDraw = playerInTurn.getInventory().getChanceCardRequest();
    if (numberOfDraw != 0){
        playerInTurn.inv.addChanceCard(cCard);
        ChanceCardWindow window = new ChanceCardWindow(cCard.getName());
        window.createChanceWindow();
    }

    playerInTurn.getInventory().setChanceCardRequest(0);
  }

  public void addTerritoryCard() {
    // cardPlayer = playerInTurn;
        CardController cc  = CardController.getInstance();
        TerritoryCard tCard = cc.drawTerritoryCard(playerInTurn);
        if (tCard != null) {
        int numberOfDraw = playerInTurn.getInventory().getTerrCardRequest();
        for (int i = 0; i < numberOfDraw; i++) {
             if (tCard != null) {
            System.out.println(tCard.getName());

            playerInTurn.inv.addTerritoryCard(tCard);
        }
        }
       playerInTurn.getInventory().setTerrCardRequest(0);}
  }

  public void useTerritoryCard() {

    playerInTurn.inv.useTerritoryCards();

  }

  public void addArmyCard() {
    CardController cc  = CardController.getInstance();
        ArmyCard tCard = cc.drawArmyCard(playerInTurn);
        if (tCard != null) {
        int numberOfDraw = playerInTurn.getInventory().getDrawCardRequest();
        for (int i = 0; i < numberOfDraw; i++) {
             if (tCard != null) {
            System.out.println(tCard.getName());

            playerInTurn.inv.addArmyCard(tCard);
        }
        }
       playerInTurn.getInventory().setDrawCardRequest(0);
    }

  }

  public void useArmyCards(int type) {

    playerInTurn.inv.useArmyCards(type);
    // System.out.println("After Using!");
    // f

  }

  public void roll() {

    // Rollayarak ilk playera karar veriyor
    Player player = diceRoller.getFirstPlayer();
    // bu playerın solundaki playerlari listeye atıyor ve orderli bir player listesi
    // oluşturuyor
    startMod.setOrderedAfterRoll(player);
    // ilk ismi döndürüyor
    giveFirstPlayer(player.getName());

  }

  public void useChanceCard() {
    playerInTurn.inv.useChanceCard();
  }

  public void addCOListener(ChanceObserverListener coListener, Player p1) {
        // for (Player p : PlayerExpert.getPlayersList()) {
        //     p.getInventory().addCOListener(coListener);
        // }
        // PlayerInventory inv1 = p1.getInventory();
        // inv1.addCOListener(coListener);
  }

  public void increasePhaseIndex() {
    if (phaseIndex == 6) {
      territoryOrArmyCard = rand.nextInt(2);
      System.out.println("rand sonucu");
      System.out.println(territoryOrArmyCard);

      // if it is one that means it is Territory Card
      if (territoryOrArmyCard == 1) {
        phaseIndex += 1;

      }

    }

    phaseIndex += 1;
    System.out.println(phaseIndex);

    for (NextButtonListener l : nButtonListener) {
      l.nextPhaseEvent(phaseIndex);
    }
  }

  public void prepareForOtherPlayer() {
    this.phaseIndex = 3;
    setGameMode(GameMode.CHANCECARD);
    for (NextButtonListener l : nButtonListener) {
      l.nextPhaseEvent(phaseIndex);
    }
    orderedPlayerList = PlayerExpert.getPlayersList();
    int currentIndex = orderedPlayerList.indexOf(playerInTurn);
    PlayerExpert.updatePlayerCount(currentIndex);
    int newIndex = (currentIndex + 1) % orderedPlayerList.size();
    playerInTurn = orderedPlayerList.get(newIndex);
    PlayerExpert.setPlayerInTurn(playerInTurn);
    int oldIndex = currentIndex;
    PlayerExpert.publishPlayerInfoEvent(oldIndex, newIndex, playerInTurn.getColor());

    if (playerInTurn.getType().equals("Computer")) {
      executeComputerLoop();
    }

  }

  public int getGamePhaseAsIndex() {
    return this.phaseIndex;

  }

  public void setGamePhaseIndex(int n) {
    this.phaseIndex = n;
  }

  public void setRequestToDefault() {
      //  playerInTurn.getInventory().setDrawCardRequest(1);
        playerInTurn.getInventory().setTerrCardRequest(1);
        playerInTurn.getInventory().setChanceCardRequest(1);
        playerInTurn.getInventory().removeChanceCards();
    }

    public void setArmyReqToDefault() {
         playerInTurn.getInventory().setDrawCardRequest(1);
    }

  public void moveToOtherPhase() {

    if (currentGameMode == GameMode.BUILD) {
      setGameMode(GameMode.CONNECTION);
      Board.initUnoccupiedTerritories();
      unoccupiedTerritories = Board.getUnoccupiedTerritories();

      // unoccupiedTerritories = board.getUnoccupiedTerritories();

    } else if (currentGameMode == GameMode.CONNECTION) {
      setGameMode(GameMode.START);

    } else if (currentGameMode == GameMode.START) {
      setGameMode(GameMode.CHANCECARD);
      if (playerInTurn.getType().equals("Computer")) {
        executeComputerLoop();
        this.phaseIndex = 3;
      }

    } else if (currentGameMode == GameMode.CHANCECARD) {
      setGameMode(GameMode.DEPLOY);
      DeployMode.setDeployedArmy(playerInTurn);
      System.out.println("move to other phase deki method");

      int player_index = PlayerExpert.getPlayersList().indexOf(playerInTurn);
      PlayerExpert.updatePlayerCount(player_index);
    } else if (currentGameMode == GameMode.DEPLOY) {
      setGameMode(GameMode.ATTACK);
    }

    else if (currentGameMode == GameMode.ATTACK) {
      memory[0] = null;
      memory[1] = null;

      setGameMode(GameMode.FORTIFY);
    } else if (currentGameMode == GameMode.FORTIFY) {
      if (territoryOrArmyCard == 1) {
        setGameMode(GameMode.TERRITORYCARD);

      } else {
        setGameMode(GameMode.ARMYCARD);

      }

    }

  }

  public void prepareGame(Territory t, GameMode gameMode) throws InterruptedException {

    if (playerInTurn == null || playerInTurn.getType().equals("Real")) {

      switch (gameMode) {

        case BUILD:

          this.inputTerritory = t;
          this.phaseIndex = 0;
          prepareTerritory(t);
          addToMemory(t);

          break;

        case CONNECTION:
          this.inputTerritory = t;
          this.phaseIndex = 1;
          Map<Integer, Territory> adjList = t.getAdjacencyList();
          List<Integer> neighborIds = new ArrayList<Integer>();
          for (Map.Entry<Integer, Territory> set : adjList.entrySet()) {
            int territoryId = set.getKey();
            neighborIds.add(territoryId);
          }
          giveNeighborIdsOfSelectedTerritoryButton(neighborIds);

          break;

        case START:
          this.inputTerritory = t;
          this.phaseIndex = 2;
          if (playerInTurn.getInventory().getTotalArmy() > 0) {

            if (t.getOwner() == null) {
              playerInTurn.getInventory().removeInfantries(1);
              t.setOwner(playerInTurn);
              t.addInfantries(1);
              playerInTurn.getInventory().addTerritory(t);
              setTerritoryInfo(t.getId(), playerInTurn.getInventory().getTotalArmy(), playerInTurn.getColor(),
                  t.getTotalUnit());

              unoccupiedTerritories.remove(t.getId());

              passToNextPlayer(playerInTurn);

            } else if (t.getOwner() == playerInTurn) {
              playerInTurn.getInventory().removeInfantries(1);
              t.addInfantries(1);
              setTerritoryInfo(t.getId(), playerInTurn.getInventory().getTotalArmy(), playerInTurn.getColor(),
                  t.getTotalUnit());

              passToNextPlayer(playerInTurn);

            }

          }

          break;

        case CHANCECARD:
          System.out.println("Card");

          this.phaseIndex = 3;
          this.inputTerritory = t;
          prepareTerritory(t);
          break;

        case DEPLOY:
          System.out.println("Deploy");
          this.phaseIndex = 4;
          this.inputTerritory = t;

          if (t.getOwner() == playerInTurn) {
            prepareTerritory(t);

          }

          break;

        case ATTACK:
          System.out.println("Attack");
          this.phaseIndex = 5;
          this.inputTerritory = t;

          // Map<Integer,Territory> adjacentList = t.getAdjacencyList();
          List<Integer> territoriesAvailableForAttack = new ArrayList<Integer>();
          t.checkAvailableAttacks(territoriesAvailableForAttack);
          showAvailableAttacks(territoriesAvailableForAttack);
          addToMemory(t);
          break;

        case FORTIFY:
          System.out.println("Fortify ");
          this.phaseIndex = 6;

          if (t.getOwner() == playerInTurn) {

            if (memory[0] != null && memory[0].getId() != t.getId() && accessibleTerritoryIds.contains(t.getId())) {
              memory[1] = t; // Add the green territory to the second slot of memory
              FortifyMode.setCanFortify();

            }

            else if (memory[0] == null || memory[0].getId() != t.getId()) {
              this.inputTerritory = t;
              Set<Integer> visited = new HashSet<>();
              List<Integer> ownedTerritoryIds = new ArrayList<>();
              dfs(inputTerritory, visited, ownedTerritoryIds);
              accessibleTerritoryIds.clear();
              accessibleTerritoryIds.addAll(ownedTerritoryIds);
              giveNeighborIdsOfSelectedTerritoryButton(ownedTerritoryIds);
              memory[0] = t; // Add the territory to memory
              memory[1] = null; // Reset the second slot of memory
            }

          }
          // If the territory is not owned by the current player, clear the memory
          else {
            memory[0] = null;
            memory[1] = null;
            accessibleTerritoryIds.clear();
          }

          break;

        case ARMYCARD:
          System.out.println("Army Card Phase test");
          System.out.println("Territory Card Phase test");
          this.phaseIndex = 7;
          break;

        case TERRITORYCARD:
          System.out.println("Territory Card Phase test");
          this.phaseIndex = 8;
          break;

      }

    }

  }

  private void dfs(Territory territory, Set<Integer> visited, List<Integer> ownedTerritoryIds) {
    // Check if this territory has already been visited or not owned by the player
    int territoryId = territory.getId();
    if (visited.contains(territoryId) || territory.getOwner() != playerInTurn) {
      return;
    }

    // Mark the current node as visited and add it to our list
    visited.add(territoryId);
    ownedTerritoryIds.add(territoryId);

    // Get the adjacency list for the current territory
    Map<Integer, Territory> adjList = territory.getAdjacencyList();

    // Recursively visit all the neighbors
    for (Map.Entry<Integer, Territory> set : adjList.entrySet()) {
      Territory neighbor = set.getValue();
      dfs(neighbor, visited, ownedTerritoryIds);
    }
  }

  public int getAttackingArmyUnit() {
    return attackingArmyUnit;
  }

  public void setAttackingArmyUnit(int attackingArmyUnit) {
    this.attackingArmyUnit = attackingArmyUnit;
  }

  public boolean setForAttack(List<Infantry> attackingInfantries, List<Cavalry> attackingCavalries,
      List<Artillery> attackingArtilleries) throws IOException {

    boolean attackResult = false;
    // int attackingArmyUnits = attackingArmyUnit;
    if (!memory[0].getOwner().equals(memory[1].getOwner())) {
      try {
        Army defendingArmy = memory[1].getArmy();
        attackResult = playerInTurn.attack(attackingInfantries, attackingCavalries, attackingArtilleries,
            defendingArmy);
      } catch (NullPointerException e) {
        System.out.println("Please choose an attack target");
      }

      if (attackResult) {
        memory[1].getOwner().removeTerritory(memory[1]);
        if (!PlayerExpert.checkIfAnyTerritoryLeft(memory[1].getOwner()))
          ;
        {
          if (PlayerExpert.getPlayersList().size() == 1) {
            Player winner = PlayerExpert.getPlayersList().get(0);
            publishEndOfTheGameEvent(winner);
          }
        }

      }

    }
    return attackResult;
  }

  public void publishAttackResultEvent(boolean attackResult) {
    for (TerritoryButtonListener l : territoryButtonListeners) {
      l.updateAfterAttack(attackResult, playerInTurn, memory[0], memory[1]);
    }
  }

  public void setEndOfTheGameListener(EndOfTheGameListener endScreenListener) {
    this.endScreenListener = endScreenListener;
  }

  public void publishEndOfTheGameEvent(Player player) throws IOException {
    endScreenListener.resolveGame(player);
  }

}