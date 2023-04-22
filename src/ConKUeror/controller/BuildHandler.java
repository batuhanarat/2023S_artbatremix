package src.ConKUeror.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import src.ConKUeror.model.BuildMode;
import src.ConKUeror.model.GameLogic;
import src.ConKUeror.model.Board.Board;


public class BuildHandler {
    

private BuildMode buildMode;
private Board board;
private GameLogic gamelogic;

    public BuildHandler(BuildMode bMode) {
        this.buildMode = bMode;
	}

	public void registerAsListener(BuildModeListener listener) {
			buildMode.addBuildModeListener(listener);
	}

	//Bu yaptığım model-view-controllera aykırı mı bilmiyorum. Samete sorcam
	public Boolean validateNumbersAndOpenPlayerMenu(int totalNum, int botNum) {		
			Boolean isValid = buildMode.validatePlayerNums(totalNum,botNum);
			return isValid;

	}

	public void enterNameForRealPlayers(String name) {
		buildMode.initalizePlayer(name,"Real Player");
	}


	public void initalizeBots(int botPlayerCount) {
		
		for (int i = 1; i <= botPlayerCount; i++) {
			String name = "Computer " + i;
			buildMode.initalizePlayer(name, "Computer Player");
		}

	}

	public void openPlayerSelection() {
		buildMode.handleMenuLogicAndPlayerCreation();
	}

	public void setStartStatus() {
		buildMode.setStart();
	}

	//Bu yaptığım model-view-controllera aykırı mı bilmiyorum. Samete sorcam
	public Boolean checkStartStatus() {
		return buildMode.getStartStatus();
	}

	//burada da controllerda logic kullanıyorum. Bu olabilir mi bilmiyorum bunu da sorarım da şu anlık işler kod olması için yapıyorum
	public void initializeGame() {
		 board = new Board();
		 gamelogic = new GameLogic(board);
    }



	public MapHandler giveMapHandler() {

			MapHandler mapHandler = new MapHandler(gamelogic,board);

			return mapHandler;

	}
 
	






}
