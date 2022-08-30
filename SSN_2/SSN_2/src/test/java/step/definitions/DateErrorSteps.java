package step.definitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import setup.BaseClass;

import java.io.FileWriter;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class DateErrorSteps extends BaseClass {
    private WebDriver driver;
    private String year;
    private FileWriter writer;

    private static final String ERROR_MSG_CLASS = "ui-messages-error-summary";

    @Given("simulacao page")
    public void simulacao_page() {
        setupDriver();
        driver = getDriver();
        getToDateInputPage(driver);
        createFile("dateError");
        writer = getWriter();
    }

    @And("^insert retirement (.*)$")
    public void insert_retirement_year(String year) {
        this.year = year;
        waitForElementsId(driver, getCALENDAR_IN_ID()).sendKeys(year);
        saveScreenshot("InputsDateError");
        waitForElementsId(driver, getBTN_PROSSEGUIR_ID()).click();
    }

    @When("click on salarios")
    public void click_on_salarios() {
        retryWaitingForElementAndClick(driver, getBTN_PASSO_SEGUINTE() );
    }

    @And("click resultado da simulacao")
    public void click_resultado_da_simulacao() {
        retryWaitingForElementAndClick(driver,getBTN_PASSO_SEGUINTE());
    }

    @Then("^check if (.*) is expected$")
    public void check_if_error_is_expected(String output) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        assertEquals(output, wait.until(ExpectedConditions.presenceOfElementLocated(By.className(ERROR_MSG_CLASS))).getText());
        saveScreenshot("ErrorMessageDate");
        boolean result = output.equals(driver.findElement(By.className(ERROR_MSG_CLASS)).getText());
        writeTestResult(year, output, String.valueOf(result), writer);
        tearDown();
    }
}
