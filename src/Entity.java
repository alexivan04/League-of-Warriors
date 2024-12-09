import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Battle{
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

    void useAbility(int i, Entity entity) {
        Spell currSpell = spells.get(i);
        if (mana >= currSpell.manaCost) {
            if(currSpell.getClass().equals(Ice.class) && iceImmunity ||
               currSpell.getClass().equals(Fire.class) && fireImmunity ||
               currSpell.getClass().equals(Earth.class) && earthImmunity) {
                System.out.println(entity.getClass().getSimpleName() + " has " + currSpell.getClass().getSimpleName() + " resistance.");
                entity.recieveDamage(getDamage());
                spells.remove(i);
                useMana(currSpell.manaCost);
            }

            else {
                System.out.println(currSpell.getClass().getSimpleName() + " damaged " + entity.getClass().getSimpleName());
                entity.recieveDamage(currSpell.damage + getDamage());
                spells.remove(i);
                useMana(currSpell.manaCost);
            }
        }
        else {
            entity.recieveDamage(getDamage());
        }

        // else do basic attack
    }
}
