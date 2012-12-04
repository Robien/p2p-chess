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
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Move;
import lo23.data.Position;
import lo23.data.pieces.GamePiece;
import lo23.utils.Enums.COLOR;



@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	private ApplicationModel myModel;
	private Game game;
	private GamePiece[][] board;
	private EventListener eventListener;
	private BorderLayout borderLayout;
    private GridBagLayout gameBoard = new GridBagLayout();
    private GridBagConstraints constraints = new GridBagConstraints();
      
    private String path = getClass().getClassLoader().getResource(".").getPath();
    private ImageIcon squareBorder = new ImageIcon(path + "lo23/ui/resources/squareBorder.png");
    private ImageIcon filledSquare = new ImageIcon(path + "lo23/ui/resources/PossibleSquare.png");
    private ImageIcon imageCaseB = new ImageIcon(path + "lo23/ui/resources/caseB.JPG");
    private ImageIcon imageCaseN = new ImageIcon(path + "lo23/ui/resources/caseN.JPG");
    private ImageIcon preSelection = new ImageIcon(path + "lo23/ui/resources/preSelection.png");
    
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
    
   
   
    public GamePanel(ApplicationModel model){
        super();
        myModel = model; 
        eventListener = new EventListener(this, myModel);
    }
 
    public GamePanel(ApplicationModel model, Game gm) {
        super();
        myModel = model;
        eventListener = new EventListener(this, myModel);
        game = gm;
        board = gm.getBoard();
        playerColor = COLOR.WHITE;
      
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
    
        
    private void launchGame(){
        // Launch a game and build the board with events or not
        
       
        if(myModel.getGManager().getCurrentGame().getEvents().isEmpty()){
        	buildBoard();
        }else {
            // TO DO : Parcourir la liste des events et reconstituer la partie.
            buildReviewBoard();
        }
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
                    System.out.println(ySquare);
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
                	listOfPreSelection.get(formerPreSelection).setVisible(false);
                }   
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			}
        	
        });
          
        addPropertyChangeListener(eventListener);
       
        launchGame();
    }
    
    private void receiveSelectedCase(int x, int y){ 
    	constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = x;
        constraints.gridy = y;
 
        Position newSelection = new Position(x,y);
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
    
    private void receiveFirstClick(Position newSelection, GamePiece currentPiece){
    	//if a case is already selected, the former selection disapears
        playerColor = game.getLocalPlayer().getColor();
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
            repaint();
        }
        isFormerSelectionExist = true;
        for (Position possibleMove : listOfPossibleMove) {

            GamePiece tempPiece = null;
            if (myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE) {
                if (possibleMove.getX() == newSelection.getWX() && possibleMove.getY() == newSelection.getWY()) {

                    if (game.getPieceAtXY(newSelection.getWX(), newSelection.getWY()) != null) {
                        if (game.getPieceAtXY(newSelection.getWX(), newSelection.getWY()).getOwner().getColor() != playerColor) {
                            eatPiece(newSelection);
                        }
                        //Eat Sound
                        if (Menu.get_noise_on()) {
                            new Launch_Sound("eat_piece.wav").play();
                        }

                    } else if (Menu.get_noise_on()) {
                        //displacement sound
                        new Launch_Sound("move_piece.wav").play();
                    }

                    //Update model
                    tempPiece = game.getPieceAtXY(formerPositionSelected.getWX(), formerPositionSelected.getWY());
                    System.out.println("type pi�ce : " + tempPiece.getClass().getName());
                    System.out.println("model position : " + newSelection.toString());

                    Move move = myModel.getGManager().createMove(new Position(newSelection.getWX(), newSelection.getWY()), tempPiece);
                    myModel.getGManager().playMove(move);
                } else {
                    if (possibleMove.getX() == newSelection.getBX() && possibleMove.getY() == newSelection.getBY()) {

                        if (game.getPieceAtXY(newSelection.getBX(), newSelection.getBY()) != null) {
                            if (game.getPieceAtXY(newSelection.getBX(), newSelection.getBY()).getOwner().getColor() != playerColor) {
                                eatPiece(newSelection);
                            }
                            //Eat Sound
                            if (Menu.get_noise_on()) {
                                new Launch_Sound("eat_piece.wav").play();
                            }

                        } else if (Menu.get_noise_on()) {
                            //displacement sound
                            new Launch_Sound("move_piece.wav").play();
                        }

                        //Update model
                        tempPiece = game.getPieceAtXY(formerPositionSelected.getBX(), formerPositionSelected.getBY());
                        System.out.println("type pi�ce : " + tempPiece.getClass().getName());
                        System.out.println("model position : " + newSelection.toString());

                        Move move = myModel.getGManager().createMove(new Position(newSelection.getWX(), newSelection.getWY()), tempPiece);
                        myModel.getGManager().playMove(move);
                    }
                }
                
    
            }
            hidePossibleCase();
        }
    }
      
    public void updateBoard(Move move){
        // Update board after player play a move
    	System.out.println("position d�part grid : " + move.getFrom().toString());
    	System.out.println("position arriv�e grid : " + move.getTo().toString());
    
    
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
        listOfPiece.put(move.getTo(), currentPiece);
        add(currentPiece, constraints, 0);
        
        if(myModel.getGManager().getCurrentGame().getLocalPlayer().isCheckAndMat()){
            // End of game   
        }
         
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
        System.out.println("position départ grid : " + move.getFrom().toString());
    	System.out.println("position arrivée grid : " + move.getTo().toString());
        
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
        listOfPiece.put(move.getTo(), currentPiece);
        add(currentPiece, constraints, 0);
         
         //System.out.println(move);
    }
    
    public void eatPiece(Position p){
    	listOfPiece.get(p).setVisible(false);
    	JLabel atePiece = listOfPiece.remove(p);

    	if (playerColor == COLOR.WHITE) {
    		blackAtePieces.add(atePiece);
    	} else {
            whiteAtePieces.add(atePiece);
        }
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
    		if(!currentPiece.getPossibleMovesWithCheck().isEmpty()) {
    				//&& currentPiece.getOwner().getColor() == playerColor 
    				//&& game.getLocalPlayer().getColor() == playerColor) {
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
	            	COLOR tempColor = tempPiece.getOwner().getColor();
                
                	if (playerColor == COLOR.WHITE) {
	            		
	            		if (tempName.equals("lo23.data.pieces.Pawn") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon image = new ImageIcon(path + "lo23/ui/resources/PW.png");
	                        JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
	                        add(pawnLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), pawnLabel);
	            			
	            		} else if (tempName.equals("lo23.data.pieces.Rook") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon tower = new ImageIcon(path + "lo23/ui/resources/TW.png");
	                        JLabel towerLabel = new JLabel("", tower, JLabel.CENTER);
	                        add(towerLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), towerLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Knight") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon knight = new ImageIcon(path + "lo23/ui/resources/KW.png");
	                        JLabel knightLabel = new JLabel("", knight, JLabel.CENTER);
	                        add(knightLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), knightLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Bishop") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon bishop = new ImageIcon(path + "lo23/ui/resources/BW.png");
	                        JLabel bishopLabel = new JLabel("", bishop, JLabel.CENTER);
	                        add(bishopLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), bishopLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Queen") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon queen = new ImageIcon(path + "lo23/ui/resources/QW.png");
	                        JLabel queenLabel = new JLabel("", queen, JLabel.CENTER);
	                        add(queenLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), queenLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.King") && tempColor == COLOR.WHITE){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon king = new ImageIcon(path + "lo23/ui/resources/KKW.png");
	                        JLabel kingLabel = new JLabel("", king, JLabel.CENTER);
	                        add(kingLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), kingLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Pawn") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon image = new ImageIcon(path + "lo23/ui/resources/PB.png");
	                        JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
	                        add(pawnLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), pawnLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Rook") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon tower = new ImageIcon(path + "lo23/ui/resources/TB.png");
	                        JLabel towerLabel = new JLabel("", tower, JLabel.CENTER);
	                        add(towerLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), towerLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Knight") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon knight = new ImageIcon(path + "lo23/ui/resources/KB.png");
	                        JLabel knightLabel = new JLabel("", knight, JLabel.CENTER);
	                        add(knightLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), knightLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Bishop") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon bishop = new ImageIcon(path + "lo23/ui/resources/BB.png");
	                        JLabel bishopLabel = new JLabel("", bishop, JLabel.CENTER);
	                        add(bishopLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), bishopLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.Queen") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon queen = new ImageIcon(path + "lo23/ui/resources/QB.png");
	                        JLabel queenLabel = new JLabel("", queen, JLabel.CENTER);
	                        add(queenLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), queenLabel);
	                        
	            		} else if (tempName.equals("lo23.data.pieces.King") && tempColor == COLOR.BLACK){
	            			
	            			constraints.gridx = tempPosition.getX();
	                        constraints.gridy = 7 - tempPosition.getY(); 
	                        ImageIcon king = new ImageIcon(path + "lo23/ui/resources/KKB.png");
	                        JLabel kingLabel = new JLabel("", king, JLabel.CENTER);
	                        add(kingLabel, constraints, 2);
	                        listOfPiece.put(new Position(tempPosition.getX(),7 - tempPosition.getY()), kingLabel);
	                        
	            		}
	            	} else {
		            		
		            	if (tempName.equals("lo23.data.pieces.Pawn") && tempColor == COLOR.WHITE){
		            			
		            		constraints.gridx = 7 - tempPosition.getX();
		                    constraints.gridy = tempPosition.getY(); 
		                    ImageIcon image = new ImageIcon(path + "lo23/ui/resources/PW.png");
		                    JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
		                    add(pawnLabel, constraints, 2);
		                    listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), pawnLabel);
		            			
		            	} else if (tempName.equals("lo23.data.pieces.Rook") && tempColor == COLOR.WHITE){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon tower = new ImageIcon(path + "lo23/ui/resources/TW.png");
		                        JLabel towerLabel = new JLabel("", tower, JLabel.CENTER);
		                        add(towerLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), towerLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Knight") && tempColor == COLOR.WHITE){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon knight = new ImageIcon(path + "lo23/ui/resources/KW.png");
		                        JLabel knightLabel = new JLabel("", knight, JLabel.CENTER);
		                        add(knightLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), knightLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Bishop") && tempColor == COLOR.WHITE){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon bishop = new ImageIcon(path + "lo23/ui/resources/BW.png");
		                        JLabel bishopLabel = new JLabel("", bishop, JLabel.CENTER);
		                        add(bishopLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), bishopLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Queen") && tempColor == COLOR.WHITE){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon queen = new ImageIcon(path + "lo23/ui/resources/QW.png");
		                        JLabel queenLabel = new JLabel("", queen, JLabel.CENTER);
		                        add(queenLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), queenLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.King") && tempColor == COLOR.WHITE){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon king = new ImageIcon(path + "lo23/ui/resources/KKW.png");
		                        JLabel kingLabel = new JLabel("", king, JLabel.CENTER);
		                        add(kingLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), kingLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Pawn") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon image = new ImageIcon(path + "lo23/ui/resources/PB.png");
		                        JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
		                        add(pawnLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), pawnLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Rook") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon tower = new ImageIcon(path + "lo23/ui/resources/TB.png");
		                        JLabel towerLabel = new JLabel("", tower, JLabel.CENTER);
		                        add(towerLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), towerLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Knight") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon knight = new ImageIcon(path + "lo23/ui/resources/KB.png");
		                        JLabel knightLabel = new JLabel("", knight, JLabel.CENTER);
		                        add(knightLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), knightLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Bishop") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon bishop = new ImageIcon(path + "lo23/ui/resources/BB.png");
		                        JLabel bishopLabel = new JLabel("", bishop, JLabel.CENTER);
		                        add(bishopLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), bishopLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.Queen") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon queen = new ImageIcon(path + "lo23/ui/resources/QB.png");
		                        JLabel queenLabel = new JLabel("", queen, JLabel.CENTER);
		                        add(queenLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), queenLabel);
		                        
		            	} else if (tempName.equals("lo23.data.pieces.King") && tempColor == COLOR.BLACK){
		            			
		            			constraints.gridx = 7 - tempPosition.getX();
		                        constraints.gridy = tempPosition.getY(); 
		                        ImageIcon king = new ImageIcon(path + "lo23/ui/resources/KKB.png");
		                        JLabel kingLabel = new JLabel("", king, JLabel.CENTER);
		                        add(kingLabel, constraints, 2);
		                        listOfPiece.put(new Position(7 - tempPosition.getX(),tempPosition.getY()), kingLabel);
		                        
		            	}
	            	}
            	}
            }
        }    
    }


}
