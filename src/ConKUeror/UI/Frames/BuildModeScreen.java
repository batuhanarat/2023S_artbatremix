package ConKUeror.UI.Frames;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


import javax.swing.*;

import ConKUeror.domain.controller.BuildHandler;
import ConKUeror.UI.HelpScreen.HelpScreen;
import ConKUeror.domain.controller.BuildModeListener;
import ConKUeror.domain.model.Modes.BuildMode;

import java.awt.*;

public class BuildModeScreen extends JFrame implements BuildModeListener{
    //OVERVIEW: This class is UI representation of player number choice.

    private Boolean canStart;
    private JPanel buildModePanel;
    private JLabel playerNumberLabel, botNumberLabel;
    private JButton confirmButton;
    private JButton startButton;
    private JButton helpButton;
    private JComboBox<Integer> playerNumberBox;
    private JComboBox<Integer> botNumberBox;

    private Integer[] totalPlayerNumbers = {2, 3, 4, 5, 6};
    private Integer[] botPlayerNumbers = {1, 2, 3, 4, 5};

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
    public void onBoardIndexEvent(int index) {
        openPanelForPlayerInput(index);
    }


    private void addBoardFrameAsListener() {
        buildHandler.registerAsListener(this);
    }


    private class ConfirmButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

                int totalPlayerNumber = getPlayerNumberComboboxValue();
                int botPlayerNumber = getBotNumberComboboxValue();
                buildHandler.handlePlayerCounts(totalPlayerNumber, botPlayerNumber);

		}

    }
    private class HelpButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {


                new HelpScreen();


		}

    }

    private class StartButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub

          Boolean canStart =BuildMode.getStartStatus();
          if(canStart) {
            dispose();
            buildHandler.initalizeBots(getBotNumberComboboxValue());
            buildHandler.initializeGame();
                  try {
                    MapView map = new MapView();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

          }




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


    public void openPanelForPlayerInput(int index)
    {
        String playerNameIndexMessage =  String.format("Enter player %d name:", index);

        JTextField textField = new JTextField();
        Object[] message = {

            String.format(playerNameIndexMessage), textField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Name Entry", JOptionPane.OK_CANCEL_OPTION);
        buildHandler.enterNameForRealPlayers(textField.getText());
        buildHandler.setStartStatus();
    }





    public void initGUI() {
        //@requires: (Precondition) BuildModeScreen
        //@modifies:
        //@effects: (Postcondition)
        // try {
        //     UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        setTitle("Building Mode");

        playerNumberLabel = new JLabel("Number of total players:");
        botNumberLabel = new JLabel("Number of bots among the players:");

        playerNumberBox = new JComboBox<>(totalPlayerNumbers);
        botNumberBox = new JComboBox<>(botPlayerNumbers);

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

    public void updatePlayerCount(int playerCount) {
        playerNumberBox.setSelectedItem(playerCount);
    }
    
    public void updateBotCount(int botCount) {
        botNumberBox.setSelectedItem(botCount);
    }
    
    public void setCanStart(boolean canStart) {
        // assuming canStart is a member variable in BuildModeScreen class.
        this.canStart = canStart; 
    }






}

