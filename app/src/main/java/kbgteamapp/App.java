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
        System.out.println("Welcome to the Kivotos Battlegrounds Team App");
        while (choice != -1) {
            if (!app.getPlayers().isEmpty()) {
                System.out.print("Current Players:\n" + app.getPlayers() + "\n");
            }
            if (!app.getPlayersTeams().isEmpty()) {
                System.out.print("Current Players in Teams:\n" + app.getPlayersTeams() + "\n");
            }
            if (app.getTeamCaptainsExist()) {
                System.out.println("Team Captains: " + app.getTeamCaptains(1) + " and " + app.getTeamCaptains(2));
            }
            System.out.print(
                    "\n1) Add Players\n" +
                            "2) Remove Players\n" +
                            "3) Update Players\n" +
                            "4) Randomize Teams\n" +
                            "5) Choose Teams\n" +
                            "6) Show Teams\n" +
                            "7) Roll Team Captains\n" +
                            "8) Clear Players / Reset Teams\n" +
                            "9) Exit\n" +
                            ">> ");
            try {
                choice = in.nextInt();
                in.nextLine(); // Consume the newline character
                switch (choice) {
                    case 1:
                        boolean condition = true;
                        while (condition) {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println("Current Players: " + app.getPlayers());
                            System.out.print("Enter player name >> ");
                            String name = in.nextLine();
                            if (app.addPlayers(name)) {
                                System.out.println(name + " added to List.\n");
                            } else {
                                System.out.println(name + " already in List.\n");
                            }
                            System.out.print("Would you like to add another player? (y/n) >> ");
                            String choiceP = in.nextLine();
                            if (!choiceP.equalsIgnoreCase("y")) {
                                condition = false;
                            }
                        }
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
                        System.out.print("Current Players: ");
                        for (String s : app.getPlayers()) {
                            System.out.print(s + ", ");
                        }
                        System.out.print("\nEnter player name to update >> ");
                        String oldName = in.nextLine();
                        System.out.print("Enter new name >> ");
                        String newName = in.nextLine();
                        if (app.updatePlayer(oldName, newName)) {
                            System.out.println(oldName + " updated to " + newName + ".\n");
                        } else {
                            System.out.println(oldName + " not found in List.\n");
                        }
                        break;
                    case 4:
                        app.randomizeTeams();
                        break;
                    case 5:
                        app.chooseTeams();
                        break;
                    case 6:
                        app.showTeams();
                        break;
                    case 7:
                        app.teamCaptainRoll();
                        System.out.println("Team Captains: " + app.getTeamCaptains(1) + " and " + app.getTeamCaptains(2));
                        clearScreen();
                        break;
                    case 8:
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
                        clearScreen();
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
            } catch (NoSuchElementException e) {
                System.out.println("No input available. Exiting.");
                choice = -1;
            }
        }
        in.close();
    }

    private LinkedList<String> playerNames, playerTeams;
    private Random rand;
    private String[] team1, team2;
    private String[] teamCaptains;
    private Boolean teamCaptainsExist;

    public App() {
        playerTeams = new LinkedList<String>();
        playerNames = new LinkedList<String>();
        rand = new Random();
        teamCaptainsExist = false;
        teamCaptains = new String[2];
        team1 = new String[6];
        team2 = new String[6];
    }

    public Boolean addPlayers(String n) {
        Boolean found = false;
        for (String string : playerNames) {
            if (string.equalsIgnoreCase(n)) {
                found = true;
                break;
            }
        }

        if (!found) {
            playerNames.add(n);
            return true;
        } else {
            return false;
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

    public Boolean updatePlayer(String o, String n) {
        for (String string : playerNames) {
            if (string.equalsIgnoreCase(o)) {
                playerNames.remove(string);
                playerNames.add(n);
                return true;
            }
        }
        return false;
    }

    public LinkedList<String> getPlayers() {
        return playerNames;
    }

    public LinkedList<String> getPlayersTeams() {
        return playerTeams;
    }

    public void clearList(boolean n) {
        if (n) {
            if (!playerTeams.isEmpty()) {
                playerNames.addAll(playerTeams);
                playerTeams.clear();
                team1 = new String[6];
                team2 = new String[6];
            }
            if (teamCaptainsExist) {
                teamCaptainsExist = false;
                teamCaptains = new String[2];
            }
            playerNames.clear();
        } else {
            playerNames.addAll(playerTeams);
            playerTeams.clear();
            teamCaptainsExist = false;
            team1 = new String[6];
            team2 = new String[6];
        }
    }

    public void teamCaptainRoll() {
        if (teamCaptainsExist) {
            System.out.println("Team captains have already been rolled.");
            System.out.println("Would you like to roll again? (y/n)");
            @SuppressWarnings("resource")
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                int count = playerNames.size();
                for (int i = 0; i < 2; i++) {
                    teamCaptains[i] = playerNames.get(rand.nextInt(count));
                    playerNames.remove(teamCaptains[i]);
                    count--;
                }
            }
        } else {
            int count = playerNames.size();
            for (int i = 0; i < 2; i++) {
                teamCaptains[i] = playerNames.get(rand.nextInt(count));
                playerNames.remove(teamCaptains[i]);
                count--;
            }
            teamCaptainsExist = true;
        }
    }

    public String getTeamCaptains(int n) {
        if (n == 1) {
            return teamCaptains[0];
        } else {
            return teamCaptains[1];
        }
    }

    public Boolean getTeamCaptainsExist() {
        return teamCaptainsExist;
    }

    public void chooseTeams() {
        Scanner in = new Scanner(System.in);
        if (team1[0] != null && (team1[5] != null || team2[5] != null)) {
            System.out.println("Teams are already set.");
            System.out.println("Would you like to change the teams again? (y/n)");
            String choice = in.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                clearList(false);
                System.out.println("\nWould you like to randomize or choose teams? (1/2)");
                choice = in.nextLine();
                if (choice.equalsIgnoreCase("1")) {
                    randomizeAlgorithm();
                } else {
                    choiceAlgorithm(in);
                }
            }
        } else {
            choiceAlgorithm(in);
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        in.nextLine();
        showTeams();
    }

    public void randomizeTeams() {
        if (team1[0] != null && (team1[5] != null || team2[5] != null)) {
            System.out.println("Teams are already set.");
            System.out.println("Would you like to change the teams again? (y/n)");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                clearList(false);
                System.out.println("Would you like to randomize or choose teams? (1/2)");
                choice = in.nextLine();
                if (choice.equalsIgnoreCase("1")) {
                    randomizeAlgorithm();
                } else {
                    choiceAlgorithm(in);
                }
            }
        } else {
            randomizeAlgorithm();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        showTeams();
    }

    public void randomizeAlgorithm() {
        int t1 = 0, t2 = 0, count = playerNames.size();
        int maxPlayers = Math.min(playerNames.size(), 12);

        playerTeams.addAll(playerNames);

        while (t2 + t1 != maxPlayers) {
            int team = rand.nextInt(2);
            String name = playerTeams.get(rand.nextInt(count));
            if (team == 1) {
                if (t1 < team1.length) {
                    team1[t1++] = name;
                } else if (t2 < team2.length) {
                    team2[t2++] = name;
                }
            } else {
                if (t2 < team2.length) {
                    team2[t2++] = name;
                } else if (t1 < team1.length) {
                    team1[t1++] = name;
                }
            }
            count--;
            playerNames.remove(name);
            playerTeams.remove(name);
        }
        playerTeams.clear();
        for (String string : team1) {
            playerTeams.add(string);
        }
        for (String string : team2) {
            playerTeams.add(string);
        }
    }

    public void choiceAlgorithm(Scanner in) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        if (!teamCaptainsExist) {
            System.out.println("Would you like to roll team captains? (y/n)");
            String choice = in.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                teamCaptainRoll();
                actualChoiceAlgorithm(in);
            } else {
                actualChoiceAlgorithm(in);
            }
        } else {
            actualChoiceAlgorithm(in);
        }
    }

    public void actualChoiceAlgorithm(Scanner in) {
        if (teamCaptainsExist) {

            team1[0] = teamCaptains[0];
            team2[0] = teamCaptains[1];

            for (int i = 2; i < 12; i++) {
                System.out.print("Current Players: ");
                for (int j = 0; j < playerNames.size(); j++) {
                    if (j == playerNames.size() - 1) {
                        System.out.print(playerNames.get(j));
                    } else {
                        System.out.print(playerNames.get(j) + ", ");
                    }
                }
                System.out.print("\n");
    
                System.out.println("Team 1: ");
                for (int j = 0; j < team1.length; j++) {
                    if (j == 5) {
                        System.out.print(team1[j]);
                    } else {
                        System.out.print(team1[j] + ", ");
                    }
                }
                System.out.print("\n");
    
                System.out.println("Team 2: ");
                for (int j = 0; j < team2.length; j++) {
                    if (j == 5) {
                        System.out.print(team2[j]);
                    } else {
                        System.out.print(team2[j] + ", ");
                    }
                }
                System.out.print("\n");
    
                System.out.print("Enter player (Team " + ((i % 2) + 1) + ") >> ");
                String name = in.nextLine();
                while (!playerNames.contains(name)) {
                    System.out.println("Player not found in list.");
                    System.out.print("Enter player (Team " + ((i % 2) + 1) + ") >> ");
                    name = in.nextLine();
                }
                if (i % 2 == 0) {
                    team1[i / 2] = name;
                } else {
                    team2[i / 2] = name;
                }
                playerNames.remove(name);
                playerTeams.add(name);
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } else {
            for (int i = 0; i < 12; i++) {
                System.out.print("Current Players: ");
                for (int j = 0; j < playerNames.size(); j++) {
                    if (j == playerNames.size() - 1) {
                        System.out.print(playerNames.get(j));
                    } else {
                        System.out.print(playerNames.get(j) + ", ");
                    }
                }
                System.out.println();
    
                System.out.println("Current Teams:");
                System.out.println("Team 1: ");
                for (int j = 0; j < team1.length; j++) {
                    if (j == 5) {
                        System.out.print(team1[j]);
                    } else {
                        System.out.print(team1[j] + ", ");
                    }
                }
                System.out.println();
    
                System.out.println("Team 2: ");
                for (int j = 0; j < team2.length; j++) {
                    if (j == 5) {
                        System.out.print(team2[j]);
                    } else {
                        System.out.print(team2[j] + ", ");
                    }
                }
                System.out.println();
    
                System.out.print("Enter player (Team " + ((i % 2) + 1) + ") >> ");
                String name = in.nextLine();
                while (!playerNames.contains(name)) {
                    System.out.println("Player not found in list.");
                    System.out.print("Enter player (Team " + ((i % 2) + 1) + ") >> ");
                    name = in.nextLine();
                }
                if (i % 2 == 0) {
                    team1[i / 2] = name;
                } else {
                    team2[i / 2] = name;
                }
                playerNames.remove(name);
                playerTeams.add(name);
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }
    }

    public void showTeams() {
        System.out.println("Current Teams:\n");
        if (team1[0] == null && team2[0] == null) {
            System.out.println("No teams have been set.");
        } else {
            printTable(1);
        }
        clearScreen(); // Call clearScreen after showing teams
    }

    public void printTable(int n) {
        String[] columnNames = { "Team 1", "Team 2" };
        Table t = new Table(2, BorderStyle.DESIGN_TUBES_WIDE, ShownBorders.ALL);

        t.addCell(columnNames[0]);
        t.addCell(columnNames[1]);

        for (int i = 0; i < 6; i++) {
            t.addCell(team1[i] != null ? team1[i] : "N/A");
            t.addCell(team2[i] != null ? team2[i] : "N/A");
        }

        System.out.println(t.render());
    }

    @SuppressWarnings("resource")
    public static void clearScreen() {
        System.out.println("Press Enter to continue...");
        try {
            new Scanner(System.in).nextLine(); // Wait for user to press Enter
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
