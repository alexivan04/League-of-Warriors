import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class MainGUI {
    static Game game = Game.getInstance();
    static JFrame loginFrame;
    static JFrame chooseCharacterFrame;
    static JFrame mainGameMapFrame;
    static JPanel gridPanel;
    static JPanel statsPanel;
    static JLabel[][] gridLabels;

    static JDialog battleDialog;
    static JFrame abilitySelectFrame;
    static JFrame endgameFrame;
    static Random rand = new Random();

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
        gridPanel = new JPanel(new GridLayout(game.map.rows, game.map.columns));

        drawGrid(gridPanel);
        mainGameMapFrame.add(gridPanel, BorderLayout.CENTER);

        // Upper-left: Controls for movement
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));

        JButton moveUpButton = new JButton("W - MOVE UP");
        JButton moveDownButton = new JButton("S - MOVE DOWN");
        JButton moveLeftButton = new JButton("A - MOVE LEFT");
        JButton moveRightButton = new JButton("D - MOVE RIGHT");

        // Add action listeners to movement buttons
         moveUpButton.addActionListener(e -> goNorthWrapper());
         moveDownButton.addActionListener(e -> goSoutWrapper());
         moveLeftButton.addActionListener(e -> goWestWrapper());
         moveRightButton.addActionListener(e -> goEastWrapper());

        controlPanel.add(moveUpButton);
        controlPanel.add(moveDownButton);
        controlPanel.add(moveLeftButton);
        controlPanel.add(moveRightButton);

        mainGameMapFrame.add(controlPanel, BorderLayout.SOUTH);

        // Bottom-left: Player stats
        statsPanel = new JPanel();
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

    private static void drawGrid(JPanel gridPanel) {
        gridPanel.removeAll();
        gridLabels = new JLabel[game.map.rows][game.map.columns];
        System.out.println("Redrawing grid on movement");

        for (int i = 0; i < game.map.rows; i++) {
            for (int j = 0; j < game.map.columns; j++) {
                gridLabels[i][j] = new JLabel();
                gridLabels[i][j].setPreferredSize(new Dimension(50, 50));
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
    }

    static private void goSoutWrapper() {
        try {
            game.map.goSouth();
            updateStatsPanel();
            drawGrid(gridPanel);
            gridPanel.revalidate();
            gridPanel.repaint();
            statsPanel.revalidate();
            statsPanel.repaint();
            mainGameMapFrame.revalidate();
            mainGameMapFrame.repaint();

            makeActionWrapper();

            updateStatsPanel();
            drawGrid(gridPanel);
            gridPanel.revalidate();
            gridPanel.repaint();
            statsPanel.revalidate();
            statsPanel.repaint();
            mainGameMapFrame.revalidate();
            mainGameMapFrame.repaint();
        } catch (ImpossibleMove e) {
            JOptionPane.showMessageDialog(mainGameMapFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static private void goNorthWrapper() {
        try {
            game.map.goNorth();
            updateStatsPanel();
            drawGrid(gridPanel);
            gridPanel.revalidate();
            gridPanel.repaint();
            statsPanel.revalidate();
            statsPanel.repaint();
            mainGameMapFrame.revalidate();
            mainGameMapFrame.repaint();

            makeActionWrapper();

            updateStatsPanel();
            drawGrid(gridPanel);
            gridPanel.revalidate();
            gridPanel.repaint();
            statsPanel.revalidate();
            statsPanel.repaint();
            mainGameMapFrame.revalidate();
            mainGameMapFrame.repaint();
        } catch (ImpossibleMove e) {
            JOptionPane.showMessageDialog(mainGameMapFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static private void goWestWrapper() {
        try {
            game.map.goWest();
            updateStatsPanel();
            drawGrid(gridPanel);
            gridPanel.revalidate();
            gridPanel.repaint();
            statsPanel.revalidate();
            statsPanel.repaint();
            mainGameMapFrame.revalidate();
            mainGameMapFrame.repaint();

            makeActionWrapper();

            updateStatsPanel();
            drawGrid(gridPanel);
            gridPanel.revalidate();
            gridPanel.repaint();
            statsPanel.revalidate();
            statsPanel.repaint();
            mainGameMapFrame.revalidate();
            mainGameMapFrame.repaint();
        } catch (ImpossibleMove e) {
            JOptionPane.showMessageDialog(mainGameMapFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static private void goEastWrapper() {
        try {
            game.map.goEast();
            updateStatsPanel();
            drawGrid(gridPanel);
            gridPanel.revalidate();
            gridPanel.repaint();
            statsPanel.revalidate();
            statsPanel.repaint();
            mainGameMapFrame.revalidate();
            mainGameMapFrame.repaint();

            makeActionWrapper();

            updateStatsPanel();
            drawGrid(gridPanel);
            gridPanel.revalidate();
            gridPanel.repaint();
            statsPanel.revalidate();
            statsPanel.repaint();
            mainGameMapFrame.revalidate();
            mainGameMapFrame.repaint();
        } catch (ImpossibleMove e) {
            JOptionPane.showMessageDialog(mainGameMapFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static private void makeActionWrapper() {
        switch (game.map.get(game.map.getPlayerCell().getX()).get(game.map.getPlayerCell().getY()).getType()) {
            case PORTAL:
                portalActionWrapper();
                break;

            case SANCTUARY:
                sanctuaryActionWrapper();
                break;

            case ENEMY:
                enemyActionWrapper();
                break;

            default:
                break;
        }
    }

    static private void portalActionWrapper() {
        return;
    }

    static private void sanctuaryActionWrapper() {
        int healthRegen = rand.nextInt(20) + 10;
        int manaRegen = rand.nextInt(50) + 20;
        game.map.getPlayer().regenHealth(healthRegen);
        game.map.getPlayer().regenMana(manaRegen);
        // display message dialogue
        JOptionPane.showMessageDialog(mainGameMapFrame, "Healing: \n\t" + game.map.getPlayer().health + " health \n\t" +  game.map.getPlayer().mana + " mana", "Healing", JOptionPane.INFORMATION_MESSAGE);
    }

    static private void enemyActionWrapper() {
        // open battle frame, then close it after battle ended (player won or lost)
        Enemy enemy = new Enemy();
        enemy.generateRandomAbilities();
        game.map.getPlayer().generateRandomAbilities();
        setupBattleDialog(enemy);
        battleDialog.setVisible(true);
        System.out.println("Battle ended");
        return;
    }

    static private void setupEndgameFrame() {
        endgameFrame = new JFrame("Endgame");
        endgameFrame.setSize(400, 250);
        endgameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        endgameFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel endgameLabel = new JLabel("Game Over");
        JLabel endgameMessage = new JLabel("You have died. Game over.");

        JButton endgameButton = new JButton("End Game");

        gbc.gridx = 0;
        gbc.gridy = 0;
        endgameFrame.add(endgameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        endgameFrame.add(endgameMessage, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        endgameFrame.add(endgameButton, gbc);

        endgameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endgameFrame.setVisible(false);
                game.currCharacter.health = game.currCharacter.maxHealth;
                game.currCharacter.mana = game.currCharacter.maxMana;
                setupChooseCharacterFrame();
                chooseCharacterFrame.setVisible(true);
            }
        });
    }

    static private void setupBattleDialog(Enemy enemy) {
        battleDialog = new JDialog(mainGameMapFrame, "Battle", true); // Set to modal
        battleDialog.setSize(600, 300);
        battleDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

//        battleDialog.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosed(WindowEvent e) {
//                System.out.println("Battle dialog closed. Exiting game.");
//                System.exit(0); // Exit the entire application
//            }
//        });

        battleDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        // Player Section
        JLabel playerLabel = new JLabel("Player");
        JLabel playerHealthLabel = new JLabel("Health: " + game.map.getPlayer().health);
        JLabel playerManaLabel = new JLabel("Mana: " + game.map.getPlayer().mana);
        JLabel playerLevelLabel = new JLabel("Level: " + game.map.getPlayer().level);

        JButton attackButton = new JButton("Attack");
        JButton abilityButton = new JButton("Use Ability");

        // Enemy Section
        JLabel enemyLabel = new JLabel("Enemy");
        JLabel enemyHealthLabel = new JLabel("Health: " + enemy.health);

        // Layout for Player Stats
        gbc.gridx = 0;
        gbc.gridy = 0;
        battleDialog.add(playerLabel, gbc);

        gbc.gridy = 1;
        battleDialog.add(playerHealthLabel, gbc);

        gbc.gridy = 2;
        battleDialog.add(playerManaLabel, gbc);

        gbc.gridy = 3;
        battleDialog.add(playerLevelLabel, gbc);

        // Player Actions
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        battleDialog.add(attackButton, gbc);

        gbc.gridy = 5;
        battleDialog.add(abilityButton, gbc);

        // Layout for Enemy Stats
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1; // Right column
        gbc.gridy = 0;
        battleDialog.add(enemyLabel, gbc);

        gbc.gridy = 1;
        battleDialog.add(enemyHealthLabel, gbc);

        attackButton.addActionListener(e -> {
            enemy.receiveDamage(game.map.getPlayer().getDamage());
            if (enemy.health > 0) {
                enemyTurn(enemy, playerHealthLabel, playerManaLabel);
            }
            updateBattleUI(playerHealthLabel, playerManaLabel, enemyHealthLabel, enemy);
        });

        abilityButton.addActionListener(e -> {
            if (game.map.getPlayer().displayAbilities()) {
                int abilityChoice = displayAbilitySelectionDialog();
                if (abilityChoice >= 0) {
                    game.map.getPlayer().useAbility(abilityChoice, enemy);
                    if (enemy.health > 0) {
                        enemyTurn(enemy, playerHealthLabel, playerManaLabel);
                    }
                    updateBattleUI(playerHealthLabel, playerManaLabel, enemyHealthLabel, enemy);
                }
            } else {
                JOptionPane.showMessageDialog(battleDialog, "No abilities available!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    static private void enemyTurn(Enemy enemy, JLabel playerHealthLabel, JLabel playerManaLabel) {
        boolean useAbility = rand.nextBoolean();
        if (useAbility && !enemy.spells.isEmpty()) {
            int ability = rand.nextInt(enemy.spells.size());
            enemy.useAbility(ability, game.map.getPlayer());
        } else {
            game.map.getPlayer().receiveDamage(enemy.getDamage());
        }
        playerHealthLabel.setText("Health: " + game.map.getPlayer().health);
        playerManaLabel.setText("Mana: " + game.map.getPlayer().mana);
    }

    static private void updateBattleUI(JLabel playerHealthLabel, JLabel playerManaLabel, JLabel enemyHealthLabel, Enemy enemy) {
        playerHealthLabel.setText("Health: " + game.map.getPlayer().health);
        playerManaLabel.setText("Mana: " + game.map.getPlayer().mana);
        enemyHealthLabel.setText("Health: " + enemy.health);

        if (game.map.getPlayer().health <= 0) {
//            JOptionPane.showMessageDialog(battleDialog, "Game Over!", "Game Ended", JOptionPane.INFORMATION_MESSAGE);
            battleDialog.dispose();
            game.gameEnded = true;
            setupEndgameFrame();
            endgameFrame.setVisible(true);

        } else if (enemy.health <= 0) {
            JOptionPane.showMessageDialog(battleDialog, "Enemy defeated!", "Victory", JOptionPane.INFORMATION_MESSAGE);
            battleDialog.dispose();
            game.map.getPlayer().regenHealth(10);
            game.map.getPlayer().regenMana(100);
            game.map.getPlayer().addExperience(rand.nextInt(11) + 5);
        }
    }

    static private int displayAbilitySelectionDialog() {
        String[] abilities = game.map.getPlayer().spells.stream()
                .map(spell -> spell.toString() + " (Mana: " + spell.manaCost + ")")
                .toArray(String[]::new);

        String chosenAbility = (String) JOptionPane.showInputDialog(
                battleDialog,
                "Choose an ability:",
                "Use Ability",
                JOptionPane.PLAIN_MESSAGE,
                null,
                abilities,
                abilities[0]
        );

        if (chosenAbility != null) {
            for (int i = 0; i < abilities.length; i++) {
                if (abilities[i].equals(chosenAbility)) {
                    return i;
                }
            }
        }
        return -1; // No ability chosen
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

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

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

    static private void updateStatsPanel() {
        JLabel levelLabel = (JLabel) statsPanel.getComponent(0);
        JLabel experienceLabel = (JLabel) statsPanel.getComponent(1);
        JLabel healthLabel = (JLabel) statsPanel.getComponent(2);
        JLabel manaLabel = (JLabel) statsPanel.getComponent(3);

        levelLabel.setText("Level: " + game.map.getPlayer().level);
        experienceLabel.setText("Experience: " + game.map.getPlayer().experience);
        healthLabel.setText("Health: " + game.map.getPlayer().health);
        manaLabel.setText("Mana: " + game.map.getPlayer().mana);
    }

    public static void main(String[] args) throws InvalidGridSize {
        game.useKeyboardInput = false;
        setupLoginFrame();
        loginFrame.setVisible(true);
    }
}