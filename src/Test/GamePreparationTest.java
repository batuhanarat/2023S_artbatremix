package Test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import ConKUeror.domain.enums.GameMode;
import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Modes.BuildMode;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.domain.model.Modes.StartMode;
import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerFactory;

public class GamePreparationTest {

    

    BuildMode bMode;
    StartMode sMode;
    Board board;
    GameLogic game;


    Player realPlayer;
    Player computerPlayer;
    Player playerInTurn;

    PlayerFactory playerFactory;

  
    @Test
    public void testValidGamePreparationForBuildMode() throws InterruptedException, IOException {  // Tests that if game is built.
          
    bMode = new BuildMode();                      
    sMode = new StartMode(bMode);
    board = new Board();
    game = new GameLogic(board , sMode);
        Territory t = new Territory(0);
        GameMode gameMode = GameMode.BUILD;


        game.prepareGame(t, gameMode);


        Assert.assertEquals(t, game.inputTerritory);
        Assert.assertEquals(0, game.phaseIndex);

        // Assert.assertEquals(0, game.getPhaseIndex());
        // Assert.assertTrue(game.isPrepareTerritoryCalled());
        // Assert.assertTrue(game.isAddToMemoryCalled());
        Assert.assertEquals(GameMode.BUILD,gameMode);





  
    }

    @Test
    public void testValidGamePreparationForConnectionMode() throws InterruptedException, IOException { // Tests that if Connection Mode is valid
        Territory t = new Territory(0);
        // game.prepareGame(t,GameMode.BUILD);
        bMode = new BuildMode();
        sMode = new StartMode(bMode);
        board = new Board();
        game = new GameLogic(board , sMode);

        // this.inputTerritory=t;
        // this.phaseIndex=1;

        GameMode gameMode = GameMode.CONNECTION;

        game.prepareGame(t, gameMode);
  
       
        Assert.assertEquals(t, game.inputTerritory);
        Assert.assertEquals(1, game.phaseIndex);

    }

    @Test
    public void testValidGamePreparationForChanceCardMode() throws InterruptedException, IOException { // Tests that if ChanceCard mode is valid.
        Territory t = new Territory(0);

        bMode = new BuildMode();
        sMode = new StartMode(bMode);
        board = new Board();
        game = new GameLogic(board , sMode);
        GameMode gameMode = GameMode.CHANCECARD;
        game.prepareGame(t, gameMode);


        // Assert.assertEquals(t, GamePreparation.getInputTerritory());
        // Assert.assertEquals(2, GamePreparation.getPhaseIndex());
        // Assert.assertEquals(t, GamePreparation.getTerritoryOwner());
        // Assert.assertEquals(1, GamePreparation.getTerritoryUnitCount());
        // Assert.assertTrue(GamePreparation.isSetTerritoryInfoCalled());
        // Assert.assertTrue(GamePreparation.isRemoveFromUnoccupiedTerritoriesCalled());
        // Assert.assertTrue(GamePreparation.isPassToNextPlayerCalled());

        Assert.assertEquals(GameMode.CHANCECARD,gameMode);
        Assert.assertEquals(t, game.inputTerritory);
        Assert.assertEquals(3, game.phaseIndex);

    }

    @Test
    public void testValidGamePreparationForDeploy() throws InterruptedException, IOException { // Tests that if Deploy is prepared.
        Territory t = new Territory(0);

        bMode = new BuildMode();
        sMode = new StartMode(bMode);
        board = new Board();
        game = new GameLogic(board , sMode);
        GameMode gameMode = GameMode.CHANCECARD;
        game.prepareGame(t, gameMode);
        Assert.assertEquals(t, game.inputTerritory);
        Assert.assertEquals(3, game.phaseIndex);

    }



    @Test
    public void testValidGamePreparationForAttack() throws InterruptedException, IOException { // Tests that Attack is prepared.
        Territory t = new Territory(0);

        bMode = new BuildMode();
        sMode = new StartMode(bMode);
        board = new Board();
        game = new GameLogic(board , sMode);
        GameMode gameMode = GameMode.ATTACK;
        game.prepareGame(t, gameMode);
        Assert.assertEquals(t, game.inputTerritory);
        Assert.assertEquals(5, game.phaseIndex);

    }
  





    @Test
    public void testValidGamePreparationForFortify() throws InterruptedException, IOException { // Tests that Fortify is prepared.
        Territory t = new Territory(0);

        bMode = new BuildMode();
        sMode = new StartMode(bMode);
        board = new Board();
        game = new GameLogic(board , sMode);
        GameMode gameMode = GameMode.FORTIFY;
        game.prepareGame(t, gameMode);
        Assert.assertEquals(t, game.inputTerritory);
        Assert.assertEquals(6, game.phaseIndex);

    }



    @Test
    public void testValidGamePreparationForArmyCard() throws InterruptedException, IOException { // Tests that ArmyCard is prepared.
        Territory t = new Territory(0);

        bMode = new BuildMode();
        sMode = new StartMode(bMode);
        board = new Board();
        game = new GameLogic(board , sMode);
        GameMode gameMode = GameMode.ARMYCARD;
        game.prepareGame(t, gameMode);
      
        Assert.assertEquals(7, game.phaseIndex);

    }



    @Test
    public void testValidGamePreparationForTerritoryCard() throws InterruptedException, IOException { // Tests that if Territory Card Phase is prepared.


        Territory t = new Territory(0);
        bMode = new BuildMode();
        sMode = new StartMode(bMode);
        board = new Board();
        game = new GameLogic(board , sMode);
        GameMode gameMode = GameMode.TERRITORYCARD;
        game.prepareGame(t, gameMode);
        Assert.assertEquals(8, game.phaseIndex);

    }
}
