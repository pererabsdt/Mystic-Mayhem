import java.io.Serializable;

public class Healer extends Character implements Serializable {

    public Healer(String name, int price, double attack, double defence, double health, double speed, String category) {
        super(name, price, attack, defence, health, speed, category);
    }

    @Override
    public String getType() {
        return "Healer";
    }
}
