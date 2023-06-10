
package ConKUeror.domain.controller;



import java.awt.Color;

import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Modes.BuildMode;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.domain.model.Modes.StartMode;


public class BuildHandler {

private static BuildHandler instance;
private Boolean isFirstConfirmClick = true;
private BuildMode buildMode;
private Board board;
private GameLogic gamelogic;
private StartMode sMode;

    private BuildHandler(BuildMode bMode) {
        this.buildMode = bMode;

	}
	public static BuildHandler getInstance(BuildMode buildMode) {
        if (instance == null) {
            instance = new BuildHandler(buildMode);
        }
        return instance;
    }

	public void registerAsListener(BuildModeListener listener) {
			buildMode.addBuildModeListener(listener);
	}

	public void handlePlayerCounts(int totalNum, int botNum ) {

			Boolean isPlayerCountsValid = buildMode.validatePlayerNums(totalNum, botNum);
			if(isPlayerCountsValid && isFirstConfirmClick) {
				buildMode.setPlayerIndex();
				isFirstConfirmClick = false;
			}

	}

		public  Color handlePlayerColors(String colorName) {
			colorName = colorName.toLowerCase();
			switch (colorName) {
				case "magenta":
					return Color.MAGENTA;
				case "white":
					return Color.WHITE;
				case "pink":
					return Color.PINK;
				case "yellow":
					return Color.YELLOW;
				case "minty":
					return new Color(128, 0, 128); // Custom purple color
				case "orange":
					return Color.ORANGE;
				default:
					throw new IllegalArgumentException("Invalid color name: " + colorName);
			}
		}

	


	public void enterNameForRealPlayers(String name) {
		buildMode.initalizePlayer(name,"Real Player");
	}


	public void initalizeBots(int botPlayerCount) {

		for (int i = 1; i <= botPlayerCount; i++) {
			String name = "Comp" +i;
			buildMode.initalizePlayer(name, "Computer Player");
		}

	}

	public void openPlayerSelection() {
		buildMode.setPlayerIndex();
	}

	public void setStartStatus() {
		buildMode.setStart();
	}


	public void initializeGame() {
		Board.initAllTerritoriesAndContinents();
		buildMode.initalizeConnections();
		HandlerFactory controller = HandlerFactory.getInstance();
		StartHandler startHandler =controller.giveStartHandler();
		startHandler.setStartMode();
    }







}
