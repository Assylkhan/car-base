package com.epam.action;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ActionFactory {
    public static final Logger log = Logger.getLogger(ActionFactory.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("actions");
    private static Map<String, Action> actions = new HashMap<>();
    private static final String PACKAGE = "com.epam.action.";

    public ActionFactory() {

        for (String key : bundle.keySet()) {
            String actionName = bundle.getString(key);
            Action action = null;
            if (actionName.endsWith("Action")) {
                try {
                    action = (Action) Class.forName(PACKAGE+actionName).newInstance();
                } catch (InstantiationException e) {
                    log.error(e);
                } catch (IllegalAccessException e) {
                    log.error(e);
                } catch (ClassNotFoundException e) {
                    log.error(e);
                }
            } else {
                action = new ShowPageAction(actionName);
            }
            actions.put(key, action);
        }

    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}
