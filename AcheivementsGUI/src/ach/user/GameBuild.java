package ach.user;

/**
 *
 * @file GameBuild.java
 * @author MB
 * @author JF
 * @version 1.0
 * @date 11/24/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import java.io.Serializable;

@SuppressWarnings("serial")
/**
 * Game Object that Sets info about a specific game.
 */
public class GameBuild extends Game implements Serializable {

    /**
     * Constructs an empty game object.
     */
    public GameBuild() {
        super();
    }

    /**
     * Constructs a game object with a specified Name.
     *
     * @param gameName The name of the game as a String.
     */
    public GameBuild(String gameName) {
        super(gameName);
    }

    /*------------------------------/
     * Set Methods
     *-----------------------------*/
    
    /**
     * Sets the games name.
     *
     * @param name the name of the game to set.
     */
    public void setName(String name) {
        super.name = name;
    }

    /**
     * Sets the games AppID.
     *
     * @param appID the appID to set.
     */
    public void setAppID(String appID) {
        super.appID = appID;
    }

    /**
     * Sets the logo path URL as a string.
     *
     * @param logoPath the logo Path to set.
     */
    public void setLogoPath(String logoPath) {
        super.logoPath = logoPath;
    }

    /**
     * Sets the stats link path URL as a String.
     *
     * @param statsLink the stats link path URL to be set.
     */
    public void setStatsLink(String statsLink) {
        super.statsLink = statsLink;
    }

    /**
     * Sets the Global Stats link path URL as a String.
     *
     * @param globalStatsLink the global stats link path URL to be set.
     */
    public void setGlobalStatsLink(String globalStatsLink) {
        super.globalStatsLink = globalStatsLink;
    }

    /**
     * Sets the User reference as a User Object.
     *
     * @param user The User Object the game belongs to.
     */
    public void setUser(User user) {
        super.user = user;
    }
}