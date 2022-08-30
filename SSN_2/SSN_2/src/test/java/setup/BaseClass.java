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
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class BaseClass extends ConfigReader{

    private WebDriver driver;
    private List<WebElement> elementList;
    private static int count = 1;
    private FileWriter writer;

    private static final String USERNAME_ID = "username";
    private static final String PASSWORD_ID = "password";
    private static final String SUBMIT_BUTTON = "submitBtn";
    private static final String PENSOES_XPATH = "//*[@id=\"M7645\"]/span";
    private static final String SIMULADOR_XPATH = "//*[@id=\"M7645-M7646-M7914\"]/a/span";
    private static final String DROPDOWN_SIMULADOR = "//*[@id=\"formPaginaPrincipal:menuButtonPrincipalSip_button\"]/span[2]";
    private static final String PENSAO_VELHICE = "//*[@id=\"formPaginaPrincipal:menuButtonItemOne\"]/span";
    private static final String CALENDAR_IN_ID = "formPassos:calendarDataInicioPensao:calendar_input";
    private static final String BTN_PASSO_SEGUINTE_ID = "formPassos:btnPassoSeguinte";
    private static final String BTN_PROSSEGUIR_ID = "formPassos:buttonProsseguir";
    private static final String YEAR = "20381212";

    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to(getURL());
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void loginWebsite(WebDriver driver) {
        driver.findElement(By.id(USERNAME_ID)).sendKeys(getUsername());
        driver.findElement(By.id(PASSWORD_ID)).sendKeys(getPassword());
        driver.findElement(By.name(SUBMIT_BUTTON)).click();
    }

    public void getToDateInputPage(WebDriver driver) {
        loginWebsite(driver);
        driver.findElement(By.xpath(getPENSOES_XPATH())).click();
        waitForElementsXpath(driver, getSIMULADOR_XPATH()).click();
        driver.findElement(By.xpath(getDROPDOWN_SIMULADOR())).click();
        waitForElementsXpath(driver, getPENSAO_VELHICE()).click();
    }

    public void getToSalaryPage(WebDriver driver) {
        getToDateInputPage(driver);
        waitForElementsId(driver, getCALENDAR_IN_ID()).sendKeys(YEAR);
        retryWaitingForElementAndClick(driver, getBTN_PROSSEGUIR_ID());
        retryWaitingForElementAndClick(driver, getBTN_PASSO_SEGUINTE());
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

    public WebElement waitForElementsId(WebDriver driver, String id) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public WebElement waitForElementsXpath(WebDriver driver, String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public void createNewListAndClick(String className, int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        elementList = driver.findElements(By.className(className));
        wait.until(ExpectedConditions.elementToBeClickable(elementList.get(index))).click();
    }

    public void retryWaitingForElementAndClick(WebDriver driver, String id) {
        int count = 10;
        while (count != 0) {
            try {
                waitForElementsId(driver, id).click();
                break;
            } catch (Exception e) {
                count--;
                if(count == 0)
                    throw e;
            }
        }
    }

    public void writeTestResult(String input, String output, String testResult, FileWriter writer) {
        try {
            writer.append(input);
            writer.append(',');
            writer.append(output);
            writer.append(',');
            writer.append(testResult);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing test result into the csv file: " + e.getMessage());
        }
    }

    public void createFile(String name) {
        try {
            writer = new FileWriter(getResult().replace("name", name));
        } catch (IOException e) {
            System.err.println("Error creating test result file: " + e.getMessage());
        }
    }

    public FileWriter getWriter() {
        return writer;
    }

    public String getPENSOES_XPATH() {
        return PENSOES_XPATH;
    }

    public String getSIMULADOR_XPATH() {
        return SIMULADOR_XPATH;
    }

    public String getDROPDOWN_SIMULADOR() {
        return DROPDOWN_SIMULADOR;
    }

    public String getPENSAO_VELHICE() {
        return PENSAO_VELHICE;
    }

    public String getCALENDAR_IN_ID() {
        return CALENDAR_IN_ID;
    }

    public String getBTN_PASSO_SEGUINTE() {
        return BTN_PASSO_SEGUINTE_ID;
    }

    public String getBTN_PROSSEGUIR_ID() {
        return BTN_PROSSEGUIR_ID;
    }
}
