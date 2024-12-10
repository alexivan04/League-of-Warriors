import java.util.Random;

public class Warrior extends Character{
    public Warrior(String name, int experience, int level) {
        super(name, experience, level);
        strength = 10;
        charisma = 5;
        dexterity = 5;
        fireImmunity = true;
        earthImmunity = false;
        iceImmunity = false;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        strength += 10;
        charisma += 2;
        dexterity += 5;
    }

    @Override
    public void recieveDamage(int damage) {
        Random rand = new Random();
        if(rand.nextInt(100)  + dexterity / 2 + charisma / 2 > 50)
            damage /= 2;
        super.recieveDamage(damage);
    }

    @Override
    public int getDamage() {
        if(health > 0) {
            Random rand = new Random();
            int damage = baseDamage * strength / 5;
            if (rand.nextInt(100) + strength / 2 > 50)
                damage *= 2;
            System.out.println(name + " dealt " + damage + " damage");
            return damage;
        }
        else return 0;
    }
}