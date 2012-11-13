/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
        boolean enpause;
        public Launch_Sound()
        {
          a = new Thread(this);
          s = new Sound();
          enpause=false;
        }

        
        public void run()
        {    
            while(true)
            {
                String path = getClass().getClassLoader().getResource(".").getPath();
                try 
                {
                    s.readAudioFile(path + "lo23/ui/resources/pendant_partie.wav");


                } catch (Exception ex) 
                {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
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

