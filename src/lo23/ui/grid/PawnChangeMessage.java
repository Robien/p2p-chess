/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import lo23.data.pieces.Bishop;
import lo23.data.pieces.GamePiece;
import lo23.data.pieces.Knight;
import lo23.data.pieces.Pawn;
import lo23.data.pieces.Queen;
import lo23.data.pieces.Rook;

/** Message Box for paw tranformation
 * this class provides a personnal managment of the paw tranformation.
 * 
 * @author Laura
 */

public class PawnChangeMessage{
    private String path = getClass().getClassLoader().getResource(".").getPath();
    Pawn pawn;
    public PawnChangeMessage(Pawn p) {
        this.pawn = p ;
    }
    
    /**
     * Affichage de la fenêtre de message personnalisée
     * @return GamePiece the piece that will replace our pawn
     */
    public GamePiece display(){
    String[] pieces = {"Knight", "Bishop", "Queen", "Rook"};
      
    // Message creation
    JOptionPane jop = new JOptionPane();
    int rang = jop.showOptionDialog(null, "Transformer le pion en : ", "Pawn", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
      null, pieces, pieces[3]);
    
    switch(rang){
        case 0 : return (new Knight(pawn.getPosition(), pawn.getOwner(), pawn.getGame()));
        case 1 : return (new Bishop(pawn.getPosition(), pawn.getOwner(), pawn.getGame()));
        case 2 : return (new Queen(pawn.getPosition(), pawn.getOwner(), pawn.getGame()));
        case 3 : return (new Rook(pawn.getPosition(), pawn.getOwner(), pawn.getGame()));
        default: return pawn; // There's no reason to pass here, but it could be safer to do this
    }
  }
}

/*
 Pour afficher la fenêtre :
 PawChangeMessage pcm = new PawChangeMessage();
 pcm.display(); 
 */