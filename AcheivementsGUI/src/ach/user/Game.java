package ach.user;

/**
 *
 * @file Game.java
 * @author MB
 * @author JF
 * @version 1.0
 * @date 11/29/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import ach.parser.AchHandler;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;

@SuppressWarnings("serial")
/**
 * Game Object that holds info about a specific game.
 */
public class Game implements Serializable {

    /**
     * Reference to the User This game Belongs To.
     */
    protected User user;
    /**
     * Last Half Of Base Directory.
     */
    protected static final String dir = "/?xml=1";
    /**
     * The name of the game.
     */
    protected String name;
    /**
     * The app ID.
     */
    protected String appID;
    /**
     * The logo for the game (URL in String form).
     */
    protected String logoPath;
    /**
     * The URL of the stats page in String Form.
     */
    protected String statsLink;
    /**
     * The URL of the global stats page in String Form.
     */
    protected String globalStatsLink;
    /**
     * The URL of the logo.
     */
    protected URL logoURL;
    /**
     * The URL of the player's stats page.
     */
    protected URL statsURL;
    /**
     * The URL of the global stats page.
     */
    protected URL globalStatsURL;
    /**
     * The achievements belonging to the game In The Default Order.
     */
    private ArrayList<Achievement> achievements = new ArrayList<>();
    /**
     * The hours on record of the game.
     */
    protected double hours;
    /**
     * The Game name in web friendly name.
     */
    protected String friendlyName;
    /**
     * The Game Icon's Path On The HDD.
     */
    protected String gamePath;
    /**
     * The Number Of Achievements Tied To This Game.
     */
    private int totalAchievements;
    /**
     * The Number Of Achievements Achieved.
     */
    private int achieved;

    /**
     * Constructs an empty game object.
     */
    public Game() {
    }

    /**
     * Constructs a game object with a specified Name.
     *
     * @param gameName The name of the game as a String.
     */
    public Game(String gameName) {
        name = gameName;
    }

    /**
     * Creates the friendly name for the game object.
     *
     * @param nameToBe the name as it normally is.
     */
    public void setFriendlyName(String nameToBe) {
        friendlyName = nameToBe.replaceAll("[-+.^:, ']", "");
    }

    /*------------------------------/
     * Get Methods
     *-----------------------------*/
    /**
     * Gets the game's gamePath variable.
     *
     * @return String The Game's Directory Path as A String.
     */
    public String getGamePath() {
        return gamePath;
    }

    /**
     * Returns the number of achievements achieved for this game.
     *
     * @return Integer the number of the achievements achieved for this game.
     */
    public int getAchieved() {
        return achieved;
    }

    /**
     * Returns the total number of achievements attached to this game.
     *
     * @return Integer the total number of achievements for this game.
     */
    public int getTotalAchievements() {
        return totalAchievements;
    }

    /**
     * Returns the name of the Game as a String.
     *
     * @return String The String name of the game.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the game's Friendly Name.
     *
     * @return String the game's friendly name.
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    /**
     * Returns the AppID of the Game as a String.
     *
     * @return String The String representation of the AppID.
     */
    public String getAppID() {
        return appID;
    }

    /**
     * Returns the URL of the Logo as a String.
     *
     * @return String The String representation of the logo URL.
     */
    public String getLogoPath() {
        return logoPath;
    }

    /**
     * Returns the hours played for a game as a double.
     *
     * @return double The hours played.
     */
    public double getHours() {
        return hours;
    }

    /**
     * Returns an ArrayList of Achievement Objects Belonging to a game.
     *
     * @return ArrayList<Achievements> The ArrayList of Achievements that
     * belongs to a game.
     */
    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    /*------------------------------/
     * Set Methods
     *-----------------------------*/
    /**
     * Sets the number of achievements that are achieved.
     *
     * @param number the number of achievements achieved.
     */
    public void setAchieved(int number) {
        achieved = number;
    }

    /**
     * Sets the total number of achievements the game has.
     *
     * @param number the number of achievements the game has.
     */
    public void setTotalAchievements(int number) {
        totalAchievements = number;
    }

    /**
     * Updates the Hours Played For A Game.
     *
     * @param newHours The updated hours pulled from the web.
     */
    public void updateHoursPlayed(double newHours) {
        hours = newHours;
    }

