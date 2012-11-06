/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data.managers;

import lo23.data.ApplicationModel;

/**
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
