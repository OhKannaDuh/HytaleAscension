package faye.rpg;

import com.google.inject.Inject;
import com.hypixel.hytale.logger.HytaleLogger;

import java.util.logging.Level;

public class Logger {
    private final HytaleLogger logger;

    @Inject
    public Logger(HytaleLogger logger) {
        this.logger = logger;
    }

    public void info(String message) {
        logger.at(Level.INFO).log(message);
    }

    public void warn(String message) {
        logger.at(Level.WARNING).log(message);
    }

    public void severe(String message) {
        logger.at(Level.SEVERE).log(message);
    }

    public void severe(String message, Throwable t) {
        logger.at(Level.SEVERE).log(message, t);
    }
}
