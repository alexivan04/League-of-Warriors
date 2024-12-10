import java.util.ArrayList;
import java.util.Random;

public abstract class Character extends Entity{
    String name;
    int experience;
    final int experienceTreshold = 25;
    int level;


    int strength;
    int charisma;
    int dexterity;

    int baseDamage;

    public Character(String name, int experience, int level) {
        Random rand = new Random();
        this.name = name;
        this.experience = experience;
        this.level = level;
        this.mana = 100;
        this.maxMana = 100;
        this.health = 100;
        this.maxHealth = 100;
        baseDamage = rand.nextInt(6) + 5;
        spells = new ArrayList<Spell>();
    }

    public void levelUp() {
        this.level++;
        System.out.println(name + " leveled up: " + level);
    }

    public void addExperience(int experience) {
        this.experience += experience;
        while (this.experience >= this.level * this.experienceTreshold) {
            this.experience -= this.level * this.experienceTreshold;
            levelUp();
        }
        System.out.println("level: " + level + ", experience: " + this.experience);
    }


    @Override
    public void recieveDamage(int damage) {
        health -= damage;
        if(health <= 0) health = 0;
        System.out.println(name + " received " + damage + " damage");
    }

    public void gainExperience(int experience) {
        this.experience += experience;
    }

    public void printCharacter() {
        System.out.println("\tName: " + name);
        System.out.println("\tLevel: " + level);
        System.out.println("\tExperience: " + experience);
    }
}
