package com.createhaus.biscotti;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BPresenter {

    protected Map<String, BView> views = new HashMap<>();
    protected Map<String, BModel> models = new HashMap<>();
    protected List<BError> errors = new ArrayList<>();

    public BModel getModel(String name) {
        return models.get(name);
    }

    public void addView(String name, BView view) {
        views.put(name, view);
    }
    
    public void addModel(String name, BModel model) {
        models.put(name, model);
    }

    public void submit() {
        // Clear the errors
        errors = new ArrayList<>();

        // Synchronize the model with the view
        for (String key: models.keySet()) {
            BModel model = models.get(key);

            // Look for the set methods
            Class c = model.getClass();

            Method methods[] = c.getDeclaredMethods();
            for(Method method : methods) {
                // See if this is a setter
                if(method.getName().indexOf("set") == 0) {

                    // Do we have a corresponding getter on the corresponding view
                    try {
                        BView view = views.get(key);

                        Method getter = view.getClass().getMethod("get" + method.getName().substring(3));

                        // Invoke the setter of the model with the getter of the view
                        method.invoke(model, getter.invoke(view));
                    }
                    catch(Exception e) {}
                }
            }
        }
    }

    public void display() {
        // Synchronize the model with the view
        for (String key: views.keySet()) {
            BView view = views.get(key);

            // Look for the set methods
            Class c = view.getClass();

            Method methods[] = c.getDeclaredMethods();
            for(Method method : methods) {
                // See if this is a setter
                if(method.getName().indexOf("set") == 0) {

                    // Do we have a corresponding getter on the corresponding view
                    try {
                        BModel model = models.get(key);
                        Method getter = model.getClass().getMethod("get" + method.getName().substring(3));

                        // Invoke the setter of the model with the getter of the view
                        method.invoke(view, getter.invoke(model));
                    }
                    catch(Exception e) {}
                }
            }
        }
    }
}
