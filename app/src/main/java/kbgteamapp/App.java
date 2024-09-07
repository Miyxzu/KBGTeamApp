package kbgteamapp;

import java.util.*;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

public class App {
    public static void main(String[] args) {
        App app = new App();
        int choice = 0;
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the Kivotos Battlegrounds Team App\n");
        while (choice != -1) {
            System.out.print("Current Players:\n" + app.getPlayers() + "\n\n1) Add Players\n" +
                    "2) Remove Players\n" +
                    "3) Randomize Teams\n" +
                    "4) Choose Teams\n" +
                    "5) Show Teams\n" +
                    "6) Clear Players / Reset Teams\n" +
                    "7) Remove Song\n" +
                    "8) Sort Playlist\n" +
                    "9) Exit\n" +
                    ">> ");
            try {
                choice = in.nextInt();
                in.nextLine(); // Consume the newline character
                switch (choice) {
                    case 1:
                        System.out.print("Enter player names separated by spaces >> ");
                        app.addPlayers(in.nextLine());
                        System.out.println("Players added to List.\n");
                        clearScreen();
                        break;
                    case 2:
                        System.out.print("Current Players: ");
                        for (String s : app.getPlayers()) {
                            System.out.print(s + ", ");
                        }
                        System.out.print("\nEnter player name to remove >> ");
                        String name = in.nextLine();
                        if (app.removePlayer(name)) {
                            System.out.println(name + " removed from List.\n");
                        } else {
                            System.out.println(name + " not found in List.\n");
                        }
                        clearScreen();
                        break;
                    case 3:
                        app.randomizeTeams();
                        clearScreen();
                        break;
                    case 5:
                        app.showTeams();
                        clearScreen();
                        break;
                    case 6:
                        System.out.print("Would you like to clear the player list or reset the teams? (1/2) >> ");
                        int n = in.nextInt();
                        in.nextLine(); // Consume the newline character
                        if (n == 1) {
                            app.clearList(true);
                            System.out.println("Player list cleared.\n");
                        } else {
                            app.clearList(false);
                            System.out.println("Teams reset.\n");
                        }
                        break;
                    case 9:
                        choice = -1;
                        break;
                    default:
                        System.out.println("Invalid choice");
                        clearScreen();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                in.nextLine(); // Consume the invalid input
                clearScreen();
            }
        }
        in.close();
    }

    private static LinkedList<String> playerNames;
    private static Random rand;
    private static String[] team1;
    private static String[] team2;
    private static Boolean teamCaptainsExist;

    public App() {
        playerNames = new LinkedList<String>();
        rand = new Random();
        team1 = new String[6];
        team2 = new String[6];
    }

    public void addPlayers(String n) {
        String[] nameArray = n.split(" ");
        for (String name : nameArray) {
            playerNames.add(name);
        }    
    }

    public Boolean removePlayer(String n) {
        for (String string : playerNames) {
            if (string.equalsIgnoreCase(n)) {
                playerNames.remove(string);
                return true;
           }
        }
        return false;
    }

    public LinkedList<String> getPlayers() {
        return playerNames;
    }

    public void clearList(boolean n) {
        if (n) {
            playerNames.clear();
        } else {
            team1 = new String[6];
            team2 = new String[6];
        }
    }

    public void teamCaptainRoll() {
        
        teamCaptainsExist = true;
    }

    public void chooseTeams() {
        Scanner in = new Scanner(System.in);
        if (team1[0] != null && (team1[5] != null || team2[5] != null)) {
            System.out.println("Teams are already set.");
            System.out.println("Would you like to randomize the teams again? (y/n)");
            String choice = in.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                for (int i = 0; i < 6; i++) {
                    team1[i] = null;
                    team2[i] = null;
                }
                System.out.println("Would you like to randomize or choose teams? (1/2)");
                choice = in.nextLine();
                if (choice.equalsIgnoreCase("1")) {
                    randomizeAlgorithm();
                } else {
                    choiceAlgorithm();
                }
            }
        } else {
            choiceAlgorithm();
        }
        in.close();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        showTeams();
    }

