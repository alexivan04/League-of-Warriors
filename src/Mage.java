import java.util.Random;

public class Mage extends Character{
    public Mage(String name, int experience, int level) {
        super(name, experience, level);
        Random rand = new Random();
        strength = 5;
        charisma = 10;
        dexterity = 5;
        fireImmunity = false;
        earthImmunity = true;
        iceImmunity = false;
//        for(int i = 1; i < level; i++)
//            this.levelUp();
        this.experience = experience;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        charisma += 10;
        strength += 2;
        dexterity += 5;
    }

    @Override
    public void recieveDamage(int damage) {
        Random rand = new Random();
        if(rand.nextInt(100)  + strength / 2 + dexterity / 2 > 50) {
            System.out.println(name + " recieved halfed damage!");
            damage /= 2;
        }
        super.recieveDamage(damage);
    }

    @Override
    public int getDamage() {
        if (health > 0) {
            Random rand = new Random();
            int damage = baseDamage * charisma / 5;
            if (rand.nextInt(100) + charisma / 2 > 50) {
                damage *= 2;
            }
            System.out.println(name + " dealt " + damage + " damage");
            return damage;
        }
        else return 0;
    }
}
