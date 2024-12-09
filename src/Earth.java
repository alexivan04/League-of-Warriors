import java.util.Random;

public class Earth extends Spell{
    public Earth() {
        Random rand = new Random();
        damage = rand.nextInt(5) + 15;
        manaCost = rand.nextInt(10) + 10;
    }
}
