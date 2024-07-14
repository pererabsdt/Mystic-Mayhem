import java.io.Serializable;
import java.util.ArrayList;

public class HomeGround implements Serializable {
    private String homeGround; // "Hillcrest", "Marshland", "Desert", "Arcane"

    public HomeGround(String homeGround) {
        this.homeGround = homeGround;
    }

    public void applyEffects(Character character) {
        switch (homeGround) {
            case "Hillcrest":
                if (character.getCategory().equals("Highlander")) {
                    character.setAttack(character.getAttack() + 1);
                    character.setDefence(character.getDefence() + 1);
                } else if (character.getCategory().equals("Marshlander") ||
                        character.getCategory().equals("Sunchildren")) {
                    character.setSpeed(character.getSpeed() - 1);
                }
                break;

            case "Marshland":
                if (character.getCategory().equals("Marshlander")) {
                    character.setDefence(character.getDefence() + 2);
                } else if (character.getCategory().equals("Sunchildren")){
                    character.setAttack(character.getAttack() - 1);
                } else if (character.getCategory().equals("Mystic")){
                    character.setSpeed(character.getSpeed() - 1);
                }
                break;

            case "Desert":
                if (character.getCategory().equals("Marshlander")) {
                    character.setHealth(character.getHealth() - 1);
                } else if (character.getCategory().equals("Sunchildren")) {
                    character.setAttack(character.getAttack() + 1);
                }
                break;

            case "Arcane":
                if (character.getCategory().equals("Mystic")) {
                    character.setAttack(character.getAttack() + 2);
                } else if (character.getCategory().equals("Highlander") || character.getCategory().equals("Marshlander")) {
                    character.setSpeed(character.getSpeed() - 1);
                    character.setDefence(character.getDefence() - 1);
                }
                // Health increase to be implemented in battle class or something
                break;

            default:
                System.err.println("Error: Invalid Home Ground type ");
        }
    }

    public void setHomeGround(String homeGround) {
        this.homeGround = homeGround;
    }

    public String getHomeGroundName() {
        return homeGround;
    }

    public ArrayList<String> getHomeGroundNameList() {
        ArrayList<String> homeGroundNameList = new ArrayList<>();
        homeGroundNameList.add("Desert");
        homeGroundNameList.add("Marshland");
        homeGroundNameList.add("Arcane");
        homeGroundNameList.add("Hillcrest");

        return homeGroundNameList;
    }



}