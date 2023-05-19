package src.ConKUeror.UI.HelpScreen;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelpScreen extends JFrame {
    public JFrame introductionFrame = new JFrame("Introduction Window");

    public HelpScreen() {


        introductionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        introductionFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JButton gameMapButton = new JButton("GameMap");
        JButton armiesButton = new JButton("Armies");
        JButton cardsButton = new JButton("Cards");

        ButtonPanel buttonPanel = new ButtonPanel(gameMapButton, armiesButton, cardsButton);

        JTextAreaPlus textAreaPlus = new JTextAreaPlus("Welcome to the game!\n\n" +
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
        " \n");
        textAreaPlus.setFont(new Font("Times New Roman",Font.PLAIN,20));
        //image = ImageIO.read(getClass().getResourceAsStream("/src/images/Map.png"));

        try {
            ImageIcon icon2 = new ImageIcon(getClass().getClassLoader().getResource("conquer.jpg"));
            textAreaPlus.setImage(icon2);
        } catch (Exception e) {
            // TODO: handle exception
        }



       // textAreaPlus.setForeground(Color.white);
        introductionFrame.add(textAreaPlus, BorderLayout.CENTER); // Add the text area to the frame
        introductionFrame.add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the frame

        introductionFrame.setVisible(true);
    }
}