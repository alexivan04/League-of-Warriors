import java.util.Random;

public class Fire extends Spell{
    public Fire() {
        Random rand = new Random();
        damage = rand.nextInt(20) + 25;
        manaCost = rand.nextInt(10) + 30;
    }

    public void visit(Entity character) {
        if(character.fireImmunity) {
            System.out.println(character.name + " is immune to fire damage");
        }
        else {
            System.out.println("Fire spell dealt " + damage + " damage to " + character.name);
            character.receiveDamage(damage);
        }
    }
}