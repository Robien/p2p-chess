
package lo23.ui.grid;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guigou
 */
public class Launch_Sound extends Thread implements Runnable{
        Thread a;
        Sound s;
        Noise n;
        String sound;
        boolean enpause;
        
       
        public Launch_Sound(String sound)
        {
          
         a = new Thread(this);
         if(sound.equals("chess.wav") || sound.equals("chess_login.wav"))
         {
               s = new Sound();
         }
         else
         {
               n = new Noise();
         }
             
       
         enpause=false;
         this.sound = sound;
         
        }
      

        
    @Override
        public void run()
        {    
            
                String path = getClass().getClassLoader().getResource(".").getPath();
                try 
                {
                    
                    if(sound.equals("chess.wav"))
                    {
                         System.out.println("son en boucle");
                         this.s.readAudioFile(path + "lo23/ui/resources/" + sound);
                         
                    }
                    else
                    {
                        System.out.println("son court");
                        n.readAudioFile(path + "lo23/ui/resources/" + sound);
                    }

                } catch (Exception ex) 
                {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
             
        }
        
        public void play()
        {
            if(enpause==false)
            { 
                a.start();
            }
            else
            { 
                a.resume();
            }
        }
        public void pause()
        {
            enpause = true;
            a.suspend();
        }
    
    @Override
        public void interrupt() {
        //super.interrupt();
       
            n.stop_sound(); // Fermeture du flux si l'interruption n'a pas fonctionné.
        
    }
        
                
        
}   

