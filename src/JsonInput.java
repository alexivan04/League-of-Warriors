import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class JsonInput {
    static String accountPath = "accounts.json";
    static String newAccountPath = "accounts_backup.json";
    public static ArrayList<Account> deserializeAccounts() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(accountPath)) {
            // Parse JSON file
            JSONObject obj = (JSONObject) parser.parse(reader);
            JSONArray accountsArray = (JSONArray) obj.get("accounts");

            ArrayList<Account> accounts = new ArrayList<>();
            for (Object o : accountsArray) {
                JSONObject accountJson = (JSONObject) o;

                String name = (String) accountJson.get("name");
                String country = (String) accountJson.get("country");
                int gamesNumber = Integer.parseInt((String) accountJson.get("maps_completed"));

                Credentials credentials = null;
                JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                if (credentialsJson != null) {
                    String email = (String) credentialsJson.get("email");
                    String password = (String) credentialsJson.get("password");
                    credentials = new Credentials(email, password);
                } else {
                    System.out.println("! This account doesn't have all credentials !");
                }

                SortedSet<String> favoriteGames = new TreeSet<>();
                JSONArray games = (JSONArray) accountJson.get("favorite_games");
                if (games != null) {
                    for (Object game : games) {
                        favoriteGames.add((String) game);
                    }
                } else {
                    System.out.println("! This account doesn't have favorite games !");
                }

                ArrayList<Character> characters = new ArrayList<>();
                JSONArray charactersListJson = (JSONArray) accountJson.get("characters");
                if (charactersListJson != null) {
                    for (Object characterObj : charactersListJson) {
                        Character newCharacter = getNewCharacter((JSONObject) characterObj);
                        characters.add(newCharacter);
                    }
                } else {
                    System.out.println("! This account doesn't have characters !");
                }

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

    private static Character getNewCharacter(JSONObject characterObj) {
        String cname = (String) characterObj.get("name");
        String profession = (String) characterObj.get("profession");
        int lvl = Integer.parseInt((String) characterObj.get("level"));
        int experience = ((Long) characterObj.get("experience")).intValue();

        Character newCharacter = null;
        if ("Warrior".equals(profession))
            newCharacter = new Warrior(cname, experience, lvl);
        else if ("Rogue".equals(profession))
            newCharacter = new Rogue(cname, experience, lvl);
        else if ("Mage".equals(profession))
            newCharacter = new Mage(cname, experience, lvl);
        return newCharacter;
    }


    public static void writeAccountsToJson(ArrayList<Account> accounts) {
        JSONObject jsonObject = new JSONObject();
        JSONArray accountsArray = new JSONArray();

        for (Account account : accounts) {
            JSONObject accountJson = new JSONObject();

            Account.Information info = account.getInformation();
            accountJson.put("name", info.getName());
            accountJson.put("country", info.getCountry());
            accountJson.put("maps_completed", String.valueOf(account.getGamesNumber()));

            JSONObject credentialsJson = new JSONObject();
            Credentials credentials = info.getCredentials();
            if (credentials != null) {
                credentialsJson.put("email", credentials.getEmail());
                credentialsJson.put("password", credentials.getPassword());
            }
            accountJson.put("credentials", credentialsJson);

            JSONArray favoriteGamesArray = new JSONArray();
            for (String game : info.getFavoriteGames()) {
                favoriteGamesArray.add(game);
            }
            accountJson.put("favorite_games", favoriteGamesArray);

            JSONArray charactersArray = new JSONArray();
            for (Character character : account.getCharacters()) {
                JSONObject characterJson = new JSONObject();
                characterJson.put("name", character.name);
                characterJson.put("profession", character.getClass().getSimpleName());
                characterJson.put("level", String.valueOf(character.level));
                characterJson.put("experience", character.experience);
                charactersArray.add(characterJson);
            }
            accountJson.put("characters", charactersArray);

            accountsArray.add(accountJson);
        }

        jsonObject.put("accounts", accountsArray);

        // Write JSON to file
        try (FileWriter file = new FileWriter(accountPath)) {
            file.write(jsonObject.toJSONString());
            file.flush();
            System.out.println("Successfully written accounts to JSON.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
