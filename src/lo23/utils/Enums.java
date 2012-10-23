package lo23.utils;

/**
 * This class contains every enumerations used in this project
 */
public class Enums
{
    /**
     * Enumeration containing possible profile's status values
     */
    public enum STATUS {
        OFFLINE,
        CONNECTED,
        INGAME};
    
    /**
     * Possible color values that a player can take during a game
     */
    public enum COLOR {
        BLACK,
        WHITE};
    
    /**
     * Value accepted and used by the Constant class
     */
    public enum CONSTANT_TYPE {
        DRAW_ASKED,
        DRAW_ACCEPTED,
        SURRENDER,
        OUT_OF_TIME};
}
