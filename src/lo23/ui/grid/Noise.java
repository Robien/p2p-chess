package lo23.ui.grid;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
 
 
public class Noise
{
    static SourceDataLine source;
    static boolean boucle = true;
    static boolean lancer = false;
   
    
    
     public  void readAudioFile(String fileName) throws IOException, UnsupportedAudioFileException, LineUnavailableException
    {
     
        lancer = true;
      
           
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
        
            AudioFormat format = ais.getFormat();
            Info info = new Info(SourceDataLine.class, format);
             source = (SourceDataLine)AudioSystem.getLine(info);
            source.open(format);
            source.start();
           

            int read = 0;
            byte[] audioData = new byte[16384];

            while(read > -1)
                {
                read = ais.read(audioData, 0 , audioData.length);
                if(read >= 0)
                    source.write(audioData, 0, read);
                }
            source.drain();
            source.close();
     
      }
    
    
    public void stop_sound()
    {
        if(lancer == true)
        { 
            System.out.println("entre dans stop sound");
            source.drain();
            source.close();
        }
    }
    
}
