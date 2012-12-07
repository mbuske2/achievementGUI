package ach;

/**
 *
 * @file GameComparator.java
 * @author MB
 * @version 1.0
 * @date 11/29/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import ach.user.Game;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("serial")
/**
 * GameComparator Class That sorts game objects by certain criteria.
 */
public class GameComparator implements Serializable {

    /**
     * Constant Parameter Hours Played High to Low Flag
     */
    public static final String HPHTL = "HoursPlayedHTL";
    /**
     * Constant Parameter Hours Played Low To High Flag
     */
    public static final String HPLTH = "HoursPlayedLTH";
    /**
     * Constant Parameter Alphabetical.
     */
    public static final String ALPHA = "Alpha";
    //compares the objects by hours played and returns the lower one
    //returns 0 if equal, returns -1 if a is less than b, 1 if a is greater than b

    /**
     * Compares two game objects by hours played and returns the result.
     *
     * @param a The First Game to Compare.
     * @param b The Second Game To Compare.
     * @return Integer Returns 0 if a == b, -1 if a < b, 1 if a > b.
     */
    public static int compareByHoursPlayed(Game a, Game b) {
        if (a.getHours() < b.getHours()) {
            return -1;
        } else if (a.getHours() > b.getHours()) {
            return 1;
        } else {
            return 0;
        }

    }

    /**
     * Compares two game objects Alphabetically.
     *
     * @param a The First Game to Compare.
     * @param b The Second Game To Compare.
     * @return Integer Returns 0 if a == b, -1 if a < b, 1 if a > b.
     */
    public static int compareAlpha(Game a, Game b) {
        String aL, bL;
        aL = a.getFriendlyName().toLowerCase();
        bL = b.getFriendlyName().toLowerCase();
        return aL.compareTo(bL);
    }

    /**
     * Uses merge sort to sort the Keys For A Game by hours played by a
     * Parameter.
     *
     * @param a The list to sort.
     * @param games The HashMap Of The Games To Sort.
     * @param parameter A constant value that decides what way the list is
     * sorted. #If the parameter == HPLTH, then it is sorted by hours played -
     * low to high. #If the parameter == HPHTL, then it is sorted by hours
     * played - high to low. #If the parameter == ALPHA, then it is sorted
     * alphabetically.
     * @return ArrayList<String> the sortedList
     */
    public static ArrayList<String> sortHoursPlayed(ArrayList<String> a, HashMap<String, Game> games, String parameter) {
        if (a.size() == 1) {
            return a;
        }
        int mid = a.size() / 2;
        ArrayList<String> left = new ArrayList<>();
        for (int i = 0; i < mid; i++) {
            left.add(a.get(i));
        }
        ArrayList<String> right = new ArrayList<>();
        for (int i = mid; i < a.size(); i++) {
            right.add(a.get(i));
        }
        ArrayList<String> lRes = sortHoursPlayed(left, games, parameter);
        ArrayList<String> rRes = sortHoursPlayed(right, games, parameter);
        return mergeHoursPlayed(lRes, rRes, games, parameter);
    }

    /**
     * Merges two String ArrayList's into one sorted one for use with
     * HoursPlayedLTH.
     *
     * @param a1 the first list to merge.
     * @param a2 the second list to merge.
     * @param games The HashMap Of The Games To Sort.
     * @param parameter A constant value that decides what way the list is
     * sorted. #If the parameter == HPLTH, then it is sorted by hours played -
     * low to high. #If the parameter == HPHTL, then it is sorted by hours
     * played - high to low. #If the parameter == ALPHA, then it is sorted
     * alphabetically.
     * @return ArrayList<String> the merged list.
     */
    private static ArrayList<String> mergeHoursPlayed(ArrayList<String> a1, ArrayList<String> a2,
            HashMap<String, Game> games, String parameter) {
        int aCount = 0;
        int bCount = 0;
        int tCount = 0;
        ArrayList<String> total = new ArrayList<>();

        if (parameter.equalsIgnoreCase("HoursPlayedLTH")) {
            while (aCount < a1.size() && bCount < a2.size()) {
                if (compareByHoursPlayed(games.get(a1.get(aCount)), games.get(a2.get(bCount))) < 0) {
                    total.add(tCount++, a1.get(aCount++));
                } else {
                    total.add(tCount++, a2.get(bCount++));
                }
            }
        } else if (parameter.equalsIgnoreCase("HoursPlayedHTL")) {
            while (aCount < a1.size() && bCount < a2.size()) {
                if (compareByHoursPlayed(games.get(a1.get(aCount)), games.get(a2.get(bCount))) > 0) {
                    total.add(tCount++, a1.get(aCount++));
                } else {
                    total.add(tCount++, a2.get(bCount++));
                }
            }
        } else if (parameter.equalsIgnoreCase("Alpha")) {
            while (aCount < a1.size() && bCount < a2.size()) {
                if (compareAlpha(games.get(a1.get(aCount)), games.get(a2.get(bCount))) < 0) {
                    total.add(tCount++, a1.get(aCount++));
                } else {
                    total.add(tCount++, a2.get(bCount++));
                }
            }
        } else {
            throw new UnsupportedOperationException("Parameter Not Recognized.");
        }
        if (aCount >= a1.size()) {
            for (int i = bCount; i < a2.size(); i++) {
                total.add(tCount++, a2.get(i));
            }
        } else {
            for (int i = aCount; i < a1.size(); i++) {
                total.add(tCount++, a1.get(i));
            }
        }
        return total;
    }

    /**
     * 
     * @param games The hashmap of unfiltered games that you wish to filter
     * @return An ArrayList of type Game where the games are those in which
     *          all achievements have been completed.
     */
    
    public static ArrayList<Game> filterCompletedAchievements(HashMap<String, Game> games) {
        ArrayList<Game> filteredGames = new ArrayList();
        Iterator it = games.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            Game temp = (Game) pairs.getValue();
            int a = temp.getTotalAchievements();
            int b = temp.getAchieved();
            if (a == b) {
                filteredGames.add(temp);
           }
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

        return filteredGames;
    }


}
