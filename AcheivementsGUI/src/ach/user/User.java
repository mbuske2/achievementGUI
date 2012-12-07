package ach.user;

/**
 *
 * @file User.java
 * @author MB
 * @author JF
 * @version 1.0
 * @date 11/29/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import ach.DataPath;
import ach.UserFile;
import ach.WebTask;
import ach.parser.GameHandler;
import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;

@SuppressWarnings("serial")
/**
 * A User Object that stores info about the user.
 */
public class User implements Serializable {

    /**
     * Basic User Name.
     */
    protected String userName;
    /**
     * URL for user profile on Steam(String).
     */
    protected String uDirectory;
    /**
     * URL for game directory on Steam(String).
     */
    protected String gDirectory;
    /**
     * URL for user profile.
     */
    protected URL userURL;
    /**
     * The URL of the Games.
     */
    protected URL gamesURL;
    /**
     * The Steam ID of the User.
     */
    protected String steamID64;
    /**
     * The Users Steam ID non-number version.
     */
    protected String steamID;
    /**
     * HashMap of all the Games the user has in steam.
     */
    private HashMap<String, Game> games = new HashMap<>();
    /**
     * The Keys for the Game Hash Map in ArrayList Of Strings
     */
    private ArrayList<String> gameKeys = new ArrayList<>();
    /**
     * HashMap of all the Games the user has in steam without Achievements.
     */
    private HashMap<String, Game> gamesWOA = new HashMap<>();
    /**
     * String address of the avatar image URL.
     */
    protected String avatarIcon;
    /**
     * URL of the avatar Icon.
     */
    protected URL avatarIconURL;
    /**
     * A Reference to This User's UserFile.
     */
    protected UserFile userFile;
    /**
     * The Path (as a String) to the user's avatar image on the disk.
     */
    private String avatarPath;
    /**
     * The Total Number Of Achievements Possible for the user.
     */
    private int totalAchievements;
    /**
     * The Total Number of Achieved Achievements.
     */
    private int totalAchieved;

    /**
     * Constructs a user object with a specific Name.
     *
     * @param uName The Name To set for the new user Object.
     */
    public User(String uName) {
        userName = uName;
        uDirectory = WebTask.iDAddress + userName + WebTask.xml;
        gDirectory = WebTask.iDAddress + userName + "/games" + WebTask.xml;
    }

    /*------------------------------/
     * Get Methods
     *-----------------------------*/
    /**
     * Gets the game Keys in an ArrayList of Strings.
     *
     * @return ArrayList<String> Keys for the Games as Strings.
     */
    public ArrayList<String> getGameKeys() {
        return gameKeys;
    }

    /**
     * Gets the total number of achievements the user has achieved.
     *
     * @return Integer the number of achievements the user has achieved.
     */
    public int getTotalAchieved() {
        return totalAchieved;
    }

    /**
     * Gets the total Number of Achievements the user has.
     *
     * @return Integer the total number of achievements the user has.
     */
    public int getTotalAchievements() {
        return totalAchievements;
    }

    /**
     * Gets the AvatarURL.
     *
     * @return URL the URL to the Icon for the User Avatar.
     */
    public URL getAvatarURL() {
        return avatarIconURL;
    }

    /**
     * Returns the user directory as a String.
     *
     * @return String The directory in String form.
     */
    public String getDirectory() {
        return uDirectory;
    }

    /**
     * Gets the Users URL Path to the Profile.
     *
     * @return URL the URL to the user's profile on steam.
     */
    public URL getURL() {
        return userURL;
    }

    /**
     * Returns the User's Steam ID #.
     *
     * @return String The user's Steam ID #.
     */
    public String getSteamID64() {
        return steamID64;
    }

    /**
     * Returns the User's Steam ID non-number.
     *
     * @return String The user's Steam ID non-number.
     */
    public String getSteamID() {
        return steamID;
    }

    /**
     * Returns the User's Name.
     *
     * @return String The User's Name In String Form.
     */
    public String getName() {
        return userName;
    }

    /**
     * Returns the List of Games With Achievements.
     *
     * @return HashMap<String, Game> The HashMap of Games With Achievements.
     */
    public HashMap<String, Game> getGames() {
        return games;
    }

    /**
     * Returns the List of Games Without Achievements.
     *
     * @return HashMap<String, Game> The HashMap of Games Without Achievements.
     */
    public HashMap<String, Game> getGamesWOA() {
        return gamesWOA;
    }

    /**
     * Returns the User's avatar icon URL as a string.
     *
     * @return String the User's avatar icon as a URL.
     */
    public String getAvatarIcon() {
        return avatarIcon;
    }
    
    /**Gets the Avatar Image path on the Disk as a String.
     *@return String The Avatar Image Path on The Disk.
     */
    public String getAvatarPath(){
        return avatarPath;
    }

    /*------------------------------/
     * Set Methods
     *-----------------------------*/
    /**
     * Sets the total number of achievements the user achieved.
     *
     * @param newTotal the new total number of achievements the user achieved.
     */
    public void setTotalAchieved(int newTotal) {
        totalAchieved = newTotal;
    }

    /**
     * Sets the total number of achievements possible for the user.
     *
     * @param newTotal the new total number of achievements possible.
     */
    public void setTotalAchievements(int newTotal) {
        totalAchievements = newTotal;
    }

