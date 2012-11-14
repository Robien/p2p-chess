/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data.serializer.tests;

import java.awt.Image;
import java.util.List;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.NewInvitation;
import lo23.data.Player;
import lo23.data.Position;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.managers.GameManager;
import lo23.data.managers.ProfileManager;
import lo23.data.pieces.GamePiece;
import lo23.utils.Enums.COLOR;
import lo23.utils.Enums.STATUS;

/**
 *
 * @author Romain
 */
public class TestCoupPossible {


    public static void main(String[] args) {
        ApplicationModel app = new ApplicationModel();

        app.setGameManager(new GameManager(app));
        app.setProfileManager(new ProfileManager(app));
        Player p1 = new Player(COLOR.WHITE, 0, new PublicProfile("id", "pseudo", STATUS.CONNECTED, "adress",null, "dupont", "robert", 12));
        Player p2 = new Player(COLOR.BLACK, 0, new PublicProfile("id", "pseudo", STATUS.CONNECTED, "adress", null, "dupont", "robert2", 12));

        Profile pHost = new Profile("", "host", "", STATUS.INGAME, "", null, "", "", 21);
        Profile pGuest = new Profile("", "host", "", STATUS.INGAME, "", null, "", "", 21);
        NewInvitation inv = new NewInvitation(pHost.getPublicProfile(), pGuest.getPublicProfile());
        Game gm = app.getGManager().createGame(inv);
        gm.buildPieces();
       // gm.dumpBoard();

        GamePiece roi = gm.getPieceAtXY(4, 0);
        roi.movePiece(new Position(3, 3));
        //System.out.println(piece.getOwner().getColor());
         GamePiece fou = gm.getPieceAtXY(5, 7);
         fou.movePiece(new Position(5, 5));
       GamePiece piece = gm.getPieceAtXY(0, 0);
        piece.movePiece(new Position(5, 4));

//System.out.println(roi.isOncheck());
        

        List<Position> possibleMoves = piece.getPossibleMoves();
        possibleMoves = piece.removeCheckingMove(possibleMoves);

        TestCoupPossible test = new TestCoupPossible();
        test.dumpBoardAndList(possibleMoves, gm);

    }

    public void dumpBoardAndList(List<Position> list, Game gm) {

        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                GamePiece p = gm.getPieceAtXY(x, y);
                if(p != null)
                {
                    boolean test = false;
                    for (int i = 0; i < list.size();i++)
                    {
                        if (list.get(i).getX() == x && list.get(i).getY() == y)
                        {
                            test = true;
                        }
                    }
                    if (test)
                    {
                        System.out.print("x");
                    }
                    else
                    {
                        System.out.print(p.getClass().getSimpleName().charAt(0));
                    }
                }
                else
                {
                    boolean test = false;
                    for (int i = 0; i < list.size();i++)
                    {
                        if (list.get(i).getX() == x && list.get(i).getY() == y)
                        {
                            test = true;
                        }
                    }
                    if (test)
                    {
                        System.out.print("x");
                    }
                    else
                    {
                       System.out.print("-");
                    }
                }

            }

            System.out.println();
            
        }
    }
}
