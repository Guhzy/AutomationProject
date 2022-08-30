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

public class SalarioSteps extends BaseClass {

    private WebDriver driver;
    private Integer check;
    private FileWriter writer;

    private static final String SALARY_BOX_XPATH = "//*[@id=\"formPassos:sipSimuladorTabelaTresId:0:sipInputValorRemuneracaoTres\"]";
    private static final String PENCIL_CLASS = "ui-icon-pencil";
    private static final String CHECK_CLASS = "ui-icon-check";
    private static final String BTN_DESCONTOS_ID = "formPassos:sipButtonObterDescontos";

    @Given("salarios page")
    public void salarios_page() {
        setupDriver();
        driver = getDriver();
        getToSalaryPage(driver);
        createFile("salario");
        writer = getWriter();
    }

    @And("^select (.*)$")
    public void select_pencil(Integer pencil) {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.className("ui-icon-pencil")));
        createNewListAndClick(PENCIL_CLASS, pencil);
    }

    @When("^write new salary (.*)$")
    public void write_new_salary_value(String value) {
        waitForElementsXpath(driver, SALARY_BOX_XPATH);
        WebElement element = driver.findElement(By.xpath(SALARY_BOX_XPATH));
        element.clear();
        element.sendKeys(value);
        saveScreenshot("NewValorSalario");
    }

    @And("^verify (.*)$")
    public void verify_check(Integer check) {
        setCheck(check);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.className(CHECK_CLASS)));
        createNewListAndClick(CHECK_CLASS, check);
    }

    @Then("check new retirement salary")
    public void check_new_retirement_salary() {
        retryWaitingForElementAndClick(driver, getBTN_PASSO_SEGUINTE());
        retryWaitingForElementAndClick(driver, BTN_DESCONTOS_ID);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
        saveScreenshot("NewResultadoSimulacaoPDF");
        writeTestResult(String.valueOf(check), "", "true", writer);
        tearDown();
    }

    public void setCheck (Integer check) {
        this.check = check;
    }
}
