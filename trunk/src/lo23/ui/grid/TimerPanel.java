/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.ui.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author mantonel
 */
public class TimerPanel extends JLabel {

    private PlayerTimer playerTimer;
    private Border border;

    public TimerPanel() {
        setPreferredSize(new Dimension(100,100));
        setHorizontalTextPosition(SwingConstants.CENTER);

        border = new LineBorder(new Color(255));
        this.setBorder(border);

        playerTimer = new PlayerTimer(this);
        playerTimer.startTimer();

    }

}
