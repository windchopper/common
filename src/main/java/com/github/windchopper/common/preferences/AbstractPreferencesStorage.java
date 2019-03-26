package com.github.windchopper.common.preferences;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractPreferencesStorage implements PreferencesStorage {

    protected static final Logger logger = Logger.getLogger("com.github.windchopper.common.preferences");

    protected void logError(Throwable exception) {
        logger.log(Level.SEVERE, "Unexpected error", exception);
    }

}
