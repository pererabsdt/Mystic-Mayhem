import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Scanner scanner;
    private Menu menu;
    private PlayerManagement playerManagement;
    private CharacterShop characterShop;
    private Player player;

    public Game() {
        scanner = new Scanner(System.in);
        menu = new Menu();
        playerManagement = new PlayerManagement();
        characterShop = new CharacterShop(player);
    }

    public void startGame() {
        String art = """
                ███╗   ███╗██╗   ██╗███████╗████████╗██╗ ██████╗      \s
                ████╗ ████║╚██╗ ██╔╝██╔════╝╚══██╔══╝██║██╔════╝      \s
                ██╔████╔██║ ╚████╔╝ ███████╗   ██║   ██║██║           \s
                ██║╚██╔╝██║  ╚██╔╝  ╚════██║   ██║   ██║██║           \s
                ██║ ╚═╝ ██║   ██║   ███████║   ██║   ██║╚██████╗      \s
                ╚═╝     ╚═╝   ╚═╝   ╚══════╝   ╚═╝   ╚═╝ ╚═════╝      \s
                                                                      \s
                ███╗   ███╗ █████╗ ██╗   ██╗██╗  ██╗███████╗███╗   ███╗
                ████╗ ████║██╔══██╗╚██╗ ██╔╝██║  ██║██╔════╝████╗ ████║
                ██╔████╔██║███████║ ╚████╔╝ ███████║█████╗  ██╔████╔██║
                ██║╚██╔╝██║██╔══██║  ╚██╔╝  ██╔══██║██╔══╝  ██║╚██╔╝██║
                ██║ ╚═╝ ██║██║  ██║   ██║   ██║  ██║███████╗██║ ╚═╝ ██║
                ╚═╝     ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝     ╚═╝
                
                """;
        for (int i=0;i<art.length();i++){
            System.out.print(art.charAt(i));
        }

        Player player = handleNewOrExistingPlayer();


        boolean running = true;
        while (running) {
            menu.displayMainMenu();
            int choice = menu.getUserChoice();
            handlePlayerChoice(choice, player);
        }
    }

    private Player handleNewOrExistingPlayer() {
        System.out.println("Are you a new player?\nY : Yes          N : No");
        String newPlayerChoice = scanner.nextLine();

        if (newPlayerChoice.equalsIgnoreCase("y")) {
            return playerManagement.createNewPlayer();
        } else if (newPlayerChoice.equalsIgnoreCase("n")) {
            return playerManagement.loadPlayer();
        } else {
            System.out.println("Invalid input. Please try again.");
            return handleNewOrExistingPlayer();
        }
    }


    private void handlePlayerChoice(int choice, Player player) {
        switch (choice) {
            case 1:
                player.displayPlayerInfo(player);
                break;
            case 2: // Change characters
                characterShop.changeCharacter(player, characterShop);
                break;
            case 3: // Buy equipment
                if (player.getCoins() < 70.0) {
                    System.out.println("You don't have enough coins to buy equipment");
                } else {
                    EquipmentShop equipmentShop = new EquipmentShop(player);
                    equipmentShop.displayAvailableEquipment();
                }
                break;
            case 4: // Equip equipment
                playerManagement.equipAddons(player);
                break;
            case 5: // Go to battle
                BattleSystem battleSystem = new BattleSystem(player);
                battleSystem.startBattleLoop();
                break;
            case 6: // Save and exit
                playerManagement.savePlayer(player, true);  // Assuming you have this method
                System.out.println("Progress saved. Exiting...");
                System.exit(0); // Terminate the program
            default:
                System.out.println("Invalid Choice");
        }
    }

    public void saveDefaultPlayers() {
        List<Player> defaultOpponents = new ArrayList<>();

        // Default Player Geralt of Rivia
        HomeGround defaultPlayer1HomeGround = new HomeGround("Marshland");
        Player defaultPlayer1 = new Player("GeraltofRivia", "whitewolf", defaultPlayer1HomeGround);
        ArrayList<Character> defaultArmy1 = new ArrayList<>();
        defaultArmy1.add(new Archer("Ranger", 115, 14, 5, 8, 10, "Highlander"));
        defaultArmy1.add(new Knight("Squire", 85, 8, 9, 7, 8, "Marshlander"));
        defaultArmy1.add(new Mage("Warlock", 100, 12, 7, 10, 12, "Marshlander"));
        defaultArmy1.add(new Healer("Medic", 125, 12, 9, 10, 7, "Highlander"));
        defaultArmy1.add(new MythicalCreature("Dragon", 120, 12, 14, 15, 8, "Sunchildren"));
        defaultArmy1.get(0).equipItem(new Armour("Chainmail", 70, 0, 1, 0, -1));
        defaultArmy1.get(3).equipItem(new Artefact("Amulet", 200, 1, -1, 1, 1));
        defaultPlayer1.setXP(32);
        defaultPlayer1.setCoins(215);
        defaultPlayer1.setArmy(defaultArmy1);

        // Default Player Bit_Master
        HomeGround defaultPlayer2HomeGround = new HomeGround("Marshland");
        Player defaultPlayer2 = new Player("Bit_Master", "DuRaKaSa", defaultPlayer2HomeGround);
        ArrayList<Character> defaultArmy2 = new ArrayList<>();
        defaultArmy2.add(new Archer("Sunfire", 160, 15, 5, 7, 14, "Sunchildren"));
        defaultArmy2.add(new Knight("Zoro", 180, 17, 16, 13, 14, "Highlander"));
        defaultArmy2.add(new Mage("Enchanter", 160, 16, 10, 13, 16, "Highlander"));
        defaultArmy2.add(new Healer("Alchemist", 150, 13, 13, 13, 13, "Marshlander"));
        defaultArmy2.add(new MythicalCreature("Phoenix", 275, 17, 13, 17, 19, "Sunchildren"));
        defaultArmy2.get(1).equipItem(new Armour("Regalia", 105, 0, 1, 0, 0));
        defaultArmy2.get(1).equipItem(new Armour("Crystal", 210, 2, 1, -1, -1));
        defaultArmy2.get(4).equipItem(new Artefact("Fleece", 150, 0, 2, 1, -1));
        defaultPlayer2.setXP(45);
        defaultPlayer2.setCoins(420);
        defaultPlayer2.setArmy(defaultArmy2);

        defaultOpponents.add(defaultPlayer1);
        defaultOpponents.add(defaultPlayer2);

        playerManagement.savePlayer(defaultPlayer1, false);
        playerManagement.savePlayer(defaultPlayer2, false);
    }

}
