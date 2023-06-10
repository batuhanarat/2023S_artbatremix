package ConKUeror.UI.HelpScreen;

import javax.swing.*;
import java.awt.*;

public class TerritoryCardScreen extends JFrame {
    public TerritoryCardScreen() {

        setTitle("TerritoryCards Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1250, 600);

        JTextArea textArea = new JTextArea(
                "Territory Cards\n\n" +
                        "There will be territory cards as many as the number of territories in the game. During the game, the aim of collecting territory" +
                        "cards is to conquer a continent without attacking. \n When the player collects all territory cards of a continent, the player conquers all territories"+
                        " of that continent without a need to attack these territories. \n\n"
        );

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
