import java.util.ArrayList;
import java.util.SortedSet;

public class Account {
    ArrayList<Character> characters;
    Information information;
    int gamesNumber;

    public static class Information {
        private final Credentials credentials;
        private final SortedSet<String> favoriteGames;
        private final String name;
        private final String country;

        private Information(Builder builder) {
            this.credentials = builder.credentials;
            this.favoriteGames = builder.favoriteGames;
            this.name = builder.name;
            this.country = builder.country;
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

        public static class Builder {
            private Credentials credentials;
            private SortedSet<String> favoriteGames;
            private String name;
            private String country;

            public Builder() {
            }

            public Builder setCredentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }

            public Builder setFavoriteGames(SortedSet<String> favoriteGames) {
                this.favoriteGames = favoriteGames;
                return this;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setCountry(String country) {
                this.country = country;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
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