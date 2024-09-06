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
        while (choice != -1) {
            System.out.print("Welcome to the Kivotos Battlegrounds Team App\n\n" +
                    "1) Add Players\n" +
                    "2) Remove Players\n" +
                    "3) Clear Players / Reset Teams\n" +
                    "4) Show Teams\n" +
                    "5) Add Song\n" +
                    "6) Edit Song\n" +
                    "7) Remove Song\n" +
                    "8) Sort Playlist\n" +
                    "9) Exit\n" +
                    ">> ");
            String name;
            choice = in.nextInt();
            switch (choice) {
                case 9:
                    choice = -1;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        in.close();

    }

    private static LinkedList<String> playerNames;
    private static Random rand;
    private static String[] team1;
    private static String[] team2;

    public App() {
        playerNames = new LinkedList<String>();
        rand = new Random();
        team1 = new String[6];
        team2 = new String[6];
    }

    public void addNames(String n) {
        String[] nameArray = n.split(" ");
        for (String name : nameArray) {
            playerNames.add(name);
        }    
    }

    public void removeName(String n) {
        playerNames.remove(n);
    }

    public LinkedList<String> getNames() {
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
                    int t1 = 0, t2 = 0;

                    while (t2 + t1 != 12) {
                        int team = rand.nextInt(2) + 1;
                        String name = playerNames.get(t2 + t1);
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
                    }
                } else {
                    for (int i = 0; i < 12; i++) {
                        System.out.print("Current Players: ");
                        for (String s : playerNames) {
                            System.out.print(s + ", ");
                        }
                        System.out.println();
                        System.out.print("Enter player (Team " + (i + 1) + ") >> ");
                        String name = in.nextLine();
                        if (i % 2 == 0) {
                            team1[i / 2] = name;
                        } else {
                            team2[i / 2] = name;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 12; i++) {
                System.out.print("Current Players: ");
                for (String s : playerNames) {
                    System.out.print(s + ", ");
                }
                System.out.println();
                System.out.print("Enter player (Team " + (i + 1) + ") >> ");
                String name = in.nextLine();
                if (i % 2 == 0) {
                    team1[i / 2] = name;
                } else {
                    team2[i / 2] = name;
                }
            }
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
                    int t1 = 0, t2 = 0;

                    while (t2 + t1 != 12) {
                        int team = rand.nextInt(1, 2);
                        String name = playerNames.get(t2 + t1);
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
                    }
                } else {
                    for (int i = 0; i < 12; i++) {
                        System.out.print("Current Players: ");
                        for (String s : playerNames) {
                            System.out.print(s + ", ");
                        }
                        System.out.println();
                        System.out.print("Enter player (Team " + (i + 1) + ") >> ");
                        String name = in.nextLine();
                        if (i % 2 == 0) {
                            team1[i / 2] = name;
                        } else {
                            team2[i / 2] = name;
                        }
                    }
                }
                in.close();
            }
        } else {
            int t1 = 0, t2 = 0;

            while (t2 + t1 != 12) {
                int team = rand.nextInt(1, 2);
                String name = playerNames.get(t2 + t1);
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
            }
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        showTeams();
    }

    public void showTeams() {
        System.out.println("Current Teams:\n");
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

    public void clearScreen() {
        Scanner in = new Scanner(System.in);
        System.out.println("Press Enter to continue...");
        in.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        in.close();
    }
}
