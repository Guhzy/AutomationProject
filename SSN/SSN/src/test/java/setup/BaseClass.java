package setup;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public class BaseClass extends ConfigReader {

    private WebDriver driver;
    private static int count = 1;

    private static final String USERNAME_ID = "username";
    private static final String PASSWORD_ID = "password";
    private static final String SUBMIT_BUTTON = "submitBtn";
    private static final String LOGIN_SUCCESS_MSG = "frawUser";
    private static final String EMPREGO_XPATH = "//*[@id=\"M7612\"]/span";
    private static final String CARREIRA_XPATH = "//*[@id=\"M7612-M7601-M8039\"]/a/span";
    private static final String IFRAME_CAPTCHA_XPATH = "//iframe[starts-with(@name, 'a-') and starts-with(@src, 'https://www.google.com/recaptcha')]";
    private static final String REGISTER_XPATH = "//*[@id=\"main\"]/div[1]/div/div[2]/div/div[1]/a";
    private static final String CAPTCHA_CHECKBOX_SELECTOR = "div.recaptcha-checkbox-border";

    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to(getUrl());
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    public void loginWebsite(WebDriver driver) {
        driver.findElement(By.id(USERNAME_ID)).sendKeys(getUsername());
        driver.findElement(By.id(PASSWORD_ID)).sendKeys(getPassword());
        driver.findElement(By.name(SUBMIT_BUTTON)).click();
    }

    public void getToCarreira(WebDriver driver) {
        driver.findElement(By.xpath(EMPREGO_XPATH)).click();
        WebElement element = waitForElementsXpath(driver, CARREIRA_XPATH);
        element.click();
    }

    public void saveScreenshot(String name) {
        try {
            File scrFile = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(getScreenshot() + count + "_" + name + ".png"));
            count++;
        } catch (IOException e) {
            System.err.println("Erro a tirar o screenshot: " + e.getMessage());
        }
    }

    public void getToFrame(){
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(IFRAME_CAPTCHA_XPATH)));
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.cssSelector(CAPTCHA_CHECKBOX_SELECTOR))).click();
    }

    public void getToNewTab(WebDriver driver){
        String originalWindow = driver.getWindowHandle();
        assert driver.getWindowHandles().size() == 1;
        driver.findElement(By.xpath(REGISTER_XPATH)).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(numberOfWindowsToBe(2));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public WebElement waitForElementsId(WebDriver driver, String id) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public WebElement waitForElementsXpath(WebDriver driver, String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getUSERNAME_ID() {
        return USERNAME_ID;
    }

    public String getPASSWORD_ID() {
        return PASSWORD_ID;
    }

    public String getSUBMIT_BUTTON() {
        return SUBMIT_BUTTON;
    }

    public String getLOGIN_SUCCESS_MSG() {
        return LOGIN_SUCCESS_MSG;
    }

    public String getEMPREGO_XPATH() {
        return EMPREGO_XPATH;
    }

    public String getCARREIRA_XPATH() {
        return CARREIRA_XPATH;
    }
}