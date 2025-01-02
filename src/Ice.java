import java.util.Random;

public class Ice extends Spell{
    public Ice() {
        Random rand = new Random();
        damage = rand.nextInt(10) + 15;
        manaCost = rand.nextInt(25) + 20;
    }

    public void visit(Entity character) {
        if(character.iceImmunity) {
            System.out.println(character.name + " is immune to ice damage");
        }
        else {
            System.out.println("Ice spell dealt " + damage + " damage to " + character.name);
            character.receiveDamage(damage);
        }
    }
}
