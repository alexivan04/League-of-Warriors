import java.util.Random;

public class Ice extends Spell{
    public Ice() {
        Random rand = new Random();
        damage = rand.nextInt(10) + 15;
        manaCost = rand.nextInt(25) + 20;
    }
}
