package step.definitions;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import setup.BaseClass;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CarreiraContributivaSteps extends BaseClass {

    private WebDriver driver;

    private static final String DATE_DROPDOWN_XPATH = "//*[@id=\"mainForm:ano\"]/div[3]";
    private static final String TOTAL_SALARY_ID = "mainForm:ano_label";
    private static final String SEE_DETAIL_CLASS = "ui-commandlink";
    private static final String YEAR_DROPDOWN_XPATH = "//li[@data-label=\"year\"]";

    @Given("carreira contributiva web page")
    public void carreira_contributiva_web_page() {
        setupDriver();
        driver = getDriver();
        loginWebsite(driver);
        getToCarreira(driver);
    }

    @And("click on see details")
    public void click_on_see_details() {
        try{
            List<WebElement> elementList = driver.findElements(By.className(SEE_DETAIL_CLASS));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(ExpectedConditions.elementToBeClickable(elementList.get(0))).click();
        } catch (TimeoutException e) {
            System.out.println("TimeoutException: "+e.getMessage());
            tearDown();
        } catch (Exception e) {
            System.out.println("Exception: "+e.getMessage());
            tearDown();
        }
    }

    @When("click on date dropdown")
    public void click_on_date_dropdown() {
        WebElement element = waitForElementsXpath(driver, DATE_DROPDOWN_XPATH);
        element.click();
    }

    @And("^selects (.*)$")
    public void selects_year(String year) {
        WebElement element = waitForElementsXpath(driver, YEAR_DROPDOWN_XPATH.replace("year", year));
        element.click();
        saveScreenshot("Year_" + year);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Then("^check total salary of that (.*)$")
    public void check_total_salary_of_that_year(Integer result) {
        String element = driver.findElement(By.id(TOTAL_SALARY_ID)).getText();
        assertEquals(element, String.valueOf(result));
        tearDown();
    }
}