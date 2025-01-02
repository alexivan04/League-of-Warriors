abstract public class Spell implements Visitor<Entity>{
    int damage;
    int manaCost;

    public String toString() {
        return "Type: " + this.getClass().getSimpleName()  + " Damage: " + damage + " Mana cost: " + manaCost;
    }
}
