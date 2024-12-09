import java.util.Random;

public class Fire extends Spell{
    public Fire() {
        Random rand = new Random();
        damage = rand.nextInt(20) + 25;
        manaCost = rand.nextInt(10) + 30;
    }
}