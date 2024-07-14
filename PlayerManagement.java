import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Scanner;
import java.util.InputMismatchException;


public class PlayerManagement {
    public ArrayList<String> usernameList;
    public static final String ANSI_PURPLE  = "\u001B[32m";
    public static final String ANSI_RESET  = "\u001B[0m";


    public PlayerManagement() {
        usernameList = new ArrayList<>();
        loadUsernames();
    }

    public Player createNewPlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Create your profile");
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your Username: ");
        String username = scanner.nextLine();
        while (!isValidUsername(username)) {
            System.out.println("Username is taken. Please choose another one: ");
            username = scanner.nextLine();
        }
        usernameList.add(username);
        saveUsernames();

        HomeGround homeGround = new HomeGround(getHomeGroundName());
        Player player = new Player(name, username, homeGround);

        System.out.println(ANSI_PURPLE + "\n\nSelect your army. You have 500 Gold Coins" + ANSI_RESET );

        CharacterShop characterShop = new CharacterShop(player);
        createStartingArmy(player, characterShop); // New method to handle character selection

        return player;
    }

    private boolean isValidUsername(String username) {
        return !usernameList.contains(username);
    }



    public String getHomeGroundName() {
        Scanner scanner = new Scanner(System.in);

        while (true) { // Keep asking until a valid option is selected
            try {
                System.out.println("Select your home ground:");
                System.out.print("1. Desert\t\t 2. Hillcrest\t\t 3. Marshland\t\t 4.Arcane ----->    ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline

                switch (choice) {
                    case 1:
                        return "Desert";
                    case 2:
                        return "Hillcrest";
                    case 3:
                        return "Marshland";
                    case 4:
                        return "Arcane";
                    default:
                        System.out.println("Invalid choice. Please select a number between 1 and 4");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input to avoid an infinite loop
            }
        }
    }


    private void loadUsernames() {
        try {
            File file = new File("usernames.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                usernameList.add(fileScanner.nextLine());
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {

            try {
                new File("usernames.txt").createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUsernames() {
        try {
            FileWriter writer = new FileWriter("usernames.txt");
            for (String username : usernameList) {
                writer.write(username + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createStartingArmy(Player player, CharacterShop characterShop) {
        while(true){
            boolean is_finished = characterShop.displayAvailableCharacters();
            if (is_finished) {
                break;
            }
        }
    }


    public Player loadPlayer() {
        Scanner scanner = new Scanner(System.in);

        List<Player> playerList = loadAllPlayers();

        if (playerList == null) {
            return null;
        }

        while (true) {
            System.out.println("Enter your username:");
            String username = scanner.nextLine().trim().toLowerCase();

            for (Player player : playerList) {
                if (player.getUserName().toLowerCase().equals(username)) {
                    System.out.println("Player data loaded successfully!");
                    return player;
                }
            }

            System.out.println("Error: Player data for '" + username + "' not found.");
            System.out.println("Username not found.");
            System.out.println("Would you like to create a new profile? Y: Yes    N: No ----->  ");
            String createProfileChoice = scanner.nextLine().toLowerCase();

            if (createProfileChoice.equals("y")) {
                return createNewPlayer();
            } else {
                System.out.println("Exiting program...");
                System.exit(0);
            }
        }


    }

    public List<Player> loadAllPlayers() {
        List<Player> playerList = new ArrayList<>();

        try {
            FileInputStream fileIn = new FileInputStream("player_data.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            while (true) {
                try {
                    Player player = (Player) in.readObject();
                    playerList.add(player);
                } catch (EOFException e) {
                    // End of file reached
                    break;
                }
            }
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return playerList;
    }


    public void savePlayer(Player player, boolean result) {
        try {
            FileOutputStream fileOut = new FileOutputStream("player_data.ser", true); // Append mode
            ObjectOutputStream out;

            if (new File("player_data.ser").length() == 0) {
                // If the file is empty, create a new ObjectOutputStream
                out = new ObjectOutputStream(fileOut);
            } else {
                // Otherwise, append to the existing ObjectOutputStream
                out = new AppendingObjectOutputStream(fileOut);
            }

            out.writeObject(player);
            out.close();
            fileOut.close();
            if (result)
                System.out.println("Player data saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class AppendingObjectOutputStream extends ObjectOutputStream {
        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // Don't write a new header when appending.
            reset(); // Reset instead
        }
    }

    public void equipAddons(Player player) { // You can choose a different name if you prefer
        ArrayList<Character> army = player.getArmy();
        ArrayList<Equipment> equipmentInventory = player.getEquipmentInventory();
        Scanner scanner = new Scanner(System.in);

        if (equipmentInventory.isEmpty()) {
            System.out.println("You don't have any equipment in your inventory.");
            return;
        } else {
            for (int j = equipmentInventory.size(); j > 0; --j) {
                System.out.println("Select character to equip equipment: ");
                ArrayList<String> armyMembers = player.getArmyMembers();
                for (int i = 1; i <= 5; ++i) {
                    System.out.print(" " + i + ". " + armyMembers.get(i - 1));
                }
                System.out.println("---->   ");
                int characterChoice = scanner.nextInt();

                Character equippingCharacter = army.get(characterChoice - 1);

                System.out.println("Select which equipment needs to be equipped: ");
                ArrayList<String> equipmentNames = player.getEquipmentNames();
                for (int i = 1; i <= equipmentInventory.size(); ++i) {
                    System.out.println(" " + i + ". " + equipmentNames.get(i - 1));
                }
                int equipmentChoice = scanner.nextInt();

                Equipment equipmentToEquip = equipmentInventory.get(equipmentChoice - 1);

                if (equipmentToEquip instanceof Armour) {
                    if (equippingCharacter.getArmour() == null) {
                        player.equipItemToCharacter(equippingCharacter, equipmentToEquip);
                    } else {
                        Armour unequippedArmour = equippingCharacter.getArmour();
                        equippingCharacter.unequipItem("Armour");
                        if (unequippedArmour != null) {
                            System.out.println(unequippedArmour.getName() + " unequipped from " + equippingCharacter.getName());
                            double priceAfterUnequip = equippingCharacter.getPrice() - unequippedArmour.getPrice() * 0.2;
                            equippingCharacter.setPrice((int)priceAfterUnequip);
                            System.out.println(equippingCharacter.getName() + "'s price reverted back to " + equippingCharacter.getPrice() + " Gold Coins");
                        }
                        player.equipItemToCharacter(equippingCharacter, equipmentToEquip);
                    }
                } else {
                    if (equippingCharacter.getArtefact() == null) {
                        player.equipItemToCharacter(equippingCharacter, equipmentToEquip);
                    } else {
                        Artefact unequippedArtefact = equippingCharacter.getArtefact();
                        equippingCharacter.unequipItem("Artefact");
                        if (unequippedArtefact != null) {
                            System.out.println(unequippedArtefact.getName() + " unequipped from " + equippingCharacter.getName());
                            double priceAfterUnequip = equippingCharacter.getPrice() - unequippedArtefact.getPrice() * 0.2;
                            equippingCharacter.setPrice((int)priceAfterUnequip);
                            System.out.println(equippingCharacter.getName() + "'s price reverted back to " + equippingCharacter.getPrice() + " Gold Coins");
                        }
                        player.equipItemToCharacter(equippingCharacter, equipmentToEquip);
                    }
                }

                double characterNewPrice = equippingCharacter.getPrice() + equipmentToEquip.getPrice() * 0.2;
                equippingCharacter.setPrice((int)characterNewPrice);
                System.out.println(equippingCharacter.getName() + " is equipped with " + equipmentToEquip.getName() +
                        ". Character price increased to " + equippingCharacter.getPrice() + " Gold Coins");
            }

        }
    }

}