package com.example.zzjp.clothesShop.jmx;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Profile("default")
@Component
@ManagedResource
@Slf4j
public class LoggerLevelManager {

    @Value("${logging.level.}")
    private String loggingLevel;

    @ManagedAttribute
    public String getLoggingLevel() {
        return loggingLevel;
    }

    @ManagedAttribute
    public void setLoggingLevel(final String loggingLevel) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        if (loggingLevel.equalsIgnoreCase("DEBUG")) {
            loggerContext.getLogger("ROOT").setLevel(Level.DEBUG);
            this.loggingLevel = Level.DEBUG.toString();
        } else if (loggingLevel.equalsIgnoreCase("INFO")) {
            loggerContext.getLogger("ROOT").setLevel(Level.INFO);
            this.loggingLevel = Level.INFO.toString();
        } else if (loggingLevel.equalsIgnoreCase("TRACE")) {
            loggerContext.getLogger("ROOT").setLevel(Level.TRACE);
            this.loggingLevel = Level.TRACE.toString();
        }
    }
}
