package logging;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Logging {

    private static final Logger LOGGER = Logger.getRootLogger();

    public Logging() {
        BasicConfigurator.configure();
        LOGGER.setLevel(Level.INFO);
    }

    public static void log(String response) {
        LOGGER.info(response);
    }
}
