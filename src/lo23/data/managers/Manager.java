package lo23.data.managers;

import lo23.data.ApplicationModel;

/**
 * This class is the main Manager for the data management
 *
 * @author khamidou
 */
public class Manager {

    private ApplicationModel app;


    public Manager(ApplicationModel app) {
        this.app = app;
    }


    public ApplicationModel getApplicationModel() {
        return app;
    }



}
