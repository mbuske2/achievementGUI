package ach.parser;

/**
 *
 * @file ProfHandler.java
 * @author MB
 * @version 1.0
 * @date 11/14/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import ach.user.UserBuild;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A Handler To Parse A Player Profile For Creation.
 */
public class ProfHandler extends DefaultHandler {

    /**
     * A UserBuild object to create and populate.
     */
    UserBuild user;
    /**
     * The Current value of the element.
     */
    private String currentValue;
    /**
     * flag to make sure that the avatar image address being pulled is the
     * profile avatar.
     */
    private boolean full;

    /**
     * Constructs an Profile Handler Object.
     *
     * @param u A UserBuild object to update.
     */
    public ProfHandler(UserBuild u) {
        user = u;
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
        // if (qName.equalsIgnoreCase("steamID64")){
        //     GXML.getUser().setSteamID(attributes.getValue("steamID64"));
        // }
    }

    /**
     * Receive notification of the end of an element. Places elements in the
     * User Object.
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
        //if (name.equalsIgnoreCase("steamID64")) {
        //    user.setSteamID64(currentValue);
        //}
        if (name.equalsIgnoreCase("steamID")) {
            user.setSteamID(currentValue);
        }
        if (name.equalsIgnoreCase("avatarMedium")) {
            if (full == false) {
                user.setAvatarIcon(currentValue);
                full = true;
            }

        }
    }

    //Does Nothing Right Now- Move messages to the update window screen.
    @Override
    public void endDocument() throws SAXException {
        System.out.println("Profile Scraped...");
        System.out.println("Loading Achievements...");
    }
}
