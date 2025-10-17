package kbgteamapp;

import java.util.*;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

public class kbgTeam {

    private LinkedList<String> playerNames, playerTeams;
    private Random rand;
    private String[] team1, team2;
    private String[] teamCaptains;
    private Boolean teamCaptainsExist;

    public kbgTeam() {
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

        if (found) {
            return false;
        }
        playerNames.add(n);
        return true;
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
        if (!n) {
            playerNames.addAll(playerTeams);
            playerTeams.clear();
            teamCaptainsExist = false;
            team1 = new String[6];
            team2 = new String[6];
        }
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
            if (playerNames.size() < 2) {
                System.out.println("There are less than 2 players, team captains cannot be rolled.");
            }
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
        int count = Math.min(playerNames.size(), 12);
        ArrayList<String> tempPlayers = new ArrayList<>(playerNames);
        
        // Clear previous teams
        playerTeams.clear();
        Arrays.fill(team1, null);
        Arrays.fill(team2, null);

        // Fill teams evenly
        for (int i = 0; i < count; i++) {
            int index = rand.nextInt(tempPlayers.size());
            String player = tempPlayers.remove(index);
            playerTeams.add(player);
            
            if (i % 2 == 0) {
                team1[i/2] = player;
            } else {
                team2[i/2] = player;
            }
        }
        
        playerNames.removeAll(playerTeams);
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
