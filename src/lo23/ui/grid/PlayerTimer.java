/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.ui.grid;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author mantonel
 */
public class PlayerTimer {

    private int delay = 1000; //milliseconds
    private Timer timer;
    private int currentTimer;
    private int endOfTimer = 1000*60*20; //20mn

    public PlayerTimer(final TimerPanel timerPanel) {

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                currentTimer++;
                timerPanel.setText(getText());
                //System.out.println(getText());
            }
        };

        currentTimer = 0;
        timer = new Timer(delay, taskPerformer);
        startTimer();
    }

    public void startTimer(){
        timer.start();
    }

    public void stopTimer(){
        currentTimer = 0;
        timer.stop();
    }

    public void breakTimer(){
        timer.stop();
    }

    public void recoveryTimer(){
        timer.start();
    }
    
    //à test
    public void startAt(int s){
        currentTimer = s;
        timer.start();
    }

    public String getText(){

        int hour = 0;
        int minute = 0;
        int second = 0;

        if (currentTimer >= 3600){
            minute = currentTimer%3600;
            hour = (currentTimer - minute)/3600;
            second = minute%60;
            if (minute >= 60){
                minute = (minute - second)/60;
            } else {
                minute = 0;
            }
        } else if (currentTimer >= 60) {
            second = currentTimer%60;
            minute = (currentTimer - second)/60;
        } else second = currentTimer;

        return hour + " : " + minute + " : " + second;
    }



}
