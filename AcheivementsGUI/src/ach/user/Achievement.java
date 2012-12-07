package ach.user;

/**
 *
 * @file Achievement.java
 * @author MB
 * @version 1.0
 * @date 11/29/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
/**
 * Achievement Object that contains information about an achievement.
 */
public class Achievement implements Serializable {

    /**
     * A reference to the Game object that this achievement belongs to.
     */
    private Game game;
    /**
     * Boolean variable to indicate if the achievement has been unlocked.
     */
    private boolean unlocked = false;
    /**
     * Variable that stores the path to the image for a locked achievement.
     */
    private String iconClosedURLPath;
    /**
     * Variable that stores the path to the image for an unlocked achievement.
     */
    private String iconOpenURLPath;
    /**
     * The name of the Achievement.
     */
    private String name;
    /**
     * The description of the achievement.
     */
    private String description;
    /**
     * The Path as a String for the open icon image on disk.
     */
    private String iconOpenPath;
    /**
     * The path as a string for the closed icon image on disk.
     */
    private String iconClosedPath;
    /**
     * String for the timestamp in String Format.
     */
    private String timeStamp;
    /**
     * Long representation for the timestamp.
     */
    private long longTimeStamp;

    /**
     * Constructs an Achievement object with its unlocked state.
     *
     * @param closed locked if 0, unlocked if anything else.
     */
    public Achievement(int closed) {
        if (closed == 0) {
            unlocked = false;
        } else if (closed == 1) {
            unlocked = true;
        }
    }

    /*------------------------------/
     * Get Methods
     *-----------------------------*/
    /**
     * Gets the timestamp in String format.
     *
     * @return String The String version of the timeStamp.
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Gets the timestamp in long format.
     *
     * @return long The timestamp in long format (in Unix Time).
     */
    public long getUnixTimeStamp() {
        return longTimeStamp;
    }

    /**
     * Gets the Closed Icon's Path on the disk as a String
     *
     * @return String the path on the disk where this image resides.
     */
    public String getIconClosedPath() {
        return iconClosedPath;
    }

    /**
     * Gets the Open Icon's Path on the disk as a String
     *
     * @return String the path on the disk where this image resides.
     */
    public String getIconOpenPath() {
        return iconOpenPath;
    }

    /**
     * Gets the game this achievement belongs to.
     *
     * @return Game The game object the achievement belongs to.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Gets the unlocked status of this achievement.
     *
     * @return boolean returns true if the game is unlocked, false otherwise.
     */
    public boolean getUnlockedStatus() {
        return unlocked;
    }

    /**
     * Gets the path (URL) to the locked image of this achievement.
     *
     * @return String The path in which the image for the closed(locked) icon
     * resides.
     */
    public String getIconClosedURLPath() {
        return iconClosedURLPath;
    }

    /**
     * Gets the path (URL) to the unlocked image of this achievement.
     *
     * @return String The path in which the image for the Open(unlocked) icon
     * resides.
     */
    public String getIconOpenURLPath() {
        return iconOpenURLPath;
    }

    /**
     * Gets the name of the achievement.
     *
     * @return String The name of the achievement.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the achievement.
     *
     * @return String The description of the achievement.
     */
    public String getDescription() {
        return description;
    }

    /*------------------------------/
     * Set Methods
     *-----------------------------*/
    /**
     * Takes a String and converts it to a long and then Sets the long
     * timestamp.
     *
     * @param newStamp The String to be converted to a long.
     * @return boolean Returns if the method was successful and false if it
     * wasn't.
     */
    public boolean convertNSetTimeStamp(String newStamp) {
        try {
            longTimeStamp = Long.parseLong(newStamp);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Sets the long (Unix) timestamp variable.
     *
     * @param newStamp The timestamp to set.
     */
    public void setUnixTimeStamp(long newStamp) {
        longTimeStamp = newStamp;
    }

    /**
     * Sets the String format TimeStamp
     *
     * @param newStamp The String TimeStamp To Set.
     */
    public void setTimeStamp(String newStamp) {
        timeStamp = newStamp;
    }

    /**
     * Sets the game this achievement belongs to.
     *
     * @param newGame The game object to be set for the current achievement.
     */
    public void setGame(Game newGame) {
        game = newGame;
    }

    /**
     * Sets the unlocked status of this achievement.
     *
     * @param i if i is 0, unlocked is set to false, if i is 1, unlocked is set
     * to true To be used with AchHandler.
     */
    public void setUnlockedStatus(int i) {
        if (i == 0) {
            unlocked = false;
        } else if (i == 1) {
            unlocked = true;
        }
    }

    /**
     * Sets the path (URL as String) to the locked image of this achievement.
     *
     * @param newIconClosedURLPath The path to be set in which the image for the
     * closed(locked) icon resides.
     */
    public void setIconClosedURLPath(String newIconClosedURLPath) {
        iconClosedURLPath = newIconClosedURLPath;
    }

    /**
     * Sets the path (URL as String) to the unlocked image of this achievement.
     *
     * @param newIconOpenURLPath The path to be set in which the image for the
     * Open(unlocked) icon resides.
     */
    public void setIconOpenURLPath(String newIconOpenURLPath) {
        iconOpenURLPath = newIconOpenURLPath;
    }

    /**
     * Sets the name of the achievement.
     *
     * @param newName The name of the achievement.
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Sets the description of the achievement.
     *
     * @param newDescription The description of the achievement.
     */
    public void setDescription(String newDescription) {
        description = newDescription;
    }

    /**
     * Sets the path for the Open Icon Image on disk as a String.
     *
     * @param newIconOpenPath The Path to the Achievement's open Icon Image.
     */
    public void setIconOpenPath(String newIconOpenPath) {
        iconOpenPath = newIconOpenPath;
    }

    /**
     * Sets the path for the Closed Icon Image on disk as a String.
     *
     * @param newIconClosedPath The Path to the Achievement's closed Icon Image.
     */
    public void setIconClosedPath(String newIconClosedPath) {
        iconClosedPath = newIconClosedPath;
    }

    /**
     * Sets the String TimeStamp With A Long Value.
     *
     * @param l The long value to be converted to a String Date object.
     */
    public void setTimeStamp(long l) {
        timeStamp = convert(l);
    }

    /**
     * Converts a long variable as a timeStamp into a String. Takes a Unix time
     * stamp and converts it to a String representation of a date.
     *
     * @param stamp The stamp to be converted to a string date.
     * @return String The converted date.
     */
    private String convert(long stamp) {
        String tStamp;
        long s = stamp * 1000;
        Date d = new Date(s);
        tStamp = d.toString();
        return tStamp;
    }

    /**
     * The Achievement Object's toString Method.
     *
     * @return String A String representation of this object.
     */
    @Override
    public String toString() {
        String a;
        a = name + ": " + description + " - Unlocked: ";
        if (unlocked) {
            a = a + "Yes";
        } else {
            a = a + "No";
        }
        //a = a + "\n" + iconClosedURLPath + "\n" + iconOpenURLPath;
        return a;
    }
}