    /**
     * Sets a Game's Achievements
     *
     * @param newAchievements The new set of Achievements to attach to a game.
     */
    public void setAchievements(ArrayList<Achievement> newAchievements) {
        achievements = newAchievements;
    }

    /**
     * Sets the Game Icon Path for the Game Object.
     *
     * @param gamePath the path as a String of the Icon For The Game.
     */
    public void setGamePath(String gamePath) {
        this.gamePath = gamePath;
    }

    /**
     * Sets the number of achieved achievements.
     *
     */
    public void resetAchieved() {
        achieved = 0;
        for (int i = 0; i < achievements.size(); i++) {
            if (achievements.get(i).getUnlockedStatus()) {
                achieved++;
            }
        }
    }

    /**
     * The Game Object's toString Method.
     *
     * @return String A String representation of this object.
     */
    @Override
    public String toString() {
        String g;
        g = "Game: " + name;
        return g;
    }

    /**
     * Returns a String containing all of the Achievement Objects for A Game.
     *
     * @return String A String representation of the Achievements.
     */
    public String achToString() {
        String a = "";
        for (int i = 0; i < achievements.size(); i++) {
            a = a + "\n\t" + (i + 1) + ": " + achievements.get(i).toString();
        }
        return a;
    }

    //update achievements -TO DOCUMENT
    /**
     * Updates Achievements for A game Object and returns if it was successful
     * or not.
     *
     * @param retries The max amount of retries before moving on.
     * @return boolean Returns true if it was able to update a game's
     * achievements, false if it failed.
     */
    public boolean updateAchievements2(int retries) {
        //the first two if statements are hard coded right now, and reply the reason the achievements cannot be loaded.
        if (name.equalsIgnoreCase("Galactic Civilizations II: Ultimate Edition")
                || name.equalsIgnoreCase("Gotham City Impostors")) {
            System.out.println(name + " Does not have Achievements");
            return false;
        } else if (name.equalsIgnoreCase("Warhammer® 40,000™: Dawn of War® II – Retribution™")) {
            System.out.println(name + "'s Stats Page Is Currently Broken.");
            return false;
        } else {
            if (retries == 0) {
                try {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    //replace with URL for #2
                    if (statsLink != null) {
                        AchHandler achievementHandler = new AchHandler(this);
                        URL url = new URL(statsLink + dir);
                        saxParser.parse(new InputSource(url.openStream()), achievementHandler);
                        return true;
                    } else {
                        System.out.println(name + " Does not have Achievements");
                        return false;
                    }
                } catch (Exception e) {
                    System.out.println();
                    System.out.println("Could not update Achievements list for " + name);
                    System.out.println("Retry: " + (retries + 1));
                    return this.updateAchievements2(retries + 1);
                }
            } else if (retries <= 25) {
                try {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    //replace with URL for #2
                    AchHandler achievementHandler = new AchHandler(this);
                    URL url = new URL(statsLink + dir);
                    saxParser.parse(new InputSource(url.openStream()), achievementHandler);
                    return true;
                } catch (Exception e) {
                    System.out.println("Retry: " + (retries + 1));
                    return this.updateAchievements2(retries + 1);
                }
            } else {
                System.out.println("Could not update Achievements list for " + name
                        + " after 25 attempts.");
                System.out.println();
                return false;
            }
        }
    }
    /**
     * Updates the Achievements for this Game from the Disk -Not Used Right now,
     * Would need Updating to work
     */
    /*public void updateAchievementsOnDisk() {
     try {
     SAXParserFactory factory = SAXParserFactory.newInstance();
     SAXParser saxParser = factory.newSAXParser();
     //replace with URL for #2
     if (fileExists(System.getProperty("user.dir") + System.getProperty("file.separator")
     + user.getName() + friendlyName + ".xml")) {
     AchHandler achievementHandler = new AchHandler(this);
     saxParser.parse(System.getProperty("user.dir") + System.getProperty("file.separator")
     + user.getName() + friendlyName + ".xml", achievementHandler);
     } else {
     System.out.println("File not found!");
     }
     } catch (Exception e) {
     System.out.println("Could not update Achievements list");
     }
     }*/
    /**
     * Temporary?
     */
    /*private static boolean fileExists(String fileName) {
     File f = new File(fileName);
     return f.exists();
     }*/
    //JOE'S DEBUG CODE
    /*public void setUser(User u) {
     user = u;
     }
     public void addOneAchievement(Achievement a) {
     achievements.add(a);
     }*/
}
