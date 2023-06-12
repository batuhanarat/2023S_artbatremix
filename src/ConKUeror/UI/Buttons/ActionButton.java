package ConKUeror.UI.Buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JButton;

import ConKUeror.domain.controller.ButtonHandler;
import ConKUeror.domain.controller.HandlerFactory;

public class ActionButton extends JButton implements ActionListener,Serializable {

    private String _name;
    private int[] id;
    ButtonHandler buttonHandler;

    public ActionButton(String buttonName, Font labelFont, int[] id, Color color) {
        super(buttonName);
        this.id = id;
        setFont(labelFont);
        this._name = buttonName;
        this.setName(_name);
        HandlerFactory controller = HandlerFactory.getInstance();
        buttonHandler = controller.giveButtonHandler();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        // This phase is for build mode
        switch (id[0]) {
            case 0:
                switch (id[1]) {
                    case 0:
                        buttonHandler.addConnection();
                        break;
                    case 1:
                        buttonHandler.removeButton();
                        break;
                    case 2:
                        buttonHandler.nextPhase();
                        break;
                }
                break;
            // this phase is just for test to see connections
            case 1:
                switch (id[1]) {
                    case 0:
                        // do nothing
                        break;
                    case 1:
                        // do nothing
                        break;
                    case 2:
                        buttonHandler.nextPhase();
                        break;

                }
                break;
            // this phase is for start game
            case 2:
                switch (id[1]) {
                    case 0:
                        try {
                            buttonHandler.rollButton();
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        break;
                    case 1:
                        // Code to execute if the first element is 2 and the second is 1
                        break;
                    case 2:
                        buttonHandler.nextPhase();
                        break;
                }
                break;
            // This phase is for pick chance card
            case 3:
                switch (id[1]) {
                    case 0:
                        buttonHandler.showChanceCardInfo();
                        break;
                    case 1:
                        // Code to execute if the first element is 2 and the second is 1
                        buttonHandler.useChanceCard();
                        break;
                    case 2:
                        buttonHandler.nextPhase();
                        buttonHandler.setRequestToDefault();

                        break;
                }
                break;
            // This phase is for deploy phase
            case 4:
                switch (id[1]) {
                    case 0:
                        buttonHandler.deploy();
                        break;
                    case 1:
                        buttonHandler.chooseDeployArmy();
                        break;
                    case 2:
                        buttonHandler.nextPhase();
                        break;
                }
                break;
            // attack
            case 5:
                switch (id[1]) {
                    case 0:
                        try {
                            buttonHandler.attack();
                        } catch (IOException e1) {

                        }
                        break;
                    case 1:
                        buttonHandler.increaseArmyCount();
                        break;
                    case 2:
                        buttonHandler.nextPhase();
                        break;
                }
                break;
            // fortify
            case 6:
                switch (id[1]) {
                    case 0:
                        buttonHandler.fortify();
                        break;
                    case 1:
                        buttonHandler.chooseFortifyArmy();
                        break;
                    case 2:
                        buttonHandler.nextPhase();
                        break;
                }
                break;
            case 7:
                switch (id[1]) {
                    case 0:
                        buttonHandler.addArmyCard();
                        break;
                    case 1:
                        buttonHandler.useArmyCards();
                        break;
                    case 2:
                        buttonHandler.endTurn();
                        buttonHandler.setArmyToDefault();
                        break;
                }
                break;
            case 8:
                switch (id[1]) {
                    case 0:
                        buttonHandler.addTerritoryCard();
                        break;
                    case 1:
                        buttonHandler.useTerritoryCards();
                        break;
                    case 2:
                        buttonHandler.endTurn();
                        buttonHandler.setArmyToDefault();
                        break;
                }
                break;

        }

    }

}
