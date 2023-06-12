package ConKUeror.domain.controller;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Data.GameState;
import ConKUeror.domain.model.Data.ISaveLoadAdapter;
import ConKUeror.domain.model.Data.MongoDBServiceAdapter;
import ConKUeror.domain.model.Data.PlayerData;
import ConKUeror.domain.model.Data.TerritoryData;
import ConKUeror.domain.model.Data.TextFileServiceAdapter;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerExpert;

public class SaveLoadController implements Serializable {

	 private ISaveLoadAdapter saveLoadService;
	 private GameState gameState;
	 private List<Player> playerList;
	 private IUIRefreshListener uiRefreshListener;
	 private List<PlayerData> playerDataList;
	 private List<TerritoryData> territoryDataList;
	 private Map<Integer, Territory> territoryMap;
	 private GameState loadedGameState;

	 public static SaveLoadController instance;


    private SaveLoadController() {
    	playerList = PlayerExpert.getPlayersList();
		territoryMap = Board.getTerritories();

    }

    public static SaveLoadController getInstance() {
        if (instance == null) {
            instance = new SaveLoadController();
        }
        return instance;
    }



	public void handleSave(int type) {


		switch(type) {


		case 0:
			generatePlayerDatas( playerList);
            generateTerritoryDatas(territoryMap);
            HandlerFactory controller = HandlerFactory.getInstance();
            GameLogic game =  controller.getGameLogic();
            gameState = new GameState(playerDataList,territoryDataList,Board.getContinents(), game.getGameMode(),game.getGamePhaseAsIndex());
            saveLoadService = new TextFileServiceAdapter();
			saveLoadService.save(gameState);

			break;


		case 1:
			generatePlayerDatas( playerList);
            generateTerritoryDatas(territoryMap);
            HandlerFactory controller1 = HandlerFactory.getInstance();
            GameLogic game2 =  controller1.getGameLogic();
            gameState = new GameState(playerDataList,territoryDataList,Board.getContinents(), game2.getGameMode(),game2.getGamePhaseAsIndex());
			
			
			saveLoadService = new MongoDBServiceAdapter();
			
			 saveLoadService.save(gameState);

			break;



		}



	}

	public void handleLoad(int type) {

		switch(type) {
			case 0:
				saveLoadService = new TextFileServiceAdapter();
				 this.loadedGameState = saveLoadService.load();
				 
				break; 
			case 1:
				
				 saveLoadService = new MongoDBServiceAdapter();
				 this.loadedGameState = saveLoadService.load();
				break;
		}
	}


	public GameState getLoadedGameState( ) {
		return this.loadedGameState;
	}




	public void generatePlayerDatas(List<Player> playerList){
		playerDataList = new ArrayList<PlayerData>();
        for(Player p : playerList){
            playerDataList.add(new PlayerData(p));
        }
    }

    public void generateTerritoryDatas(Map<Integer, Territory> map) {
		territoryDataList = new ArrayList<TerritoryData>();


        for (Map.Entry<Integer, Territory> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Territory t1 = entry.getValue();
            System.out.println("noluyo");

            System.out.println(t1.getId());
            System.out.println(map);
            territoryDataList.add(new TerritoryData(t1));
        }
    }

}
