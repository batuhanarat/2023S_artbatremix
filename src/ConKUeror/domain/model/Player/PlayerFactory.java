package ConKUeror.domain.model.Player;


import java.util.List;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import ConKUeror.domain.model.Player.Strategies.ComputerPlayer.ComputerPlayerAttack;
import ConKUeror.domain.model.Player.Strategies.ComputerPlayer.ComputerPlayerDeploy;
import ConKUeror.domain.model.Player.Strategies.ComputerPlayer.ComputerPlayerFortify;
import ConKUeror.domain.model.Player.Strategies.RealPlayer.RealPlayerAttack;
import ConKUeror.domain.model.Player.Strategies.RealPlayer.RealPlayerDeploy;
import ConKUeror.domain.model.Player.Strategies.RealPlayer.RealPlayerFortify;

public class PlayerFactory implements Serializable {

    //PlayerFactory is implemented with singleton pattern.

    private static PlayerFactory instance;
    private PlayerFactory() {};
    private List<String> playerNames = new ArrayList<String>();
    private transient List<Color> playerColors;
    private int colorIndex= 0;

    public static PlayerFactory getInstance() {
        if(instance==null) {
            instance = new PlayerFactory();
        }
        return instance;

    }

    public void setColorList(List<Color> colors) {
        this.playerColors = colors;

    }

    public Player createPlayer(String type,String name) {

        Player player=null;

        if (name == null) {
            throw new IllegalArgumentException("Player name cannot be null");
        }

       if(type.equals("Real Player")) {

           PlayerInventory inv = new PlayerInventory();
           RealPlayerDeploy db =new RealPlayerDeploy();
           RealPlayerAttack ab =new RealPlayerAttack();
           RealPlayerFortify fb =new RealPlayerFortify();

           if (searchName(name) && (name.length() != 0)) {

            player = new Player(name,db,ab,fb,inv,type);

            player.setType("Real");

            setColorToPlayer(player);
            playerNames.add(name);
           }

       }

       else if (type.equals("Computer Player")) {

            PlayerInventory inv = new PlayerInventory();
            ComputerPlayerDeploy db = new ComputerPlayerDeploy();
            ComputerPlayerAttack ab = new ComputerPlayerAttack();
            ComputerPlayerFortify fb = new ComputerPlayerFortify();


            player = new Player(name, db, ab, fb,inv,type);


            player.setType("Computer");


            setColorToPlayer(player);

       }

       else {
        throw new IllegalArgumentException("Invalid player type: " + type);
       }

       return player;


    }

    public void setColorToPlayer(Player player) {
        player.setColor(playerColors.get(colorIndex));
        colorIndex++;

    }

    public boolean searchName(String playerName) {
        for (int i = 0; i < playerNames.size(); i++) {
            if (playerName.equals(playerNames.get(i))) {
                return false;
            }
        }

        return true;
    }

    public void createColoredPlayer(String string, String name, Color color) {
        if (name == null) {
            throw new IllegalArgumentException("Player name cannot be null");
        }
        Player player=null;


           PlayerInventory inv = new PlayerInventory();
           RealPlayerDeploy db =new RealPlayerDeploy();
           RealPlayerAttack ab =new RealPlayerAttack();
           RealPlayerFortify fb =new RealPlayerFortify();

           if (searchName(name) && (name.length() != 0)) {

            player = new Player(name,db,ab,fb,inv,"Real Player");

            player.setType("Real");

            playerNames.add(name);




    }

    }


}
