abstract public class Spell {
    int damage;
    int manaCost;

    public String toString() {
        return "Type: " + this.getClass().getSimpleName()  + " Damage: " + damage + " Mana cost: " + manaCost;
    }
}
