package ach;

/**
 *
 * @file UserFile.java
 * @author MB
 * @version 1.0
 * @date 11/29/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import ach.parser.IDHandler;
import ach.parser.ProfHandler;
import ach.user.User;
import ach.user.UserBuild;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;

@SuppressWarnings("serial")
/**
 * A Class That Handles a User's File methods and properties.
 */
public class UserFile implements Serializable {
    //Should this be working with the user object?

    /**
     * A String Variable for the UserName.
     */
    private String userName;
    /**
     * The user's UserFile on disk.
     */
    private File userFile;
    /**
     * The user's UserDirectory on the disk.
     */
    private File userDirPath;
    /**
     * A String variable for the iD of the user. Used to create the file name.
     */
    private String iD;

    /**
     * Public constructor, takes a UserName and creates a UserFile Object.
     *
     * @param uN The UserName For The UserFile.
     */
    public UserFile(String uN) {
        userName = uN;
    }

    /**
     * Finds the ID64 of the User.
     *
     * @return boolean returns true if ID64 was found, false if not.
     */
    public boolean findID() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            IDHandler iDHandle = new IDHandler(this);
            saxParser.parse(new InputSource(new URL("http://steamcommunity.com/id/" + userName + "/?xml=1").openStream()),
                    iDHandle);
            System.out.println(iD);
            userFile = new File(DataPath.userDir + DataPath.sep + iD + System.getProperty("file.separator") + iD + ".usr");
            return true;
        } catch (Exception e) {
            System.out.println("Could not find User ID");
            return false;
        }
    }

    /**
     * Returns true if the userFile exists, false if it does not.
     *
     * @return boolean true if the userFile exists, false if it does not.
     */
    public boolean exists() {
        if (userFile.exists()) {
            return true;
        }
        return false;
    }

    //this may not be needed, check IDhandler
    /**
     * Sets the iD Variable for the UserFile Object.
     *
     * @param newID The New Id to set.
     */
    public void setID(String newID) {
        iD = newID;
    }

    /**
     * Creates to see of the user's personal directory exists, creates it if it
     * doesn't.
     */
    public void checkUserDirectory() {
        userDirPath = new File(DataPath.userDir + DataPath.sep + iD);
        if (userDirPath.isDirectory()) {
            System.out.println("User Directory " + iD + " exists.");
        } else {
            System.out.println("User Directory " + iD + " does not exist. Creating...");
            if (userDirPath.mkdirs()) {
                System.out.println("User Directory " + iD + " Created.");
            } else {
                System.out.println("Could not create user directory: " + userDirPath.getAbsolutePath());
            }

        }
    }

    /**
     * Checks to see if the user's file exists, creates it if it doesn't.
     */
    public void checkUserFile() {
        //userFile = new File(userDirPath.getAbsolutePath() + iD + ".usr");
        if (userFile.isFile()) {
            System.out.println("User file " + iD + ".usr exists.");
        } else {
            System.out.println("User file " + iD + ".usr does not exist. Creating...");
            try {
                userFile.createNewFile();
                System.out.println("User file " + iD + ".usr Created.");
            } catch (IOException ex) {
                System.out.println("Could not create user file: " + iD + ".usr");
            }
        }
    }

    /**
     * Loads the User Object and returns it.
     *
     * @return User The User Object that was loaded from file.
     */
    public User loadFile(User user) {
        try {
            FileInputStream fileIn;
            fileIn = new FileInputStream(userFile);
            ObjectInputStream in;
            in = new ObjectInputStream(fileIn);
            user = (User) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("User Loaded.");
        } catch (Exception e) {
            System.out.println("Could not load User.");
        }

        return user;
    }

    /**
     * Saves the User Object to File.
     *
     * @param user The User Object to save to File.
     */
    public void saveFile(User user) {
        try {
            FileOutputStream fileOut;
            fileOut = new FileOutputStream(userFile);
            ObjectOutputStream out;
            out = new ObjectOutputStream(fileOut);
            out.writeObject(user);
            out.close();
            fileOut.close();
            System.out.println("User Saved");
        } catch (IOException f) {
            //System.out.println("Could Not Save BST! ERROR!");
            System.out.println("Could Not Save User");
        }
    }

    /**
     * Returns the UserFile iD.
     *
     * @return String The UserFile iD.
     */
    public String getID() {
        return iD;
    }

    /**
     * Returns the userName associated with this UserFile.
     *
     * @return String The Username associated with this UserFile.
     */
    public String getName() {
        return userName;
    }

    /**
     * The First time a user is created, this is called to create and populate
     * the user data, which is then returned.
     *
     * @return User The created and populated User.
     */
    public User populateUserFirstTime() {
        try {
            System.out.println("Working...");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserBuild user2 = new UserBuild(this);
            ProfHandler profileHandler = new ProfHandler(user2);
            user2.setUserURL(new URL(user2.getDirectory()));
            saxParser.parse(new InputSource(user2.getURL().openStream()), profileHandler);
            //dangerous usage, could throw exception if user is not populated from parsing.
            //try to find a method to ignore it if this is the case?
            user2.setAvatarPath(saveProfileImage(user2.getAvatarIcon(), iD));
            user2.updateUserURL();
            user2.loadGames();
            user2.setGameKeys();
            user2.setTotalsAch();

            System.out.println("\nUser Details: ");
            System.out.println(user2.toString());
            return user2;
        } catch (Exception e) {
            System.out.println("Could not Create User");
            return null;
        }
    }

    /**
     * Gets the profile image for the user and saves it to disk and returns its
     * name.
     *
     * @param imageURL the URL as a string of the profile image.
     * @param iD the user ID.
     * @return String the name of the image file.
     */
    private static String saveProfileImage(String imageURL, String iD) {
        //At some point a check to see if the image exists might be needed?
        try {
            String imagePath = DataPath.getImageName(imageURL);
            URL myURL = new URL(imageURL);
            File f = new File(DataPath.userDir + DataPath.sep + iD + DataPath.sep + imagePath);
            BufferedImage image;
            image = ImageIO.read(myURL);
            ImageIO.write(image, "jpg", f);
            System.out.println("Profile Image Saved");
            return f.getAbsolutePath();
        } catch (Exception e) {
            System.out.println("Cant Save Image Of " + iD);
            return "Not Found";
        }
    }
}