    /**
     * Sets the total number of achievements and achieved variables.
     */
    public void setTotalsAch() {
        Game g;
        totalAchieved = 0;
        Set s = games.entrySet();
        Iterator it = s.iterator();
        totalAchievements = 0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            g = (Game) pairs.getValue();
            totalAchievements += g.getTotalAchievements();
            totalAchieved += g.getAchieved();
        }
    }

    /**
     * Sets the game Keys unsorted.
     */
    public void setGameKeys() {
        gameKeys = new ArrayList<>();
        Set s = games.keySet();
        Iterator it = s.iterator();
        String key;
        while (it.hasNext()) {
            key = (String) it.next();
            gameKeys.add(key);
        }

    }

    /**
     * Sets the game Keys as a Sorted List.
     *
     * @param sortedList The Sorted List to Set for this User Object.
     */
    public void setGameKeys(ArrayList<String> sortedList) {
        gameKeys = sortedList;
    }

    /**
     * Adds a key to the games key list.
     *
     * @param key The Key to add as a String.
     */
    public void addKey(String key) {
        gameKeys.add(key);
    }

    /**
     * Sets the path as a String for the user's avatar Icon on disk.
     *
     * @param newPath the new path as a String for the user's avatar image.
     */
    public void setAvatarPath(String newPath) {
        avatarPath = newPath;
    }

    /**
     * Sets the games lists in the form of HashMap. This may be changed at a
     * later date.
     *
     * @param newGamesList The hashmap of games with Achievements.
     * @param newGamesListWOA The hashmap of games without Achievements.
     */
    public void setGames(HashMap<String, Game> newGamesList, HashMap<String, Game> newGamesListWOA) {
        games = newGamesList;
        gamesWOA = newGamesListWOA;
    }

    //is this for test data?
    //CODE JOE ADDED FOR DEMO #1
    /**
     * Adds a single game to the games with achievements list. Used for
     * Debugging purposes.
     *
     * @param g The Game to add to the list.
     */
    public void addOneGame(Game g) {
        games.put(g.getName(), g);
    }
    //END CODE JOE ADDED FOR DEMO #1
    //NOT USED YET

    /**
     * Updates the user object, not used or complete yet. DO NOT USE.
     */
    /* public void updateUser() {
     try {
     SAXParserFactory factory = SAXParserFactory.newInstance();
     SAXParser saxParser = factory.newSAXParser();
     GameHandler gameHandler = new GameHandler(this);
     saxParser.parse(new InputSource(this.gamesURL.openStream()),
     gameHandler);
     } catch (Exception e) {
     System.out.println("Could not update games list");
     }
     }*/
    /**
     * Loads the games for the user Object.
     */
    public void loadGames() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GameHandler gameHandler = new GameHandler(this);
            saxParser.parse(new InputSource(gamesURL.openStream()),
                    gameHandler);
            //saves only the games that have achievements images.
            DataPath.getGameImages(games);
        } catch (Exception e) {
            System.out.println("Could not update games list");
        }
    }

    //updates games list
    /*public void updateGames() {
     try {
     SAXParserFactory factory = SAXParserFactory.newInstance();
     SAXParser saxParser = factory.newSAXParser();
     //replace with URL for #2
     if (fileExists(System.getProperty("user.dir") + System.getProperty("file.separator")
     + userName + "gameslist.xml")) {
     GameHandler gameHandler = new GameHandler(this);
     saxParser.parse(System.getProperty("user.dir") + System.getProperty("file.separator")
     + userName + "gameslist.xml", gameHandler);
     } else {
     System.out.println("File not found!");
     }
     } catch (Exception e) {
     System.out.println("Could not update games list");
     }
     }*/
    //create user
    /*public User populateUser() {
     try {
     SAXParserFactory factory = SAXParserFactory.newInstance();
     SAXParser saxParser = factory.newSAXParser();
     UserBuild user2 = new UserBuild(userFile);
     if (fileExists(System.getProperty("user.dir") + System.getProperty("file.separator")
     + userName + "profile.xml")) {
     ProfHandler profileHandler = new ProfHandler(user2);
     saxParser.parse(System.getProperty("user.dir") + System.getProperty("file.separator")
     + userName + "profile.xml", profileHandler);
     user2.updateGames();
     System.out.println(user2.toString());
     return user2;
     } else {
     System.out.println("File not found!");
     return null;
     }
     } catch (Exception e) {
     System.out.println("Could not Create User");
     return null;
     }
     }*/
    //create user with web data
    /**Creates the user URLs from strings and places them in the proper URL variables.*/
    public void updateUserURL() {
        try {
            avatarIconURL = new URL(avatarIcon);
            userURL = new URL(uDirectory);
            gamesURL = new URL(gDirectory);
        } catch (Exception e) {
            System.out.println("Could Not Update User URLS");
        }

    }

    /**Returns a String representation of the User Object.
     *@return String The String representation of this Object.
     */
    public String toString() {
        String u;
        String currentKey;
        Game t;
        ArrayList<Achievement> a;
        u = "Username: " + userName + "\nSteamID: " + steamID + "\nSteamID64: " + steamID64
                + "\nUser Profile URL: " + uDirectory;
        Set s = games.keySet();
        Iterator i = s.iterator();
        int count = 1;
        while (i.hasNext()) {
            currentKey = i.next().toString();
            u = u + "\n" + count + ": " + currentKey;
            t = games.get(currentKey);
            u = u + "\n\t" + currentKey + " has " + t.getAchievements().size() + " Achievements.";
            //u = u + t.achToString();
            count++;
        }
        s = gamesWOA.keySet();
        i = s.iterator();
        count = 1;
        u = u + "\n\nGames Without Achievements:";
        while (i.hasNext()) {
            currentKey = i.next().toString();
            u = u + "\n" + count + ": " + currentKey;
            count++;
        }
        return u;
    }
}
