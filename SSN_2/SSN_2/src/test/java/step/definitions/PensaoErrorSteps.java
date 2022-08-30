package step.definitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import setup.BaseClass;

import java.io.FileWriter;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class PensaoErrorSteps extends BaseClass {

    private WebDriver driver;
    private String date;
    private FileWriter writer;
    private static int count = 1;

    private static String login_error_msg = "ui-message-error-detail";

    @Given("inside SSN website")
    public void inside_ssn_website() {
        createFile("pensaoError"+count);
        setupDriver();
        driver = getDriver();
        loginWebsite(driver);
        writer = getWriter();
        count++;
    }

    @And("click on pensoes")
    public void click_on_pensoes() {
        driver.findElement(By.xpath(getPENSOES_XPATH())).click();
    }

    @When("click on simulador de pensoes")
    public void click_on_simulador_de_pensoes() {
        WebElement element = waitForElementsXpath(driver, getSIMULADOR_XPATH());
        element.click();
    }

    @And("choose the simulation")
    public void choose_the_simulation() {
        driver.findElement(By.xpath(getDROPDOWN_SIMULADOR())).click();
        WebElement element = waitForElementsXpath(driver, getPENSAO_VELHICE());
        element.click();
    }

    @And("^input (.*)$")
    public void input_date(String date) {
        setDate(date);
        WebElement element = waitForElementsId(driver, getCALENDAR_IN_ID());
        element.sendKeys(date);
    }

    @Then("^check if date is (.*)$")
    public void check_if_date_is_valid(String valid) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(500));
        retryWaitingForElementAndClick(driver, getBTN_PROSSEGUIR_ID());

        try{
            login_error_msg = "ui-message-error-detail";
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className(login_error_msg)));
            assertEquals(valid, driver.findElement(By.className(login_error_msg)).getText());
            saveScreenshot("ErrorMessage");
        } catch(Exception e) {
            login_error_msg = "ui-messages-error-summary";
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className(login_error_msg)));
            assertEquals(valid, driver.findElement(By.className(login_error_msg)).getText());
            saveScreenshot("ErrorMessage");
        }
        boolean result = valid.equals(driver.findElement(By.className(login_error_msg)).getText());
        writeTestResult(date, valid, String.valueOf(result), writer);
        tearDown();
    }

    public void setDate(String date) {
        this.date = date;
    }
}
