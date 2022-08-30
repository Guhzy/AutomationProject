package step.definitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import setup.BaseClass;

import java.io.FileWriter;

import static org.junit.Assert.assertEquals;

public class PensaoSuccessSteps extends BaseClass{

    private WebDriver driver;
    private String date;
    private FileWriter writer;

    private static final String LOGIN_SUCCESS_MSG = "//*[@id=\"formPassos:idTextoReforma\"]/span[1]";

    @Given("SSN website")
    public void ssn_website() {
        setupDriver();
        driver = getDriver();
        loginWebsite(driver);
        createFile("pensaoSuccess");
        writer = getWriter();
    }

    @And("clicks on pensoes")
    public void clicks_on_pensoes() {
        driver.findElement(By.xpath(getPENSOES_XPATH())).click();
    }

    @When("clicks on simulador de pensoes")
    public void clicks_on_simulador_de_pensoes() {
        WebElement element = waitForElementsXpath(driver, getSIMULADOR_XPATH());
        element.click();
    }

    @And("chooses the simulation")
    public void chooses_the_simulation() {
        driver.findElement(By.xpath(getDROPDOWN_SIMULADOR())).click();
        WebElement element = waitForElementsXpath(driver, getPENSAO_VELHICE());
        element.click();
    }

    @And("^inputs (.*)$")
    public void inputs_date(String date) {
        setDate(date);
        WebElement element = waitForElementsId(driver, getCALENDAR_IN_ID());
        element.sendKeys(date);
    }

    @When("clicks prosseguir")
    public void clicks_prosseguir() {
        retryWaitingForElementAndClick(driver, getBTN_PROSSEGUIR_ID());
    }

    @Then("^checks (.*)$")
    public void checks_output(String valid) {
        String successMessage = waitForElementsXpath(driver, LOGIN_SUCCESS_MSG).getText();
        assertEquals(valid, successMessage);
        saveScreenshot("DataReforma");
        retryWaitingForElementAndClick(driver, getBTN_PASSO_SEGUINTE());
        boolean result = valid.equals(successMessage);
        writeTestResult(date, valid, String.valueOf(result), writer);
        tearDown();
    }

    public void setDate (String date) {
        this.date = date;
    }
}
