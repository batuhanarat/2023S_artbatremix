package ConKUeror.UI.HelpScreen;

import javax.swing.*;
import java.awt.*;

public class ChanceCardScreen extends JFrame {
    public ChanceCardScreen() {

        setTitle("Chance Cards Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1250, 600);

        JTextArea textArea = new JTextArea(
                "Chance Cards\n\n"+
                        "“Alliance”: This card allows a player to form an alliance with another player of their "+
                        "choice for the duration of the turn. The two players can coordinate their attacks and defenses, \n"
                        +" and share territories and cards. \n At the end of the turn, the alliance dissolves"+
                        "and the players resume normal play.\n\n “Reinforcements”: This card allows a player to add a certain number of armies to one of their territories. The number of armies added is"+
                        "determined by a roll of the dice.\n\n “Sabotage”: This card allows a player to choose one territory belonging to another player and remove a certain number of armies from it."+
                        "The number of armies removed is determined by a roll \n of the dice. \n\n“Coup”: This card allows a player to take control of one of their opponent's territories without a fight."+
                        "The opponent loses all armies on that territory, and the player gains control of it.\n\n “Surprise Attack”: This card allows a player to launch a surprise attack on an opponent's"+
                        "territory, catching them off guard. The player can add a certain number of armies to their attack, and \n the opponent can't defend against it with their full force.\n"+
                        "“Diplomatic Immunity”: This card allows a player to protect one of their territories from attack for one turn. No other player can attack that territory during that turn.\n\n"+
                        "“Mercenaries: This card allows a player to hire a group of mercenaries to fight for them. The mercenaries are added to one of the player's territories, and act as extra armies"+
                        "for that turn.\n\n “Spy”: This card allows a player to spy on one of their opponents and learn information about their strategy. The player can choose to either see the opponent's "+
                        "cards, or learn how many \n armies they have on a certain territory.\n\n Revolution: This card allows a player to incite a revolution in one of their opponent's territories. The opponent"+
                        "“Revolution”: This card allows a player to incite a revolution in one of their \n opponent's territories. The opponent loses control of the territory, and the player gains control of it.\n\n"+
                        "“Disaster”: This card causes a natural disaster to strike a certain continent, affecting all players who control territories on that continent. The disaster causes a certain number of armies to be lost \n on each territory, and the player with the fewest armies on each territory loses control of it.\n"
                        + "“Draft”: This card allows you to draw two additional army cards at the end of your turn.\n"
                        + "“Bombardment”: You can use this card to attack a territory with a barrage of artillery, allowing you to roll two dice instead of one for that attack.\n"
                        + "“Rebellion”: This card allows you to incite a rebellion in one of your opponent's territories, causing half/percentage of their armies there to switch to your side.\n"
                        + "“Decoy”: This card allows you to place a fake army onto a territory you control, tricking your opponent into thinking it is a stronger position than it actually is.\n"
                        + "“Nuclear Strike”: This card allows you to wipe out all armies in one territory, regardless of how many there are, but at the cost of destroying one of your own territories as well.\n"
                        + "“Revolt”: Play this card on your turn to remove all armies from one of your territories and add them to another.\n"
                        + "“Trade Deal”: Play this card to trade one territory card of your choice with an opponent.\n"
                        + "“Secret Weapon”: Play this card to reveal and use one of your opponent's unplayed territory or army cards.\n"
                        + "“World Event”: Roll a die. On a 1-2, all players receive three extra armies. On a 3-4, all players lose three armies. On a 5-6, all players exchange one territory they control with another player \n of their choice.");

        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(44, 62, 80)); // Added a background color

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);

        setVisible(true);
    }
}
