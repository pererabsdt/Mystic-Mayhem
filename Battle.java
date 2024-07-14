import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Battle {
    private Player player1;
    private Player player2;
    private Player player1Clone;
    private Player player2Clone;
    private HomeGround battleHomeGround;
    private ArrayList<Character> player1Army;
    private ArrayList<Character> player2Army;
    private List<Character> player1DefendPriority;
    private List<Character> player2DefendPriority;
    private List<Character> player1AttackPriority;
    private List<Character> player2AttackPriority;
    public static final String ANSI_RESET  = "\u001B[0m";
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public Battle(Player player1, Player player2) {
        this.player1 = player1;
        for (Character character : player1.getArmy()) {
            character.calculateSpeed();
            character.calculateDefence();
            character.calculateHealth();
            character.calculateAttack();
        }

        this.player2 = player2;
        for (Character character : player2.getArmy()) {
            character.calculateSpeed();
            character.calculateDefence();
            character.calculateHealth();
            character.calculateAttack();
        }
        this.player1Clone = new Player(player1);
        this.player2Clone = new Player(player2);
        this.battleHomeGround = player2.getHomeGround();

        // Create hard copies of the armies
        this.player1Army = player1Clone.getArmy();
        this.player2Army = player2Clone.getArmy();

        initializeArmies(); // Initialize armies with calculations and effects

        player1DefendPriority = getDefendPriority(player1Army);
        player1AttackPriority = getAttackPriority(player1Army);
        player2AttackPriority = getAttackPriority(player2Army);
        player2DefendPriority = getDefendPriority(player2Army);
    }

    // Initialize armies with calculations and effects
    private void initializeArmies() {
        for (Character character: player1Army) {
            battleHomeGround.applyEffects(character);
        }

        for (Character character: player2Army) {
            battleHomeGround.applyEffects(character);
        }
    }

    public void startBattle() {
        printBattleHeader();

        int turn = 0;
        while (!player1Army.isEmpty() && !player2Army.isEmpty() && turn < 10) {
            System.out.println("__________________________________________________________________________________");
            System.out.println("                    Turn " + (turn + 1));
            System.out.println(ANSI_GREEN + player1.getName()  + "'s Round" + ANSI_RESET);

            if (!player1AttackPriority.isEmpty() && !player2DefendPriority.isEmpty()) {
                Character attacker = player1AttackPriority.getFirst();
                Character defender = player2DefendPriority.getLast();
                performAttack(attacker, defender);
            }
            System.out.println("\n");
            System.out.println(ANSI_RED + player2.getName() + "'s Round" +  ANSI_RESET);

            if (!player2AttackPriority.isEmpty() && !player1DefendPriority.isEmpty()) {
                Character attacker = player2AttackPriority.getFirst();
                Character defender = player1DefendPriority.getLast();
                performAttack(attacker, defender);
            }
            turn++;
        }

        printBattleResult();
    }

    private List<Character> getAttackPriority(ArrayList<Character> army) {
        List<Character> armyAttackPriority = new ArrayList<>(army);
        Collections.sort(armyAttackPriority, new Comparator<Character>() {
            @Override
            public int compare(Character c1, Character c2) {
                if (c1.getSpeed() != c2.getSpeed()) {
                    return (int)c2.getSpeed() - (int)c1.getSpeed();
                } else {
                    return getClassPriority(c1) - getClassPriority(c2);
                }
            }

            private int getClassPriority(Character character) {
                // Return a lower number for higher priority classes
                switch (character.getClass().getSimpleName()) {
                    case "Healer":
                        return 5;
                    case "Mage":
                        return 4;
                    case "MythicalCreature":
                        return 3;
                    case "Knight":
                        return 2;
                    case "Archer":
                        return 1;
                    default:
                        return 0;
                }
            }
        });
        return armyAttackPriority;
    }

    private List<Character> getDefendPriority(ArrayList<Character> army) {
        List<Character> armyDefendPriority = new ArrayList<>(army);
        Collections.sort(armyDefendPriority, new Comparator<Character>() {
            @Override
            public int compare(Character c1, Character c2) {
                if (c1.getDefence() != c2.getDefence()) {
                    return (int)c2.getDefence() - (int)c1.getDefence();
                } else {
                    return getClassPriority(c1) - getClassPriority(c2);
                }
            }

            private int getClassPriority(Character character) {
                // Return a lower number for higher priority classes
                switch (character.getClass().getSimpleName()) {
                    case "Mage":
                        return 1;
                    case "Knight":
                        return 2;
                    case "Archer":
                        return 3;
                    case "MythicalCreature":
                        return 4;
                    case "Healer":
                        return 5;
                    default:
                        return 0;
                }
            }
        });
        return armyDefendPriority;
    }

    private void performAttack(Character attacker, Character defender) {
        if (attacker instanceof Healer) {
            if (player1Army.contains(attacker)) {
                Character teammateToHeal = findCharacterWithLowestHealth(player1Army);
                double healAmount =  (0.1 * attacker.getAttack());
                teammateToHeal.setHealth(teammateToHeal.getHealth() + healAmount);
                System.out.println(attacker.getName() + " heals " + teammateToHeal.getName());
                System.out.println(teammateToHeal.getName() + "'s health: " + teammateToHeal.getHealth());
            } else if (player2Army.contains(attacker)){
                Character teammateToHeal = findCharacterWithLowestHealth(player2Army);
                double healAmount =  (0.1 * attacker.getAttack());
                teammateToHeal.setHealth(teammateToHeal.getHealth() + healAmount);
                System.out.println(attacker.getName() + " heals " + teammateToHeal.getName());
                System.out.println(teammateToHeal.getName() + "'s health: " + teammateToHeal.getHealth());
            }

        } else {
            double damage = 0.5 * attacker.getAttack() - 0.1 * defender.getDefence();
            defender.setHealth(defender.getHealth() - damage);

            System.out.println(attacker.getName() + " attacks " + defender.getName());
            System.out.println(defender.getName() + "'s health: " + defender.getHealth());
            System.out.println(attacker.getName() + "'s health: " + attacker.getHealth());

            if (defender.getHealth() <= 0) {
                System.out.println(defender.getName() + " died!");
                if (player1Army.contains(defender)) {
                    player1Army.remove(defender);
                    player1AttackPriority.remove(defender);
                    player1DefendPriority.remove(defender);
                } else {
                    player2Army.remove(defender);
                    player2AttackPriority.remove(defender);
                    player2DefendPriority.remove(defender);
                }
            }
        }

        // Home ground special case: Hillcrest bonus for Highlanders
        if (battleHomeGround.getHomeGroundName().equals("Hillcrest") && attacker.getCategory().equals("Highlander") &&
                !player1Army.isEmpty() && !player2Army.isEmpty()) {
            System.out.println(attacker.getName() + " is a Highlander! Bonus Turn!!");
            if (attacker instanceof Healer) {
                // Find teammate with the lowest health
                if (player1Army.contains(attacker)) {
                    Character teammateToHeal = findCharacterWithLowestHealth(player1Army);
                    double bonusHealAmount =  (0.1 * attacker.getAttack() * 0.2);
                    teammateToHeal.setHealth(teammateToHeal.getHealth() + bonusHealAmount);
                    System.out.println(attacker.getName() + " heals " + teammateToHeal.getName());
                    System.out.println(teammateToHeal.getName() + "'s health: " + teammateToHeal.getHealth());
                } else if (player2Army.contains(attacker)){
                    Character teammateToHeal = findCharacterWithLowestHealth(player2Army);
                    double bonusHealAmount =  (0.1 * attacker.getAttack() * 0.2);
                    teammateToHeal.setHealth(teammateToHeal.getHealth() + bonusHealAmount);
                    System.out.println(attacker.getName() + " heals " + teammateToHeal.getName());
                    System.out.println(teammateToHeal.getName() + "'s health: " + teammateToHeal.getHealth());
                }
            } else {

                List<Character> opposingTeam = (player1Army.contains(attacker))? player2DefendPriority : player1DefendPriority;
                Character newDefender = opposingTeam.getLast();

                double bonusDamage = 0.2 * 0.5 * attacker.getAttack() - 0.1 * newDefender.getDefence();
                newDefender.setHealth(newDefender.getHealth() - bonusDamage);

                System.out.println(attacker.getName() + " attacks " + newDefender.getName());
                System.out.println(newDefender.getName() + "'s health: " + newDefender.getHealth());
                System.out.println(attacker.getName() + "'s health: " + attacker.getHealth());


                if (newDefender.getHealth() <= 0) {
                    System.out.println(newDefender.getName() + " died!");
                    if (player1Army.contains(newDefender)) {
                        player1Army.remove(newDefender);
                        player1AttackPriority.remove(newDefender);
                        player1DefendPriority.remove(newDefender);
                    } else {
                        player2Army.remove(newDefender);
                        player2AttackPriority.remove(newDefender);
                        player2DefendPriority.remove(newDefender);
                    }
                }
            }
        } else if (battleHomeGround.getHomeGroundName().equals("Arcane") && attacker.getCategory().equals("Mystic") &&
                !player1Army.isEmpty() && !player2Army.isEmpty()) {
            System.out.println(attacker.getName() + " is a Mystic! Health Increase Activated!");
            double selfHealAmount = 0.1 * attacker.getHealth();
            double newHealth = attacker.getHealth() + selfHealAmount;
            attacker.setHealth(newHealth);
            System.out.println(attacker.getName() + "' s new Health : " + attacker.getHealth());
        }

        if (player1Army.contains(attacker)) {
            Character temp = player1AttackPriority.get(0);
            player1AttackPriority.remove(0);
            player1AttackPriority.add(temp);
        } else {
            Character temp = player2AttackPriority.get(0);
            player2AttackPriority.remove(0);
            player2AttackPriority.add(temp);
        }
    }


    private Character findCharacterWithLowestHealth(ArrayList<Character> army) {
        Character lowestHPCharacter = null;
        for (Character cha : army) {
            if (lowestHPCharacter == null || cha.getHealth() < lowestHPCharacter.getHealth()) {
                lowestHPCharacter = cha;
            }
        }
        return lowestHPCharacter;
    }

    private void printBattleHeader() {
        System.out.println("\n\n" + ANSI_RED + "__________________________________________________________________________________" + ANSI_RESET);
        System.out.println("\t\t\t" + ANSI_GREEN + player1.getName() + ANSI_RESET + " Vs " + ANSI_RED + player2.getName() + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "=========================================" + ANSI_RESET);
        System.out.println("\t\t\t" + ANSI_YELLOW + battleHomeGround.getHomeGroundName().toUpperCase() + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "=========================================" + ANSI_RESET);
    }

    public Player getBattleResult() {
        if (player1Army.isEmpty()) {
            System.out.println(ANSI_YELLOW + player2.getName() + " WINS!!!!!" + ANSI_RESET);
            return player2;
        } else if (player2Army.isEmpty()) {
            System.out.println(ANSI_YELLOW + player1.getName() + " WINS!!!!!" + ANSI_RESET);
            return player1;
        } else {
            System.out.println(ANSI_YELLOW + "Battle Draw!!!" + ANSI_RESET);
            return null;
        }
    }

    private void printBattleResult() {
        System.out.println("__________________________________________________________________________________");
        Player winner = getBattleResult();

        if (winner != null) { // Only adjust XP/coins if there's a clear winner
            Player winningPlayer = (winner == player1) ? player1 : player2;
            Player losingPlayer = (winningPlayer == player1) ? player2 : player1;

            winningPlayer.increaseXP();
            winningPlayer.setCoins(winningPlayer.getCoins() + losingPlayer.getCoins() * 0.1);

            double losingPlayerCoins = losingPlayer.getCoins() * 0.9;
            losingPlayer.setCoins(losingPlayerCoins);
        }


        System.out.println(player1.getName() + "'s XP: " + player1.getXP() + "    Gold coins: " + (int)player1.getCoins());
        System.out.println(player2.getName() + "'s XP: " + player2.getXP() + "    Gold coins: " + (int)player2.getCoins());
    }

}



