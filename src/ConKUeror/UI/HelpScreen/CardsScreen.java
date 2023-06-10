package ConKUeror.UI.HelpScreen;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardsScreen extends JFrame {
    public CardsScreen() {
        setTitle("Cards Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1250, 600);

        JTextArea textArea = new JTextArea(
            "Cards\n" +
            "The game will feature three types of cards: territory cards, chance cards, and army cards. At the start of each turn, you will select a \n" +
            "chance card, and by the end of any turn where you have conquered at least one territory, you will receive a randomly selected territory or \n" +
            "army card.\n"
        );
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        textArea.setBackground(new Color(44, 62, 80, 123)); // semi-transparent color
        textArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JButton returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font
        returnButton.setMargin(new Insets(5, 5, 5, 5)); // Compact margins
        returnButton.setPreferredSize(new Dimension(40, 30)); // Preferred size
        returnButton.addActionListener(e -> {
            dispose();
            new HelpScreen().setVisible(true);
        });

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create the buttons
        JButton armyCardButton = new JButton("Army Card");
        JButton territoryCardButton = new JButton("Territory Card");
        JButton chanceCardButton = new JButton("Chance Card");

        // Set the button attributes
        armyCardButton.setFont(new Font("Arial", Font.BOLD, 14));
        territoryCardButton.setFont(new Font("Arial", Font.BOLD, 14));
        chanceCardButton.setFont(new Font("Arial", Font.BOLD, 14));

        armyCardButton.setBackground(new Color(41, 128, 185));
        territoryCardButton.setBackground(new Color(39, 174, 96));
        chanceCardButton.setBackground(new Color(142, 68, 173));

        armyCardButton.setForeground(Color.WHITE);
        territoryCardButton.setForeground(Color.WHITE);
        chanceCardButton.setForeground(Color.WHITE);

        // Add the buttons to the panel
        buttonPanel.add(armyCardButton);
        buttonPanel.add(territoryCardButton);
        buttonPanel.add(chanceCardButton);

        // Add action listener for the Army Card button
        armyCardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ArmyCardScreen().setVisible(true);
                dispose();
            }
        });

        // Add action listener for the Territory Card button
        territoryCardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TerritoryCardScreen().setVisible(true);
                dispose();
            }
        });

        // Add action listener for the Chance Card button
        chanceCardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ChanceCardScreen().setVisible(true);
                dispose();
            }
        });

        try {
            BufferedImage myPicture = ImageIO.read(getClass().getClassLoader().getResource("ConKUeror/UI/HelpScreen/HelpScreenImages/card.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));

            picLabel.setLayout(new BorderLayout());

            picLabel.add(scrollPane, BorderLayout.CENTER);
            picLabel.add(buttonPanel, BorderLayout.SOUTH);
            picLabel.add(returnButton, BorderLayout.NORTH);

            add(picLabel);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setVisible(true);
    }
}
