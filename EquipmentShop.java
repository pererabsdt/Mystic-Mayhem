import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EquipmentShop {
    private ArrayList<Equipment> availableEquipment;
    Player player;

    public EquipmentShop(Player player) {
        this.player = player;
        availableEquipment = new ArrayList<>();
        initializeShop();
    }

    private void initializeShop() {
        // Armour
        availableEquipment.add(new Armour("Chainmail", 70, 0, 1, 0, -1));
        availableEquipment.add(new Armour("Regalia", 105, 0, 1, 0, 0));
        availableEquipment.add(new Armour("Fleece", 150, 0, 2, 1, -1));

        // Artefacts
        availableEquipment.add(new Artefact("Excalibur", 150, 2, 0, 0, 0));
        availableEquipment.add(new Artefact("Amulet", 200, 1, -1, 1, 1));
        availableEquipment.add(new Artefact("Crystal", 210, 2, 1, -1, -1));
    }

    public void displayAvailableEquipment() {
        System.out.println("\n******** Armour ********");
        displayEquipment(getAvailableArmour());
        buyEquipment(player, getAvailableArmour());

        System.out.println("\n******** Artefacts ********");
        displayEquipment(getAvailableArtefacts());
        buyEquipment(player, getAvailableArtefacts());
    }

    private List<Armour> getAvailableArmour() {
        List<Armour> ArmourList = new ArrayList<>();
        for (Equipment equipment: availableEquipment) {
            if (equipment instanceof Armour) {
                ArmourList.add((Armour)equipment);
            }
        }
        return ArmourList;
    }

    private List<Artefact> getAvailableArtefacts() {
        List<Artefact> artefactList = new ArrayList<>();
        for (Equipment equipment: availableEquipment) {
            if (equipment instanceof Artefact) {
                artefactList.add((Artefact)equipment);
            }
        }
        return artefactList;
    }

    private void displayEquipment(List<? extends Equipment> equipmentList) {
        int index = 1;
        for (Equipment equipment : equipmentList) {
            System.out.print("\n" + index + ". Name: " + equipment.getName());
            System.out.print("    Price: " + equipment.getPrice());
            System.out.print("    Attack Modifier: " + equipment.getAttackModifier());
            System.out.print("    Defense Modifier: " + equipment.getDefenseModifier());
            System.out.print("    Health Modifier: " + equipment.getHealthModifier());
            System.out.print("    Speed Modifier: " + equipment.getSpeedModifier());
            index++;
        }
        System.out.print("\nEnter the number of the equipment to buy (Press any other number to skip purchases): ");
    }

    public void buyEquipment(Player player, List<? extends Equipment> equipments) {
        Scanner scanner = new Scanner(System.in);

        while (true) { // Keep asking until valid input is provided
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline

                if (choice >= 1 && choice <= equipments.size()) {
                    Equipment equipmentToBuy = equipments.get(choice - 1); // Get character based on index
                    if (equipmentToBuy != null) {
                        if (player.buyEquipment(equipmentToBuy)) {
                            System.out.println("Purchase successful! You have " + player.getCoins() + " Gold Coins remaining");

                        } else {
                            System.out.println("Not enough gold!");
                        }
                    } else {
                        System.out.println("Character not found in shop.");
                    }
                } else {  //(choice == equipments.size() + 1)
                    System.out.println("Skipping purchase...");
                }

                break; // Exit the loop if input was a valid integer

            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.next(); // Clear invalid input from scanner
            }
        }
    }



}
