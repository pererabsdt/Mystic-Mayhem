import java.io.Serializable;

public class Archer extends Character implements Serializable {

    public Archer(String name, int price, double attack, double defence, double health, double speed, String category) {
        super(name, price, attack, defence, health, speed, category);
    }

    @Override
    public String getType() {
        return "Archer";
    }
}
