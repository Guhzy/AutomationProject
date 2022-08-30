package step.definitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import setup.BaseClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginSuccessSteps extends BaseClass{

    private WebDriver driver;

    @Given("SSN website opens")
    public void ssn_website_opens() {
        setupDriver();
        driver = getDriver();
    }

    @And("on login page")
    public void on_login_page() {
        WebElement element = driver.findElement(By.id(getUSERNAME_ID()));
        assertTrue(element.isDisplayed());
    }

    @When("^entered username and password$")
    public void entered_username_and_password() {
        String username = getUsername();
        String password = getPassword();

        driver.findElement(By.id(getUSERNAME_ID())).sendKeys(username);
        driver.findElement(By.id(getPASSWORD_ID())).sendKeys(password);
    }

    @And("click on login")
    public void click_on_login() {
        driver.findElement(By.name(getSUBMIT_BUTTON())).click();
    }

    @Then("^check (.*) for login successful$")
    public void check_valid_for_login_successful(String message) {
        WebElement successMessage = waitForElementsId(driver, getLOGIN_SUCCESS_MSG());
        assertEquals(message.replace("valid", getUsername()), successMessage.getText());
        saveScreenshot("SuccessLogin");
        tearDown();
    }
}
