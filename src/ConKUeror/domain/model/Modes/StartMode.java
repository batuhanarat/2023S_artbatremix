package src.ConKUeror.domain.model.Modes;

import src.ConKUeror.domain.model.Player.Player;

public class StartMode {
    private int playerCount;
    private BuildMode buildMode;
    
    public StartMode(BuildMode buildMode) {

        this.buildMode=buildMode;
        this.playerCount = buildMode.getPlayerCount();
        setInitialInfantries();
    }


    public void setInitialInfantries() {
            int inf_count;
        
            if(playerCount == 2) {
                inf_count=40;
            } else if( playerCount==3) {
                inf_count = 35;
            }else if( playerCount==4) {
                inf_count = 30;
             }else if( playerCount==5) {
                inf_count = 25;
             }else {
                inf_count = 20;
                }

            for(Player player : buildMode.getPlayers()) {
                player.getInventory().addInfantries(inf_count);
            }
           
                   


    }
  

    public String getInitialMessage() {

        String message = "You can select the territories that you don't want to be in the game! \n "+
        "Click the territory and press Remove Button.\n  " + 
        "When you are ready press the Next Button";
    
        return message;
    
    }





}
