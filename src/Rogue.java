import java.util.Random;

public class Rogue extends Character{
    public Rogue(String name, int experience, int level) {
        super(name, experience, level);
        strength = 5;
        charisma = 5;
        dexterity = 10;
        fireImmunity = false;
        earthImmunity = true;
        iceImmunity = false;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        dexterity += 10;
        strength += 5;
        charisma += 2;
    }

    @Override
    public void receiveDamage(int damage) {
        Random rand = new Random();
        if(rand.nextInt(100)  + strength / 2 + charisma / 2 > 50) {
            System.out.println(name + " recieved halfed damage!");
            damage /= 2;
        }
        super.receiveDamage(damage);
    }

    @Override
    public int getDamage() {
        if(health > 0) {
            Random rand = new Random();
            int damage = baseDamage * dexterity / 5;
            if (rand.nextInt(100) + dexterity / 2 > 50) {
                damage *= 2;
            }
            System.out.println(name + " dealt " + damage + " damage");
            return damage;
        }
        else return 0;

    }
}