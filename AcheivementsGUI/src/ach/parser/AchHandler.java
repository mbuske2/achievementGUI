package ach.parser;

/**
 *
 * @file AchHandler.java
 * @author MB
 * @version 1.0
 * @date 11/12/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import ach.DataPath;
import ach.user.Achievement;
import ach.user.Game;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A Class that parses an XML achievement from steam for creation purposes.
 */
public class AchHandler extends DefaultHandler {

    /**
     * An ArrayList of temp Achievement Objects.
     */
    private ArrayList<Achievement> achievements = new ArrayList<>();
    /**
     * The Current Achievement.
     */
    private Achievement currentA;
    /**
     * The Current Value.
     */
    private String currentValue;
    /**
     * The Current Game Name.
     */
    private String gameName;
    /**
     * The Current Game Object.
     */
    private Game game;
    /**
     * Boolean flag to make sure the parser is in the achievement element to
     * grab things like name when there is the possibility of two tags with the
     * same name.
     */
    private boolean isAchievement = false;

    /**
     * Constructs a AchHandler for a game.
     *
     * @param g The game this AchHandler is connected to.
     */
    public AchHandler(Game g) {
        game = g;
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
        if (qName.equalsIgnoreCase("achievement")) {
            //surround with try statement
            int closed = Integer.parseInt(attributes.getValue("closed"));
            Achievement a = new Achievement(closed);
            currentA = a;
            currentA.setGame(game);
            isAchievement = true;

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

        if (name.equalsIgnoreCase("gameName")) {
            gameName = currentValue;
        }
        if (isAchievement) {
            if (name.equalsIgnoreCase("iconClosed")) {
                currentA.setIconOpenURLPath(currentValue);
            }
            if (name.equalsIgnoreCase("iconOpen")) {
                currentA.setIconClosedURLPath(currentValue);
            }
            if (name.equalsIgnoreCase("name")) {
                currentA.setName(currentValue);
            }
            if (name.equalsIgnoreCase("description")) {
                currentA.setDescription(currentValue);
            }
            if (name.equalsIgnoreCase("unlockTimeStamp")){
                if(currentA.convertNSetTimeStamp(currentValue)){
                    currentA.setTimeStamp(currentA.getUnixTimeStamp());
                }
            }
            if (name.equalsIgnoreCase("achievement")) {
                String gName = currentA.getGame().getFriendlyName();
                currentA.setIconClosedPath(DataPath.gameDir + DataPath.sep + gName
                        + DataPath.sep + "achievements" + DataPath.sep + DataPath.getImageName(currentA.getIconClosedURLPath()));
                currentA.setIconOpenPath(DataPath.gameDir + DataPath.sep + gName
                        + DataPath.sep + "achievements" + DataPath.sep + DataPath.getImageName(currentA.getIconOpenURLPath()));
                achievements.add(currentA);
            }
        }
    }

    /**
     * Updates the game achievements after parsing is finished.
     */
    @Override
    public void endDocument() throws SAXException {
        game.setAchievements(achievements);
        System.out.println("Achievements Set For " + gameName);
    }
}
