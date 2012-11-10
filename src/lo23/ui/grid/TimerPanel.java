/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.ui.grid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author mantonel
 */
@SuppressWarnings("serial")
public class TimerPanel extends JPanel {

    private PlayerTimer playerTimer;
    private Border border;
    JLabel timer;
    JButton start;
    JButton stop;
    JButton pause;
    JButton recovery;

    public TimerPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(100,100));
        
        //setHorizontalTextPosition(SwingConstants.CENTER);

        timer = new JLabel();
        timer.setText("00 : 00 : 00");
        start = new JButton("Start");
        stop = new JButton("Stop");
        pause = new JButton("Pause");
        recovery = new JButton("Recovery");
        add(timer, BorderLayout.NORTH);
//        add(start, BorderLayout.WEST);
//        add(stop, BorderLayout.EAST);
//        add(pause, BorderLayout.CENTER);
//        add(recovery, BorderLayout.SOUTH);

        border = new LineBorder(new Color(255));
        this.setBorder(border);

        playerTimer = new PlayerTimer(this);
        playerTimer.startTimer();

        start.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playerTimer.startTimer();
            }
        });

        stop.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playerTimer.stopTimer();
            }
        });

        pause.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playerTimer.pauseTimer();
            }
        });

        recovery.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playerTimer.recoveryTimer();
            }
        });

    }

    public JLabel getLabel(){
        return timer;
    }

}
