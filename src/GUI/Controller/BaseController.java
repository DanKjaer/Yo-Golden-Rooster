package GUI.Controller;

import GUI.Model.PmcModel;

public abstract class BaseController {

    private PmcModel model;

    public void setModel(PmcModel model) {
        this.model = model;
    }

    public PmcModel getModel() {
        return model;
    }

    public abstract void setUp();
}
