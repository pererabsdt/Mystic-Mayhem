import java.io.Serializable;

public class Character implements Serializable {
    private String name;
    private int price;
    private double attack;
    private double defence;
    private double health;
    private double speed;
    private String category;
    private Armour armour;
    private Artefact artefact;

    public Character(String name, int price, double attack, double defence, double health, double speed, String category) {
        this.name = name;
        this.price = price;
        this.attack = attack;
        this.defence = defence;
        this.health = health;
        this.speed = speed;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return null;
    }

    public int getPrice() {
        return this.price;
    }
    
    public void setPrice(int newPrice) {this.price = newPrice;}


    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAttack() {
        return this.attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }


    public double getDefence() {
        return this.defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public String getCategory() {
        return this.category;
    }

    public Artefact getArtefact() {
        return this.artefact;
    }

    public Armour getArmour() {
        return armour;
    }

    public void equipItem(Equipment equipment) {
        if (equipment.getType().equals("Armour")) {
            armour = (Armour) equipment;
        } else if (equipment.getType().equals("Artefact")) {
            artefact = (Artefact) equipment;
        }
    }

    public void unequipItem(String slot) {
        if (slot.equals("Armour")) {
            armour = null;
        } else if (slot.equals("Artefact")) {
            artefact = null;
        }
    }

    public double calculateDamage() {
        return 0;
    }

    public void calculateAttack() {
        double baseAttack = getAttack();
        int equipmentBonus = 0;

        if (artefact != null) {
            equipmentBonus += artefact.getAttackModifier();
        }
        if (armour != null) {
            equipmentBonus += armour.getAttackModifier();
        }
        setAttack(baseAttack + equipmentBonus);
    }

    public void calculateDefence() {
        double baseDefence = getDefence();
        int equipmentBonus = 0;
        if (artefact != null) {
            equipmentBonus += artefact.getDefenseModifier();
        }
        if (armour != null) {
            equipmentBonus += armour.getDefenseModifier();
        }

        setDefence(baseDefence + equipmentBonus);
    }


    public void calculateHealth() {
        double baseHealth = getHealth();
        int equipmentBonus = 0;

        if (artefact != null) {
            equipmentBonus += artefact.getHealthModifier();
        }
        if (armour != null) {
            equipmentBonus += armour.getHealthModifier();
        }

        setHealth(baseHealth + equipmentBonus);

    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        if (this.health < 0) {
            return 0;
        } else {
            return Math.round(this.health * 10.0) / 10.0;
        }
    }

    public void calculateSpeed() {
        double baseSpeed = getSpeed();
        int equipmentBonus = 0;

        if (artefact != null) {
            equipmentBonus += artefact.getSpeedModifier();
        }
        if (armour != null) {
            equipmentBonus += armour.getSpeedModifier();
        }

        setSpeed(baseSpeed + equipmentBonus);
    }


}
