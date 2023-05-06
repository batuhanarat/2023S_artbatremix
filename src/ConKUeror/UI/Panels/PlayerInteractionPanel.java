package src.ConKUeror.UI.Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import src.ConKUeror.UI.Buttons.ActionButton;
import src.ConKUeror.domain.controller.BuildHandler;
import src.ConKUeror.domain.controller.ButtonHandler;
import src.ConKUeror.domain.controller.GameHandler;
import src.ConKUeror.domain.controller.NextButtonListener;
import src.ConKUeror.domain.controller.TerritoryButtonListener;
import src.ConKUeror.domain.model.Modes.GameLogic;

public class PlayerInteractionPanel extends JPanel implements NextButtonListener,TerritoryButtonListener{


    private ButtonHandler buttonHandler;
    private GameHandler gameHandler;
    private List<ActionButton> actionButtonsList = new ArrayList<ActionButton>();
    int buttons_num = 3;
    List<NextButtonListener> nextButtonList;
    Border blackBorder;
    Border padding;
    int panelWidth;
    int panelHeight;
    int p_index;
    // JPanel interactionPanelContainer;

    public String[][] buttonNames =  {
        {"Add Connections", "Remove Territory", "Next"},
        {"Test","Test","Next"},
        {"Set Game Order","Place Army","Next"},
        {"Pick Chance Card","Card Info","Next"},
        {"Deploy Army","Select Army","Next"},
        {"Attack","Select Army","Next"},
        {"Fortify Army","Select Army" ,"Next"},
        {"Fortify Army","Select Army" ,"End Turn"},
        {"Pick Army Card","Card Info", "End Turn"},
        {"Pick Territory Card","Card Info","End Turn"}};

    public PlayerInteractionPanel(ButtonHandler buttonHandler, GameHandler gameHandler) {

        this.buttonHandler = buttonHandler;
        this.gameHandler = gameHandler;

       // buttons_num = GameLogic.getButtonCount();
        setLayout(new GridLayout(buttons_num, 1));

        setUI();
        setPanel();
        addNextButtonObserver() ;
        addPlayerInteractionPanelListenerForArmyPlacement();
       }

    public void addNextButtonObserver() {
        buttonHandler.registerNextAsListener(this);
    }

    public void addPlayerInteractionPanelListenerForArmyPlacement() {
        buttonHandler.registerAsTerritoryListener(this);
    }



       public void setUI() {

        blackBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);

            panelWidth = 130;
            panelHeight = 300;

           int startX = 30;
           int startY = 420;

          setBounds(startX, startY, panelWidth , panelHeight);
          setPreferredSize(new Dimension(panelWidth, panelHeight));

      }



/*
public void setPanel() {

    for(int i = 0 ; i<buttons_num ; i++) {

         interactionPanelContainer = new JPanel(new BorderLayout());
        interactionPanelContainer.setBorder(BorderFactory.createCompoundBorder(blackBorder, padding));
        interactionPanelContainer.setBackground(Color.WHITE);

       //  p_index = GameLogic.getGamePhaseAsIndex();
        p_index = buttonHandler.getPhaseIndex();
        String buttonName = buttonNames[p_index][i];
        int[] id = {p_index,i};
        System.out.println(p_index + "HEEEEEEEEEEEY");

        Font labelFont = new Font("Arial", Font.PLAIN, 10);


        ActionButton actionButton = new ActionButton(buttonName, labelFont, id ,Color.WHITE);
        actionButton.addActionListener(actionButton);


        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);

        interactionPanelContainer.add(actionButton,BorderLayout.CENTER);
        interactionPanelContainer.add(emptyPanel, BorderLayout.SOUTH);

        add(actionButton);

    }


}
*/

public void setPanel() {
    // Remove all components from the PlayerInteractionPanel instance
    this.removeAll();

    for (int i = 0; i < buttons_num; i++) {
        JPanel interactionPanelContainer = new JPanel(new BorderLayout());
        interactionPanelContainer.setBorder(BorderFactory.createCompoundBorder(blackBorder, padding));
        interactionPanelContainer.setBackground(Color.WHITE);

        // p_index = GameLogic.getGamePhaseAsIndex();
        p_index = buttonHandler.getPhaseIndex();
        String buttonName = buttonNames[p_index][i];
        int[] id = {p_index, i};

        Font labelFont = new Font("Arial", Font.PLAIN, 10);

        ActionButton actionButton = new ActionButton(buttonName, labelFont, id, Color.WHITE);
        actionButton.addActionListener(actionButton);
        actionButtonsList.add(actionButton);

        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);

        interactionPanelContainer.add(actionButton, BorderLayout.CENTER);
        interactionPanelContainer.add(emptyPanel, BorderLayout.SOUTH);

        // Add the interactionPanelContainer to the PlayerInteractionPanel instance
        this.add(interactionPanelContainer);
    }
}


public void addTerritoryButtonListener() {

    buttonHandler.registerAsTerritoryListener(this);
}


public ActionButton getActionButtonWithIndex(int i) {


    return  actionButtonsList.get(i);

}




@Override
public void nextPhaseEvent(int i) {
    // TODO Auto-generated method stub

    setPanel();
    revalidate();
    repaint();


}

@Override
public void getButtonList(List<Integer> neigborIdsList) {
    // TODO Auto-generated method stub
    //reserve for atack
}

@Override
public void setTerritoryButtonInfo(int buttonID, int armyUnit, Color color,int territoryArmy) {
    // TODO Auto-generated method stub



    getActionButtonWithIndex(7).setText(Integer.toString(armyUnit));

}

}










