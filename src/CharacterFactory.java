public class CharacterFactory {
    public static Character createCharacter(String name, String profession, int level, int experience) {
        Character newCharacter = null;
        if ("Warrior".equals(profession))
            newCharacter = new Warrior(name, experience, level);
        else if ("Rogue".equals(profession))
            newCharacter = new Rogue(name, experience, level);
        else if ("Mage".equals(profession))
            newCharacter = new Mage(name, experience, level);
        return newCharacter;
    }
}
