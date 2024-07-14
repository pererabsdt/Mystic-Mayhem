import java.io.Serializable;

public class Armour extends Equipment implements Serializable {

    public Armour(String name, int price, int attackModifier, int defenseModifier, int healthModifier, int speedModifier) {
        super(name, price, attackModifier, defenseModifier, healthModifier, speedModifier);
    }

    @Override
    public String getType() {
        return "Armour";
    }
}
