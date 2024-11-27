// from accounts.json, using deserializeJsonFile() method from JsonInput.java
public class Account {
    ArrayList<Character> characters;
    Information information;
    int gamesNumber;

    class Information
    {
        Credentials credentials;
        SortedSet<String> favoriteGames; //sorted alfabetically
        String name;
        String country;
    }

    public void nextChoice() {
        System.out.println("What would you like to do next?");
        System.out.println("1. Create a new character");
        System.out.println("2. Select a character");
        System.out.println("3. Delete a character");
        System.out.println("4. Exit");
    }

    // load parsed data from accounts.json
    // login into account and choose character to play with
    public void run() {

    }
}

class Credentials
{
    String email, password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}