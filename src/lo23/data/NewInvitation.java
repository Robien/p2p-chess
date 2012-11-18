package lo23.data;

import java.util.Date;
import lo23.utils.Enums.COLOR;

public class NewInvitation extends Invitation {

    private COLOR color;
    private long duration;
    private Date date;

    public NewInvitation(COLOR color, long duration, PublicProfile host, PublicProfile guest) {
        super(host, guest);
        this.color = color;
        this.duration = duration;
        this.date = new Date();
    }

    public COLOR getColor() {
        return color;
    }

    public long getDuration() {
        return duration;
    }

    public Date getDate() {
        return date;
    }
}
