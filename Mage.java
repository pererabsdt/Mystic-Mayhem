import java.io.Serializable;

public class Mage extends Character implements Serializable {

    public Mage(String name, int price, double attack, double defence, double health, double speed, String category) {
        super(name, price, attack, defence, health, speed, category);
    }

    @Override
    public String getType() {
        return "Mage";
    }
}
