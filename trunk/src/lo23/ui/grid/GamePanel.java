/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lo23.data.ApplicationModel;
import lo23.data.Constant;
import lo23.data.Game;
import lo23.data.Move;
import lo23.data.Player;
import lo23.data.Position;
import lo23.data.exceptions.IllegalMoveException;
import lo23.data.exceptions.UndefinedGamePieceException;
import lo23.data.pieces.GamePiece;
import lo23.data.pieces.Pawn;
import lo23.utils.Enums;
import lo23.utils.Enums.COLOR;
import lo23.utils.ResourceManager;



@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
    private ApplicationModel myModel;
    private Game game;
    private GamePiece[][] board;
    private EventListener eventListener;
    private BorderLayout borderLayout;
    private GridBagLayout gameBoard = new GridBagLayout();
    private GridBagConstraints constraints = new GridBagConstraints();
      
    
    private ImageIcon squareBorder = new ImageIcon(ResourceManager.getInstance().getResource("SquareBorder.png"));
    private ImageIcon filledSquare = new ImageIcon(ResourceManager.getInstance().getResource("PossibleSquare.png"));
    private ImageIcon imageCaseB = new ImageIcon(ResourceManager.getInstance().getResource("caseB.JPG"));
    private ImageIcon imageCaseN = new ImageIcon(ResourceManager.getInstance().getResource("caseN.JPG"));
    private ImageIcon preSelection = new ImageIcon(ResourceManager.getInstance().getResource("preSelection.png"));
    
	//hashmap with JLabel and their positions
    private HashMap<Position, JLabel> listOfPiece = new HashMap<Position, JLabel>();
    private HashMap<Position, JLabel> listOfSelection = new HashMap<Position, JLabel>();
    private HashMap<Position, JLabel> listOfSquare = new HashMap<Position, JLabel>();
    private HashMap<Position, JLabel> listOfPreSelection = new HashMap<Position, JLabel>();
    private ArrayList<Position> listOfPossibleMove = new ArrayList<Position>();
