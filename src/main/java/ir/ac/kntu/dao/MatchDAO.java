package ir.ac.kntu.dao;

import ir.ac.kntu.matchandhischildren.Match;

import java.io.*;
import java.util.ArrayList;

public class MatchDAO {

    private final File file = new File("src/dataBase/matchesData.txt");

    public ArrayList<Match> getAllMatches() {
        ArrayList<Match> savedMatches = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file); ObjectInputStream input = new ObjectInputStream(fileInputStream)) {
            while (true) {
                try {
                    Match match = (Match) input.readObject();
                    savedMatches.add(match);
                } catch (EOFException e) {
                    break;
                } catch (Exception e) {
                    System.out.println("Problem with some of the records in the matches data file");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("No previous data for matches has been saved.");
        }
        return savedMatches;
    }

    public void saveAllMatches(ArrayList<Match> matches) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file); ObjectOutputStream output = new ObjectOutputStream(fileOutputStream)) {
            for (Match m : matches) {
                try {
                    output.writeObject(m);
                } catch (IOException e) {
                    System.out.println("An error occurred while trying to save info");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while trying to save info");
            System.out.println(e.getMessage());
        }
    }

    public void updateMatch(Match match) {
        ArrayList<Match> matches = getAllMatches();
        if (matches.contains(match)) {
            matches.set(matches.indexOf(match), match);
            System.out.println(match.getMatchName() + " successfully updated.");
        } else {
            matches.add(match);
            System.out.println(match.getMatchName() + " successfully added.");
        }
        saveAllMatches(matches);
    }

    public void deleteMatch(Match match) {
        ArrayList<Match> matches = getAllMatches();
        if (matches.contains(match)) {
            matches.remove(match);
            System.out.println(match.getMatchName() + " successfully removed.");
        } else {
            System.out.println("No match have been registered with this username.");
        }
        saveAllMatches(matches);
    }
}
