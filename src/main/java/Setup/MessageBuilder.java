package Setup;

import io.cucumber.java.Scenario;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Ivan Andraschko
 **/

public class MessageBuilder extends TestEnvironment {

    public void messageStartScenario(Scenario scenario) {
        logger.info(StringUtils.repeat("#", 110));
        logger.info(StringUtils.repeat("=", 46) + " BEFORE SCENARIO " + StringUtils.repeat("=", 47));
        logger.info(StringUtils.repeat("#", 110));
        logger.info(ANSI_BLUE + "SCENARIO NAME: " + scenario.getName().toUpperCase() + ANSI_RESET);
        logger.info(String.format("Chosen executor: \"%S\"", SeleniumDriver.getTestsExecutor()));
    }

    public void messageFinishScenario(Scenario scenario) {
        String status = (scenario.isFailed() ? ANSI_RED + "FAILED STATUS " + ANSI_RESET : ANSI_GREEN + "SUCCESS STATUS " + ANSI_RESET);
        logger.info(StringUtils.repeat("#", 110));
        logger.info(StringUtils.repeat("=", 36) + " SCENARIO FINISHED WITH " + status + StringUtils.repeat("=", 35));
        logger.info(StringUtils.repeat("#", 110));
        System.out.println();
    }
}