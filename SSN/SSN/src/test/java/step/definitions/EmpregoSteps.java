package step.definitions;

import setup.BaseClass;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class EmpregoSteps extends BaseClass {

    private WebDriver driver;

    private static final String REMUNERACOES_ID = "item-M7612-M7601";

    @Given("logged in")
    public void logged_in() {
        setupDriver();
        driver = getDriver();
        loginWebsite(driver);
    }

    @And("on homepage")
    public void on_homepage() {
        WebElement element = driver.findElement(By.id(getLOGIN_SUCCESS_MSG()));
        assertTrue(element.isDisplayed());
    }

    @When("click on emprego")
    public void click_on_emprego() {
        driver.findElement(By.xpath(getEMPREGO_XPATH())).click();
    }

    @And("click on carreira contributiva")
    public void click_on_carreira_contributiva() {
        WebElement element = waitForElementsXpath(driver, getCARREIRA_XPATH());
        element.click();
    }

    @Then("check if in right place")
    public void check_if_in_right_place() {
        WebElement element = driver.findElement(By.id(REMUNERACOES_ID));
        assertTrue(element.isDisplayed());
        saveScreenshot("InsideCarreiraContributiva");
        tearDown();
    }
}