package kbgteamapp;

import java.util.*;

public class mainCLI {
    public static void main(String[] args) {
        kbgTeam kbg = new kbgTeam();
        int choice = 0;
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the Kivotos Battlegrounds Team App");
        while (choice != -1) {
            if (!kbg.getPlayers().isEmpty()) {
                System.out.print("Current Players:\n" + kbg.getPlayers() + "\n");
            }
            if (!kbg.getPlayersTeams().isEmpty()) {
                System.out.print("Current Players in Teams:\n" + kbg.getPlayersTeams() + "\n");
            }
            if (kbg.getTeamCaptainsExist()) {
                System.out.println("Team Captains: " + kbg.getTeamCaptains(1) + " and " + kbg.getTeamCaptains(2));
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
                            System.out.println("Current Players: " + kbg.getPlayers());
                            System.out.print("Enter player name >> ");
                            String name = in.nextLine();
                            if (kbg.addPlayers(name)) {
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
                        for (String s : kbg.getPlayers()) {
                            System.out.print(s + ", ");
                        }
                        System.out.print("\nEnter player name to remove >> ");
                        String name = in.nextLine();
                        if (kbg.removePlayer(name)) {
                            System.out.println(name + " removed from List.\n");
                        } else {
                            System.out.println(name + " not found in List.\n");
                        }
                        clearScreen();
                        break;
                    case 3:
                        System.out.print("Current Players: ");
                        for (String s : kbg.getPlayers()) {
                            System.out.print(s + ", ");
                        }
                        System.out.print("\nEnter player name to update >> ");
                        String oldName = in.nextLine();
                        System.out.print("Enter new name >> ");
                        String newName = in.nextLine();
                        if (kbg.updatePlayer(oldName, newName)) {
                            System.out.println(oldName + " updated to " + newName + ".\n");
                        } else {
                            System.out.println(oldName + " not found in List.\n");
                        }
                        break;
                    case 4:
                        kbg.randomizeTeams();
                        break;
                    case 5:
                        kbg.chooseTeams();
                        break;
                    case 6:
                        kbg.showTeams();
                        break;
                    case 7:
                        kbg.teamCaptainRoll();
                        System.out.println("Team Captains: " + kbg.getTeamCaptains(1) + " and " + kbg.getTeamCaptains(2));
                        clearScreen();
                        break;
                    case 8:
                        System.out.print("Would you like to clear the player list or reset the teams? (1/2) >> ");
                        int n = in.nextInt();
                        in.nextLine(); // Consume the newline character
                        if (n == 1) {
                            kbg.clearList(true);
                            System.out.println("Player list cleared.\n");
                        } else {
                            kbg.clearList(false);
                            System.out.println("Teams reset.\n");
                        }
                        clearScreen();
                        break;
                    case 9:
                        // ill figure this out once i fix the oter hting
                        break;
                    case 10:
                        choice = -1;
                        break;
                    default:
                        System.out.println("Invalid choice");
                        clearScreen();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number from the given choices.");
                in.nextLine(); // Consume the invalid input
                clearScreen();
            } catch (NoSuchElementException e) {
                System.out.println("No input available. Exiting.");
                choice = -1;
            }
        }
        in.close();
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
