/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.communication.tests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lo23.communication.connection.ConnectionManager;
import lo23.communication.connection.ConnectionParams;
import lo23.communication.message.Message;
import lo23.communication.message.MulticastInvit;
import lo23.data.PublicProfile;


/**
 *
 * @author Paco
 */

public class MainTestMulticast {
    PublicProfile Profil_to_send = new PublicProfile();

    // Multicast Socket
    private MulticastSocket multicastSocket; //server
    private DatagramSocket datagramSocket; //client

    public void sendMulticast() {
       // Message creation
       MulticastInvit Message = new MulticastInvit(this.Profil_to_send);
       byte b[] = this.serialize(Message);
       DatagramPacket dgram = null;
       try {
           dgram = new DatagramPacket(b, b.length, InetAddress.getByName(ConnectionParams.multicastAddress), ConnectionParams.multicastPort); // multicast
       } catch (UnknownHostException ex) {
       Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error during the datagramme creation", ex);
       }

       try {
           this.datagramSocket.send(dgram);
       } catch (IOException ex) {
           Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error: Cannot sent datagramme on Multicast server", ex);
       }
    }


    private byte[] serialize(Message message){
       ByteArrayOutputStream b_out = new ByteArrayOutputStream();
       ObjectOutputStream o_out = null;
       try {
           o_out = new ObjectOutputStream(b_out);
       } catch (IOException ex) {
           Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
       }

       try {
           // Message serialization
           o_out.writeObject(message);
       } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error during the message serialization", ex);
       }
       byte[] b = b_out.toByteArray();

       return b;
    }
}
