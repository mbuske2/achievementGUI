package ach;

/**
 *
 * @file GNAComparator.java
 * @author MB
 * @version 1.0
 * @date 11/29/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import ach.user.Achievement;
import ach.user.Game;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
/**
 * GNAComparator Class That sorts game objects by certain criteria.
 */
public class GNAComparator implements Serializable {

    /**
     * Constant Parameter Hours Played High to Low Flag. Sort Parameter. Game.
     */
    public static final String GSHPHTL = "HoursPlayedHTL";
    /**
     * Constant Parameter Hours Played Low To High Flag. Sort Parameter. Game.
     */
    public static final String GSHPLTH = "HoursPlayedLTH";
    /**
     * Constant Parameter Alphabetical. Sort Parameter. Game and Achievement.
     */
    public static final String GASALPHA = "Alpha";
    /**
     * Constant Parameter Percent - Ascending. Sort Parameter. Game.
     */
    public static final String GSPCENT = "Percent";
    /**
     * Constant Parameter Percent - Descending. Sort Parameter. Game.
     */
    public static final String GSDCENT = "De-Percent";
    /**
     * Constant Parameter 100% Filter. Filter Parameter. Game.
     */
    public static final String GFOHPAF = "100%";
    /**
     * Constant Parameter To Reset Filter. Filter Parameter. Game.
     */
    public static final String GFNULVA = "Null";
    /**
     * Constant Parameter Sort by Most Recent Achieved. Sort
     * Parameter.Achievement.
     */
    public static final String ASDAREA = "Recent Date Achieved";
    /**
     * Constant Parameter Default. Sort Parameter.Achievement.
     */
    public static final String ASFDEFAL = "Default";
    /**
     * Constant Parameter Filter Locked. Filter Parameter. Achievement.
     */
    public static final String AFACHLK = "Locked";
    /**
     * Constant Parameter Filter UnLocked. Filter Parameter. Achievement.
     */
    public static final String AFACHUL = "Unlocked";
    /**
     * Constant Parameter Filter All. Filter Parameter. Achievement.
     */
    public static final String AFACHAL = "All";
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
     * Compares two game objects by Percentage Complete.
     *
     * @param a The First Game to Compare.
     * @param b The Second Game To Compare.
     * @return Integer Returns 0 if a == b, -1 if a% < b%, 1 if a% > b%.
     *
     */
    public static int comparePercent(Game a, Game b) {
        int centA, centB;
        double percent = ((double) a.getAchieved() / (double) a.getTotalAchievements()) * 100;
        centA = (int) percent;
        percent = ((double) b.getAchieved() / (double) b.getTotalAchievements()) * 100;
        centB = (int) percent;
        if (centA > centB) {
            return 1;
        } else if (centA < centB) {
            return -1;
        } else {
            return 0;
        }
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
        } else if (parameter.equalsIgnoreCase("Percent")) {
            while (aCount < a1.size() && bCount < a2.size()) {
                if (comparePercent(games.get(a1.get(aCount)), games.get(a2.get(bCount))) < 0) {
                    total.add(tCount++, a1.get(aCount++));
                } else {
                    total.add(tCount++, a2.get(bCount++));
                }
            }
        } else if (parameter.equalsIgnoreCase("De-Percent")) {
            while (aCount < a1.size() && bCount < a2.size()) {
                if (comparePercent(games.get(a1.get(aCount)), games.get(a2.get(bCount))) > 0) {
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
     * Filters The Games Based On A Parameter and returns the ArrayList of
     * Filtered Games.
     *
     * @param games The hashmap of un-filtered games that you wish to filter
     * @param parameter The String Constant Parameter to filter the games by.
     * @return An ArrayList of keys for the filtered games.
     */
    public static ArrayList<String> filterGames(HashMap<String, Game> games, String parameter) {
        ArrayList<String> filteredKeys = new ArrayList<>();
        Game g;
        Set s = games.keySet();
        Iterator it = s.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            g = games.get(key);
            if (parameter.equalsIgnoreCase("100%")) {
                if ((g.getAchieved() / g.getTotalAchievements()) == 1) {
                    filteredKeys.add(key);
                }
            } else if (parameter.equalsIgnoreCase("null")) {
                filteredKeys.add(key);
            }
        }
        return filteredKeys;
    }

    /**
     * Compares two Achievement objects Alphabetically.
     *
     * @param a The First Achievement to Compare.
     * @param b The Second Achievement To Compare.
     * @return Integer Returns 0 if a == b, -1 if a < b, 1 if a > b.
     */
    public static int compareAlpha(Achievement a, Achievement b) {
        String aL, bL;
        aL = a.getName().toLowerCase();
        bL = b.getName().toLowerCase();
        return aL.compareTo(bL);
    }

    /**
     * Compares two Achievement objects By Date Unlocked.
     *
     * @param a The First Achievement to Compare.
     * @param b The Second Achievement To Compare.
     * @return Integer Returns 0 if a == b, 1 if a before b, -1 if b before a.
     */
    public static int compareDate(Achievement a, Achievement b) {
        Date a1 = new Date(a.getUnixTimeStamp());
        Date b2 = new Date(b.getUnixTimeStamp());
        if (a1.before(b2)) {
            return 1;
        } else if (a1.after(b2)) {
            return -1;
        } else {
            return 0;
        }

    }

    /**
     * Filters Achievements
     *
     */
    public static ArrayList<Achievement> filterAchievements(ArrayList<Achievement> a, String parameter) {
        ArrayList<Achievement> temp = new ArrayList<>();

        if (parameter.equalsIgnoreCase("Locked")) {
            for (int i = 0; i < a.size(); i++) {
                if (!a.get(i).getUnlockedStatus()) {
                    temp.add(a.get(i));
                }
            }

            return temp;
        } else if (parameter.equalsIgnoreCase("Unlocked")) {
            for (int i = 0; i < a.size(); i++) {
                if (a.get(i).getUnlockedStatus()) {
                    temp.add(a.get(i));
                }
            }

            return temp;
        } else if (parameter.equalsIgnoreCase("All")) {
            if (!a.isEmpty()) {
                return a.get(0).getGame().getAchievements();
            }
            return a;
        } else {
            return a;
        }


    }

    /**
     * Uses merge sort to sort the Achievements
     *
     * @param a The list to sort.
     * @param games The HashMap Of The Games To Sort.
     * @param parameter A constant value that decides what way the list is
     * sorted. #If the parameter == HPLTH, then it is sorted by hours played -
     * low to high. #If the parameter == HPHTL, then it is sorted by hours
     * played - high to low. #If the parameter == ALPHA, then it is sorted
     * alphabetically.
     * @return ArrayList<Achievement> the sortedList
     */
    public static ArrayList<Achievement> sortAch(ArrayList<Achievement> a, String parameter) {
        if (a.size() == 1) {
            return a;
        }
        int mid = a.size() / 2;
        ArrayList<Achievement> left = new ArrayList<>();
        for (int i = 0; i < mid; i++) {
            left.add(a.get(i));
        }
        ArrayList<Achievement> right = new ArrayList<>();
        for (int i = mid; i < a.size(); i++) {
            right.add(a.get(i));
        }
        ArrayList<Achievement> lRes = sortAch(left, parameter);
        ArrayList<Achievement> rRes = sortAch(right, parameter);
        return mergeAch(lRes, rRes, parameter);
    }

    /**
     * Merges two String ArrayList's into one sorted one for use with
     * HoursPlayedLTH.
     *
     * @param a1 the first list to merge.
     * @param a2 the second list to merge.
     * @param games The HashMap Of The Games To Sort.
     * @param parameter A constant value that decides what way the list is
     * sorted. #If the parameter == ALPHA, then it is sorted alphabetically.
     * @return ArrayList<Achievement> the merged list.
     */
    private static ArrayList<Achievement> mergeAch(ArrayList<Achievement> a1, ArrayList<Achievement> a2, String parameter) {
        int aCount = 0;
        int bCount = 0;
        int tCount = 0;
        ArrayList<Achievement> total = new ArrayList<>();

        if (parameter.equalsIgnoreCase("Alpha")) {
            while (aCount < a1.size() && bCount < a2.size()) {
                if (compareAlpha(a1.get(aCount), a2.get(bCount)) < 0) {
                    total.add(tCount++, a1.get(aCount++));
                } else {
                    total.add(tCount++, a2.get(bCount++));
                }
            }
        } else if (parameter.equalsIgnoreCase("Recent Date Achieved")) {
            while (aCount < a1.size() && bCount < a2.size()) {
                if (compareDate(a1.get(aCount), a2.get(bCount)) < 0) {
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
     * Gets The Default List either all Locked Or Unlocked.
     *
     * @param list The Default List
     * @param isLocked True if to return locked achievement list, false for
     * unlocked.
     * @return ArrayList<Achievement> The list of achievements.
     */
    public static ArrayList<Achievement> getDefaults(ArrayList<Achievement> list, boolean isLocked) {
        ArrayList<Achievement> a = new ArrayList<>();
        if (isLocked) {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getUnlockedStatus()) {
                    a.add(list.get(i));
                }
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUnlockedStatus()) {
                    a.add(list.get(i));
                }
            }
        }
        return a;
    }
}
