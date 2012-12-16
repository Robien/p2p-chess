
package lo23.ui.grid;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lo23.utils.ResourceManager;

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
            
                String path = getClass().getResource("/lo23/ui/resources/").getPath();
//                File dir1 = new File(".");
//                try
//                {
//                    path = dir1.getCanonicalPath();
//                } catch (IOException ex)
//                {
//                    Logger.getLogger(Launch_Sound.class.getName()).log(Level.SEVERE, null, ex);
//                }

                try
                {
                    
                    if(sound.equals("chess.wav"))
                    {
                        // System.out.println("son en boucle");
                         this.s.readAudioFile(ResourceManager.getInstance().getResource(sound));
                         
                    }
                    else
                    {
                       // System.out.println("son court");
                        this.n.readAudioFile(ResourceManager.getInstance().getResource(sound));
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
    
   
        
                
        
}   

