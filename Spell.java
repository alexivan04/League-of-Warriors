abstract class Spell {
    int damage;
    int manaCost;

    public String toString() {
        return "Damage: " + damage + ", Mana cost: " + manaCost;
    }
}

class Ice extends Spell {
    public Ice() {
        damage = 10;
        manaCost = 5;
    }
}

class Fire extends Spell {
    public Fire() {
        damage = 15;
        manaCost = 10;
    }
}

class Earth extends Spell {
    public Earth() {
        damage = 20;
        manaCost = 15;
    }
}