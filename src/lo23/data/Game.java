package lo23.data;

import lo23.data.exceptions.UndefinedGamePieceException;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import lo23.data.exceptions.IllegalMoveException;
import lo23.data.pieces.Bishop;
import lo23.data.pieces.Pawn;
import lo23.utils.Enums;
import lo23.data.pieces.GamePiece;
import lo23.data.pieces.King;
import lo23.data.pieces.Knight;
import lo23.data.pieces.Queen;
import lo23.data.pieces.Rook;
import lo23.utils.Enums.COLOR;

/**
 *
 * @author khamidou
 */
public class Game implements Serializable {

    private long gameId;
    private Date start;
    private Date end;
    private float duration;     // Time in seconds
    private ArrayList<GamePiece> pieces;
    private GamePiece[][] board;
    private Player localPlayer;
    private Player remotePlayer;
    private ArrayList<Event> events;
    private Enums.COLOR currentPlayerColor;

    public COLOR getCurrentPlayerColor() {
	return currentPlayerColor;
    }

    public void setCurrentPlayerColor(COLOR currentPlayerColor) {
	this.currentPlayerColor = currentPlayerColor;
    }

    public void swapCurrentPlayerColor() {
	if (currentPlayerColor == Enums.COLOR.BLACK) {
	    currentPlayerColor=Enums.COLOR.WHITE;
	} else {
	    currentPlayerColor=Enums.COLOR.BLACK;
	}
    }

    public Game(Player localPlayer, Player remotePlayer) {
	gameId = (new Date()).getTime();
	start = new Date();
	end = null;
	duration = 0;
	board = new GamePiece[8][8];
	pieces = new ArrayList<GamePiece>();
	events = new ArrayList<Event>();
	this.localPlayer = localPlayer;
	this.remotePlayer = remotePlayer;
	currentPlayerColor = null;
    }

    public void buildPieces() {
	// white are at the bottom.
	Player whitePlayer, blackPlayer;
	if (localPlayer.getColor() == Enums.COLOR.BLACK) {
	    blackPlayer = localPlayer;
	    whitePlayer = remotePlayer;
	} else {
	    whitePlayer = localPlayer;
	    blackPlayer = remotePlayer;
	}

	for (int i = 0; i < 8; i++) {
	    Pawn p = new Pawn(new Position(i, 1), whitePlayer, this);
	    Pawn p2 = new Pawn(new Position(i, 6), blackPlayer, this);

	    board[i][1] = p;
	    board[i][6] = p2;

	    whitePlayer.addPiece(p);
	    blackPlayer.addPiece(p2);
	    pieces.add(p);
	    pieces.add(p2);
	}

	// ROOOOOKS
	Rook r1 = new Rook(new Position(0, 0), whitePlayer, this);
	board[0][0] = r1;
	Rook r2 = new Rook(new Position(7, 0), whitePlayer, this);
	board[7][0] = r2;
	whitePlayer.addPiece(r1);
	whitePlayer.addPiece(r2);

	Rook r3 = new Rook(new Position(0, 7), blackPlayer, this);
	board[0][7] = r3;
	Rook r4 = new Rook(new Position(7, 7), blackPlayer, this);
	board[7][7] = r4;
	blackPlayer.addPiece(r3);
	blackPlayer.addPiece(r4);

	// KNIGHTS
	Knight k1 = new Knight(new Position(1, 0), whitePlayer, this);
	board[1][0] = k1;
	Knight k2 = new Knight(new Position(6, 0), whitePlayer, this);
	board[6][0] = k2;
	whitePlayer.addPiece(k1);
	whitePlayer.addPiece(k2);

	Knight k3 = new Knight(new Position(1, 7), blackPlayer, this);
	board[1][7] = k3;
	Knight k4 = new Knight(new Position(6, 7), blackPlayer, this);
	board[6][7] = k4;
	blackPlayer.addPiece(k3);
	blackPlayer.addPiece(k4);

	// Bishops
	Bishop b1 = new Bishop(new Position(2, 0), whitePlayer, this);
	board[2][0] = b1;
	Bishop b2 = new Bishop(new Position(5, 0), whitePlayer, this);
	board[5][0] = b2;
	whitePlayer.addPiece(b1);
	whitePlayer.addPiece(b2);

	Bishop b3 = new Bishop(new Position(2, 7), blackPlayer, this);
	board[2][7] = b3;
	Bishop b4 = new Bishop(new Position(5, 7), blackPlayer, this);
	board[5][7] = b4;
	blackPlayer.addPiece(b3);
	blackPlayer.addPiece(b4);

	// Queen
	Queen q1 = new Queen(new Position(3, 0), whitePlayer, this);
	board[3][0] = q1;
	whitePlayer.addPiece(q1);

	Queen q2 = new Queen(new Position(3, 7), blackPlayer, this);
	board[3][7] = q2;
	blackPlayer.addPiece(q2);

	// King
	King ki1 = new King(new Position(4, 0), whitePlayer, this);
	board[4][0] = ki1;
	whitePlayer.addPiece(ki1);

	King ki2 = new King(new Position(4, 7), blackPlayer, this);
	board[4][7] = ki2;
	blackPlayer.addPiece(ki2);

    }

