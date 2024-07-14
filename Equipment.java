import java.io.Serializable;

public class Equipment implements Serializable {
    private String name;
    private int price;
    private int defenseModifier;
    private int attackModifier;
    private int healthModifier;
    private int speedModifier;

    public Equipment(String name, int price, int attackModifier, int defenseModifier, int healthModifier, int speedModifier) {
        this.name = name;
        this.price = price;
        this.defenseModifier = defenseModifier;
        this.attackModifier = attackModifier;
        this.healthModifier = healthModifier;
        this.speedModifier = speedModifier;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return null;
    }

    public int getPrice() {
        return price;
    }

    public int getAttackModifier() {
        return attackModifier;
    }

    public int getDefenseModifier() {
        return defenseModifier;
    }

    public int getHealthModifier() {
        return healthModifier;
    }

    public int getSpeedModifier() {
        return speedModifier;
    }

}