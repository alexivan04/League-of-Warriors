import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Element<Entity>, Battle{
    public String name;
    ArrayList<Spell> spells;
    int health, maxHealth;
    int mana, maxMana;

    boolean iceImmunity, fireImmunity, earthImmunity;

    public void regenHealth(int inc) {
        health += inc;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public void regenMana(int inc) {
        mana += inc;
        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public void useMana(int value) {
        mana -= value;
        if(mana <= 0) mana = 0;
    }

    public void generateRandomAbilities() {
        spells.clear();
        Random rand = new Random();
        int abilitiesCnt = rand.nextInt(4) + 3;
        for(int i = 0; i < abilitiesCnt; i++) {
            int randAbility = rand.nextInt(3);
            switch (randAbility) {
                case 0:
                    spells.add(new Fire());
                    break;
                case 1:
                    spells.add(new Ice());
                    break;
                case 2:
                    spells.add(new Earth());
                    break;
            }
        }
    }

    boolean displayAbilities() {
        if(spells.isEmpty()) {
            System.out.println("No abilities left!");
            return false;
        }
        else {
            System.out.println("Choose ability to use:");
            for (int i = 0; i < spells.size(); i++)
                System.out.println("\t" + i + ") " + spells.get(i).toString());
            return true;
        }
    }

    void useAbility(int i, Entity target) {
        Spell spell = spells.get(i);

        if (mana >= spell.manaCost) {
            System.out.println(name + " uses " + spell + " on " + target.name);
            target.accept(spell);
            spells.remove(i);
            useMana(spell.manaCost);
        } else {
            System.out.println(name + " doesn't have enough mana! Performing a normal attack.");
            target.receiveDamage(getDamage());
        }
    }

    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }

}
