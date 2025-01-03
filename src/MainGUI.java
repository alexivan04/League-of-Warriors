import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    static Game game = Game.getInstance();
    static JFrame loginFrame;
    static JFrame chooseCharacterFrame;
    static JFrame mainGameMapFrame;
    static JFrame battleFrame;
    static JFrame endgameFrame;

    // displays map of the game, as a grid of cells
    // each cell can be empty, contain a portal, a sanctuary, or an enemy
    // player can move around the map using arrow keys
    static private void setupMainGameMapFrame() {
        mainGameMapFrame = new JFrame("Main Game Map");
        mainGameMapFrame.setSize(800, 600);
        mainGameMapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout: BorderLayout for overall frame structure
        mainGameMapFrame.setLayout(new BorderLayout());

        // Create the grid of labels representing the game map
        System.out.println("rows: " + game.map.rows + ", columns: " + game.map.columns);
        JLabel[][] gridLabels = new JLabel[game.map.rows][game.map.columns];
        JPanel gridPanel = new JPanel(new GridLayout(game.map.rows, game.map.columns));

        for (int i = 0; i < game.map.rows; i++) {
            for (int j = 0; j < game.map.columns; j++) {
                gridLabels[i][j] = new JLabel();
                gridLabels[i][j].setPreferredSize(new Dimension(50, 50)); // Adjust size
                gridLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridLabels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                gridLabels[i][j].setVerticalAlignment(SwingConstants.CENTER);

                // Set content based on cell type and visited status
                if (game.map.getPlayerCell().getX() == i && game.map.getPlayerCell().getY() == j) {
                    gridLabels[i][j].setText("P");
                } else if (!game.map.get(i).get(j).isVisited()) {
                    gridLabels[i][j].setText("?");
                } else {
                    switch (game.map.get(i).get(j).getType()) {
                        case VOID:
                            gridLabels[i][j].setText("V");
                            break;
                        case ENEMY:
                            gridLabels[i][j].setText("E");
                            break;
                        case SANCTUARY:
                            gridLabels[i][j].setText("S");
                            break;
                        case PORTAL:
                            gridLabels[i][j].setText("F");
                            break;
                    }
                }
                gridPanel.add(gridLabels[i][j]);
            }
        }
        mainGameMapFrame.add(gridPanel, BorderLayout.CENTER);

        // Upper-left: Controls for movement
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center the controls
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));

        JButton moveUpButton = new JButton("W - MOVE UP");
        JButton moveDownButton = new JButton("S - MOVE DOWN");
        JButton moveLeftButton = new JButton("A - MOVE LEFT");
        JButton moveRightButton = new JButton("D - MOVE RIGHT");

        // Add action listeners to movement buttons
        // Uncomment and implement as needed
        // moveUpButton.addActionListener(e -> movePlayer("UP", gridLabels));
        // moveDownButton.addActionListener(e -> movePlayer("DOWN", gridLabels));
        // moveLeftButton.addActionListener(e -> movePlayer("LEFT", gridLabels));
        // moveRightButton.addActionListener(e -> movePlayer("RIGHT", gridLabels));

        controlPanel.add(moveUpButton);
        controlPanel.add(moveDownButton);
        controlPanel.add(moveLeftButton);
        controlPanel.add(moveRightButton);

        mainGameMapFrame.add(controlPanel, BorderLayout.SOUTH);

        // Bottom-left: Player stats
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Player Stats"));

        JLabel levelLabel = new JLabel("Level: " + game.map.getPlayer().level);
        JLabel experienceLabel = new JLabel("Experience: " + game.map.getPlayer().experience);
        JLabel healthLabel = new JLabel("Health: " + game.map.getPlayer().health);
        JLabel manaLabel = new JLabel("Mana: " + game.map.getPlayer().mana);

        statsPanel.add(levelLabel);
        statsPanel.add(experienceLabel);
        statsPanel.add(healthLabel);
        statsPanel.add(manaLabel);

        mainGameMapFrame.add(statsPanel, BorderLayout.WEST);

        // Make the frame visible
        mainGameMapFrame.setVisible(true);
    }

    // a scrollable list of all Characters loaded for current account in game
    // player can click on a Character to select it and then click "Play" to start the game
    // on clicking a character, information about it is displayed on the right side of the frame
    static private void setupChooseCharacterFrame() {
        chooseCharacterFrame = new JFrame("Choose Character");
        chooseCharacterFrame.setSize(800, 600);
        chooseCharacterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chooseCharacterFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.NONE; // Do not resize components
        gbc.anchor = GridBagConstraints.CENTER; // Center alignment

        JLabel characterLabel = new JLabel("Characters");
        DefaultListModel<String> characterListModel = new DefaultListModel<>();
        JList<String> characterList = new JList<>(characterListModel);
        characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        characterList.setLayoutOrientation(JList.VERTICAL);
        characterList.setVisibleRowCount(-1);
        JScrollPane characterListScroller = new JScrollPane(characterList);
        characterListScroller.setPreferredSize(new Dimension(250, 200));

        JLabel characterInfoLabel = new JLabel("Character Information");
        JTextArea characterInfoArea = new JTextArea(5, 20);
        characterInfoArea.setEditable(false);
        JScrollPane characterInfoScroller = new JScrollPane(characterInfoArea);
        characterInfoScroller.setPreferredSize(new Dimension(250, 200));

        JButton playButton = new JButton("Play");

        gbc.gridx = 0;
        gbc.gridy = 0;
        chooseCharacterFrame.add(characterLabel, gbc);

        gbc.gridx = 1;
        chooseCharacterFrame.add(characterListScroller, gbc);

        gbc.gridx = 2;
        chooseCharacterFrame.add(characterInfoLabel, gbc);

        gbc.gridx = 3;
        chooseCharacterFrame.add(characterInfoScroller, gbc);

        gbc.gridx = 0; // Span across two columns
        gbc.gridy = 1; // Second row
        gbc.gridwidth = 4; // Span the button across all columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        chooseCharacterFrame.add(playButton, gbc);

        for (Character character : game.currAccount.getCharacters()) {
            characterListModel.addElement(character.name);
        }

        characterList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = characterList.getSelectedIndex();
                if (index != -1) {
                    Character selectedCharacter = game.currAccount.getCharacters().get(index);
                    characterInfoArea.setText("Name: " + selectedCharacter.name + "\n" +
                            "Level: " + selectedCharacter.level + "\n" +
                            "Experience: " + selectedCharacter.experience + "\n" +
                            "Health: " + selectedCharacter.health + "\n" +
                            "Mana: " + selectedCharacter.mana + "\n" +
                            "Strength: " + selectedCharacter.strength + "\n" +
                            "Charisma: " + selectedCharacter.charisma + "\n" +
                            "Dexterity: " + selectedCharacter.dexterity + "\n" +
                            "Base Damage: " + selectedCharacter.baseDamage + "\n"
                    );
                }
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = characterList.getSelectedIndex();
                if (index != -1) {
                    game.currCharacter = game.currAccount.getCharacters().get(index);
                    game.map = Grid.generateGrid(6, 6);
                    game.map.setPlayer(game.currCharacter);
                    game.gameEnded = false;
                    chooseCharacterFrame.setVisible(false);
                    setupMainGameMapFrame();
                    mainGameMapFrame.setVisible(true);
                }
            }
        });
    }
    static private void setupLoginFrame() {
        loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 250);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.NONE; // Do not resize components
        gbc.anchor = GridBagConstraints.CENTER; // Center alignment

        JLabel emailLabel = new JLabel("Email");
        JTextField emailField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password");
        JTextField passwordField = new JTextField(15);

        JButton loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginFrame.add(emailLabel, gbc);

        gbc.gridx = 1;
        loginFrame.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginFrame.add(passwordLabel, gbc);

        gbc.gridx = 1;
        loginFrame.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginFrame.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = passwordField.getText().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(loginFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    game.username = email;
                    game.password = password;
                    game.login();
                    if(game.loggedIn) {
                        loginFrame.setVisible(false);
                        setupChooseCharacterFrame();
                        chooseCharacterFrame.setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(loginFrame, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

    }

    public static void main(String[] args) throws InvalidGridSize {
        game.useKeyboardInput = false;
        setupLoginFrame();
        loginFrame.setVisible(true);
    }
}