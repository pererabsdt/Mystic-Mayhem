import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BattleSystem {
    private Player userPlayer; // The player controlled by the user
    public List<Player> potentialOpponents = new ArrayList<>();
    private int currentOpponentIndex = 0;

    public BattleSystem(Player userPlayer) {
        this.userPlayer = userPlayer;
        potentialOpponents = loadPlayersFromFile();
    }

    public void startBattleLoop() {
        while (true) {
            Player opponent = findOpponent();

            if (opponent != null) {
                displayOpponentInfo(opponent);

                int choice = displayBattleMenu();


                if (choice == 1) {
                    Battle battle = new Battle(userPlayer, opponent);
                    battle.startBattle();
                } else if (choice == 3) {
                    break;
                }

            } else {
                System.out.println("No opponents found.");
                break;
            }
        }
    }

    private int displayBattleMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n1. Battle Player\t\t2. Skip to next Player\t\t3. Return to main menu");
            System.out.print("Select a Number: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                if (choice >= 1 && choice <= 3) {
                    // Valid choice, break the loop
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        }


        return choice;
    }


    private void displayOpponentInfo(Player opponent) {
        System.out.println("__________________________________________________________________________________");
        System.out.println("\nOpponent Found:");
        System.out.println("Name: " + opponent.getName());
        System.out.println("XP: " + opponent.getXP());
        System.out.println("Gold Coins: " + opponent.getCoins());
        System.out.println("Army: ");
        for (Character character : opponent.getArmy()) {
            System.out.print(character.getName() + "   ");
        }
        System.out.println("\n__________________________________________________________________________________");
    }


    private List<Player> loadPlayersFromFile() {
        List<Player> loadedPlayers = new ArrayList<>();

        try {
            FileInputStream fileIn = new FileInputStream("player_data.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            while (true) {
                try {
                    Player player = (Player) in.readObject();
                    loadedPlayers.add(player);
                } catch (EOFException e) {
                    break;
                }
            }
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loadedPlayers;
    }


    private Player findOpponent() {
        if (potentialOpponents.isEmpty()) {
            return null;
        }
        potentialOpponents.removeIf(opponent -> opponent.getUserName().equalsIgnoreCase(userPlayer.getUserName()));

        Player opponent = potentialOpponents.get(currentOpponentIndex);
        currentOpponentIndex = (currentOpponentIndex + 1) % potentialOpponents.size();
        return opponent;
    }

}
