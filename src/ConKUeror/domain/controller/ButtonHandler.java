package ConKUeror.domain.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

//import com.apple.laf.resources.aqua;

import ConKUeror.UI.Buttons.TerritoryButton;
import ConKUeror.UI.Frames.MapView;
import ConKUeror.UI.Panels.ArmyCardWindow;
import ConKUeror.UI.Panels.ArmySelectionPanel;
import ConKUeror.UI.Panels.AttackingArmyPanel;
import ConKUeror.UI.Panels.ChanceCardWindow;
import ConKUeror.UI.Panels.PlayerInteractionPanel;
import ConKUeror.domain.enums.GameMode;
import java.io.IOException;
import java.io.Serializable;

import ConKUeror.domain.model.Army.Artillery;
import ConKUeror.domain.model.Army.Cavalry;
import ConKUeror.domain.model.Army.Infantry;
import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Modes.BuildMode;
import ConKUeror.domain.model.Modes.DeployMode;
import ConKUeror.domain.model.Modes.FortifyMode;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerExpert;
import ConKUeror.domain.model.Board.Die;
import ConKUeror.domain.model.Board.DiceRoller;

public class ButtonHandler implements Serializable  {
    private static ButtonHandler instance;
    private BuildMode bMode;
    private GameLogic gMode;
    private Territory[] memory;
    private TerritoryButton selectedButton;

    private ArrayList<Infantry> attackingInfantries;
    private ArrayList<Cavalry> attackingCavalries;
    private ArrayList<Artillery> attackingArtilleries;

    private ButtonHandler(BuildMode bMode, GameLogic gMode) {
        this.bMode = bMode;
        this.gMode = gMode;

    }

    public static ButtonHandler getInstance(BuildMode bMode, GameLogic gMode) {
        if (instance == null) {
            instance = new ButtonHandler(bMode, gMode);
        }
        return instance;
    }

    public void matchButtonWithTerritory(int id) throws InterruptedException {
        Territory t = Board.getTerritoryWithIndex(id);
        // System.out.println(t.getId());
        System.out.println(gMode.getGameMode());
        gMode.setPlayerInTurn(PlayerExpert.getPlayerInTurn());
        gMode.prepareGame(t, gMode.getGameMode());

    }

    public void addToMemory(int id) {
        Territory t = Board.getTerritoryWithIndex(id);
        gMode.addToMemory(t);
    }

    public Territory[] getMemoryList() {
        return gMode.getMemory();
    }

    public void selectButton(TerritoryButton button) {

        selectedButton = button;

    }

