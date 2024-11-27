import java.util.ArrayList;

abstract class Entity implements Battle {
    ArrayList<Spell> spells = new ArrayList<Spell>();
    int health, maxHealth;
    int mana, maxMana;

    boolean iceImmunity, fireImmunity, earthImmunity;

    public void regenHealth(int inc) {
        health += inc;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public void regenMana(int inc) {
        mana += inc;
        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public void useAbility();
}

abstract class Character extends Entity {
    String name;
    int experience;
    int level;

    int strength;
    int charisma;
    int dexterity;

    public Character(String name, int experience, int level) {
        this.name = name;
        this.experience = experience;
        this.level = level;
    }

    public void levelUp() {
        level++;
    }

    public void gainExperience(int experience) {
        this.experience += experience;
    }

    public void recieveDamage(int damage) {
        System.out.println(name + " received " + damage + " damage.");
    }

    public void getDamage() {
        System.out.println(name + " dealt " + level + " damage.");
    }

    public void printCharacter() {
        System.out.println("Name: " + name);
        System.out.println("Level: " + level);
        System.out.println("Experience: " + experience);
    }
}

class Warrior extends Character {
    public Warrior(String name, int experience, int level) {
        super(name, experience, level);
        strength = 10;
        charisma = 5;
        dexterity = 5;
    }
}