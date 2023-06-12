package ConKUeror.UI.Frames;

import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ConKUeror.domain.controller.ButtonHandler;
import ConKUeror.domain.controller.EndOfTheGameListener;
import ConKUeror.domain.controller.HandlerFactory;
import ConKUeror.domain.model.Player.Player;

public class EndGameScreen extends JFrame {

    private JPanel endGamePanel;
    //private JLabel winnerName;
    private BufferedImage winnerImage;
    private JButton closeApplicationButton;
    private ButtonHandler buttonHandler;

    public EndGameScreen(Player player) throws IOException {
        super(player.getName() + " wins!");
        
        HandlerFactory controller = HandlerFactory.getInstance();
        this.buttonHandler = controller.giveButtonHandler();
        winnerImage = ImageIO.read(getClass().getResourceAsStream("/images/winner_image.jpeg"));
        endGamePanel = new JPanel();
        endGamePanel.setLayout(new BorderLayout());
        setSize(winnerImage.getWidth(), winnerImage.getHeight());
        JLabel winnerImageLabel = new JLabel(new ImageIcon(winnerImage));
        JLabel winnerTitle = new JLabel(player.getName() + " wins!");
        endGamePanel.add(winnerTitle);
        endGamePanel.add(winnerImageLabel);
        // closeApplicationButton = new JButton("Close Game");
        // closeApplicationButton.addActionListener(new CloseApplicationButtonListener());
        // endGamePanel.add(closeApplicationButton);
        this.add(endGamePanel);
    }


}

class CloseApplicationButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}