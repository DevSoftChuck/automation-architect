package setup;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public enum DriverOptions {

    FIREFOX {
        public FirefoxOptions getOptions() {
            FirefoxOptions options = new FirefoxOptions();


            return options;
        }
    },
    CHROME {
        public ChromeOptions getOptions() {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--lang=en");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--disable-popup-blocking");
            chromeOptions.addArguments("--disable-notifications");
            chromeOptions.addArguments("--ignore-certificate-errors");
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

            return chromeOptions;
        }
    };

    public abstract Capabilities getOptions();
}
