import java.util.*;
import java.util.Random;

public class Game {
    ArrayList <Account> accounts;
    Grid map;
    boolean gameEnded;
    int level;
    Scanner input;
    Random rand;
    Account currAccount;
    Character currCharacter;
    boolean loggedIn;
    String username;
    String password;

    int characterChoice;
    int attackChoice;
    boolean useKeyboardInput;

    public Game() {
        gameEnded = false;
        level = 1;
        input = new Scanner(System.in);
        rand = new Random();
        characterChoice = -1;
        useKeyboardInput = true;
    }

    public void makeAction() {
        switch (map.get(map.getPlayerCell().getX()).get(map.getPlayerCell().getY()).getType()) {
            case PORTAL:
                portalAction();
                break;

            case SANCTUARY:
                sanctuaryAction();
                break;

            case ENEMY:
                enemyAction();
                break;

            default:
                break;
        }
    }

    private void enemyAction() {
        Enemy enemy = new Enemy();
        enemy.generateRandomAbilities();
        map.getPlayer().generateRandomAbilities();
        System.out.println("Enemy ahead!");
        while(enemy.health > 0 && map.getPlayer().health > 0) {
            System.out.println("Make a choice:\n\t1) Attack\n\t2) Use Ability");
            if(useKeyboardInput) {
                try {
                    attackChoice = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, try again.");
                    input.nextLine();
                    continue;
                }
            }
            if (attackChoice == 1)
                enemy.receiveDamage(map.getPlayer().getDamage());
            else if (attackChoice == 2) {
                if(map.getPlayer().displayAbilities()) {
                    boolean validAbilityChoice = false;
                    int abilityChoice;
                    while(!validAbilityChoice) {
                        validAbilityChoice = true;
                        if(useKeyboardInput) {
                            try {
                                abilityChoice = input.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input, try again.");
                                input.nextLine();
                                validAbilityChoice = false;
                                continue;
                            }
                        }
                        else abilityChoice = 0;
                        map.getPlayer().useAbility(abilityChoice, enemy);
                    }
                }
                else continue;
            }

            boolean useAbility = rand.nextBoolean();
            if(useAbility && !enemy.spells.isEmpty()) {
                int ability = rand.nextInt(enemy.spells.size());
                enemy.useAbility(ability, map.getPlayer());
            }
            else {
                map.getPlayer().receiveDamage(enemy.getDamage());
            }

            System.out.println("Enemy health: " + enemy.health);
            System.out.println("Player: \n\thealth: " + map.getPlayer().health + "\n\tmana: " + map.getPlayer().mana);
        }

        if(map.getPlayer().health <= 0) {
            System.out.println("Game Over!");
            gameEnded = true;
            map.getPlayer().regenHealth(map.getPlayer().maxHealth);
            map.getPlayer().regenMana(map.getPlayer().maxMana);
        }

        else if(enemy.health <= 0) {
            System.out.println("Enemy defeated");
            map.getPlayer().regenHealth(10);
            map.getPlayer().regenMana(100);
            map.getPlayer().addExperience(rand.nextInt(11) + 5);
        }
    }

    private void sanctuaryAction() {
        int healthRegen = rand.nextInt(20) + 10;
        int manaRegen = rand.nextInt(50) + 20;
        map.getPlayer().regenHealth(healthRegen);
        map.getPlayer().regenMana(manaRegen);
        System.out.println("Healing: \n\t" + map.getPlayer().health + " health \n\t" +  map.getPlayer().mana + " mana");
    }

    private void portalAction() {
        System.out.println("Level finished");
        map.getPlayer().addExperience(level * 5);
        level++;
        currAccount.gamesNumber++;
        map.getPlayer().regenMana(map.getPlayer().maxMana);
        map.getPlayer().regenHealth(map.getPlayer().health);
        System.out.println("New level: " + level + " \n");
        Character playerCpy = map.getPlayer();
        map = Grid.generateGrid(rand.nextInt(6) + 5, rand.nextInt(6) + 5);
        map.setPlayer(playerCpy);
        JsonInput.writeAccountsToJson(accounts);
    }

    public void chooseNextAction() throws InvalidGridSize {
        // display all current options
        map.printGrid();
        System.out.println("Choose direction: W A S D (or quit game - Q)");
        System.out.println("Player: \n\thealth: " + map.getPlayer().health + "\n\tmana: " + map.getPlayer().mana);

        boolean validDirection = false;
        while(!validDirection) {
            validDirection = true;
            char direction = input.next().charAt(0);
            switch (direction) {
                case 'W':
                case 'w':
                    try {
                        map.goNorth();
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'S':
                case 's':
                    try {
                        map.goSouth();
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'D':
                case 'd':
                    try {
                        map.goEast();
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'A':
                case 'a':
                    try {
                        map.goWest();
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'Q':
                case 'q':
                    gameEnded = true;
                    JsonInput.writeAccountsToJson(accounts);
                    return;
                default:
                    System.out.println("Invalid direction, try again.");
                    validDirection = false;
                    break;
            }
        }
    }

    public void run() throws InvalidGridSize {
        accounts = JsonInput.deserializeAccounts();
        boolean wrongPassword;
        boolean wrongUsername;
        loggedIn = false;

        while(!loggedIn) {
             wrongPassword = false;
             wrongUsername = false;
             gameEnded = false;

            for (Account account : accounts) {
                currAccount = account;
                wrongUsername = false;
                if (currAccount.getInformation().getCredentials().getEmail().compareTo(username) == 0) {
                    if (currAccount.getInformation().getCredentials().getPassword().compareTo(password) == 0) {
                        loggedIn = true;
                        break;
                    } else {
                        wrongPassword = true;
                        break;
                    }
                }
                wrongUsername = true;
            }

            if (wrongUsername) {
                System.out.println("Email not found, try again");
                loggedIn = false;
                break;
            }
            else if (wrongPassword) {
                System.out.println("Wrong password, try again");
                loggedIn = false;
                break;
            }

            else if (loggedIn) {
                boolean invalidChoice = true;
                System.out.println("Welcome, " + currAccount.getInformation().getName() + "!");

                while(invalidChoice) {
                    invalidChoice = false;
                    System.out.println("Choose your character: ");
                    for (int i = 0; i < currAccount.getCharacters().size(); i++) {
                        System.out.println("Option " + i);
                        currAccount.getCharacters().get(i).printCharacter();
                    }

                    if(useKeyboardInput) {
                        try {
                            characterChoice = input.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input, try again.");
                            input.nextLine();
                            invalidChoice = true;
                            continue;
                        }
                    }

                    if (characterChoice < 0 || characterChoice >= currAccount.getCharacters().size()) {
                        System.out.println("Invalid choice, try again.");
                        invalidChoice = true;
                    }
                    else {
                        System.out.println("Playing as " + currAccount.getCharacters().get(characterChoice).name);
                        currCharacter = currAccount.getCharacters().get(characterChoice);
                        System.out.println("Characteristics: \n\t-strength: " + currCharacter.strength + "\n\t-charisma: " + currCharacter.charisma +
                                "\n\t-dexterity: " + currCharacter.dexterity);
                    }
                }
            }
        }
    }
}
