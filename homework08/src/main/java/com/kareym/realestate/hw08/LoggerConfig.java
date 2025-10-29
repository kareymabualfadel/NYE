package com.kareym.realestate.hw08;

import java.io.IOException;
import java.util.logging.*;

/**
 * Initializes java.util.logging once for the whole app.
 * Creates/uses a file named 'realEstateApp.log' in the project root.
 */
public final class LoggerConfig {
    private static boolean initialized = false;

    private LoggerConfig() {}

    /** Call once at program start to setup logging to file + console. */
    public static synchronized void init() {
        if (initialized) return;
        try {
            Logger root = Logger.getLogger("");
            root.setUseParentHandlers(false);

            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            ch.setFormatter(new SimpleFormatter());

            FileHandler fh = new FileHandler("realEstateApp.log", true);
            fh.setLevel(Level.INFO);
            fh.setFormatter(new SimpleFormatter());

            for (Handler h : root.getHandlers()) root.removeHandler(h);
            root.addHandler(ch);
            root.addHandler(fh);

            root.setLevel(Level.INFO);
            initialized = true;
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to init logging", e);
        }
    }
}
