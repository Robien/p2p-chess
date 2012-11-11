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
public class Launch_Sound implements Runnable{
      
        public Launch_Sound()
        {
          Thread a = new Thread(this);
          a.start();
        }

        
        public void run()
        {     
            String path = getClass().getClassLoader().getResource(".").getPath();

             Sound application;  // = new Sound();
              try 
              {
                  application = new Sound(path + "lo23/ui/resources/pendant_partie.mp3");
                  application.play();
               
              } catch (Exception ex) 
              {
                  Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
        
}   

