import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class JsonInput {
    public static ArrayList<Account> deserializeAccounts() {
        String accountPath = "accounts.json";
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(accountPath)) {
            // Parse JSON file
            JSONObject obj = (JSONObject) parser.parse(reader);
            JSONArray accountsArray = (JSONArray) obj.get("accounts");

            ArrayList<Account> accounts = new ArrayList<>();
            for (Object o : accountsArray) {
                JSONObject accountJson = (JSONObject) o;

                // Extracting fields
                String name = (String) accountJson.get("name");
                String country = (String) accountJson.get("country");
                int gamesNumber = Integer.parseInt((String) accountJson.get("maps_completed"));

                // Extract credentials
                Credentials credentials = null;
                JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                if (credentialsJson != null) {
                    String email = (String) credentialsJson.get("email");
                    String password = (String) credentialsJson.get("password");
                    credentials = new Credentials(email, password);
                } else {
                    System.out.println("! This account doesn't have all credentials !");
                }

                // Extract favorite games
                SortedSet<String> favoriteGames = new TreeSet<>();
                JSONArray games = (JSONArray) accountJson.get("favorite_games");
                if (games != null) {
                    for (Object game : games) {
                        favoriteGames.add((String) game);
                    }
                } else {
                    System.out.println("! This account doesn't have favorite games !");
                }

                // Extract characters
                ArrayList<Character> characters = new ArrayList<>();
                JSONArray charactersListJson = (JSONArray) accountJson.get("characters");
                if (charactersListJson != null) {
                    for (Object characterObj : charactersListJson) {
                        JSONObject charJson = (JSONObject) characterObj;
                        String cname = (String) charJson.get("name");
                        String profession = (String) charJson.get("profession");
                        int lvl = Integer.parseInt((String) charJson.get("level"));
                        int experience = ((Long) charJson.get("experience")).intValue();

                        Character newCharacter = null;
                        if ("Warrior".equals(profession))
                            newCharacter = new Warrior(cname, experience, lvl);
                        else if ("Rogue".equals(profession))
                            newCharacter = new Rogue(cname, experience, lvl);
                        else if ("Mage".equals(profession))
                            newCharacter = new Mage(cname, experience, lvl);

                        characters.add(newCharacter);
                    }
                } else {
                    System.out.println("! This account doesn't have characters !");
                }

                // Create Account object
                Account.Information information = new Account.Information(credentials, favoriteGames, name, country);
                Account account = new Account(characters, gamesNumber, information);
                accounts.add(account);
            }
            return accounts;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
