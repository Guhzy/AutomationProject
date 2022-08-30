package step.definitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import setup.BaseClass;

import java.io.FileWriter;

import static org.junit.Assert.assertEquals;

public class DateSuccessSteps extends BaseClass{

    private WebDriver driver;
    private String date;
    private FileWriter writer;

    private static final String BTN_PRINT_ID = "formPassos:sipBtnImprimirSimulacaoSuperior";
    private static final String BTN_OBTER_DESC_ID = "formPassos:sipButtonObterDescontos";

    @Given("inside simulacao page")
    public void in_the_simulacao_page() {
        setupDriver();
        driver = getDriver();
        getToDateInputPage(driver);
        createFile("dateSuccess");
        writer = getWriter();
    }

    @Given("^inserts retirement (.*)$")
    public void inserts_retirement(String date) {
        setDate(date);
        waitForElementsId(driver, getCALENDAR_IN_ID()).sendKeys(date);
        saveScreenshot("InputsDateSuccess");
        waitForElementsId(driver, getBTN_PROSSEGUIR_ID()).click();
    }

    @When("clicks on salarios")
    public void clicks_on_salarios() {
        retryWaitingForElementAndClick(driver, getBTN_PASSO_SEGUINTE());
    }

    @When("clicks resultado da simulacao")
    public void clicks_resultado_da_simulacao() {
        retryWaitingForElementAndClick(driver, getBTN_PASSO_SEGUINTE());
        saveScreenshot("Salarios");
    }

    @Then("^check if (.*) is correct$")
    public void check_if_msg_is_correct(String msg) {
        retryWaitingForElementAndClick(driver, BTN_OBTER_DESC_ID);
        waitForElementsId(driver, BTN_PRINT_ID);
        assertEquals(msg, driver.findElement(By.id(BTN_PRINT_ID)).getText());
        saveScreenshot("ResultadoSimulacaoPDF");
        boolean result = msg.equals(driver.findElement(By.id(BTN_PRINT_ID)).getText());
        writeTestResult(date, msg, String.valueOf(result), writer);
        tearDown();
    }

    public void setDate(String date) {
        this.date = date;
    }
}
