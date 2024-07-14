import java.io.Serializable;

public class MythicalCreature extends Character implements Serializable {

    public MythicalCreature(String name, int price, double attack, double defence, double health, double speed, String category) {
        super(name, price, attack, defence, health, speed, category);
    }

    @Override
    public String getType() {
        return "Mythical Creature";
    }
}
