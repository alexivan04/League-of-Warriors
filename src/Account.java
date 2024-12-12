import java.util.ArrayList;
import java.util.SortedSet;

public class Account {
    ArrayList<Character> characters;
    Information information;
    int gamesNumber;

    public static class Information
    {
        private Credentials credentials;
        private SortedSet<String> favoriteGames;
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
}