    public void randomizeTeams() {
        if (team1[0] != null && (team1[5] != null || team2[5] != null)) {
            System.out.println("Teams are already set.");
            System.out.println("Would you like to randomize the teams again? (y/n)");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                for (int i = 0; i < 6; i++) {
                    team1[i] = null;
                    team2[i] = null;
                }
                System.out.println("Would you like to randomize or choose teams? (1/2)");
                choice = in.nextLine();
                if (choice.equalsIgnoreCase("1")) {
                    randomizeAlgorithm();
                } else {
                    choiceAlgorithm();
                }
            }
            in.close();
        } else {
            randomizeAlgorithm();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        showTeams();
    }

    public void randomizeAlgorithm() {
        int t1 = 0, t2 = 0, count = playerNames.size();

        LinkedList<String> temp = new LinkedList<String>();
        temp.addAll(playerNames);

        while (t2 + t1 != playerNames.size()) {
            int team = rand.nextInt(2);
            String name = temp.get(rand.nextInt(count));
            if (team == 1) {
                if (t1 < team1.length) {
                    team1[t1++] = name;
                } else {
                    team2[t2++] = name;
                }
            } else {
                if (t2 < team2.length) {
                    team2[t2++] = name;
                } else {
                    team1[t1++] = name;
                }
            }
            count--;
            temp.remove(name);
        }
    }

    public void choiceAlgorithm() {
        try (Scanner in = new Scanner(System.in)) {
            for (int i = 0; i < 12; i++) {
                System.out.print("Current Players: ");
                for (String s : playerNames) {
                    System.out.print(s + ", ");
                }
                System.out.println("Current Teams:");
                System.out.println("Team 1: ");
                for (String string : team1) {
                    System.out.print(string + ", ");
                    
                }
                System.out.println("Team 2: ");
                for (String string : team2) {
                    System.out.print(string + ", ");
                }
                System.out.println();
                
                System.out.print("Enter player (Team " + (i + 1) + ") >> ");
                String name = in.nextLine();
                if (i % 2 == 0) {
                    team1[i / 2] = name;
                } else {
                    team2[i / 2] = name;
                }
                System.out.println();
            }
        }
    }

    public void showTeams() {
        System.out.println("\nCurrent Teams:\n");
        if (team1[0] == null && team2[0] == null) {
            System.out.println("No teams have been set.");
        } else if (team1[0] == null) {
            printTable(2);
        } else if (team2[0] == null) {
            printTable(3);
        } else {
            printTable(1);
        }
    }

    public void printTable(int n) {
        if (n == 1) {
            String[] columnNames = { "Team 1", "Team 2" };
            Table t = new Table(2, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);
    
            t.addCell(columnNames[0]);
            t.addCell(columnNames[1]);
    
            for (int i = 0; i < 6; i++) {
                t.addCell(team1[i] != null ? team1[i] : "N/A");
                t.addCell(team2[i] != null ? team2[i] : "N/A");
            }
    
            System.out.println(t.render());
        }
        if (n == 2) {
            String[] columnNames = { "Team 1", "Team 2" };
            Table t = new Table(2, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);
    
            t.addCell(columnNames[0]);
            t.addCell(columnNames[1]);
    
            for (int i = 0; i < 6; i++) {
                t.addCell(team1[i] != null ? team1[i] : "N/A");
                t.addCell("N/A");
            }
    
            System.out.println(t.render());
        }
        if (n == 3) {
            String[] columnNames = { "Team 1", "Team 2" };
            Table t = new Table(2, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);
    
            t.addCell(columnNames[0]);
            t.addCell(columnNames[1]);
    
            for (int i = 0; i < 6; i++) {
                t.addCell("N/A");
                t.addCell(team2[i] != null ? team2[i] : "N/A");
            }
    
            System.out.println(t.render());
        }
    }

    public static void clearScreen() {
        @SuppressWarnings("resource")
        Scanner in = new Scanner(System.in);
        System.out.println("Press Enter to continue...");
        in.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
