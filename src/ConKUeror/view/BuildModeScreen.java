package src.ConKUeror.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.*;

import src.ConKUeror.controller.BuildHandler;
import src.ConKUeror.controller.BuildModeListener;

import java.awt.*;

public class BuildModeScreen extends JFrame implements BuildModeListener{

    private BufferedImage worldMap;
    private int width, height;
    
    private JPanel buildModePanel;
    private JLabel playerNumberLabel, botNumberLabel;
    private JButton confirmButton;
    private JButton startButton;
    private JButton helpButton;
    private JComboBox<Integer> playerNumberBox;
    private JComboBox<Integer> botNumberBox;

    private Integer[] numbers1 = {2, 3, 4, 5, 6};
    private Integer[] numbers2 = {1, 2, 3, 4, 5};

    private BuildHandler buildHandler;

    public BuildModeScreen(BuildHandler buildHandler) {

            this.buildHandler = buildHandler;
            initGUI();
            addBoardFrameAsListener();
            confirmButton.addActionListener(new ConfirmButtonHandler());
            startButton.addActionListener(new StartButtonHandler());
            helpButton.addActionListener(new HelpButtonHandler());
    }

    @Override
    public void onBoardEvent(String msg) {
        // TODO Auto-generated method stub   

        openPanelForPlayerDetail(msg);


    }


    private void addBoardFrameAsListener() {
        buildHandler.registerAsListener(this);
    }


    private class ConfirmButtonHandler implements ActionListener{

      
        int count = 0;

		@Override
		public void actionPerformed(ActionEvent e) {

            // I want the initalization of the player number at the first click. 
            // By doing this I restrict the possible changes about numbers after the first click.

            if (count == 0) {
                int totalPlayerNumber = getPlayerNumberComboboxValue();
                int botPlayerNumber = getBotNumberComboboxValue();
                if(!buildHandler.validateNumbersAndOpenPlayerMenu(totalPlayerNumber, botPlayerNumber)) {
                    count--;
                }
            } 
            count++;
            
          
    
		}

    }
    private class StartButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (buildHandler.checkStartStatus()) {
                dispose();
                //System.out.println("Game can start");
                buildHandler.initalizeBots(getBotNumberComboboxValue());
                buildHandler.initializeGame();
                try {
                    Map map = new Map( buildHandler.giveMapHandler() );
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }

        }
        
    }
    private class HelpButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
                dispose();
                new HelpScreen();

                //System.out.println("Game can start");
                
               

            

        }
        
    }




     public int getPlayerNumberComboboxValue() {

        String str = playerNumberBox.getSelectedItem().toString();
        return Integer.parseInt(str);


    }


    public int getBotNumberComboboxValue() {

        String str = botNumberBox.getSelectedItem().toString();
        return Integer.parseInt(str);

    }


    public void openPanelForPlayerDetail(String msg) {

        JTextField textField = new JTextField();
        Object[] message = {

            String.format(msg), textField 
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Name Entry", JOptionPane.OK_CANCEL_OPTION);
        buildHandler.enterNameForRealPlayers(textField.getText());
        buildHandler.setStartStatus();
    }



  
   
    public void initGUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Building Mode");

        playerNumberLabel = new JLabel("Number of total players:");
        botNumberLabel = new JLabel("Number of bots among the players:");

        playerNumberBox = new JComboBox<>(numbers1);
        botNumberBox = new JComboBox<>(numbers2);

        confirmButton = new JButton("Confirm");
        helpButton = new JButton("Help");
        startButton = new JButton("Start");


        buildModePanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font font = new Font("Arial", Font.PLAIN, 14);
        playerNumberLabel.setFont(font);
        botNumberLabel.setFont(font);
        playerNumberBox.setFont(font);
        botNumberBox.setFont(font);

        buildModePanel.add(playerNumberLabel, gbc);

        gbc.gridx++;
        buildModePanel.add(playerNumberBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        buildModePanel.add(botNumberLabel, gbc);

        gbc.gridx++;
        buildModePanel.add(botNumberBox, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.add(confirmButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(startButton);


        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        buildModePanel.add(buttonPanel, gbc);

        add(buildModePanel);
        setSize(400, 200);
        setLocationRelativeTo(null); 
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

 


  
   

    
}