    public void dumpBoard() {

	for (int y = 0; y < 8; y++) {
	    for (int x = 0; x < 8; x++) {
		GamePiece p = getPieceAtXY(x, y);
		if (p != null) {
		    System.out.print(p.toString().charAt(0));
		} else {
		    System.out.print("-");
		}
	    }

	    System.out.println();
	}
    }

    public GamePiece promotePawn(Pawn pawn, Enums.PROMOTED_PIECES_TYPES piece) throws UndefinedGamePieceException {
	Position p = pawn.getPosition();
	int px = p.getX();
	int py = p.getY();

	GamePiece np;

	switch (piece) {
	    case BISHOP:
		np = new Bishop(new Position(px, py), pawn.getOwner(), this);
		break;

	    case QUEEN:
		np = new Queen(new Position(px, py), pawn.getOwner(), this);
		break;

	    case ROOK:
		np = new Rook(new Position(px, py), pawn.getOwner(), this);
		break;

	    case KNIGHT:
		np = new Knight(new Position(px, py), pawn.getOwner(), this);
		break;
	    default:
		throw new UndefinedGamePieceException();
	}

	board[px][py] = np;
	pawn.getOwner().addPiece(np);

	return np;
    }

    /**
     * Getter for the gameId attribute
     *
     * @return The gameId attribute
     */
    public long getGameId() {
	return gameId;
    }

    /**
     * start game
     */
    public void start() {
    }

    /**
     * stop game
     */
    public void stop() {
    }

    /**
     * resume game
     */
    public void resume() {
    }

    /**
     *
     * @param x x position on the board
     * @param y y position on the board
     * @return GamePiece at (x, y)
     *
     */
    public GamePiece getPieceAtXY(int x, int y) {
	if (x >= 0 && x < 8 && y >= 0 && y < 8) {
	    return board[x][y];
	} else {
	    return null;
	}
    }

    /**
     *
     * @return the object representing the local player
     */
    public Player getLocalPlayer() {
	return localPlayer;
    }

    /**
     *
     * @return the object representing the local player
     */
    public Player getRemotePlayer() {
	return remotePlayer;
    }

    public ArrayList<Event> getEvents() {
	return events;
    }

    public void pushEvent(Event e) {
	events.add(e);
    }

    public void playMove(Move move) throws IllegalMoveException {
	//FIXME: add the rest
	int xfrom = move.from.getX();
	int yfrom = move.from.getY();
	int xto = move.to.getX();
	int yto = move.to.getY();

	if (xfrom < 0 || xfrom > 7 || yfrom < 0 || yfrom > 7
		|| xto < 0 || xto > 7 || yto < 0 || yto > 7) {
	    throw new IllegalMoveException();
	}

        GamePiece piece = board[xfrom][yfrom];
        board[xfrom][yfrom] = null;
        if(board[xto][yto] != null) {
            GamePiece eatenPiece = board[xto][yto];
            eatenPiece.setOutOfBorder(true);
        }

        board[xto][yto] = piece;

	piece.movePiece(move.to);

    }

    public ArrayList<GamePiece> getPieces() {
	return pieces;
    }

    public Date getEndDate() {
	return end;
    }

    public void setEnd() {
	this.end = new Date();
    }

    public void swapPlayer() {
	Player tmp = localPlayer;
	localPlayer = remotePlayer;
	remotePlayer = localPlayer;
    }

    public Enums.PLAYER_RESULT isWinner(String profileid) throws Exception {
	if (!(localPlayer.getPublicProfile().getProfileId().equals(profileid))
		&& !(remotePlayer.getPublicProfile().getProfileId().equals(profileid))) {
	    throw new Exception("Data.game.isWinner : Mauvais Profileid!");
	}
	if (end == null) {
	    return Enums.PLAYER_RESULT.NOT_FINISH;
	} else {
	    for (int i = events.size() - 1; i >= 0; i--) {
		if (events.get(i) instanceof Constant) {
		    Constant C = (Constant) events.get(i);
		    switch (C.getConstant()) {
			case DRAW_ACCEPTED:
			    return Enums.PLAYER_RESULT.DRAW;
			case DRAW_ASKED:
			    throw new Exception("Data.game.isWinner : La partie a une date de fin mais pas d'évenement de fin de jeu.");
			default: // OUT_OF_TIME ou SURRENDER
			    if (C.getSender().getPublicProfile().getProfileId().equals(profileid)) {
				return Enums.PLAYER_RESULT.LOST;
			    } else {
				return Enums.PLAYER_RESULT.WIN;
			    }

		    }
		}
	    }
	    throw new Exception("Data.game.isWinner : La partie a une date de fin mais pas d'évenement de fin de jeu.");
	}
    }

    public GamePiece[][] getBoard() {
	return board;
    }
}