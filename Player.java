import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.Random;


public class Player implements Serializable {
    private String username;
    private String name;
    private int userID;
    private double goldCoins;
    private int XP;
    private HomeGround homeGround;
    private ArrayList<Character> army;
    public boolean isFirstTimePurchase;
    private ArrayList<Equipment> equipmentInventory;
    private HashMap<String, Equipment> equippedItems;

    public Player(String name, String username, HomeGround homeGround) {
        this.name = name;
        this.username = username;
        this.homeGround = homeGround;
        Random random = new Random();
        this.userID = random.nextInt(9999999);
        this.goldCoins = 500;
        this.army = new ArrayList<>();
        this.isFirstTimePurchase = true;
        this.equipmentInventory = new ArrayList<>();
        this.army = new ArrayList<>();
    }
    public Player(Player original){
        ArrayList<Character> army2 = new ArrayList<>();
        for (Character originalCharacter : original.getArmy()){
            Character newCharacter = null;

            if (originalCharacter instanceof Archer){
                newCharacter = new Archer(originalCharacter.getName(), originalCharacter.getPrice(), originalCharacter.getAttack(), originalCharacter.getDefence(), originalCharacter.getHealth(), originalCharacter.getSpeed(), originalCharacter.getCategory());
            }
            if (originalCharacter instanceof Knight){
                newCharacter = new Knight(originalCharacter.getName(), originalCharacter.getPrice(), originalCharacter.getAttack(), originalCharacter.getDefence(), originalCharacter.getHealth(), originalCharacter.getSpeed(), originalCharacter.getCategory());
            }
            if (originalCharacter instanceof Mage){
                newCharacter = new Mage(originalCharacter.getName(), originalCharacter.getPrice(), originalCharacter.getAttack(), originalCharacter.getDefence(), originalCharacter.getHealth(), originalCharacter.getSpeed(), originalCharacter.getCategory());
            }
            if (originalCharacter instanceof Healer){
                newCharacter = new Healer(originalCharacter.getName(), originalCharacter.getPrice(), originalCharacter.getAttack(), originalCharacter.getDefence(), originalCharacter.getHealth(), originalCharacter.getSpeed(), originalCharacter.getCategory());
            }
            if (originalCharacter instanceof MythicalCreature){
                newCharacter = new MythicalCreature(originalCharacter.getName(), originalCharacter.getPrice(), originalCharacter.getAttack(), originalCharacter.getDefence(), originalCharacter.getHealth(), originalCharacter.getSpeed(), originalCharacter.getCategory());
            }

            army2.add(newCharacter);

        }

        this.setArmy(army2);

    }



    public void changeName(String newName) {
        this.name = newName;
    }

    public double getCoins() {
        return goldCoins;
    }

    public boolean buyCharacter(Character character) {
        if (goldCoins >= character.getPrice()) {
            goldCoins -= character.getPrice();
            army.add(character);
            return true; // Purchase successful
        } else {
            return false; // Not enough gold
        }
    }


    public void sellCharacter(Character character) {
        if (army.remove(character)) {
            goldCoins += character.getPrice() * 0.9;
        }
    }

    public Character findCharacterByName(String name) {
        for (Character character : army) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }

    public boolean buyEquipment(Equipment equipment) {
        if (goldCoins >= equipment.getPrice()) {
            goldCoins -= equipment.getPrice();
            equipmentInventory.add(equipment);
            return true; // Purchase successful
        } else {
            return false; // Not enough gold
        }
    }

    public void equipItemToCharacter(Character character, Equipment equipment) {
        character.equipItem(equipment);
        equipmentInventory.remove(equipment);
    }


    public void increaseXP() {
        XP++;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public int getXP() {
        return XP;
    }

    private void setName(String newName) {
        this.name = newName;
    }

    public HomeGround getHomeGround() {
        return this.homeGround;
    }

    public void setHomeGround(HomeGround homeGround) {
        this.homeGround = homeGround;
    }

    public void setCoins(double coins) {
        this.goldCoins = coins;
    }


    public ArrayList<Character> getArmy() {
        return this.army;
    }

    public ArrayList<Equipment> getEquipmentInventory() {
        return this.equipmentInventory;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getArmyMembers() {
        ArrayList<String>armyMembers = new ArrayList<>();
        for (Character character: getArmy()) {
            armyMembers.add(character.getName());
        }
        return armyMembers;
    }

    public ArrayList<String> getEquipmentNames() {
        ArrayList<String>equipmentNames = new ArrayList<>();
        for (Equipment equipment: getEquipmentInventory()) {
            equipmentNames.add(equipment.getName());
        }
        return equipmentNames;
    }


    public String getUserName() {
        return this.username;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setArmy(ArrayList<Character> army) {
        this.army = army;
    }

    public void displayPlayerInfo(Player player) {
        System.out.println("__________________________________________________________________________________");
        System.out.println("\nName : " + player.getName());
        System.out.println("Username : " + player.getUserName());
        System.out.println("UserID : " + player.getUserID());
        System.out.println("XP : " + getXP());
        System.out.println("Gold Coins : " + getCoins());
        System.out.print("Your Army :  ");
        for (String member : getArmyMembers()) {
            System.out.print(member + "   ");
        }

        while (true) {
            System.out.print("\n\n1. Change Name\t\t2. Back to Main Menu ----->   ");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int userChoice = scanner.nextInt();
                if (userChoice == 1) {
                    System.out.println("Enter your new name: ");
                    Scanner scanner1 = new Scanner(System.in);
                    String newName = scanner1.nextLine();
                    setName(newName);
                    System.out.println("Name changed successfully!");
                    System.out.println("__________________________________________________________________________________");
                } else if (userChoice == 2) {
                    // Code to go back to the main menu
                    break; // Exiting the loop to go back to the main menu
                } else {
                    System.out.println("Invalid input. Please enter 1 or 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        }
    }

}