    public void chooseDeployArmy() {

        ArmySelectionPanel armySelectionPanel = new ArmySelectionPanel("Choose Army");
        armySelectionPanel.setMaxValue(DeployMode.getMaxValue());
        armySelectionPanel.createSlider();

        Object[] options = { "OK", "Cancel" };
        int result = JOptionPane.showOptionDialog(null, armySelectionPanel, "Deploy",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        int deployedArmy = armySelectionPanel.getValue();
        DeployMode.setDeployedArmy(deployedArmy);

    }

    public void chooseFortifyArmy() {

        if (FortifyMode.canFortify()) {

            memory = gMode.getMemory();
            ArmySelectionPanel armySelectionPanel = new ArmySelectionPanel("Choose Army");
            armySelectionPanel.setMaxValue(FortifyMode.getMaxValue(memory[0]));
            armySelectionPanel.createSlider();

            Object[] options = { "OK", "Cancel" };
            int result = JOptionPane.showOptionDialog(null, armySelectionPanel, "Fortify",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            int fortifiedArmy = armySelectionPanel.getValue();
            FortifyMode.setFortifiedArmy(fortifiedArmy);
        } else {
            memory = gMode.getMemory();
            System.out.println("Memory slot 0" + memory[0]);
            System.out.println("Memory slot 1" + memory[1]);

            System.out.println("Sorry, cant fortify");
        }

    }

    public void addConnection() {

        // System.out.prinzt("add connection methodundayım");

        Territory[] territories = gMode.getMemory();
        territories[0].addConnectionDual(territories[1]);

        Map<Integer, Territory> tests = territories[0].getAdjacencyList();
        // System.out.println("komsularim basladi");

        for (Map.Entry<Integer, Territory> set : tests.entrySet()) {
            int territoryId = set.getKey();
            System.out.println(territoryId);
        }
        // System.out.println("komsularim bitti");

    }

    

    public int getArmyUnitFromInputTerritory() {
        int i = 0;
        try {
            i = gMode.getMemory()[0].getTotalUnit();
        } catch (NullPointerException e) {

        }
        return i;
    }

    public void increaseArmyCount() {

        AttackingArmyPanel attackingArmyPanel = new AttackingArmyPanel("Choose Army Units");

        // i had to put these because otherwise territories without any cavalry or
        // artillery create a problem
        attackingArmyPanel.setMaxInfantryValue(gMode.memory[0].getArmy().getInfantryList().size() - 1);
        attackingArmyPanel.setMaxCavalryValue(gMode.memory[0].getArmy().getCavalryList().size());
        attackingArmyPanel.setMaxArtilleryValue(gMode.memory[0].getArmy().getArtilleryList().size());

        attackingArmyPanel.createSlider();

        Object[] options = { "OK", "Cancel" };
        JOptionPane.showOptionDialog(null, attackingArmyPanel, "Choose Army Units", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        int i = 0;
        this.attackingInfantries = new ArrayList<Infantry>();
        while (attackingInfantries.size() < attackingArmyPanel.getInfantryValue()) {
            attackingInfantries.add(gMode.memory[0].getArmy().getInfantryList().get(i));
            i++;
        }

        i = 0;
        this.attackingCavalries = new ArrayList<Cavalry>();
        while (attackingCavalries.size() < attackingArmyPanel.getCavalryValue()) {
            attackingCavalries.add(gMode.memory[0].getArmy().getCavalryList().get(i));
            i++;
        }

        i = 0;
        this.attackingArtilleries = new ArrayList<Artillery>();
        while (attackingArtilleries.size() < attackingArmyPanel.getArtilleryValue()) {
            attackingArtilleries.add(gMode.memory[0].getArmy().getArtilleryList().get(i));
            i++;
        }

        i = 0;
         System.out.print("increase army count methodundayım");
         int ai = attackingInfantries.size();
         int ac = attackingCavalries.size() * 5;
        int aa = attackingArtilleries.size() * 10;
        int totalp = ai + ac + aa;
         gMode.publishArmyIncreasedEvent(totalp);
        


    }

    public void attack() throws IOException {
        boolean attackResult = gMode.setForAttack(attackingInfantries, attackingCavalries, attackingArtilleries);
        gMode.publishAttackResultEvent(attackResult);
        System.out.println(attackResult);
    }

    public void deploy() {

        Player playerInTurn = PlayerExpert.getPlayerInTurn();
        Territory t = Board.getCurrentTerritory();
        int army = DeployMode.getDeployedArmy();
        playerInTurn.deploy(t, army);

    }

    public void fortify() {
        Player playerInTurn = PlayerExpert.getPlayerInTurn();
        memory = getMemoryList();

        Territory fortifyFrom = memory[0];
        Territory fortifyTo = memory[1];

        if (FortifyMode.isValidForFortify(fortifyFrom)) {
            int army = FortifyMode.getFortifiedArmy();
            playerInTurn.fortify(fortifyFrom, fortifyTo, army);
        }

        // int army = FortifyMode.getFortifiedArmy();

    }

    public Player getPlayerInTurn() {
        return PlayerExpert.getPlayerInTurn();
    }

    public Color getPlayerColor() {

        return PlayerExpert.getPlayerInTurn().getColor();

    }

    public void removeButton() {
        getBoard().removeTerritory();
        gMode.publishRemoveEvent(selectedButton);
        ;

    }

    public void rollButton() throws InterruptedException {

        gMode.roll();
        gMode.setFirstPlayer();
        gMode.setForMapInitalization();

    }

    public Board getBoard() {
        return gMode.getBoard();
    }

    public BuildMode getBuildMode() {
        return bMode;
    }

    public void nextPhase() {
        gMode.increasePhaseIndex();
        gMode.moveToOtherPhase();

    }

    public void setRequestToDefault() {
        gMode.setRequestToDefault();
    }

    public void setArmyToDefault() {
        gMode.setArmyReqToDefault();
    }

    public void endTurn() {
        gMode.prepareForOtherPlayer();
    }

    public int getPhaseIndex() {
        return gMode.getGamePhaseAsIndex();
    }

    public int getXFromList(int i) {

        return bMode.getCoordinateList().get(i).getX();
    }

    public int getYFromList(int i) {

        return bMode.getCoordinateList().get(i).getY();

    }

    public void registerAsTerritoryListener(TerritoryButtonListener territoryListener) {
        gMode.addTerritoryButtonListener(territoryListener);
        // loginFrame.add
    }

    public void registerAsTerritoryListenerPINV(TerritoryButtonListener territoryListener) {
        for (Player p : PlayerExpert.getPlayersList()) {
            p.getInventory().addTerritoryButtonListener(territoryListener);
        }
    }

    public void registerAnimationListener(AnimationMapListener aMapListener) {
        DiceRoller.getDiceRollerInstance().addAniMapListener(aMapListener);
    }

    public void showChanceCardInfo() {
        // ChanceCardWindow window = new ChanceCardWindow("This is a coup card, select a
        // territory and pick use!");
        // window.createChanceWindow();
        gMode.addChanceCard();

    }

    public void useChanceCard() {
        gMode.useChanceCard();
    }

    public void registerAsRollListener(RollDieListener rollDieListener) {
        gMode.addRollDieListener(rollDieListener);

    }

    public void registerNextAsListener(PlayerInteractionPanel pPanel) {
        gMode.addNButtonListener(pPanel);
    }

    public void addTerritoryCard() {
        gMode.addTerritoryCard();
    }

    public void useTerritoryCards() {
        gMode.useTerritoryCard();
    }

    public void addArmyCard() {
        gMode.addArmyCard();
    }

    public void useArmyCards() {
        ArmyCardWindow window = new ArmyCardWindow("Choose your convert!", new ArmyCardListener() {

            @Override
            public void onCardSelected(int index) {
                // TODO Auto-generated method stub
                System.out.println(index);
                if (index != 0) {
                    gMode.useArmyCards(index);
                }
            }

        });
        window.createWindow();

    }

    public void setAttackingArmyCount(int armyCount) {
        gMode.setAttackingArmyUnit(armyCount);
    }

    public void registerAsEndScreenListener(EndOfTheGameListener endScreenListener) {
        gMode.setEndOfTheGameListener(endScreenListener);
    }

}