import java.io.Serializable;

public class Artefact extends Equipment implements Serializable {

    public Artefact(String name, int price, int attackModifier, int defenseModifier, int healthModifier, int speedModifier) {
        super(name, price, attackModifier, defenseModifier, healthModifier, speedModifier);
    }

    @Override
    public String getType() {
        return "Artefact";
    }
}
