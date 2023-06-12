package ConKUeror.domain.model.Modes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ConKUeror.domain.controller.CardController;
import ConKUeror.domain.controller.HandlerFactory;
import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerExpert;

public class StartMode implements Serializable {
    private int playerCount;
    private BuildMode buildMode;
    private static List<Player> orderedPlayerList;

    public StartMode(BuildMode buildMode) {

        this.buildMode=buildMode;


    }

    public void setStartMode() {
    setOrderedList();
        setInitialInfantries();
        setPlayerCount();
        HandlerFactory controller = HandlerFactory.getInstance();
        CardController cardController = controller.giveCardController();
        cardController.initializeDeck(orderedPlayerList,42); // just created 4 territory based.

    }

    public void setPlayerCount() {
        this.playerCount= buildMode.getPlayerCount();
    }
    public int getPlayerCount() {
        return playerCount;
    }

    public void setInitialInfantries() {
            int inf_count;
          int playerCount =  buildMode.getPlayerCount();
            if(playerCount == 2) {
                inf_count=5;
            } else if( playerCount==3) {
                inf_count = 16;
            }else if( playerCount==4) {
                inf_count = 30;
             }else if( playerCount==5) {
                inf_count = 25;
             }else if (playerCount ==6){
                inf_count = 20;
                }
                else {
                    inf_count=-1;
                }

            for(Player player : buildMode.getPlayers()) {
                player.getInventory().addInfantries(inf_count);
            }




    }

//viewa taşı,teknik olarak view la alakalı bir fonksiyon
    public String getInitialMessage() {

        String message = "You can select the territories that you don't want to be in the game! \n "+
        "Click the territory and press Remove Button.\n  " +
        "When you are ready press the Next Button";

        return message;

    }




    public void setOrderedList() {

        //arrange orderedPlayerList according to order array

        orderedPlayerList = buildMode.getPlayers();


    }

    public static List<Player> getOrderedPlayerList() {

        return orderedPlayerList;
    }




    public void setOrderedAfterRoll(Player player) {
        int index = orderedPlayerList.indexOf(player);
        List<Player> orderList = new ArrayList<Player>();

        for (int i = 0; i < orderedPlayerList.size(); i++) {
            orderList.add(orderedPlayerList.get((index - i + orderedPlayerList.size() ) % orderedPlayerList.size()));
        }


        orderedPlayerList = orderList;
        GameLogic.setGameOrderList(orderedPlayerList);
        PlayerExpert.setPlayersList(orderedPlayerList);
        PlayerExpert.setPlayerInTurn(orderedPlayerList.get(0));
    }

    public static void setOrderedList(List<Player> playerList) {
        orderedPlayerList = playerList;
    }





}
