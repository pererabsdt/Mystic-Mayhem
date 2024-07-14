import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    public static final String ANSI_RESET  = "\u001B[0m";
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public void displayMainMenu() {
        System.out.println("__________________________________________________________________________________");
        System.out.println(ANSI_BLUE + "    ******* MAIN MENU *******" + ANSI_RESET);
        System.out.println("\t\t1. Profile Information");
        System.out.println("\t\t2. Change Characters");
        System.out.println("\t\t3. Buy Equipments");
        System.out.println("\t\t4. Equip Armour and Artefacts");
        System.out.println("\t\t5. Go for battle");
        System.out.println("\t\t6. Save and exit");
        System.out.print("Enter the number of your choice: ");
    }

    public int getUserChoice() {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= 6) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    System.out.print("Enter the number of your choice: ");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
                System.out.print("Enter the number of your choice: ");
            }
        }
    }
}
