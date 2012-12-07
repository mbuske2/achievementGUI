package ach.user;

/**
 *
 * @file UserBuild.java
 * @author MB
 * @version 1.0
 * @date 11/14/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import ach.UserFile;
import ach.WebTask;
import java.io.Serializable;
import java.net.URL;

@SuppressWarnings("serial")
/**
 * A UserBuild Object
 */
public class UserBuild extends User implements Serializable {

    /**
     * Constructs a userbuild object using it's UserFile.
     *
     * @param uF The UserFile info for the new user Object.
     */
    public UserBuild(UserFile uF) {
        super(uF.getName());
        steamID64 = uF.getID();
        userFile = uF;
        uDirectory = WebTask.iDAddress + uF.getName() + WebTask.xml;
        gDirectory = WebTask.iDAddress + uF.getName() + "/games" + WebTask.xml;
    }

    /*------------------------------/
     * Set Methods
     *-----------------------------*/
    /**
     * Sets the User's URL from the String Directory.
     *
     * @exception e If the URL is malformed.
     */
    private void setURL() {
        try {
            super.userURL = new URL(uDirectory);
        } catch (Exception e) {
            super.userURL = null;
        }
    }

    /**
     * Sets the Steam ID #.
     *
     * @param iD the ID # to set in the User Object.
     */
    public void setSteamID64(String iD) {
        super.steamID64 = iD;
    }

    /**
     * Sets the Steam ID non-number.
     *
     * @param iD the ID to set in the user Object.
     */
    public void setSteamID(String iD) {
        super.steamID = iD;
    }

    /**
     * Sets the address of the avatar icon.
     *
     * @param icon the address of the avatar icon in string form.
     */
    public void setAvatarIcon(String icon) {
        super.avatarIcon = icon;
    }

    /**
     * Sets the address of the User URL.
     *
     * @param url the URL to set for the userbuild object.
     */
    public void setUserURL(URL url) {
        super.userURL = url;
    }
}
