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

    public Game() {
        gameEnded = false;
        level = 1;
        input = new Scanner(System.in);
        rand = new Random();
    }

    public void makeAction() {
        switch (map.get(map.getPlayerCell().getX()).get(map.getPlayerCell().getY()).getType()) {
            case PORTAL:
                System.out.println("Level finished");
                map.getPlayer().addExperience(level * 5);
                level++;
                map.getPlayer().regenMana(map.getPlayer().maxMana);
                map.getPlayer().regenHealth(map.getPlayer().health);
                System.out.println("New level: " + level + " \n");
                Character playerCpy = map.getPlayer();
                map = Grid.generateGrid(rand.nextInt(6) + 5, rand.nextInt(6) + 5);
                map.setPlayer(playerCpy);
                break;

            case SANCTUARY:
                int healthRegen = rand.nextInt(20) + 10;
                int manaRegen = rand.nextInt(50) + 20;
                map.getPlayer().regenHealth(healthRegen);
                map.getPlayer().regenMana(manaRegen);
                System.out.println("Healing: \n\t" + map.getPlayer().health + " health \n\t" +  map.getPlayer().mana + " mana");
                break;

            case ENEMY:
                int choice;
                Enemy enemy = new Enemy();
                enemy.generateRandomAbilities();
                map.getPlayer().generateRandomAbilities();
                System.out.println("Enemy ahead!");
                while(enemy.health > 0 && map.getPlayer().health > 0) {
                    System.out.println("Make a choice:\n\t1) Attack\n\t2) Use Ability");
                    try {
                        choice = input.nextInt(); //TODO: exception handling
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid input, try again.");
                        input.nextLine();
                        continue;
                    }
                    if (choice == 1)
                        enemy.recieveDamage(map.getPlayer().getDamage());
                    else if (choice == 2) {
                        if(map.getPlayer().displayAbilities()) {
                            boolean validAbilityChoice = false;
                            int abilityChoice;
                            while(!validAbilityChoice) {
                                validAbilityChoice = true;
                                try {
                                    abilityChoice = input.nextInt();
                                }
                                catch (InputMismatchException e) {
                                    System.out.println("Invalid input, try again.");
                                    input.nextLine();
                                    validAbilityChoice = false;
                                    continue;
                                }
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
                        map.getPlayer().recieveDamage(enemy.getDamage());
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
                break;

            default:
                break;
        }
    }
    public void chooseNextAction() throws InvalidGridSize {
        // display all current options
        Cell playerCell = map.getPlayerCell();
        map.printGrid();
        System.out.println("Choose direction: N S E W (or quit game - Q)");
        System.out.println("Player: \n\thealth: " + map.getPlayer().health + "\n\tmana: " + map.getPlayer().mana);

        boolean validDirection = false;
        while(!validDirection) {
            validDirection = true;
            char direction = input.next().charAt(0);
            switch (direction) {
                case 'N':
                case 'n':
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
                case 'E':
                case 'e':
                    try {
                        map.goEast();
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'W':
                case 'w':
                    try {
                        map.goWest();
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'Q':
                case 'q':
                    gameEnded = true;
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

                    int choice;
                    try {
                        choice = input.nextInt();
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid input, try again.");
                        input.nextLine();
                        invalidChoice = true;
                        continue;
                    }
                    if (choice < 0 || choice >= currAccount.getCharacters().size()) {
                        System.out.println("Invalid choice, try again.");
                        invalidChoice = true;
                    }
                    else {
//                        map.setPlayer(currAccount.getCharacters().get(choice));
                        System.out.println("Playing as " + currAccount.getCharacters().get(choice).name);
                        currCharacter = currAccount.getCharacters().get(choice);
                        System.out.println("Characteristics: \n\t-strength: " + currCharacter.strength + "\n\t-charisma: " + currCharacter.charisma +
                                "\n\t-dexterity: " + currCharacter.dexterity);
                    }
                }
            }
        }
    }
}
