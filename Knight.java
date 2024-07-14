import java.io.Serializable;

public class Knight extends Character implements Serializable {

    public Knight(String name, int price, double attack, double defence, double health, double speed, String category) {
        super(name, price, attack, defence, health, speed, category);
    }

    @Override
    public String getType() {
        return "Knight";
    }
}
