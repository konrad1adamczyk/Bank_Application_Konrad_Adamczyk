package com.luxoft.bankapp.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Konrad on 23.01.2016.
 */
public class ConfigLoggers {
    public ConfigLoggers() throws IOException {

        Logger logger = Logger.getLogger("exceptions");
        logger.addHandler(new FileHandler("exceptions.log"));

        logger = Logger.getLogger("clients");
        logger.addHandler(new FileHandler("clients.log"));

        logger = Logger.getLogger("database");
        logger.addHandler(new FileHandler("database.log"));
    }
}
