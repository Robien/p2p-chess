package lo23.communication.tests;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import lo23.communication.ComManager;
import lo23.data.ApplicationModel;
import lo23.data.Constant;
import lo23.data.Event;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.Message;
import lo23.data.Move;
import lo23.data.NewInvitation;
import lo23.data.Position;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.exceptions.NoIdException;
import lo23.data.managers.GameManagerInterface;
import lo23.data.managers.Manager;
import lo23.data.managers.ProfileManagerInterface;
import lo23.data.pieces.GamePiece;
import lo23.utils.Enums;
import lo23.utils.Enums.COLOR;
import lo23.utils.Enums.CONSTANT_TYPE;
import lo23.utils.Enums.STATUS;


public class MainTest1 {
    
    public static void main(String[] args) {
        MainTest1 main = new MainTest1();
    }
    
    public MainTest1() {
        try { 
            Scanner scanner = new Scanner(System.in);
            
            String addressIp = null;
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); 
            while (e.hasMoreElements()) { 
                Enumeration<InetAddress> i = e.nextElement().getInetAddresses(); 
                while (i.hasMoreElements()){ 
                    InetAddress a = i.nextElement();
                    if (!a.isLoopbackAddress() && !(a instanceof Inet6Address)) {
                        addressIp = a.getHostAddress();
                        System.out.print("\nIP : "+ addressIp);
                    }
                }
            }
            
            System.out.print("\nEntrez votre pseudo : ");
            String pseudo = scanner.nextLine().trim();
            PublicProfile profile = new PublicProfile("1", pseudo, Enums.STATUS.CONNECTED, addressIp, null, "Vincent", "Penot", 23, 0, 0, 0);
            
            ApplicationModel appModel = new ApplicationModel();
            appModel.setComManager(new ComManager(profile,appModel));
            appModel.setGameManager(new MyGameManagerMock(appModel));
            appModel.setProfileManager(new MyProfileManagerMock(appModel));
            
            String menuStr;
            char menu;
            do {
                System.out.print("\na. Voir les utilisateurs disponibles");
                System.out.print("\nb. Se connecter à un utilisateur");
                System.out.print("\nVotre choix: ");
                
                menuStr = scanner.nextLine().trim().toLowerCase();
                if (!menuStr.isEmpty()) {
                    menu = menuStr.charAt(0);
                    switch(menu) {
                        case 'a':
                            appModel.getComManager().sendMulticast();
                            break;
                        case 'b':
                            //System.out.print("Adresse IP : ");
                            //String ipAddress = scanner.nextLine().trim();
                            Invitation invit = new NewInvitation(COLOR.BLACK, 3600, profile, profile);
                            appModel.getComManager().sendInvitation(invit);
                            break;
                    }
                }
            } while (!menuStr.isEmpty());
            
        } catch (SocketException ex) {
            Logger.getLogger(MainTest1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class MyGameManagerMock extends Manager implements GameManagerInterface {
        
        public MyGameManagerMock(ApplicationModel model) {
            super(model);
        }
        
        @Override
        public void save() throws NoIdException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Game load(long gameId) throws FileNotFoundException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Move createMove(Position to, GamePiece piece) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void sendMove(Move move) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void playMove(Move move) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Message createMessage(String content) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void sendMessage(Message message) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void saveMessage(Message message) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ArrayList<Event> getHistory() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyChatMessage(Message message) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Game createGame(Invitation invitation) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Constant createConstant(CONSTANT_TYPE constant) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void sendConstant(Constant constant) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyConstantMessage(Constant constant) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void saveConstant(Constant constant) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyGameEnded() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Game notifyGameStarted(Invitation invitation) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ArrayList<Game> getListStopGames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ArrayList<Game> getListStartGames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ArrayList<Game> getListAllGames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Game getCurrentGame() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyGameStarted(PublicProfile userProfile) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyMovement(Move move) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    private class MyProfileManagerMock extends Manager implements ProfileManagerInterface {
        
        public MyProfileManagerMock(ApplicationModel model) {
            super(model);
        }
        
        @Override
        public Profile getCurrentProfile() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Profile createProfile(String profileId, String pseudo, char[] password, STATUS status, String ipAddress, ImageIcon avatar, String name, String firstName, int age) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void startProfilesDiscovery() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean connection(String profileId, char[] password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ArrayList<PublicProfile> getLocalPublicProfiles() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void saveProfile() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Profile loadProfile(String profileId) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyInvitation(Invitation invitation) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Invitation createInvitation(PublicProfile guest, COLOR color, long duration) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void sendInvitation(Invitation invitation) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void notifyAddProfile(PublicProfile publicProfile) {
            System.out.print("\nUtilisateur disponible :");
            System.out.print("\n  pseudo : "+publicProfile.getPseudo());
            System.out.print("\n  adresse IP : "+publicProfile.getIpAddress());
        }

        @Override
        public void notifyInvitAnswer(Invitation invitation, boolean answer) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void exportProfile(String filePath) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void importProfile(String filePath) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    
}
