package Setup.Listener;

import Setup.TestEnvironment;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @author Ivan Andraschko
 **/

public class TestNGRetry extends TestEnvironment implements IRetryAnalyzer {

    private int retry = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            int retryLimit = 1;
            if (retry < retryLimit) {
                retry++;
                iTestResult.setStatus(ITestResult.FAILURE);
                logger.info(String.format(ANSI_RED + "TEST RETRY (%d/" + retryLimit + "): %S" + ANSI_RESET, retry,
                        iTestResult.getMethod().getDescription()));
                return true;
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
}
