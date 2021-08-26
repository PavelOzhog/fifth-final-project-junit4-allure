package ru.appline.framework.managers;



import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

import static ru.appline.framework.utils.PropConst.PATH_CHROME_DRIVER_WINDOWS;
import static ru.appline.framework.utils.PropConst.TYPE_BROWSER;

public class DriverManager {

    private WebDriver driver = null;
    private final TestPropManager testPropManager = TestPropManager.getTestPropManager();

    private static DriverManager INSTANSE = null;

    private DriverManager() {
    }

    public static DriverManager getDriverManager() {
        if (INSTANSE == null) {
            INSTANSE = new DriverManager();
        }
        return INSTANSE;
    }



    public WebDriver getDriver() {
        if (driver == null) {
            switch (testPropManager.getProperty(TYPE_BROWSER)) {
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", testPropManager.getProperty("webdriver.gecko.driver"));
                    driver = new FirefoxDriver();
                    break;
                case "chrome":
                    ChromeOptions options = new ChromeOptions();
                    //options.addArguments("incognito");
                    options.addArguments("user-data-dir=C:\\Users\\flash\\AppData\\Local\\Google\\Chrome\\User Data");
                    options.addArguments("--disable-notifications");
                    System.setProperty("webdriver.chrome.driver", testPropManager.getProperty(PATH_CHROME_DRIVER_WINDOWS));
                    driver = new ChromeDriver(options);
                    //driver.manage().addCookie();
                    break;
                case "remote":
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setBrowserName("chrome");
                    capabilities.setVersion("73.0");
                    capabilities.setCapability("enableVNC", true);
                    capabilities.setCapability("enableVideo", false);
                    try {
                        driver = new RemoteWebDriver(
                                URI.create("http://selenoid.appline.ru:4445/wd/hub/").toURL(),
                                capabilities);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                default:


                    Assert.fail("Типа браузера '" + testPropManager.getProperty("browser") + "' не существует во фреймворке");
            }
        }

        return driver;
    }





    private void initDriverAnyOsFamily(String gecko, String chrome) {
        switch (testPropManager.getProperty(TYPE_BROWSER)) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", testPropManager.getProperty(gecko));
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", testPropManager.getProperty(chrome));
                driver = new ChromeDriver();
                break;
            case "remote":
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                capabilities.setVersion("73.0");
                capabilities.setCapability("enableVNC", true);
                capabilities.setCapability("enableVideo", false);
                try {
                    driver = new RemoteWebDriver(
                            URI.create("http://selenoid.appline.ru:4445/wd/hub/").toURL(),
                            capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            default:


                Assert.fail("Типа браузера '" + testPropManager.getProperty(TYPE_BROWSER) + "' не существует во фреймворке");
        }
    }


    public void quitDriver() {
        driver.quit();
    }


}
