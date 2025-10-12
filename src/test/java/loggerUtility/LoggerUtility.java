package loggerUtility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtility {

    // need to define a logger instance
    // need to define a method to start a test
    // need to define a method to stop a test
    // need to define a method for info, a method for error

    private static final Logger logger = LogManager.getLogger();

    public static void startTestCase(String testName){
        logger.info("******** Execution started: " + testName + " ********");
    }

    public static void finishTestCase(String testName){
        logger.info("******** Execution finished: " + testName + " ********");
    }

    public static void infoTest(String message){
        logger.info(message);
    }

    public static void errorTest(String message){
        logger.error(message);
    }
}
