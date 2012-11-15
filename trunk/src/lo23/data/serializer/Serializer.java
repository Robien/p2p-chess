package lo23.data.serializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import lo23.data.Game;
import lo23.data.Profile;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.exceptions.NoIdException;

/**
 * This is the util class used for every serialization
 * This class has to contain static methods and attributes only
 */
public class Serializer {

    /**
     * Get all the Profile IDs stored in {@link Constants.PROFILES_PATH}
     * @return the profile IDs
     */
    static public ArrayList<String> getProfileIds() {
        File folder = new File(Constants.PROFILES_PATH);

        ArrayList<String> profileIds = new ArrayList<String>();
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                profileIds.add(listOfFiles[i].getName());
            }
        }

        return profileIds;
    }

    /**
     * This method serializes a given Profile object to the correct path
     * 
     * @param profile The object to serialize
     * 
     * @throws NoIdException  This exception is thrown if profile argument doesn't have a correct profileId
     */
    static public void saveProfile(Profile profile) throws NoIdException {
        // Checks the profileId attribute validity
        if (profile.getProfileId() == null || profile.getProfileId().equals("")) {
            throw new NoIdException("The object you're trying to serialize handle a null or empty profileId attribute.");
        }

        try {
            ObjectOutputStream out;
            out = new ObjectOutputStream(new FileOutputStream(Constants.PROFILES_PATH + profile.getProfileId() + Constants.PROFILE_SUFFIXE));
            out.writeObject(profile);
            out.close();
        } catch (IOException expt) {
            System.out.println(expt.getMessage());
            System.out.println(expt.getStackTrace());
        }
    }

    /**
     * This method tries to read a profile whom id is given as a paramater
     * 
     * @param profileId The profileId for the expected profile
     * 
     * @return Either a Profil object, either a null value if something went wrong (IOException or file not found)
     * 
     * @throws FileNotFoundException This exception is thrown when this method can't have access to an expected file
     */
    static public Profile readProfile(String profileId) throws FileNotFoundException {
        // Checks if the profileId associated file exists

        File profileFile = new File(Constants.PROFILES_PATH + profileId + Constants.PROFILE_SUFFIXE);
        if (profileFile.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(profileFile));
                Profile profile = (Profile) in.readObject();
                in.close();

                return profile;
            } catch (ClassNotFoundException expt) {
                System.out.println(expt.getMessage());
                System.out.println(expt.getStackTrace());
                return null;
            } catch (IOException expt) {
                System.out.println(expt.getMessage());
                System.out.println(expt.getStackTrace());
                return null;
            }
        } else {
            throw new FileNotFoundException("Couldn't find the file " + Constants.PROFILES_PATH + profileId + Constants.PROFILE_SUFFIXE);
        }
    }

    /**
     * This method serializes a given Game object to the correct path
     * 
     * @param game The object to serialize
     * 
     * @throws NoIdException  This exception is thrown if game argument doesn't have a correct gameId
     */
    static public void saveGame(Game game) throws NoIdException {
        // Checks the profileId attribute validity
        if (game.getGameId() < 0 || game.getGameId() > (new Date()).getTime()) {
            throw new NoIdException("The object you're trying to serialize handle an invalid gameId attribute.");
        }

        try {
            ObjectOutputStream out;
            out = new ObjectOutputStream(new FileOutputStream(Constants.GAMES_PATH + game.getGameId() + Constants.GAME_SUFFIXE));
            out.writeObject(game);
            out.close();
        } catch (IOException expt) {
            System.out.println(expt.getMessage());
            System.out.println(expt.getStackTrace());
        }
    }

    /**
     * This method tries to read a game whom id is given as a paramater
     * 
     * @param gameId The gameId for the expected profile
     * 
     * @return Either a Game object, either a null value if something went wrong (IOException or class not found)
     * 
     * @throws FileNotFoundException This exception is thrown when this method can't have access to an expected file
     */
    static public Game readGame(long gameId) throws FileNotFoundException {
        // Checks if the gameId associated file exists

        File gameFile = new File(Constants.GAMES_PATH + gameId + Constants.GAME_SUFFIXE);
        if (gameFile.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(gameFile));
                Game game = (Game) in.readObject();
                in.close();

                return game;
            } catch (ClassNotFoundException expt) {
                System.out.println(expt.getMessage());
                System.out.println(expt.getStackTrace());
                return null;
            } catch (IOException expt) {
                System.out.println(expt.getMessage());
                System.out.println(expt.getStackTrace());
                return null;
            }
        } else {
            throw new FileNotFoundException("Couldn't find the file " + Constants.GAMES_PATH + gameId + Constants.GAME_SUFFIXE);
        }
    }
}
