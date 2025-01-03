import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    static Game game = Game.getInstance();
    static JFrame loginFrame;
    static JFrame chooseCharacterFrame;


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
                    chooseCharacterFrame.setVisible(false);
                    game.map = Grid.generateGrid(6, 6);
                    game.map.setPlayer(game.currCharacter);
                    game.gameEnded = false;
                    game.run();
                    game.map = Grid.generateGrid(6, 6);
                    game.map.setPlayer(game.currCharacter);
                    while (!game.gameEnded) {
                        game.chooseNextAction();
                        if (game.gameEnded)
                            break;
                        game.makeAction();
                    }
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