import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity{
    int damage;
    public Enemy() {
        Random rand = new Random();
        spells = new ArrayList<Spell>();

        health = rand.nextInt(26) + 25;
        mana = rand.nextInt(51) + 50;
        maxHealth = 70;
        maxMana = 100;
        damage = rand.nextInt(11);

        iceImmunity = rand.nextBoolean();
        fireImmunity = rand.nextBoolean();
        earthImmunity = rand.nextBoolean();
    }

    @Override
    public void receiveDamage(int damage) {
        Random rand = new Random();
        if(rand.nextInt(100) < 50){
            System.out.println("Enemy avoided damage");
        }
        else {
            health -= damage;
            if(health <= 0) health = 0;
            System.out.println("Enemy received " + damage + " damage");
        }

    }

    public int getDamage() {
        if(health > 0) {
            Random rand = new Random();
            if (rand.nextInt(100) < 50) {
                damage *= 2;
                System.out.println("Enemy dealt double damage!");
            }
            System.out.println("Enemy dealt " + damage + " damage");
            return damage;
        }
        else return 0;
    }
}
