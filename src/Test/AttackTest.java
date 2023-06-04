package Test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;

import ConKUeror.domain.controller.ButtonHandler;
import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Board.Territory;
import ConKUeror.domain.model.Modes.BuildMode;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.domain.model.Modes.StartMode;

public class AttackTest {

    static Board board;
    static StartMode startMode;
    static GameLogic gameLogic;
    static BuildMode buildMode;
    static ButtonHandler buttonHandler;

    @BeforeEach
    static void beforeEach()
    {
        board = null;
        buildMode = new BuildMode();
        startMode = new StartMode(buildMode);
        gameLogic = null;
        buttonHandler = null;
    }


    /*
     * whitebox
     * a slider opens and infantry number is chosen
     * for this test 5 infantries should be chosen
     */
    @Test
    public void testChosenInfantryCount()
    {
        Territory territoryOne = new Territory(1);

        territoryOne.getArmy().addInfantries(7);

        try
        {
            gameLogic.memory[0] = territoryOne;
            buttonHandler.increaseArmyCount();
        }
        catch (Exception e)
        {
            gameLogic = new GameLogic(board, startMode);
            buttonHandler = ButtonHandler.getInstance(buildMode, gameLogic);
            gameLogic.memory[0] = territoryOne;
            buttonHandler.increaseArmyCount();
        }


        assertEquals("The chosen infantry number should be 5", 5, buttonHandler.getAttackingInfantries().size(), 0);
    }

    /*
     * whitebox
     * a slider opens and cavalry number is chosen
     * for this test 7 cavalries should be chosen
     */
    @Test
    public void testChosenCavalryCount()
    {
        Territory territoryTwo = new Territory(2);

        territoryTwo.getArmy().addCavalries(10);

        try
        {
            gameLogic.memory[0] = territoryTwo;
            buttonHandler.increaseArmyCount();
        }
        catch (Exception e)
        {
            gameLogic = new GameLogic(board, startMode);
            buttonHandler = ButtonHandler.getInstance(buildMode, gameLogic);
            gameLogic.memory[0] = territoryTwo;
            buttonHandler.increaseArmyCount();
        }


        assertEquals("The chosen cavalry number should be 7", 7, buttonHandler.getAttackingCavalries().size(), 0);
    }

    /*
     * whitebox
     * a slider opens and artillery number is chosen
     * for this test 4 artilleries should be chosen
     */

    @Test
    public void testChosenArtilleryCount()
    {
        Territory territoryThree = new Territory(3);

        territoryThree.getArmy().addArtilleries(5);

        try
        {
            gameLogic.memory[0] = territoryThree;
            buttonHandler.increaseArmyCount();
        }
        catch (Exception e)
        {
            gameLogic = new GameLogic(board, startMode);
            buttonHandler = ButtonHandler.getInstance(buildMode, gameLogic);
            gameLogic.memory[0] = territoryThree;
            buttonHandler.increaseArmyCount();
        }


        assertEquals("The chosen artillery number should be 4", 4, buttonHandler.getAttackingArtilleries().size(), 0);
    }



    

    
}
