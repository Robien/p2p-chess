/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lo23.data.ApplicationModel;
import lo23.data.Event;
import lo23.data.Game;
import lo23.data.Move;
import lo23.data.Position;
import lo23.data.managers.GameManager;
import lo23.data.managers.Manager;
import lo23.data.pieces.GamePiece;
import lo23.utils.Enums.COLOR;



@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
   private ApplicationModel myModel;
   private Game game;
   private EventListener eventListener;
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
    GameManager manager = new GameManager(myModel);

   
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
        playerColor = COLOR.WHITE;
      
        build();
    }
    
    public GamePanel(ApplicationModel model, Game gm, COLOR color) {
        super();
        myModel = model;
        eventListener = new EventListener(this, myModel);
        game = gm;
        playerColor = color;

        build();
    }

    private void build() {
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
                
                //tester si la case est selectionnable
                if (isFormerPreSelectionExist){
                	listOfPreSelection.get(formerPreSelection).setVisible(false);
                	listOfPreSelection.get(newPreSelection).setVisible(true);
                } else {
                	listOfPreSelection.get(newPreSelection).setVisible(true);
                	isFormerPreSelectionExist = true;
                }
                
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
          
       
        buildBoard(true);
    }
    
    private void receiveSelectedCase(int x, int y){ 
    	constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = x;
        constraints.gridy = y;

        
        Position newSelection = new Position(x,y);
        GamePiece currentPiece = game.getPieceAtXY(newSelection.getX(), 7 - newSelection.getY());

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
    
    private void receiveSecondClick(Position newSelection, GamePiece currentPiece){ 
    	//if a case is already selected, the former selection disapears
        if (isFormerSelectionExist) {
            listOfSelection.get(formerPositionSelected).setVisible(false);
            repaint();
        }
        isFormerSelectionExist = true;
        for(Position possibleMove : listOfPossibleMove){
        	if (possibleMove.getX()==newSelection.getX() && possibleMove.getY()==(7 - newSelection.getY())){
                
        		if (game.getPieceAtXY(newSelection.getX(), 7 - newSelection.getY()) != null) {
                	if (game.getPieceAtXY(newSelection.getX(), 7 - newSelection.getY()).getOwner().getColor() != playerColor) {
                		eatPiece(newSelection);
                	}
                	//Eat Sound
                        if (Menu.get_noise_on())
                        {
                            new Launch_Sound("eat_piece.wav").play(); 
                        }
                	
                } else if (Menu.get_noise_on()){
                	//displacement sound
                	new Launch_Sound("move_piece.wav").play(); 
                }
                
                //Update model
        
                        
               //True code
//               System.out.println(newSelection + ":" + game.getPieceAtXY(formerPositionSelected.getX(),7 - formerPositionSelected.getY()));
//               Move move = myModel.getGManager().createMove(newSelection, game.getPieceAtXY(formerPositionSelected.getX(),7 - formerPositionSelected.getY()));
//               myModel.getGManager().playMove(move);
               //myModel.getGManager().sendMove(move);
                
                 game.getPieceAtXY(formerPositionSelected.getX(),7 - formerPositionSelected.getY()).movePiece(new Position(newSelection.getX(), 7 - newSelection.getY()));
                
              
                 

                //update display (remove for integration)
//                listOfPiece.remove(formerPositionSelected);
//                listOfPiece.put(newSelection, formerPieceSelected);
//                add(formerPieceSelected, constraints, 0);
                    
                updateBoard(new Move(formerPositionSelected,newSelection, game.getPieceAtXY(formerPositionSelected.getX(),7 - formerPositionSelected.getY())));

                
        	}
    	}
        hidePossibleCase();
        
    }
      
    public void eatPiece(Position p){
    	listOfPiece.get(p).setVisible(false);
    	JLabel atePiece = listOfPiece.remove(p);

//    	if (playerColor == COLOR.WHITE) {
//    		blackAtePieces.add(atePiece);
//    	} else {
//            whiteAtePieces.add(atePiece);
//        }
    }
    
    private boolean isCaseSelectionable(Position newSelection, GamePiece currentPiece){
        // Check if the case is selectionable with pieces color...
    	//commenter le dernier test sur la couleur du joueur pour pouvoir joueur les noirs!
    	if (listOfPiece.get(newSelection) != null && !currentPiece.getPossibleMovesWithCheck().isEmpty()){// && currentPiece.getOwner().getColor() == playerColor && game.getLocalPlayer().getColor() == playerColor) {
    		return true;
    	} else {
    		return false;
    	}
    }

    private void showPossiblesMoves(GamePiece piece) {
        colorPossibleCase(piece.getPossibleMovesWithCheck());
    }

    private void colorPossibleCase(List<Position> positions){
        for (Position p : positions){
        	listOfSquare.get(new Position(p.getX(),7 - p.getY())).setVisible(true);
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
    
    public void updateBoard(Move move){
        // Update board after player play a move
         JLabel currentPiece = listOfPiece.get(move.getFrom());
         listOfPiece.remove(move.getFrom());
         listOfPiece.put(move.getTo(), currentPiece);
         add(currentPiece, constraints, 0);
         
        if (playerColor == COLOR.WHITE) {
            playerColor = COLOR.BLACK;
            System.out.println("1" + playerColor);
        } else if (playerColor == COLOR.BLACK) {
            playerColor = COLOR.WHITE;
            System.out.println("2" + playerColor);
        }
    }
    
    private void launchParty(){
        // Launch A party and build the board with events or not
        if(myModel.getGManager().getCurrentGame().getEvents().isEmpty()){
            if(myModel.getGManager().getCurrentGame().getLocalPlayer().getColor() == COLOR.WHITE){
                buildBoard(true);
            } else {
                buildBoard(false);
            }
        }else {
            // TO DO : Parcourir la liste des events et reconstituer la partie.
        }
    }

    private void buildBoard(boolean playerIsWhite) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                constraints.gridx = i;
                constraints.gridy = j;
                
                JLabel currentSelection = new JLabel("", squareBorder, JLabel.CENTER);
                add(currentSelection, constraints, 0);
                listOfSelection.put(new Position(i,j), currentSelection);
                currentSelection.setVisible(false);
                
                JLabel possibleCase = new JLabel("", filledSquare, JLabel.CENTER);
                add(possibleCase, constraints, 0);
                listOfSquare.put(new Position(i,j), possibleCase);
                possibleCase.setVisible(false);
                
                JLabel preSelectionLabel = new JLabel("", preSelection, JLabel.CENTER);
                add(preSelectionLabel, constraints, 1);
                listOfPreSelection.put(new Position(i,j), preSelectionLabel);
                preSelectionLabel.setVisible(false);
                
                if ((i + j) % 2 != 0) {
                    JLabel labelCaseN = new JLabel("", imageCaseN, JLabel.CENTER);
                    add(labelCaseN, constraints, -1);
//                    listOfCase.put(new Position(i,j), labelCaseN);
                } else {
                    JLabel labelCaseB = new JLabel("", imageCaseB, JLabel.CENTER);
                    add(labelCaseB, constraints, -1);
//                    listOfCase.put(new Position(i,j), labelCaseB);
                }
            }
        }
        
        if (playerColor == COLOR.WHITE) {

            //Add pawn pieces to the board
            constraints.gridy = 6;

            for (int i = 0; i < 8; i++){
                constraints.gridx = i; 
                 ImageIcon image = new ImageIcon(path + "lo23/ui/resources/PW.png");
                 JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
                 add(pawnLabel, constraints, 2);
                 listOfPiece.put(new Position(i,6), pawnLabel);
            }

            //Add towers pieces to the board
            constraints.gridx = 0;
            constraints.gridy = 7;

            ImageIcon tower = new ImageIcon(path + "lo23/ui/resources/TW.png");
            JLabel towerRight = new JLabel("", tower, JLabel.CENTER);
            add(towerRight, constraints, 2);
            listOfPiece.put(new Position(0, 7), towerRight);

            constraints.gridx = 7;
            JLabel towerLeft = new JLabel("", tower, JLabel.CENTER);
            add(towerLeft, constraints, 2);
            listOfPiece.put(new Position(7, 7), towerLeft);

            //Add knights pieces to the board
            constraints.gridx = 1;
            constraints.gridy = 7;

            ImageIcon knight = new ImageIcon(path + "lo23/ui/resources/KW.png");
            JLabel knightRight = new JLabel("", knight, JLabel.CENTER);
            add(knightRight, constraints, 2);
            listOfPiece.put(new Position(1, 7), knightRight);

            constraints.gridx = 6;
            JLabel knightLeft = new JLabel("", knight, JLabel.CENTER);
            add(knightLeft, constraints, 2);
            listOfPiece.put(new Position(6, 7), knightLeft);

            //Add bishop pieces to the board
            constraints.gridx = 2;
            constraints.gridy = 7;

            ImageIcon bishop = new ImageIcon(path + "lo23/ui/resources/BW.png");
            JLabel bishopRight = new JLabel("", bishop, JLabel.CENTER);
            add(bishopRight, constraints, 2);
            listOfPiece.put(new Position(2, 7), bishopRight);

            constraints.gridx = 5;
            JLabel bishopLeft = new JLabel("", bishop, JLabel.CENTER);
            add(bishopLeft, constraints, 2);
            listOfPiece.put(new Position(5, 7), bishopLeft);

            //Add queen pieces to the board
            constraints.gridx = 3;
            constraints.gridy = 7;

            ImageIcon queen = new ImageIcon(path + "lo23/ui/resources/QW.png");
            JLabel queenPiece = new JLabel("", queen, JLabel.CENTER);
            add(queenPiece, constraints, 2);
            listOfPiece.put(new Position(3, 7), queenPiece);

            //Add king pieces to the board
            constraints.gridx = 4;
            constraints.gridy = 7;

            ImageIcon king = new ImageIcon(path + "lo23/ui/resources/KKW.png");
            JLabel kingPiece = new JLabel("", king, JLabel.CENTER);
            add(kingPiece, constraints, 2);
            listOfPiece.put(new Position(4, 7), kingPiece);
            //Black Pieces :
            //Add pawn pieces to the board

            constraints.gridy = 1;

            for (int i = 0; i < 8; i++){
                 constraints.gridx = i; 
                 ImageIcon image = new ImageIcon(path + "lo23/ui/resources/PB.png");
                 JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
                 add(pawnLabel, constraints, 2);
                 listOfPiece.put(new Position(i,1), pawnLabel);
            }

            //Add towers pieces to the board
            constraints.gridx = 0;
            constraints.gridy = 0;

            ImageIcon towerB = new ImageIcon(path + "lo23/ui/resources/TB.png");
            JLabel towerRightB = new JLabel("", towerB, JLabel.CENTER);
            add(towerRightB, constraints, 2);
            listOfPiece.put(new Position(0, 0), towerRightB);

            constraints.gridx = 7;
            JLabel towerLeftB = new JLabel("", towerB, JLabel.CENTER);
            add(towerLeftB, constraints, 2);
            listOfPiece.put(new Position(7, 0), towerLeftB);

            //Add knights pieces to the board
            constraints.gridx = 1;
            constraints.gridy = 0;

            ImageIcon knightB = new ImageIcon(path + "lo23/ui/resources/KB.png");
            JLabel knightRightB = new JLabel("", knightB, JLabel.CENTER);
            add(knightRightB, constraints, 2);
            listOfPiece.put(new Position(1, 0), knightRightB);

            constraints.gridx = 6;
            JLabel knightLeftB = new JLabel("", knightB, JLabel.CENTER);
            add(knightLeftB, constraints, 2);
            listOfPiece.put(new Position(6, 0), knightLeftB);

            //Add bishop pieces to the board
            constraints.gridx = 2;
            constraints.gridy = 0;

            ImageIcon bishopB = new ImageIcon(path + "lo23/ui/resources/BB.png");
            JLabel bishopRightB = new JLabel("", bishopB, JLabel.CENTER);
            add(bishopRightB, constraints, 2);
            listOfPiece.put(new Position(2, 0), bishopRightB);

            constraints.gridx = 5;
            JLabel bishopLeftB = new JLabel("", bishopB, JLabel.CENTER);
            add(bishopLeftB, constraints, 2);
            listOfPiece.put(new Position(5, 0), bishopLeftB);

            //Add queen pieces to the board
            constraints.gridx = 3;
            constraints.gridy = 0;

            ImageIcon queenB = new ImageIcon(path + "lo23/ui/resources/QB.png");
            JLabel queenPieceB = new JLabel("", queenB, JLabel.CENTER);
            add(queenPieceB, constraints, 2);
            listOfPiece.put(new Position(3, 0), queenPieceB);

            //Add king pieces to the board
            constraints.gridx = 4;
            constraints.gridy = 0;

            ImageIcon kingB = new ImageIcon(path + "lo23/ui/resources/KKB.png");
            JLabel kingPieceB = new JLabel("", kingB, JLabel.CENTER);
            add(kingPieceB, constraints, 2);
            listOfPiece.put(new Position(4, 0), kingPieceB);
            
            //test for AtePieces
            JLabel jB1 = new JLabel("", kingB, JLabel.CENTER);
            JLabel jB2 = new JLabel("", queenB, JLabel.CENTER);
            JLabel jB3 = new JLabel("", bishopB, JLabel.CENTER);
            JLabel jB4 = new JLabel("", bishopB, JLabel.CENTER);

            JLabel j1 = new JLabel("", kingB, JLabel.CENTER);
            JLabel j2 = new JLabel("", queenB, JLabel.CENTER);
            JLabel j3 = new JLabel("", bishopB, JLabel.CENTER);
            JLabel j4 = new JLabel("", bishopB, JLabel.CENTER);
            
            whiteAtePieces.add(j1);
            whiteAtePieces.add(j2);
            whiteAtePieces.add(j3);
            whiteAtePieces.add(j4);
            
            blackAtePieces.add(jB1);
            blackAtePieces.add(jB2);
            blackAtePieces.add(jB3);
            blackAtePieces.add(jB4);
        }
    }
}
