import java.util.Random;

public class Earth extends Spell{
    public Earth() {
        Random rand = new Random();
        damage = rand.nextInt(5) + 15;
        manaCost = rand.nextInt(10) + 10;
    }

    public void visit(Entity character) {
        if(character.earthImmunity) {
            System.out.println(character.name + " is immune to earth damage");
        }
        else {
            System.out.println("Earth spell dealt " + damage + " damage to " + character.name);
            character.receiveDamage(damage);
        }
    }
}
