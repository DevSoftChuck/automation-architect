package Utils;

import java.awt.Toolkit;
import java.awt.HeadlessException;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


public class Utils {

    /* --------------------------------------------- MANIPULATE STRINGS --------------------------------------------- */

    /** Method to get rid of the last characters from a string.
     * @param str = Complete string
     * @param charactersQty = Amount of letters to get rid of.
     * @return the string without the amount of characters you wanted, starting from the end.
     * **/
    public static String getRidOfLastLetters(String str, int charactersQty) {
        return str.substring(0, str.length() - charactersQty);
    }

    public static String getRidOfFirstLetters(String str, int charactersQty) {
        return str.substring(charactersQty);
    }

    /** Method that returns the text you currently have on the clipboard **/
    public static String getClipboardText() {
        try {
            return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (HeadlessException | UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /* ------------------------------------------ MANIPULATE STRINGS - END ------------------------------------------ */

}
