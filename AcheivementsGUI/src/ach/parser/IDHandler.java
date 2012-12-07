package ach.parser;

/**
 *
 * @file IDHandler.java
 * @author MB
 * @version 1.0
 * @date 11/12/2012
 *
 */
/**
 * PROJECT IMPORTS.
 */
import ach.UserFile;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A handler to parse for an ID Number on Steam for File Purposes.
 */
public class IDHandler extends DefaultHandler {

    /**
     * The current Value of the element.
     */
    private String currentValue;
    /**
     * The User ID.
     */
    private String iD;
    /**
     * The UserFile Object.
     */
    private UserFile userFile;

    /**
     * Constructs an ID Handler Object.
     *
     * @param uF A UserFile to update.
     */
    public IDHandler(UserFile uF) {
        userFile = uF;
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
        if (name.equalsIgnoreCase("steamID64")) {
            iD = currentValue;
            System.out.println("Id Found");
        }
    }

    /**
     * Updates the UserFile after parsing is finished.
     */
    @Override
    public void endDocument() throws SAXException {
        userFile.setID(iD);
    }
}
