/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

/**
 * TODO Changer le Path mettre les image dans /src et non pas /bin
 *
 * @Karim : ajouter une variable a la fin du add pour designer la priorite
 * d'affichage sur la grille add(label, constraints, -1); sera dessous
 * add(label, constraints, 1);
 * @author Karim
 */
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lo23.data.ApplicationModel;
import lo23.data.Position;
import lo23.data.pieces.GamePiece;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    ApplicationModel myModel;
    private HashMap<PositionOnBoard, JLabel> listOfPiece = new HashMap<PositionOnBoard, JLabel>();
    private GridBagLayout gameBoard = new GridBagLayout();
    private GridBagConstraints constraints = new GridBagConstraints();
    
    private HashMap<PositionOnBoard, JLabel> listOfSelection = new HashMap<PositionOnBoard, JLabel>();
    private HashMap<PositionOnBoard, JLabel> listOfSquare = new HashMap<PositionOnBoard, JLabel>();
    
    String path = getClass().getClassLoader().getResource(".").getPath();
    ImageIcon squareBorder = new ImageIcon(path + "lo23/ui/resources/squareBorder.png");
    ImageIcon filledSquare = new ImageIcon(path + "lo23/ui/resources/PossibleSquare.png");
    //JLabel currentSelection = new JLabel("", squareBorder, JLabel.CENTER);
    boolean isCurrentSelectionExist = false;
    boolean isCurrentSelectionOccupied = false;
    JLabel currentPieceSelected;
    PositionOnBoard currentPositionSelection;
 
    
   
    public GamePanel(ApplicationModel model) {
        super();
        model = myModel;
        build();
    }

    private void build() {
        setPreferredSize(new Dimension(GridConstants.SQUARE_SIZE * 8, GridConstants.SQUARE_SIZE * 8));
        setLayout(gameBoard);
        applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    
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
                    receiveSelectedCase(xSquare, ySquare);
//    	        } else if(SwingUtilities.isMiddleMouseButton(mouseEvent) ) {
//    	        	//middle button
//    	        	
//    	        } else if(SwingUtilities.isRightMouseButton(mouseEvent)) {
                    //right button
    
                }
            }
            //Sound bouton
        });
        buildBoard(true);
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
                listOfSelection.put(new PositionOnBoard(i,j), currentSelection);
                currentSelection.setVisible(false);
                
                JLabel possibleCase = new JLabel("", filledSquare, JLabel.CENTER);
                add(possibleCase, constraints, 0);
                listOfSquare.put(new PositionOnBoard(i,j), possibleCase);
                possibleCase.setVisible(false);
                
                if ((i + j) % 2 != 0) {
                    JLabel labelCaseB = new JLabel("", imageCaseB, JLabel.CENTER);
                    add(labelCaseB, constraints, -1);
                } else {
                    JLabel labelCaseN = new JLabel("", imageCaseN, JLabel.CENTER);
                    add(labelCaseN, constraints, -1);
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
                 listOfPiece.put(new PositionOnBoard(i,6), pawnLabel);

            }


            //Add towers pieces to the board
            constraints.gridx = 0;
            constraints.gridy = 7;

            ImageIcon tower = new ImageIcon(path + "lo23/ui/resources/TW.png");
            JLabel towerRight = new JLabel("", tower, JLabel.CENTER);
            add(towerRight, constraints, 1);
            listOfPiece.put(new PositionOnBoard(0, 7), towerRight);

            constraints.gridx = 7;
            JLabel towerLeft = new JLabel("", tower, JLabel.CENTER);
            add(towerLeft, constraints, 1);
            listOfPiece.put(new PositionOnBoard(7, 7), towerLeft);

            //Add knights pieces to the board
            constraints.gridx = 1;
            constraints.gridy = 7;

            ImageIcon knight = new ImageIcon(path + "lo23/ui/resources/KW.png");
            JLabel knightRight = new JLabel("", knight, JLabel.CENTER);
            add(knightRight, constraints, 1);
            listOfPiece.put(new PositionOnBoard(1, 7), knightRight);

            constraints.gridx = 6;
            JLabel knightLeft = new JLabel("", knight, JLabel.CENTER);
            add(knightLeft, constraints, 1);
            listOfPiece.put(new PositionOnBoard(6, 7), knightLeft);

            //Add bishop pieces to the board
            constraints.gridx = 2;
            constraints.gridy = 7;

            ImageIcon bishop = new ImageIcon(path + "lo23/ui/resources/BW.png");
            JLabel bishopRight = new JLabel("", bishop, JLabel.CENTER);
            add(bishopRight, constraints, 1);
            listOfPiece.put(new PositionOnBoard(2, 7), bishopRight);

            constraints.gridx = 5;
            JLabel bishopLeft = new JLabel("", bishop, JLabel.CENTER);
            add(bishopLeft, constraints, 1);
            listOfPiece.put(new PositionOnBoard(5, 7), bishopLeft);

            //Add queen pieces to the board
            constraints.gridx = 3;
            constraints.gridy = 7;

            ImageIcon queen = new ImageIcon(path + "lo23/ui/resources/QW.png");
            JLabel queenPiece = new JLabel("", queen, JLabel.CENTER);
            add(queenPiece, constraints, 1);
            listOfPiece.put(new PositionOnBoard(4, 7), queenPiece);

            //Add king pieces to the board
            constraints.gridx = 4;
            constraints.gridy = 7;

            ImageIcon king = new ImageIcon(path + "lo23/ui/resources/KKW.png");
            JLabel kingPiece = new JLabel("", king, JLabel.CENTER);
            add(kingPiece, constraints, 1);
            listOfPiece.put(new PositionOnBoard(3, 7), kingPiece);
            //Black Pieces :
            //Add pawn pieces to the board

            constraints.gridy = 1;

            for (int i = 0; i < 8; i++){
                constraints.gridx = i; 
                 ImageIcon image = new ImageIcon(path + "lo23/ui/resources/PB.png");
                 JLabel pawnLabel = new JLabel("", image, JLabel.CENTER);
                 add(pawnLabel, constraints, 1);
                 listOfPiece.put(new PositionOnBoard(i,1), pawnLabel);

            }

            //Add towers pieces to the board
            constraints.gridx = 0;
            constraints.gridy = 0;

            ImageIcon towerB = new ImageIcon(path + "lo23/ui/resources/TB.png");
            JLabel towerRightB = new JLabel("", towerB, JLabel.CENTER);
            add(towerRightB, constraints, 1);
            listOfPiece.put(new PositionOnBoard(0, 0), towerRightB);

            constraints.gridx = 7;
            JLabel towerLeftB = new JLabel("", towerB, JLabel.CENTER);
            add(towerLeftB, constraints, 1);
            listOfPiece.put(new PositionOnBoard(7, 0), towerLeftB);

            //Add knights pieces to the board
            constraints.gridx = 1;
            constraints.gridy = 0;

            ImageIcon knightB = new ImageIcon(path + "lo23/ui/resources/KB.png");
            JLabel knightRightB = new JLabel("", knightB, JLabel.CENTER);
            add(knightRightB, constraints, 1);
            listOfPiece.put(new PositionOnBoard(1, 0), knightRightB);

            constraints.gridx = 6;
            JLabel knightLeftB = new JLabel("", knightB, JLabel.CENTER);
            add(knightLeftB, constraints, 1);
            listOfPiece.put(new PositionOnBoard(6, 0), knightLeftB);

            //Add bishop pieces to the board
            constraints.gridx = 2;
            constraints.gridy = 0;

            ImageIcon bishopB = new ImageIcon(path + "lo23/ui/resources/BB.png");
            JLabel bishopRightB = new JLabel("", bishopB, JLabel.CENTER);
            add(bishopRightB, constraints, 1);
            listOfPiece.put(new PositionOnBoard(2, 0), bishopRightB);

            constraints.gridx = 5;
            JLabel bishopLeftB = new JLabel("", bishopB, JLabel.CENTER);
            add(bishopLeftB, constraints, 1);
            listOfPiece.put(new PositionOnBoard(5, 0), bishopLeftB);

            //Add queen pieces to the board
            constraints.gridx = 3;
            constraints.gridy = 0;

            ImageIcon queenB = new ImageIcon(path + "lo23/ui/resources/QB.png");
            JLabel queenPieceB = new JLabel("", queenB, JLabel.CENTER);
            add(queenPieceB, constraints, 1);
            listOfPiece.put(new PositionOnBoard(4, 0), queenPieceB);

            //Add king pieces to the board
            constraints.gridx = 4;
            constraints.gridy = 0;

            ImageIcon kingB = new ImageIcon(path + "lo23/ui/resources/KKB.png");
            JLabel kingPieceB = new JLabel("", kingB, JLabel.CENTER);
            add(kingPieceB, constraints, 1);
            listOfPiece.put(new PositionOnBoard(3, 0), kingPieceB);

        }
    }
    
     private void receiveSelectedCase(int x, int y) {
        //if a case is already selected, the former selection disapears
        if (isCurrentSelectionExist) {
            listOfSelection.get(currentPositionSelection).setVisible(false);
            repaint();
        }
        //TODO corriger le sens de la grille
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 7 - x;
        constraints.gridy = y;

        //add(currentSelection, constraints, 1);
        isCurrentSelectionExist = true;

        PositionOnBoard newSelection = new PositionOnBoard(7 - x, y);
       // GamePiece currentPiece = myModel.getGManager().getCurrentGame().getPieceAtXY(7 - x, y); 
       // showPossiblesMoves(currentPiece);
        
        
        if (listOfPiece.get(newSelection) != null) {
            //save the current position
            currentPieceSelected = listOfPiece.get(newSelection);
            currentPositionSelection = newSelection;
            isCurrentSelectionOccupied = true;
            listOfSelection.get(currentPositionSelection).setVisible(true);
          
        } else if (isCurrentSelectionOccupied) {
            //Move the piece
            
               
            constraints.gridx = 7 - x;
            constraints.gridy = y;
            //remove the former position
            listOfPiece.remove(currentPositionSelection);
            //add the new position
            listOfPiece.put(newSelection, currentPieceSelected);
            //update the display
            add(currentPieceSelected, constraints, 0);
            listOfSelection.get(newSelection).setVisible(false);
            isCurrentSelectionOccupied = false;
           
           
                 Launch_Sound movement = new Launch_Sound("move_piece.wav");
                 movement.play();
            
             
            
        }
    }
     
//    private void showPossiblesMoves(GamePiece piece){
//       // List<Position> cases = piece.getPossibleMoves();
//       // List<Position> cases = new 
//        cases.add(new Position(0,1));
//        cases.add(new Position(2,2));
//        
//        System.out.print(cases);
//
//        
//    }
}
