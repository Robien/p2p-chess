/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.utils;

import java.util.UUID;

/**
 *
 * @author Esteban
 */
public class RandomUUID {
    public static String RandomStringUUID() {
        // Creating a random UUID (Universally unique identifier).
        //
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }
}
