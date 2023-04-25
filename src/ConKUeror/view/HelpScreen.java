package src.ConKUeror.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class HelpScreen extends JFrame {
    public HelpScreen() {
        setTitle("Help");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Create a text area with the help information
        JTextArea helpText = new JTextArea();
       
    


        helpText.setText("Welcome to the game!\n\n" +
                          "ConKUeror is a strategy game that is easy to play yet challenging, taking inspiration from the well-known game RISK [1].\n" +
                          " It provides a mixture of entertainment and competition, with players battling to conquer the world. To emerge victorious, \n" +
                          "players must undertake daring attacks, defend their territories on all sides, and sweep across vast continents with courage\n" +
                          "and cunning. However, it is important to note that both the risks and rewards are high, as opponents can strike at any moment\n" +
                          " and claim everything that was once within reach."+
                          " \n\n" +
                          "The game takes place on a political map of the world, divided into territories and continents. Players take turns controlling armies of \n" +
                          "playing pieces, attempting to capture territories from their opponents, with outcomes determined by dice rolls. Players are able to form and\n " +
                          "break alliances as the game progresses. The ultimate goal is to occupy every territory on the board, thereby eliminating all other players.\n" +
                          "Unforeseen circumstances may arise while attacking or defending territories, much like in real life. For example, soldiers may become sick \n"+
                          "and perish, or they may receive assistance in defeating enemies. These unexpected events may also occur for opponents. The game can be  \n"+
                          "played by two to six players, with at least one computer player and the others being real people. During the first phase, computer players  \n"+
                          "take random actions, but their behavior may change in the second phase to take more logical actions. The game concludes when one player  \n"+
                          "conquers the entire world by defeating all of their adversaries. In this final phase, all players will play the game on the same computer. \n"+
                          " \n"+
                          "GAME OBJECTS\n\n" +
                          "Game Map\n"+
                          "The initial game map comprises of 42 territories divided among 6 continents, with each continent colored differently and consisting of 4 to \n"+
                          "12 territories. Players follow the rules to allocate their armies across theses territories.  \n"+
                          "However, during the building mode, it is feasible to activate or deactivate certain continents and territories.\n\n"+
                          "Armies \n"+
                          "The game comprises of 6 sets of armies, each set including 3 types of army pieces: Infantry, which holds a value of 1; Cavalry, worth 5 \n"+
                          "Infantry; and Artillery, worth either 10 Infantry or 2 Cavalry. At the beginning of the game, Infantry pieces are placed. As the game \n"+
                          "progresses, players can exchange 5 Infantry for 1 Cavalry, or trade in 2 Cavalry (or 1 Cavalry and 5 Infantry) for 1 Artillery.\n\n"+
                          "Cards\n"+
                          "The game will feature three types of cards: territory cards, chance cards, and army cards. At the start of each turn, you will select a \n"+
                          "chance card, and by the end of any turn where you have conquered at least one territory, you will receive a randomly selected territory or \n"+
                          "army card\n"+
                          "Territory Cards\n There will be territory cards as many as the number of territories in the game. During the game, the aim of collecting territory \n"+
                          "cards is to conquer a continent without attacking. When the player collects all territory cards of a continent, the player conquers all territories"+
                          "of that continent without a need to attack these territories. \n\nArmy Cards\nThere will be three types of army cards for infantry, cavalry, and artillery." + 
                            "For each player in the game, there will be three infantry, two cavalry, and one artillery card. Thus, the total number of army cards will be as follows:\n \n# "+
                            "of army cards = # of players x (3 Infantry + 2 Cavalry + 1 Artillery)\n\nA player can trade army cards to gain an additional army if she/he has a set of 3 cards."+ 
                           "He/she can place new armies in any territories she/he has. The rules of gaining armies is as follows:\n\n3 Infantry cards => 1 Cavalry\n2 Infantry cards + 1 Cavalry card => 2"+
                             "Cavalry\n2 Infantry cards + 1 Artillery card => 2 Artillery\n1 Infantry card + 2 Cavalry cards => 1 Cavalry + 1 Artillery\n1 Artillery + 2 Cavalry cards => 3 Artillery\nNOTE:"+
                              "If you have collected a set of territory and/or army cards, you may turn them in at the beginning of your next turn to conquer a continent or gain soldiers.\n\n"+
                              "NOTE: If the territory and army cards end before the game ends, they are shuffled and used again. Therefore, a player who has conquered a continent using territory"+
                              "cards may lose that continent to another player later in the game.\n\nChance Cards\n\n"+ 
                              "“Alliance”: This card allows a player to form an alliance with another player of their"+
                               "choice for the duration of the turn. The two players can coordinate their attacks and defenses, and share territories and cards. At the end of the turn, the alliance dissolves"+
                               "and the players resume normal play.\n “Reinforcements”: This card allows a player to add a certain number of armies to one of their territories. The number of armies added is"+ 
                               "determined by a roll of the dice.\n “Sabotage”: This card allows a player to choose one territory belonging to another player and remove a certain number of armies from it."+
                                 "The number of armies removed is determined by a roll of the dice.\n “Coup”: This card allows a player to take control of one of their opponent's territories without a fight."+ 
                                 "The opponent loses all armies on that territory, and the player gains control of it.\n “Surprise Attack”: This card allows a player to launch a surprise attack on an opponent's"+
                                  "territory, catching them off guard. The player can add a certain number of armies to their attack, and the opponent can't defend against it with their full force.\n"+
                                  "“Diplomatic Immunity”: This card allows a player to protect one of their territories from attack for one turn. No other player can attack that territory during that turn.\n"+
                                  "“Mercenaries: This card allows a player to hire a group of mercenaries to fight for them. The mercenaries are added to one of the player's territories, and act as extra armies"+ 
                                  "for that turn.\n “Spy”: This card allows a player to spy on one of their opponents and learn information about their strategy. The player can choose to either see the opponent's "+
                                  "cards, or learn how many armies they have on a certain territory.\n Revolution: This card allows a player to incite a revolution in one of their opponent's territories. The opponent"+
                                  "“Revolution”: This card allows a player to incite a revolution in one of their opponent's territories. The opponent loses control of the territory, and the player gains control of it.\n"+
             "“Disaster”: This card causes a natural disaster to strike a certain continent, affecting all players who control territories on that continent. The disaster causes a certain number of armies to be lost on each territory, and the player with the fewest armies on each territory loses control of it.\n"
            + "“Draft”: This card allows you to draw two additional army cards at the end of your turn.\n"
            + "“Bombardment”: You can use this card to attack a territory with a barrage of artillery, allowing you to roll two dice instead of one for that attack.\n"
            + "“Rebellion”: This card allows you to incite a rebellion in one of your opponent's territories, causing half/percentage of their armies there to switch to your side.\n"
            + "“Decoy”: This card allows you to place a fake army onto a territory you control, tricking your opponent into thinking it is a stronger position than it actually is.\n"
            + "“Nuclear Strike”: This card allows you to wipe out all armies in one territory, regardless of how many there are, but at the cost of destroying one of your own territories as well.\n"
            + "“Revolt”: Play this card on your turn to remove all armies from one of your territories and add them to another.\n"
            + "“Trade Deal”: Play this card to trade one territory card of your choice with an opponent.\n"
            + "“Secret Weapon”: Play this card to reveal and use one of your opponent's unplayed territory or army cards.\n"
            + "“World Event”: Roll a die. On a 1-2, all players receive three extra armies. On a 3-4, all players lose three armies. On a 5-6, all players exchange one territory they control with another player of their choice."); 
                            
       
        Font font = new Font("Arial", Font.PLAIN, 12);
        helpText.setFont(font);

        // Add the text area to the frame
        add(new JScrollPane(helpText));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
    }
}