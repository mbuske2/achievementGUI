package ach;

/**
 *
 * @file DataPath.java
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 * A DataPath object that contains constants and methods to create and
 * manipulate the file save directory.
 */
public class DataPath {

    /**
     * Constant String that is the program's directory Path.
     */
    public static final String homeDir = System.getProperty("user.home")
            + System.getProperty("file.separator") + ".AchievementsGUI";
    /**
     * Constant String that is the user directory path for the program.
     */
    public static final String userDir = System.getProperty("user.home")
            + System.getProperty("file.separator") + ".AchievementsGUI"
            + System.getProperty("file.separator")
            + "users";
    /**
     * Constant String that is the game directory path for the program.
     */
    public static final String gameDir = System.getProperty("user.home")
            + System.getProperty("file.separator") + ".AchievementsGUI"
            + System.getProperty("file.separator")
            + "games";
    /**
     * Constant String that is the tail end of the directory path for the
     * achievements. This constant is to be placed after the game folder name.
     */
    public static final String achievementDir = System.getProperty("file.separator") + "achievementIcons";
    /**
     * Constant String that is the file separator for the user's system.
     */
    public static final String sep = System.getProperty("file.separator");

    /**
     * Checks the directories to make sure they are present, Creates them if
     * they are not.
     */
    public static void checkDir() {
        File t = new File(homeDir);
        if (t.isDirectory()) {
            System.out.println("Home Directory Found.");
        } else {
            System.out.println("Home Directory Not Found. Creating...");
            if (t.mkdirs()) {
                System.out.println("Home Directory Created.");
            } else {
                System.out.println("Could not create home directory: " + t.getAbsolutePath());
            }
        }
        t = new File(userDir);
        if (t.isDirectory()) {
            System.out.println("User Directory Found.");
        } else {
            System.out.println("User Directory Not Found. Creating...");
            if (t.mkdirs()) {
                System.out.println("User Directory Created.");
            } else {
                System.out.println("Could not create user directory: " + t.getAbsolutePath());
            }
        }
        t = new File(gameDir);
        if (t.isDirectory()) {
            System.out.println("Game Directory Found");
        } else {
            System.out.println("Game Directory Not Found. Creating...");
            if (t.mkdirs()) {
                System.out.println("Game Directory Created.");
            } else {
                System.out.println("Could not create game directory: " + t.getAbsolutePath());
            }
        }
    }

    /**
     * Takes in a web address and returns the name of the file at the end of the
     * address.
     *
     * @param webAddress the web address to parse.
     * @return String the Name of the file.
     */
    public static String getImageName(String webAddress) {
        String[] parsed = webAddress.split("/");
        return parsed[parsed.length - 1];
    }

    /**
     * Saves an Image From The Web In The Form Of a jpg.
     *
     * @param imageURL The URL in String Form of where the Image resides.
     * @param filePath The Path where the file is to exist.
     * @param name The name Of The File -For Debug Implementation, can be
     * anything as it has no bearing and eventually be taken out possibly.
     *
     * @return boolean returns true if successful in saving the image, false if
     * not.
     */
    public static boolean saveImage(String imageURL, String filePath, String name) {
        //At some point a check to see if the image exists might be needed?
        try {
            URL myURL = new URL(imageURL);
            File f = new File(filePath);
            BufferedImage image;
            image = ImageIO.read(myURL);
            ImageIO.write(image, "jpg", f);
            System.out.println("Image " + name + " Saved");
            return true;
        } catch (Exception e) {
            System.out.println("Cant Save Image " + name);
            System.out.println("Insert Retry Here:");
            return false;
        }
    }

    /**
     * Makes the individual game directories and saves the image files to those
     * directories.
     *
     * @param games The games in which to get the images.
     */
    //possibly return an int of the numbers of which games failed to aquire images?
    public static void getGameImages(HashMap<String, Game> games) {
        Set s = games.keySet();
        Iterator it = s.iterator();
        String key, fileName, filePath;
        Game g;
        File file;
        while (it.hasNext()) {
            key = (String) it.next();
            g = games.get(key);
            fileName = getImageName(g.getLogoPath());
            filePath = gameDir + sep + g.getFriendlyName() + sep + fileName;
            file = new File(filePath);
            makeGameDirectory(g.getFriendlyName());
            //Boolean for a reason!
            if (!file.exists()) {
                saveImage(g.getLogoPath(), filePath, key);
            }
            //THIS DOES NOTHING, Must Be Moved
            g.setGamePath(filePath);
            getAchievementImages(g.getAchievements(), g);
        }
    }

    //THOUGHT. each achievement has a game reference. Dont need to pass game?
    //can I combine the game and achievement methods to make it less complex?
    //should do this in the handler? hmmmm....
    /**
     * Makes the individual Achievement directories and saves the image files to
     * those directories.
     *
     * @param achievement The achievements for which to get the images.
     * @param g The Game the achievements are attached to.
     */
    public static void getAchievementImages(ArrayList<Achievement> achievement, Game g) {
        String nameOpen, nameClosed, fileNameClosed, fileNameOpen, filePathOpen, filePathClosed;
        Achievement a;
        File fileOpen, fileClosed;
        int size = achievement.size();
        makeAchievementDirectory(g.getFriendlyName());
        for (int i = 0; i < size; i++) {
            a = achievement.get(i);
            nameOpen = a.getName() + " - Open";
            nameClosed = a.getName() + " - Closed";
            fileNameClosed = getImageName(a.getIconClosedURLPath());
            fileNameOpen = getImageName(a.getIconOpenURLPath());
            filePathOpen = gameDir + sep + g.getFriendlyName() + sep + "achievements" + sep + fileNameOpen;
            filePathClosed = gameDir + sep + g.getFriendlyName() + sep + "achievements" + sep + fileNameClosed;
            fileOpen = new File(filePathOpen);
            fileClosed = new File(filePathClosed);

            //Boolean for a reason!
            if (!fileOpen.exists()) {
                saveImage(a.getIconOpenURLPath(), filePathOpen, nameOpen);
            }// else {
            //System.out.println("Image " + nameOpen + " Already Exists.");
            //}
            if (!fileClosed.exists()) {
                saveImage(a.getIconClosedURLPath(), filePathClosed, nameClosed);
            }// else {
            //   System.out.println("Image " + nameClosed + " Already Exists.");
            //}
            //a.setIconClosedPath(filePathClosed);
            //a.setIconOpenPath(filePathOpen);
        }

    }

    /**
     * Makes the Game Directory if it does not exist.
     *
     * @param game The name of the game the directory is to be created from.
     */
    public static boolean makeGameDirectory(String game) {
        File t = new File(gameDir + sep + game);
        if (t.isDirectory()) {
            System.out.println("Directory " + game + " already exists.");
            return false;
        } else {
            t.mkdirs();
            System.out.println("Directory " + game + " created...");
            return true;
        }
    }
    
    /**
     * Makes the Achievement Directory if it does not exist.
     *
     * @param game The name of the game the directory the achievement is to be created from.
     */
    public static boolean makeAchievementDirectory(String game) {
        File t = new File(gameDir + sep + game + sep + "achievements");
        if (t.isDirectory()) {
            System.out.println("Achievement Directory For " + game + " Already Exists.");
            return false;
        } else {
            t.mkdirs();
            System.out.println("Achievement Directory for " + game + " created...");
            return true;
        }
    }
}
