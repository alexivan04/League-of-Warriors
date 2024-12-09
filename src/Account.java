import java.util.ArrayList;
import java.util.SortedSet;

public class Account {
    ArrayList<Character> characters;
    Information information;
    int gamesNumber;

    public static class Information
    {
        private Credentials credentials;
        private SortedSet<String> favoriteGames; //sorted alfabetically
        private String name;
        private String country;

        public Information(Credentials credentials, SortedSet<String> favoriteGames, String name, String country) {
            this.credentials = credentials;
            this.favoriteGames = favoriteGames;
            this.name = name;
            this.country = country;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public SortedSet<String> getFavoriteGames() {
            return favoriteGames;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

        public void setFavoriteGames(SortedSet<String> favoriteGames) {
            this.favoriteGames = favoriteGames;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public Account(ArrayList<Character> characters, int gamesNumber, Information information) {
        this.characters = characters;
        this.information = information;
        this.gamesNumber = gamesNumber;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public Information getInformation() {
        return information;
    }

    public int getGamesNumber() {
        return gamesNumber;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public void setGamesNumber(int gamesNumber) {
        this.gamesNumber = gamesNumber;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void removeCharacter(Character character) {
        characters.remove(character);
    }

    public void printAccount() {
        System.out.println("Account information:");
        System.out.println("Name: " + information.getName());
        System.out.println("Country: " + information.getCountry());
        System.out.println("Favorite games: ");
        for (String game : information.getFavoriteGames()) {
            System.out.println(game);
        }
        System.out.println("Number of games played: " + gamesNumber);
        System.out.println("Characters: ");
        for (Character character : characters) {
            character.printCharacter();
        }
    }

    public void printCharacters() {
        System.out.println("Characters: ");
        for (Character character : characters) {
            character.printCharacter();
        }
    }
    public void nextChoice() {
        System.out.println("What would you like to do next?");
        System.out.println("1. Create a new character");
        System.out.println("2. Select a character");
        System.out.println("3. Delete a character");
        System.out.println("4. Exit");
    }
}