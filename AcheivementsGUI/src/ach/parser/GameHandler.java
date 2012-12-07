package ach.parser;

/**
 *
 * @file GameHandler.java
 * @author MB
 * @version 1.0
 * @date 11/24/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import ach.DataPath;
import ach.user.Game;
import ach.user.GameBuild;
import ach.user.User;
import java.io.File;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A Handler to Parse the Games List for Game Creation.
 */
public class GameHandler extends DefaultHandler {

    /**
     * HashMap Containing the games with achievements.
     */
    private HashMap<String, Game> games = new HashMap<>();
    /**
     * HashMap Containing the Games without Achievements.
     */
    private HashMap<String, Game> gamesWOA = new HashMap<>();
    /**
     * GameBuild Object, used for the purpose of creating a game object.
     */
    private GameBuild currentGame = new GameBuild();
    /**
     * Current Game Name.
     */
    private String currentName;
    /**
     * The Current Value in the Current Element.
     */
    private String currentValue;
    /**
     * The User Object the Games Belong To.
     */
    private User user;

    /**
     * Public Constructor that creates a Game Handler using a user object To
     * create a game object.
     *
     * @param newUser the User Object to pass to the GameHandler.
     */
    public GameHandler(User newUser) {
        user = newUser;
    }

    /**
     * Receive notification of character data inside an element and places it
     * into currentValue.
     *
     * @param ch the characters.
     * @param start The start position in the character array.
     * @param length The number of characters to use from the character array.
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue = new String(ch, start, length);
    }

    /**
     * What to do at the start of an element.
     *
     * @param uri The Namespace URI, or the empty string if the element has no
     * Namespace URI or if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if
     * Namespace processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if
     * qualified names are not available.
     * @param attributes The attributes attached to the element. If there are no
     * attributes, it shall be an empty Attributes object.
     * @throws SAXException Any SAX exception, possibly wrapping another
     * exception.
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("game")) {
            currentGame = new GameBuild();
            currentGame.setUser(user);
        }
    }

    /**
     * Receive notification of the end of an element. Places elements in the
     * Game Object.
     *
     * @param localName The local name (without prefix), or the empty string if
     * Namespace processing is not being performed.
     * @param name The qualified name (with prefix), or the empty string if
     * qualified names are not available.
     * @param uri The Namespace URI, or the empty string if the element has no
     * Namespace URI or if Namespace processing is not being performed.
     */
    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        if (name.equalsIgnoreCase("appID")) {
            currentGame.setAppID(currentValue);
        }
        if (name.equalsIgnoreCase("name")) {
            currentGame.setName(currentValue);
            currentGame.setFriendlyName(currentValue);
            currentName = currentValue;
        }
        if (name.equalsIgnoreCase("logo")) {
            currentGame.setLogoPath(currentValue);
            //String fileName = DataPath.getImageName(currentValue);
            //String filePath = DataPath.gameDir + DataPath.sep + currentGame.getFriendlyName() + DataPath.sep + fileName;
            //File file = new File(filePath);
            //if (DataPath.makeGameDirectory(currentGame.getFriendlyName())){
                //Boolean for a reason!
            //    if (!file.exists()){
           //        DataPath.saveGameImage(currentValue, filePath); 
           ///     }   
           // }
           // currentGame.setGamePath(filePath);
        }
        if (name.equalsIgnoreCase("statslink")) {
            currentGame.setStatsLink(currentValue);
        }
        if (name.equalsIgnoreCase("globalstatslink")) {
            currentGame.setGlobalStatsLink(currentValue);
        }
        if (name.equalsIgnoreCase("hoursOnRecord")) {
            currentGame.updateHoursPlayed(Double.parseDouble(currentValue));
        }
        if (name.equalsIgnoreCase("game")) {
            //currentGame.updateAchievements();
            if (currentGame.updateAchievements2(0)) {
                currentGame.setTotalAchievements(currentGame.getAchievements().size());
                currentGame.resetAchieved();
                games.put(currentName, currentGame);
            } else {
                gamesWOA.put(currentName, currentGame);
            }
        }
    }

    /**
     * Updates the User Object after parsing is finished.
     */
    @Override
    public void endDocument() throws SAXException {
        user.setGames(games, gamesWOA);
        System.out.println("Games Scraped...");
    }
}
