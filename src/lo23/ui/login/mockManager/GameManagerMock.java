/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.ui.login.mockManager;

import java.util.ArrayList;
import lo23.data.Constant;
import lo23.data.Event;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.Message;
import lo23.data.Move;
import lo23.data.Position;
import lo23.data.PublicProfile;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.exceptions.NoIdException;
import lo23.data.managers.GameManagerInterface;
import lo23.data.pieces.GamePiece;
import lo23.utils.Enums.CONSTANT_TYPE;

/**
 *
 * @author lo23a009
 */
public class GameManagerMock implements GameManagerInterface{

    public void save() throws NoIdException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Game load(String gameId) throws FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Move createMove(Position to, GamePiece piece) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendMove(Move move) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void playMove(Move move) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Message createMessage(String content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Event> getHistory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyChatMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Game createGame(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Constant createConstant(CONSTANT_TYPE constant) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendConstant(Constant constant) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyConstantMessage(Constant constant) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveConstant(Constant constant) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyGameEnded() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Game notifiyGameStarted(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Game> getListStopGames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Game> getListStartGames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Game getCurrentGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyGameStarted(PublicProfile userProfile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyMovement(Move move) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
