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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Position;
import lo23.data.pieces.GamePiece;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
   private ApplicationModel myModel;
   private Game game;
    
    private GridBagLayout gameBoard = new GridBagLayout();
    private GridBagConstraints constraints = new GridBagConstraints();
      
    private String path = getClass().getClassLoader().getResource(".").getPath();
    private ImageIcon squareBorder = new ImageIcon(path + "lo23/ui/resources/squareBorder.png");
    private ImageIcon filledSquare = new ImageIcon(path + "lo23/ui/resources/PossibleSquare.png");
    
	//hashmap with JLabel and their positions
    private HashMap<Position, JLabel> listOfPiece = new HashMap<Position, JLabel>();
    private HashMap<Position, JLabel> listOfSelection = new HashMap<Position, JLabel>();
    private HashMap<Position, JLabel> listOfSquare = new HashMap<Position, JLabel>();
    private ArrayList<Position> listOfPossibleMove = new ArrayList<Position>();
    
    //ate piece
    private ArrayList<JLabel> blackAtePieces = new ArrayList<JLabel>();
    private ArrayList<JLabel> whiteAtePieces = new ArrayList<JLabel>();

    private boolean isCurrentSelectionExist = false;
    private boolean isCurrentSelectionOccupied = false;
    private JLabel currentPieceSelected;
    private Position currentPositionSelection;
    boolean localPlayerIsWhite = true;
    boolean secondClickIsAllowed = false;
 
    public GamePanel(ApplicationModel model, Game gm) {
        super();
        model = myModel;
        game = gm;
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
                    receiveSelectedCaseOld(xSquare,ySquare);
//    	        } else if(SwingUtilities.isMiddleMouseButton(mouseEvent) ) {
//    	        	//middle button
//    	        	
//    	        } else if(SwingUtilities.isRightMouseButton(mouseEvent)) {
                    //right button
    
                }
            }
        });
        buildBoard(true);
    }

    
    private boolean isCaseSelectionable(Position position){
        // Check if the case is selectionable with pieces color...
        return true;
    }
    
    private void showSelectionCase(Position position){
        // Display selection frame
           //save the current position
            currentPieceSelected = listOfPiece.get(position);
            currentPositionSelection = position;
            isCurrentSelectionOccupied = true;
            listOfSelection.get(currentPositionSelection).setVisible(true);
    }

    private void showPossiblesMoves(GamePiece piece) {
    	System.out.println(piece.toString());
        colorPossibleCase(piece.getPossibleMoves());
    }
    
    private void receiveFirstClick(int x, int y){
        //if a case is already selected, the former selection disapears
        if (isCurrentSelectionExist) {
            hidePossibleCase();
            listOfSelection.get(currentPositionSelection).setVisible(false);
            repaint();
        }
        isCurrentSelectionExist = true;
        // Case conversion
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 7 - x;
        constraints.gridy = 7 - y;

        Position newSelection = new Position(7 - x,7 - y);

        
        // Check selectionable case
        if(isCaseSelectionable(newSelection)){
            showSelectionCase(newSelection);
            showPossiblesMoves(game.getPieceAtXY(newSelection.getX(), newSelection.getY())); //game is temporary
            secondClickIsAllowed = true;
        }   
    }
    
    private void receiveSecondClick(int x, int y){     
        if (secondClickIsAllowed) {
            // Case conversion
            constraints.insets = new Insets(0, 0, 0, 0);
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.gridx = 7 - x;
            constraints.gridy = 7 - y;

            Position newSelection = new Position(7 - x, 7 - y);
            
            //if a case is already selected, the former selection disapears
            if (isCurrentSelectionExist) {
                listOfSelection.get(currentPositionSelection).setVisible(false);
                repaint();
            }
            isCurrentSelectionExist = true;
           
            // Test if selection is possible
            for (Position p : listOfPossibleMove) {
                if (p.getX() == newSelection.getX() && p.getY() == newSelection.getY()) {
                    //Move the piece
                    constraints.gridx = 7 - x;
                    constraints.gridy = 7 - y;

                    //remove the former position
                    listOfPiece.remove(currentPositionSelection);
                    //add the new position
                    listOfPiece.put(newSelection, currentPieceSelected);
                    //update the display
                    add(currentPieceSelected, constraints, 0);
                    isCurrentSelectionOccupied = false;
                    game.getPieceAtXY(currentPositionSelection.getX(), currentPositionSelection.getY()).movePiece(new Position(newSelection.getX(), newSelection.getY()));

                    //sound
                    if (MainWindow.get_noise_on()){
                    	new Launch_Sound("move_piece.wav").play(); 
                    }

                }
            
            }
        }
    }
    
    private void receiveSelectedCase(int x, int y){
         if (secondClickIsAllowed) {
             receiveSecondClick(x,y);
         }else {
             receiveFirstClick(x,y);
         }
         
         hidePossibleCase();
    }

      private void receiveSelectedCaseOld(int x, int y) {
        System.out.println(x + ":" + y);
        //TODO corriger le sens de la grille
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = x;
        constraints.gridy = y;

        Position newSelection = new Position(x,y);

        //if the actual case is occupied
        //TODO by a piece of your color
        if (listOfPiece.get(newSelection) != null) {
        	
            //if a case is already selected, the former selection disapears
            if (isCurrentSelectionExist) {
                hidePossibleCase();
                listOfSelection.get(currentPositionSelection).setVisible(false);
                repaint();
            }
            isCurrentSelectionExist = true;
        	
            //save the current position
            currentPieceSelected = listOfPiece.get(newSelection);
            currentPositionSelection = newSelection;
            isCurrentSelectionOccupied = true;
            listOfSelection.get(currentPositionSelection).setVisible(true);
            
            GamePiece currentPiece = game.getPieceAtXY(newSelection.getX(), 7 - newSelection.getY()); 
            showPossiblesMoves(currentPiece);
          
        //if the former case was occupied
        } else if (isCurrentSelectionOccupied) {
        	
            //if a case is already selected, the former selection disapears
            if (isCurrentSelectionExist) {
                listOfSelection.get(currentPositionSelection).setVisible(false);
                repaint();
            }
            isCurrentSelectionExist = true;
        	
            for(Position possibleMove : listOfPossibleMove){
            	if (possibleMove.getX()==newSelection.getX() && possibleMove.getY()==(7 - newSelection.getY())){
                    
                    //remove the former position
                    listOfPiece.remove(currentPositionSelection);
                    //add the new position
                    listOfPiece.put(newSelection, currentPieceSelected);
                    //update the display
                    add(currentPieceSelected, constraints, 0);
                    isCurrentSelectionOccupied = false;
                    System.out.println(currentPositionSelection.getX() + " " + currentPositionSelection.getY());
                    System.out.println(newSelection.getX() + " " + newSelection.getY());
                    game.getPieceAtXY(currentPositionSelection.getX(),7 - currentPositionSelection.getY()).movePiece(new Position(newSelection.getX(), 7 - newSelection.getY()));
                  
                    //sound
                    if (MainWindow.get_noise_on()){
                    	new Launch_Sound("move_piece.wav").play(); 
                    }
                    

            	}
            }
            hidePossibleCase();
        	            
        }
    }
      
    public void eatPiece(Position p){
    	JLabel atePiece = listOfPiece.remove(p);
    	//test
    	String colorPiece = "black";
    	if (colorPiece.equals("black")){
    		blackAtePieces.add(atePiece);
    	} else whiteAtePieces.add(atePiece);
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
    
     

    private void buildBoard(boolean playerIsWhite) {
        ImageIcon imageCaseB = new ImageIcon(path + "lo23/ui/resources/caseB.JPG");
        ImageIcon imageCaseN = new ImageIcon(path + "lo23/ui/resources/caseN.JPG");

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
                
                if ((i + j) % 2 != 0) {
                    JLabel labelCaseN = new JLabel("", imageCaseN, JLabel.CENTER);
                    add(labelCaseN, constraints, -1);
                } else {
                    JLabel labelCaseB = new JLabel("", imageCaseB, JLabel.CENTER);
                    add(labelCaseB, constraints, -1);
                }
            }
        }
        
        if (playerIsWhite) {

            //Add pawn pieces to the board
            constraints.gridy = 6;

            for (int i = 0; i < 8; i++){
                constraints.gridx = i; 
                 ImageIcon image = new ImageIcon(path + "lo23/ui/resources/PW.png");
                 JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
                 add(pawnLabel, constraints, 1);
                 listOfPiece.put(new Position(i,6), pawnLabel);
            }

            //Add towers pieces to the board
            constraints.gridx = 0;
            constraints.gridy = 7;

            ImageIcon tower = new ImageIcon(path + "lo23/ui/resources/TW.png");
            JLabel towerRight = new JLabel("", tower, JLabel.CENTER);
            add(towerRight, constraints, 1);
            listOfPiece.put(new Position(0, 7), towerRight);

            constraints.gridx = 7;
            JLabel towerLeft = new JLabel("", tower, JLabel.CENTER);
            add(towerLeft, constraints, 1);
            listOfPiece.put(new Position(7, 7), towerLeft);

            //Add knights pieces to the board
            constraints.gridx = 1;
            constraints.gridy = 7;

            ImageIcon knight = new ImageIcon(path + "lo23/ui/resources/KW.png");
            JLabel knightRight = new JLabel("", knight, JLabel.CENTER);
            add(knightRight, constraints, 1);
            listOfPiece.put(new Position(1, 7), knightRight);

            constraints.gridx = 6;
            JLabel knightLeft = new JLabel("", knight, JLabel.CENTER);
            add(knightLeft, constraints, 1);
            listOfPiece.put(new Position(6, 7), knightLeft);

            //Add bishop pieces to the board
            constraints.gridx = 2;
            constraints.gridy = 7;

            ImageIcon bishop = new ImageIcon(path + "lo23/ui/resources/BW.png");
            JLabel bishopRight = new JLabel("", bishop, JLabel.CENTER);
            add(bishopRight, constraints, 1);
            listOfPiece.put(new Position(2, 7), bishopRight);

            constraints.gridx = 5;
            JLabel bishopLeft = new JLabel("", bishop, JLabel.CENTER);
            add(bishopLeft, constraints, 1);
            listOfPiece.put(new Position(5, 7), bishopLeft);

            //Add queen pieces to the board
            constraints.gridx = 3;
            constraints.gridy = 7;

            ImageIcon queen = new ImageIcon(path + "lo23/ui/resources/QW.png");
            JLabel queenPiece = new JLabel("", queen, JLabel.CENTER);
            add(queenPiece, constraints, 1);
            listOfPiece.put(new Position(3, 7), queenPiece);

            //Add king pieces to the board
            constraints.gridx = 4;
            constraints.gridy = 7;

            ImageIcon king = new ImageIcon(path + "lo23/ui/resources/KKW.png");
            JLabel kingPiece = new JLabel("", king, JLabel.CENTER);
            add(kingPiece, constraints, 1);
            listOfPiece.put(new Position(4, 7), kingPiece);
            //Black Pieces :
            //Add pawn pieces to the board

            constraints.gridy = 1;

            for (int i = 0; i < 8; i++){
                 constraints.gridx = i; 
                 ImageIcon image = new ImageIcon(path + "lo23/ui/resources/PB.png");
                 JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
                 add(pawnLabel, constraints, 1);
                 listOfPiece.put(new Position(i,1), pawnLabel);
            }

            //Add towers pieces to the board
            constraints.gridx = 0;
            constraints.gridy = 0;

            ImageIcon towerB = new ImageIcon(path + "lo23/ui/resources/TB.png");
            JLabel towerRightB = new JLabel("", towerB, JLabel.CENTER);
            add(towerRightB, constraints, 1);
            listOfPiece.put(new Position(0, 0), towerRightB);

            constraints.gridx = 7;
            JLabel towerLeftB = new JLabel("", towerB, JLabel.CENTER);
            add(towerLeftB, constraints, 1);
            listOfPiece.put(new Position(7, 0), towerLeftB);

            //Add knights pieces to the board
            constraints.gridx = 1;
            constraints.gridy = 0;

            ImageIcon knightB = new ImageIcon(path + "lo23/ui/resources/KB.png");
            JLabel knightRightB = new JLabel("", knightB, JLabel.CENTER);
            add(knightRightB, constraints, 1);
            listOfPiece.put(new Position(1, 0), knightRightB);

            constraints.gridx = 6;
            JLabel knightLeftB = new JLabel("", knightB, JLabel.CENTER);
            add(knightLeftB, constraints, 1);
            listOfPiece.put(new Position(6, 0), knightLeftB);

            //Add bishop pieces to the board
            constraints.gridx = 2;
            constraints.gridy = 0;

            ImageIcon bishopB = new ImageIcon(path + "lo23/ui/resources/BB.png");
            JLabel bishopRightB = new JLabel("", bishopB, JLabel.CENTER);
            add(bishopRightB, constraints, 1);
            listOfPiece.put(new Position(2, 0), bishopRightB);

            constraints.gridx = 5;
            JLabel bishopLeftB = new JLabel("", bishopB, JLabel.CENTER);
            add(bishopLeftB, constraints, 1);
            listOfPiece.put(new Position(5, 0), bishopLeftB);

            //Add queen pieces to the board
            constraints.gridx = 3;
            constraints.gridy = 0;

            ImageIcon queenB = new ImageIcon(path + "lo23/ui/resources/QB.png");
            JLabel queenPieceB = new JLabel("", queenB, JLabel.CENTER);
            add(queenPieceB, constraints, 1);
            listOfPiece.put(new Position(3, 0), queenPieceB);

            //Add king pieces to the board
            constraints.gridx = 4;
            constraints.gridy = 0;

            ImageIcon kingB = new ImageIcon(path + "lo23/ui/resources/KKB.png");
            JLabel kingPieceB = new JLabel("", kingB, JLabel.CENTER);
            add(kingPieceB, constraints, 1);
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
