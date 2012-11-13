package lo23.data.managers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import lo23.data.ApplicationModel;

/**
 * This class is the main Manager for the data management
 *
 * @author khamidou
 */
public class Manager {


    private PropertyChangeSupport notifier;


    private ApplicationModel app;


    public Manager(ApplicationModel app) {
        this.app = app;
        notifier = new PropertyChangeSupport(this);
    }


    public ApplicationModel getApplicationModel() {
        return app;
    }


    /**
     * This method implements the Publish / Subscribe pattern.
     * It notifies this that the listener argument wants to received data sent on the channel given as argument
     *
     * @param listener The object that wants to received notification for the specific channel
     * @param channel The channel onto the listener argument will be listening to
     */
    public void subscribe(PropertyChangeListener listener, String channel) {
        notifier.addPropertyChangeListener(channel, listener);
    }

    /**
     * This method implements the Publish / Subscribe pattern.
     * It sends data to registered subscribers, on a specific channel.
     *
     * @param channel The channel onto data are sent.
     * @param value The data to be sent.
     */
    public void publish(String channel, Object value)
    {
        notifier.firePropertyChange(channel, null, value);
    }



}