//    private HashMap<Position, JLabel> listOfCase = new HashMap<Position, JLabel>();
    
    //ate piece
    private ArrayList<JLabel> blackAtePieces = new ArrayList<JLabel>();
    private ArrayList<JLabel> whiteAtePieces = new ArrayList<JLabel>();

    private boolean isFormerSelectionExist = false;
    private JLabel formerPieceSelected;
    private Position formerPositionSelected;
    private Position formerPreSelection;
    private Position newPreSelection;
    private boolean isFormerPreSelectionExist = false;
    
    //local player color
    COLOR playerColor; 
    boolean secondClickIsAllowed = false;
    
    
    //sound
    boolean is_eat;
    boolean is_move;
    boolean end_party;
    
   //playable game
   public boolean isPlayPossible;
    
    private PiecesBox localBox;
    private PiecesBox remoteBox;
    
    public GamePanel(ApplicationModel model){
        super();
        myModel = model; 
        eventListener = new EventListener(this, myModel);
        localBox=null;
        remoteBox=null;
        
        // TO DO : Instancier PlayerColor en fonction du dernier évènement.
    }
 
    public GamePanel(ApplicationModel model, Game gm, PiecesBox local, PiecesBox remote) {
        super();
        myModel = model;
        eventListener = new EventListener(this, myModel);
        game = gm;
        board = gm.getBoard();
        playerColor = COLOR.WHITE;
      
        localBox=local;
        remoteBox=remote;
        
        build();
    }
    
    public GamePanel(ApplicationModel model, Game gm, COLOR color) {
        super();
        myModel = model;
        eventListener = new EventListener(this, myModel);
        game = gm;
        board = gm.getBoard();
        playerColor = color;


        build();
    }
    
        
    public void launchGame(){
        // Launch a game and build the board with events or not
        	buildBoard();
                isPlayPossible = true;
                playerColor = myModel.getGManager().getCurrentGame().getCurrentPlayerColor();
    }
    
    public void launchReviewGame(){
         buildReviewBoard();
         isPlayPossible = false;
    }

    private void buildReviewBoard(){
           buildBoard();
                      
           JButton nextButton = new JButton("Next");
           nextButton.setSize(10, 10);
           constraints.gridx = 0;
           constraints.gridy = 8;
           add(nextButton, constraints, 0);
           
           nextButton.addActionListener(new java.awt.event.ActionListener() {
               @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed();
            }
        });
           
    }
    
    private void nextActionPerformed(){
       // myModel.getGManager().getCurrentGame().getEvents(); Tableau d'évènements
      // Pour jouer un coup (vers l'avant) : utiliser updateBoard();
      // Pour jouer en arrière (vers l'arrière) : inverser le Move (from devient to et inversement)
      // utiliser updateBoard.
    }
    
    private void build() {    
    	//test
//    	buildPieces();
    	
        setPreferredSize(new Dimension(GridConstants.SQUARE_SIZE * 8, GridConstants.SQUARE_SIZE * 8));
        setLayout(gameBoard);
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    
        //listener 
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                    //left button, main button, or mouseEvent.BUTTON1 == mouseEvent.getButton()
                    int x = mouseEvent.getX();
                    int y = mouseEvent.getY();
                    int xSquare = (x - x % GridConstants.SQUARE_SIZE) / GridConstants.SQUARE_SIZE;
                    int ySquare = (y - y % GridConstants.SQUARE_SIZE) / GridConstants.SQUARE_SIZE;
                    receiveSelectedCase(xSquare,ySquare); 
                }
            }
        });
        
        addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent mouseEvent) {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();
                int xSquare = (x - x % GridConstants.SQUARE_SIZE) / GridConstants.SQUARE_SIZE;
                int ySquare = (y - y % GridConstants.SQUARE_SIZE) / GridConstants.SQUARE_SIZE;
                
                formerPreSelection = newPreSelection;
                newPreSelection = new Position(xSquare,ySquare);
               
                GamePiece currentPiece = null;
                if(myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE){
                    currentPiece = game.getPieceAtXY(newPreSelection.getWX(), newPreSelection.getWY());
                } else {
                    currentPiece = game.getPieceAtXY(newPreSelection.getBX(), newPreSelection.getBY());
                }
                
                
                if (isCaseSelectionable(newPreSelection, currentPiece) || amongListOfPossiblesMoves(newPreSelection)){
                	
                	if (isFormerPreSelectionExist){
	                	listOfPreSelection.get(formerPreSelection).setVisible(false);
	                	listOfPreSelection.get(newPreSelection).setVisible(true);
	                } else {
	                	listOfPreSelection.get(newPreSelection).setVisible(true);
	                	isFormerPreSelectionExist = true;
	                }
                } else {
                    if(formerPreSelection != null){
                	listOfPreSelection.get(formerPreSelection).setVisible(false);
                    }
                }   
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			}
        	
        });
          
        addPropertyChangeListener(eventListener);
       end_party=false;
       // launchGame(); //comment for integration
    }
    
    private void receiveSelectedCase(int x, int y){ 
        if (isPlayPossible) {
            System.out.println(myModel.getGManager().getCurrentGame().getBoard());

            constraints.insets = new Insets(0, 0, 0, 0);
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.gridx = x;
            constraints.gridy = y;

            Position newSelection = new Position(x, y);
            GamePiece currentPiece = null;

            if (myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE) {
                currentPiece = game.getPieceAtXY(newSelection.getWX(), newSelection.getWY());
            } else {
                currentPiece = game.getPieceAtXY(newSelection.getBX(), newSelection.getBY());
            }

            if (isCaseSelectionable(newSelection, currentPiece)) {
                receiveFirstClick(newSelection, currentPiece);
            } else if (isFormerSelectionExist) {
                receiveSecondClick(newSelection, currentPiece);
            }
        }
    }
    
    private void receiveFirstClick(Position newSelection, GamePiece currentPiece){
    	//if a case is already selected, the former selection disapears
        if (isFormerSelectionExist) {
        	hidePossibleCase();
            listOfSelection.get(formerPositionSelected).setVisible(false);
            repaint();
        }
        isFormerSelectionExist = true;
        	
        //save the current position
        formerPieceSelected = listOfPiece.get(newSelection);
        formerPositionSelected = newSelection;
        listOfSelection.get(formerPositionSelected).setVisible(true);
  
        showPossiblesMoves(currentPiece);
    }
    
    private void receiveSecondClick(Position newSelection, GamePiece currentPiece) {
        //if a case is already selected, the former selection disapears
        if (isFormerSelectionExist) {
            listOfSelection.get(formerPositionSelected).setVisible(false);
        }
        isFormerSelectionExist = true;
        
        GamePiece tempPiece = currentPiece;
        
        if (myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE) {
	        	
	        for (Position p : listOfPossibleMove){
	        	//if selected position is playable
	        	if (p.getX() == newSelection.getX() && p.getY() == 7 - newSelection.getY()) {
	        		tempPiece = game.getPieceAtXY(newSelection.getX(), 7 - newSelection.getY());
	        		//if a piece is already here
	        		if (tempPiece != null) {
	        			if (tempPiece.getOwner().getColor() != playerColor) {
	        				eatPiece(newSelection);
	        			}
	        			//Eat Sound
	        			is_eat = true;
	        			is_move = false;
	        		} else {
	        			is_eat =false;
	        			is_move = true;
	        		}
	        		GamePiece formerPiece = game.getPieceAtXY(formerPositionSelected.getWX(), formerPositionSelected.getWY());
	        		Move move = myModel.getGManager().createMove(new Position(newSelection.getX(), 7 - newSelection.getY()), formerPiece);
	        		myModel.getGManager().playMove(move);
                    myModel.getGManager().sendMove(move);
	        	}
	        }
        } else {
        	
        	for (Position p : listOfPossibleMove){
	        	//if selected position is playable
	        	if (p.getX() == 7 - newSelection.getX() && p.getY() == newSelection.getY()) {
	        		tempPiece = game.getPieceAtXY(7 - newSelection.getX(), newSelection.getY());
	        		//if a piece is already here
	        		if (tempPiece != null) {
	        			if (tempPiece.getOwner().getColor() != playerColor) {
	        				eatPiece(newSelection);
	        			}
	        			//Eat Sound
	        			is_eat = true;
	        			is_move = false;
	        		} else {
	        			is_eat =false;
	        			is_move = true;
	        		}
	        		GamePiece formerPiece = game.getPieceAtXY(formerPositionSelected.getBX(), formerPositionSelected.getBY());
	        		Move move = myModel.getGManager().createMove(new Position(7 - newSelection.getX(), newSelection.getY()), formerPiece);
	        		myModel.getGManager().playMove(move);
                    myModel.getGManager().sendMove(move);
	        	}
	        }
        }
        hidePossibleCase();
       // play_sound(tempPiece);
        
        //is Pawn Top
        if (tempPiece != null && tempPiece.isPawnTop() && !tempPiece.isOutOfBorder()) {
            Enums.PROMOTED_PIECES_TYPES piece = PawnChangeMessage.display(tempPiece);
            try {
                //create new Piece
            	Pawn pawn = (Pawn) tempPiece;
                game.promotePawn(pawn,piece);
            } catch (UndefinedGamePieceException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (playerColor == COLOR.BLACK) 
            {
                 switch(piece)
                 {
                     case KNIGHT:  insertPiece(newSelection, "KW.png") ;    
                                    break;
                     case BISHOP:  insertPiece(newSelection, "BW.png") ;    
                                    break;
                     case QUEEN:  insertPiece(newSelection, "QW.png") ;    
                                    break;
                     case ROOK:  insertPiece(newSelection, "TW.png") ;    
                                    break;
                     default : insertPiece(newSelection, "QW.png"); 
                 }
            }
            else
            {
                 switch(piece)
                 {
                     case KNIGHT:  insertPiece(newSelection, "KB.png") ;    
                                    break;
                     case BISHOP:  insertPiece(newSelection, "BB.png") ;    
                                    break;
                     case QUEEN:  insertPiece(newSelection, "QB.png") ;    
                                    break;
                     case ROOK:  insertPiece(newSelection, "TB.png") ;    
                                    break;
                     default : insertPiece(newSelection, "QB.png"); 
                 }   
            } 
        }
    }
    
    
    private void insertPiece(Position p, String imagePath) {
        listOfPiece.get(p).setVisible(false);
        listOfPiece.remove(p);
        
        if (myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE) {
            constraints.gridx = p.getWX();
            constraints.gridy = 7 - p.getWY();            

            ImageIcon image = new ImageIcon(ResourceManager.getInstance().getResource(imagePath));
            JLabel WLabel = new JLabel("", image, JLabel.CENTER);
            add(WLabel, constraints, 2);
            listOfPiece.put(p, WLabel);
        } else {
            constraints.gridx = p.getBX();
            constraints.gridy = 7 - p.getBY();            

            ImageIcon image = new ImageIcon(ResourceManager.getInstance().getResource(imagePath));
            JLabel WLabel = new JLabel("", image, JLabel.CENTER);
            add(WLabel, constraints, 2);
            listOfPiece.put(p, WLabel);
        }
    }


    public void updateBoardWithoutChangeColor(Move move)
    {
          // Update board after player play a move
//    	System.out.println("position d�part grid : " + move.getFrom().toString());
//    	System.out.println("position arriv�e grid : " + move.getTo().toString());

        Position positionFrom = null;

        if (myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE) {
	        constraints.gridx = move.getTo().getWX();
	        constraints.gridy = move.getTo().getWY();
	        positionFrom = new Position(move.getFrom().getWX(), move.getFrom().getWY());
        } else {
        	constraints.gridx = move.getTo().getBX();
	        constraints.gridy = move.getTo().getBY();
	        positionFrom = new Position(move.getFrom().getBX(), move.getFrom().getBY());
        }

        JLabel currentPiece = listOfPiece.get(positionFrom);
        GamePiece currentGamePiece = myModel.getGManager().getCurrentGame().getPieceAtXY(move.getFrom().getX(), move.getFrom().getY());
        GamePiece tempPiece = myModel.getGManager().getCurrentGame().getPieceAtXY(move.getTo().getX(), move.getTo().getY());

        System.out.println("TEMPIECE : " + tempPiece.toString());

        if (myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE) {
            if(tempPiece != null){
                if (tempPiece.getOwner().getColor() != playerColor) {

                	System.out.println("mange");

                    updateEatPiece(new Position(move.getTo().getX(), 7 - move.getTo().getY()));
                }
            }
        } else {
            if(tempPiece != null){
                if (tempPiece.getOwner().getColor() != playerColor) {

                	System.out.println("mange");

                    updateEatPiece(new Position(7 - move.getTo().getX(), move.getTo().getY()));
                }
            }
        }
        

        System.out.println("listOfPiece :" + listOfPiece.get(positionFrom));
        listOfPiece.remove(positionFrom);
        
        if(myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE){
            listOfPiece.put(new Position(move.getTo().getWX(), move.getTo().getWY()), currentPiece);
        } else {
            listOfPiece.put(new Position(move.getTo().getBX(), move.getTo().getBY()), currentPiece);
        }
        
        System.out.println("CurrentPiece :" + currentPiece);
        add(currentPiece, constraints, 0);
        play_sound(currentGamePiece);
        if (myModel.getGManager().getCurrentGame().getLocalPlayer().isCheckAndMat()) {
            // End of game
            end_party=true;
            endOfGame(myModel.getGManager().getCurrentGame().getRemotePlayer());
        } else if (myModel.getGManager().getCurrentGame().getRemotePlayer().isCheckAndMat()) {
        	end_party=true;
            endOfGame(myModel.getGManager().getCurrentGame().getLocalPlayer());
        }

        	repaint();
        	revalidate();
    }

    public void majDataBoard(Move move) throws IllegalMoveException
    {
        game.playMove(move);
    }


    public void updateBoard(Move move){

        updateBoardWithoutChangeColor(move);

        if (playerColor == COLOR.WHITE) {
            playerColor = COLOR.BLACK;
//            System.out.println("1" + playerColor);
        } else if (playerColor == COLOR.BLACK) {
            playerColor = COLOR.WHITE;
//            System.out.println("2" + playerColor);
        }
        
        myModel.getGManager().getCurrentGame().dumpBoard();
        
        // TO DO : Check end of game
        //System.out.println(listOfPiece);  
        
        
        
    }
    
    public void updateReviewBoard(Move move){
        // Update board with a move extract from Review mod
//        System.out.println("position départ grid : " + move.getFrom().toString());
//    	System.out.println("position arrivée grid : " + move.getTo().toString());
        
   
        Position positionFrom = null;

        if (myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE) {
            constraints.gridx = move.getTo().getWX();
            constraints.gridy = move.getTo().getWY();
            positionFrom = new Position(move.getFrom().getWX(), move.getFrom().getWY());
        } else {
            constraints.gridx = move.getTo().getBX();
            constraints.gridy = move.getTo().getBY();
            positionFrom = new Position(move.getFrom().getBX(), move.getFrom().getBY());
        }

        JLabel currentPiece = listOfPiece.get(positionFrom);
        listOfPiece.remove(positionFrom);
        if (myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE) {
            listOfPiece.put(new Position(move.getTo().getWX(), move.getTo().getWY()), currentPiece);
        } else {
            listOfPiece.put(new Position(move.getTo().getBX(), move.getTo().getBY()), currentPiece);
        }
        add(currentPiece, constraints, 0);
         
         //System.out.println(move);
    }
    
    public void eatPiece(Position p){
    	listOfPiece.get(p).setVisible(false);
    	JLabel atePiece = listOfPiece.remove(p);

    	if (myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE) {
    		blackAtePieces.add(atePiece);
    	} else {
            whiteAtePieces.add(atePiece);
        }
        localBox.updateBox(this);
        remoteBox.updateBox(this);
    }
    
    public void updateEatPiece(Position p){
    	listOfPiece.get(p).setVisible(false);
    	JLabel atePiece = listOfPiece.remove(p);

    	if (myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.BLACK) {
    		blackAtePieces.add(atePiece);
    	} else {
            whiteAtePieces.add(atePiece);
        }
        localBox.updateBox(this);
        remoteBox.updateBox(this);
    }
    
    private boolean amongListOfPossiblesMoves (Position p){
		for (int i=0; i<listOfPossibleMove.size(); i++){
                    if(myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE){
                        if (listOfPossibleMove.get(i).getX() == p.getWX() && listOfPossibleMove.get(i).getY() == p.getWY()){
				return true;
			}
                    } else {
                        if (listOfPossibleMove.get(i).getX() == p.getBX() && listOfPossibleMove.get(i).getY() == p.getBY()){
				return true;
			}
                    }
			
		}
    	return false;
    }
    
    private boolean isCaseSelectionable(Position newSelection, GamePiece currentPiece){
        // Check if the case is selectionable with pieces color...
    	//commenter le dernier test sur la couleur du joueur pour pouvoir joueur les noirs!
    	if (listOfPiece.get(newSelection) != null) {
    		if(currentPiece != null && !currentPiece.getPossibleMovesWithCheck().isEmpty()
    				&& currentPiece.getOwner().getColor() == playerColor
    				&& game.getLocalPlayer().getColor() == playerColor) {
    			return true;
    		}
    		return false;
    	} else {
    		return false;
    	}
    }

    private void showPossiblesMoves(GamePiece piece) {
        colorPossibleCase(piece.getPossibleMovesWithCheck());
    }

    private void colorPossibleCase(List<Position> positions){
        for (Position p : positions){
           if(myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE){
               listOfSquare.get(new Position(p.getWX(),p.getWY())).setVisible(true);
           } else {
               listOfSquare.get(new Position(p.getBX(),p.getBY())).setVisible(true);
           }
        	
           listOfPossibleMove.add(p);
        }
    }
    
    private void hidePossibleCase(){
    	listOfPossibleMove.clear();
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                listOfSquare.get(new Position(i,j)).setVisible(false);
            }
        }
    }
    
    public ArrayList<JLabel> getWhiteAtePieces(){
    	return whiteAtePieces;
    }
    
    public ArrayList<JLabel> getBlackAtePieces(){
    	return blackAtePieces;
    }
    
    public void buildBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	
            	constraints.gridx = i;
                constraints.gridy = j;
                
                JLabel preSelectionLabel = new JLabel("", preSelection, JLabel.CENTER);
                add(preSelectionLabel, constraints, 0);
                listOfPreSelection.put(new Position(i,j), preSelectionLabel);
                preSelectionLabel.setVisible(false);
                
                JLabel currentSelection = new JLabel("", squareBorder, JLabel.CENTER);
                add(currentSelection, constraints, 1);
                listOfSelection.put(new Position(i,j), currentSelection);
                currentSelection.setVisible(false);
                
                JLabel possibleCase = new JLabel("", filledSquare, JLabel.CENTER);
                add(possibleCase, constraints, 1);
                listOfSquare.put(new Position(i,j), possibleCase);
                possibleCase.setVisible(false);
            	 
                if ((i + j) % 2 != 0) {
                    JLabel labelCaseN = new JLabel("", imageCaseN, JLabel.CENTER);
                    add(labelCaseN, constraints, -1);
                } else {
                    JLabel labelCaseB = new JLabel("", imageCaseB, JLabel.CENTER);
                    add(labelCaseB, constraints, -1);
                }
                
                GamePiece tempPiece = board[i][j];
                if (tempPiece != null) {
                	
	            	Position tempPosition = tempPiece.getPosition();
	            	String tempName = tempPiece.getClass().getName();
                        //System.out.println("TempName = "+tempName);
	            	COLOR tempColor = tempPiece.getOwner().getColor();
                
                	if (myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE) {
	            		
	            		if (tempName.equals("lo23.data.pieces.Pawn") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon image = new ImageIcon(ResourceManager.getInstance().getResource("PW.png"));
	                        JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
	                        add(pawnLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), pawnLabel);
	            			
	            		} else if (tempName.equals("lo23.data.pieces.Rook") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon tower = new ImageIcon(ResourceManager.getInstance().getResource("TW.png"));
	                        JLabel towerLabel = new JLabel("", tower, JLabel.CENTER);
	                        add(towerLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), towerLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Knight") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon knight = new ImageIcon(ResourceManager.getInstance().getResource("KW.png"));
	                        JLabel knightLabel = new JLabel("", knight, JLabel.CENTER);
	                        add(knightLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), knightLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Bishop") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon bishop = new ImageIcon(ResourceManager.getInstance().getResource("BW.png"));
	                        JLabel bishopLabel = new JLabel("", bishop, JLabel.CENTER);
	                        add(bishopLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), bishopLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Queen") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon queen = new ImageIcon(ResourceManager.getInstance().getResource("QW.png"));
	                        JLabel queenLabel = new JLabel("", queen, JLabel.CENTER);
	                        add(queenLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), queenLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.King") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon king = new ImageIcon(ResourceManager.getInstance().getResource("KKW.png"));
	                        JLabel kingLabel = new JLabel("", king, JLabel.CENTER);
	                        add(kingLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), kingLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Pawn") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon image = new ImageIcon(ResourceManager.getInstance().getResource("PB.png"));
	                        JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
	                        add(pawnLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), pawnLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Rook") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon tower = new ImageIcon(ResourceManager.getInstance().getResource("TB.png"));
	                        JLabel towerLabel = new JLabel("", tower, JLabel.CENTER);
	                        add(towerLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), towerLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Knight") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon knight = new ImageIcon(ResourceManager.getInstance().getResource("KB.png"));
	                        JLabel knightLabel = new JLabel("", knight, JLabel.CENTER);
	                        add(knightLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), knightLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Bishop") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon bishop = new ImageIcon(ResourceManager.getInstance().getResource("BB.png"));
	                        JLabel bishopLabel = new JLabel("", bishop, JLabel.CENTER);
	                        add(bishopLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), bishopLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Queen") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon queen = new ImageIcon(ResourceManager.getInstance().getResource("QB.png"));
	                        JLabel queenLabel = new JLabel("", queen, JLabel.CENTER);
	                        add(queenLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), queenLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.King") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon king = new ImageIcon(ResourceManager.getInstance().getResource("KKB.png"));
	                        JLabel kingLabel = new JLabel("", king, JLabel.CENTER);
	                        add(kingLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), kingLabel);
	                        
	            		}
	            	} else {
		            		
		            	if (tempName.equals("lo23.data.pieces.Pawn") && tempColor == COLOR.WHITE){
		            			
		            		constraints.gridx = 7 - tempPosition.getX();
		                    constraints.gridy = tempPosition.getY(); 
		                    ImageIcon image = new ImageIcon(ResourceManager.getInstance().getResource("PW.png"));
		                    JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
		                    add(pawnLabel, constraints, 2);
		                    listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), pawnLabel);
		            			
		            	} else if (tempName.equals("lo23.data.pieces.Rook") && tempColor == COLOR.WHITE){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon tower = new ImageIcon(ResourceManager.getInstance().getResource("TW.png"));
		                        JLabel towerLabel = new JLabel("", tower, JLabel.CENTER);
		                        add(towerLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), towerLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Knight") && tempColor == COLOR.WHITE){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon knight = new ImageIcon(ResourceManager.getInstance().getResource("KW.png"));
		                        JLabel knightLabel = new JLabel("", knight, JLabel.CENTER);
		                        add(knightLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), knightLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Bishop") && tempColor == COLOR.WHITE){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon bishop = new ImageIcon(ResourceManager.getInstance().getResource("BW.png"));
		                        JLabel bishopLabel = new JLabel("", bishop, JLabel.CENTER);
		                        add(bishopLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), bishopLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Queen") && tempColor == COLOR.WHITE){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon queen = new ImageIcon(ResourceManager.getInstance().getResource("QW.png"));
		                        JLabel queenLabel = new JLabel("", queen, JLabel.CENTER);
		                        add(queenLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), queenLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.King") && tempColor == COLOR.WHITE){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon king = new ImageIcon(ResourceManager.getInstance().getResource("KKW.png"));
		                        JLabel kingLabel = new JLabel("", king, JLabel.CENTER);
		                        add(kingLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), kingLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Pawn") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon image = new ImageIcon(ResourceManager.getInstance().getResource("PB.png"));
		                        JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
		                        add(pawnLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), pawnLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Rook") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon tower = new ImageIcon(ResourceManager.getInstance().getResource("TB.png"));
		                        JLabel towerLabel = new JLabel("", tower, JLabel.CENTER);
		                        add(towerLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), towerLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Knight") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon knight = new ImageIcon(ResourceManager.getInstance().getResource("KB.png"));
		                        JLabel knightLabel = new JLabel("", knight, JLabel.CENTER);
		                        add(knightLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), knightLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Bishop") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon bishop = new ImageIcon(ResourceManager.getInstance().getResource("BB.png"));
		                        JLabel bishopLabel = new JLabel("", bishop, JLabel.CENTER);
		                        add(bishopLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), bishopLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Queen") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon queen = new ImageIcon(ResourceManager.getInstance().getResource("QB.png"));
		                        JLabel queenLabel = new JLabel("", queen, JLabel.CENTER);
		                        add(queenLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), queenLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.King") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon king = new ImageIcon(ResourceManager.getInstance().getResource("KKB.png"));
		                        JLabel kingLabel = new JLabel("", king, JLabel.CENTER);
		                        add(kingLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), kingLabel);
		                        
		            	}
	            	}
            	}
            }
        }
        System.out.println("ListOfPiece Black :" + listOfPiece);
    }


    
     void play_sound(GamePiece currentPiece)
    {


      if(Menu.get_noise_on() && end_party==false)
      {
         MainWindow.chess_king.setVisible(false);
         MainWindow.chess_king_crown.setVisible(false);
            if (game.getLocalPlayer().isCheckAndMat() || game.getRemotePlayer().isCheckAndMat())
            {

                 new Launch_Sound("chess_mat.wav").play();
                 MainWindow.chess_king_crown.setVisible(true);

            }
            else if(game.getLocalPlayer().isOncheck() || game.getRemotePlayer().isOncheck())
            {
               new Launch_Sound("sword.wav").play();
               MainWindow.chess_king.setVisible(true);

            }
            else if (currentPiece != null && currentPiece.isPawnTop())
            {
                 new Launch_Sound("question.wav").play();

            }
            else if (currentPiece != null && currentPiece.haveDoneARook())
            {
                new Launch_Sound("roc.wav").play();
            }
            else if(is_eat)
            {
                new Launch_Sound("eat_piece.wav").play();

            }
            else if( is_move)
            {
                new Launch_Sound("move_piece.wav").play();
            }
            else
            {
             //no sound
            }


            is_eat=false;
            is_move=false;

      }


    }
     
    public void endOfGame(Player winner) {
      JOptionPane.showMessageDialog(this, winner.getPublicProfile().getPseudo() + " won ! You can still use the chat, please press quit button to leave this game.", "Enf of game", JOptionPane.INFORMATION_MESSAGE);
    }

    public void surrender(Player leaver) {
        JOptionPane.showMessageDialog(this, " You won ! Because " + leaver.getPublicProfile().getPseudo() + " just surrend.", "End of game", JOptionPane.INFORMATION_MESSAGE);
    
    }
    
    public void drawRequest(Player requester) {
        
        JOptionPane d = new JOptionPane();
        String[] choice = {"Yes", "No"};
        int retour = d.showOptionDialog(this, 
        requester.getPublicProfile().getPseudo() + " want to propose a draw. Do you want accept it ?",
        "Drawing",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        new ImageIcon(ResourceManager.getInstance().getResource("time_chess.png")),
        choice,
        choice[1]);

        if(retour == 0){ // oui j'accepte le draw
            // on envoie à l'autre player une demande de nulle
            Constant cst = myModel.getGManager().createConstant(Enums.CONSTANT_TYPE.DRAW_ACCEPTED);
            myModel.getGManager().sendConstant(cst);

        }
    }

    void drawAccepted(Player sender) {
        JOptionPane.showMessageDialog(this, sender.getPublicProfile().getPseudo() + " accept the draw ! You can still use the chat, please press quit button to leave this game.", "Enf of game", JOptionPane.INFORMATION_MESSAGE);
    }
}
