import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CharacterShop {
    private ArrayList<Character> availableCharacters;
    Player player;

    public CharacterShop(Player player) {
        this.player = player;
        availableCharacters = new ArrayList<>();
        initializeShop();
    }

    private void initializeShop() {
        // Archer Characters
        availableCharacters.add(new Archer("Shooter", 80, 11, 4, 6, 9, "Highlander"));
        availableCharacters.add(new Archer("Ranger", 115, 14, 5, 8, 10, "Highlander"));
        availableCharacters.add(new Archer("Sunfire", 160, 15, 5, 7, 14, "Sunchildren"));
        availableCharacters.add(new Archer("Zing", 200, 16, 9, 11, 14, "Sunchildren"));
        availableCharacters.add(new Archer("Saggitarius", 230, 18, 7, 12, 17, "Mystic"));

        // Knight Characters
        availableCharacters.add(new Knight("Squire", 85, 8, 9, 7, 8, "Marshlander"));
        availableCharacters.add(new Knight("Cavalier", 110, 10, 12, 7, 10, "Highlander"));
        availableCharacters.add(new Knight("Templar", 155, 14, 16, 12, 12, "Sunchildren"));
        availableCharacters.add(new Knight("Zoro", 180, 17, 16, 13, 14, "Highlander"));
        availableCharacters.add(new Knight("Swiftblade", 250, 18, 20, 17, 13, "Marshlander"));

        // Mage Characters
        availableCharacters.add(new Mage("Warlock", 100, 12, 7, 10, 12, "Marshlander"));
        availableCharacters.add(new Mage("Illusionist", 120, 13, 8, 12, 14, "Mystic"));
        availableCharacters.add(new Mage("Enchanter", 160, 16, 10, 13, 16, "Highlander"));
        availableCharacters.add(new Mage("Conjurer", 195, 18, 15, 14, 12, "Highlander"));
        availableCharacters.add(new Mage("Eldritch", 270, 19, 17, 18, 14, "Mystic"));

        // Healer Characters
        availableCharacters.add(new Healer("Soother", 95, 10, 8, 9, 6, "Sunchildren"));
        availableCharacters.add(new Healer("Medic", 125, 12, 9, 10, 7, "Highlander"));
        availableCharacters.add(new Healer("Alchemist", 150, 13, 13, 13, 13, "Marshlander"));
        availableCharacters.add(new Healer("Saint", 200, 16, 14, 17, 9, "Mystic"));
        availableCharacters.add(new Healer("Lightbringer", 260, 17, 15, 19, 12, "Sunchildren"));

        // Mythical Creature Characters
        availableCharacters.add(new MythicalCreature("Dragon", 120, 12, 14, 15, 8, "Sunchildren"));
        availableCharacters.add(new MythicalCreature("Basilisk", 165, 15, 11, 10, 12, "Marshlander"));
        availableCharacters.add(new MythicalCreature("Hydra", 205, 12, 16, 15, 11, "Marshlander"));
        availableCharacters.add(new MythicalCreature("Phoenix", 275, 17, 13, 17, 19, "Sunchildren"));
        availableCharacters.add(new MythicalCreature("Pegasus", 340, 14, 18, 20, 20, "Mystic"));
    }




    private void displayCharacters(List<Character> characters) {
        for (int i = 0; i < characters.size(); i++) {
            System.out.println((i + 1) + ". " +
                    characters.get(i).getName() + ":    " +
                    "    Price:   " + characters.get(i).getPrice() +
                    "    Attack:  " + characters.get(i).getAttack() +
                    "    Defense: " + characters.get(i).getDefence() +
                    "    Health:  " + characters.get(i).getHealth() +
                    "    Speed:   " + characters.get(i).getSpeed() +
                    "    Origin:  " + characters.get(i).getCategory());
        }
    }


    public boolean isValidToBuy(Character character , int total, String type) {
        int least_archer = 80, least_knight = 85, least_mage = 100, least_healer = 95, least_mythical = 120;

        if (!type.equals("Archer")) {
            total -= least_archer;
        }
        if ( !type.equals("Knight")) {
            total -= least_knight;
        }
        if (!type.equals("Mage")) {
            total -= least_mage;
        }
        if (!type.equals("Healer")) {
            total -= least_healer;
        }
        if (!type.equals("Mythical Creature")) {
            total -= least_mythical;
        }
        total-=character.getPrice();
        return (total>=0);
    }



    public List<Character> getAvailableArchers() {
        List<Character> archers = new ArrayList<>();
        for (Character character : availableCharacters) {
            if (character instanceof Archer) {
                archers.add((Archer) character);
            }
        }
        return archers;
    }

    public List<Character> getAvailableKnights() {
        List<Character> knights = new ArrayList<>();
        for (Character character : availableCharacters) {
            if (character instanceof Knight) {
                knights.add((Knight) character);
            }
        }
        return knights;
    }

    public List<Character> getAvailableMages() {
        List<Character> mages = new ArrayList<>();
        for (Character character : availableCharacters) {
            if (character instanceof Mage) {
                mages.add((Mage) character);
            }
        }
        return mages;
    }

    public List<Character> getAvailableHealers() {
        List<Character> healers = new ArrayList<>();
        for (Character character : availableCharacters) {
            if (character instanceof Healer) {
                healers.add((Healer) character);
            }
        }
        return healers;
    }

    public List<Character> getAvailableMythicalCreatures() {
        List<Character> mythicalCreatures = new ArrayList<>();
        for (Character character : availableCharacters) {
            if (character instanceof MythicalCreature) {
                mythicalCreatures.add((MythicalCreature) character);
            }
        }
        return mythicalCreatures;
    }


    private Character findCharacterByName(String name) {
        for (Character character : availableCharacters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }


    public int buyCharacter(Player player, List<? extends Character> characters) {
        Scanner scanner = new Scanner(System.in);

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice >= 1 && choice <= characters.size()) {
            Character characterToBuy = characters.get(choice - 1); // Get character based on index && isValidToBuy(characterToBuy,(int)player.getCoins(),characterToBuy.getType())
            if (characterToBuy != null) {

                if (player.buyCharacter(characterToBuy)) {
                    System.out.println("Purchase successful! You have " + player.getCoins() + " Gold Coins remaining");
                    return 1;

                } else {
                    System.out.println("Not enough gold!");
                    System.out.println("Select the army Again ");
                    return -1;
                }
            } else {
                System.out.println("Character not found in shop.");
            }
        } else if (choice == characters.size() + 1) {
            System.out.println("Skipping purchase...");
        } else {
            System.out.println("Invalid Choice");
        }
        return 0;
    }



    public void sellCharacter(Player player, String characterName) {
        Character characterToSell = player.findCharacterByName(characterName);

        if (characterToSell != null) {
            player.sellCharacter(characterToSell);
            //availableCharacters.add(characterToSell); // Add the character back to shop inventory
            System.out.println("Character sold!");
        } else {
            System.out.println("Character not found in your army.");
        }
    }
    public void displayAvailableCharacters(List<? extends Character> characters) {
        if (!characters.isEmpty()) {
            System.out.println("\n****** Select a character ******");
            displayCharacters((List<Character>) characters);
        } else {
            System.out.println("No characters of the same type available to select.");
        }
    }


    public boolean displayAvailableCharacters() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        while (true) {
            try {
                System.out.println("\n*************************** Select Archer ***************************\n");
                displayCharacters(getAvailableArchers());
                int response = buyCharacter(player, getAvailableArchers());
                if (response == 1)
                    break;
                if (response == -1) {
                    player.getArmy().clear();
                    player.setCoins(500);
                    return false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }


        while (true) {
            try {
                System.out.println("\n\n*************************** Select Knight ***************************\n");
                displayCharacters(getAvailableKnights());
                int response = buyCharacter(player, getAvailableKnights());
                if(response==1)
                    break;
                if (response==-1){
                    player.getArmy().clear();
                    player.setCoins(500);
                    return false;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        while (true) {
            try {
                System.out.println("\n\n*************************** Select Mage ***************************\n");
                displayCharacters(getAvailableMages());
                int response = buyCharacter(player, getAvailableMages());
                if(response==1)
                    break;
                if (response==-1){
                    player.getArmy().clear();
                    player.setCoins(500);
                    return false;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        while (true) {
            try {
                System.out.println("\n\n*************************** Select Healer ***************************\n");
                displayCharacters(getAvailableHealers());
                int response = buyCharacter(player, getAvailableHealers());
                if(response==1)
                    break;
                if (response==-1){
                    player.getArmy().clear();
                    player.setCoins(500);
                    return false;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        while (true) {
            try {
                System.out.println("\n\n*************************** Select Mythical Creature ***************************\n");
                displayCharacters(getAvailableMythicalCreatures());
                int response = buyCharacter(player, getAvailableMythicalCreatures());
                if(response==1)
                    break;
                if (response==-1){
                    player.getArmy().clear();
                    player.setCoins(500);
                    return false;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return true;
    }


    public ArrayList<Character> getAvailableCharacters() {
        return availableCharacters;
    }




    public void changeCharacter(Player player, CharacterShop characterShop) {
        Scanner scanner = new Scanner(System.in);
        boolean continueSelling = true;

        while (continueSelling) {

            System.out.println("Are you sure you want to sell? 1. Yes \t\t\t2. No, I want to go back   ");

            try {
                int sellChoice = scanner.nextInt();

                switch (sellChoice) {
                    case 1:
                        ArrayList<Character> army = player.getArmy();
                        if (army.isEmpty()) {
                            System.out.println("You don't have any characters to sell.");
                            return; // Exit if the player has no characters
                        } else {
                            System.out.println("Select the character you want to sell:");
                            for (int i = 0; i < army.size(); i++) {
                                System.out.println((i + 1) + ". " + army.get(i).getName()); // Assuming Character has a getName() method
                            }
                            int characterChoice = scanner.nextInt();
                            if (characterChoice < 1 || characterChoice > army.size()) {
                                System.out.println("Invalid character choice.");
                            } else {
                                Character characterToSell = army.get(characterChoice - 1);

                                // Determine the type of the character to sell
                                Class<? extends Character> characterType = characterToSell.getClass();

                                // Get the available characters of the same type
                                List<? extends Character> sameTypeCharacters = null;
                                if (characterType == Archer.class) {
                                    sameTypeCharacters = characterShop.getAvailableArchers();
                                } else if (characterType == Knight.class) {
                                    sameTypeCharacters = characterShop.getAvailableKnights();
                                } else if (characterType == Mage.class) {
                                    sameTypeCharacters = characterShop.getAvailableMages();
                                } else if (characterType == Healer.class) {
                                    sameTypeCharacters = characterShop.getAvailableHealers();
                                } else if (characterType == MythicalCreature.class) {
                                    sameTypeCharacters = characterShop.getAvailableMythicalCreatures();
                                }

                                if (sameTypeCharacters == null || sameTypeCharacters.isEmpty()) {
                                    System.out.println("No characters of the same type available to select.");
                                } else {
                                    player.sellCharacter(characterToSell); // Use the sellCharacter method in your Player class
                                    System.out.println(characterToSell.getName() + " has been sold. You have " + player.getCoins() + " Gold Coins");

                                    displayAvailableCharacters(sameTypeCharacters);
                                    buyCharacter(player, sameTypeCharacters);
                                }
                            }
                        }
                        break;
                    case 2:
                        continueSelling = false; // Exit the loop if the user doesn't want to sell
                        break;
                    default:
                        System.out.println("Invalid choice. Please select 1 or 2.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid option (1 or 2).");
                scanner.next(); // Clear the invalid input
            }
        }
    }


}