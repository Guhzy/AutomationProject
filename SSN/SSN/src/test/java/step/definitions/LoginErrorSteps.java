package step.definitions;

import setup.BaseClass;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginErrorSteps extends BaseClass{

    private WebDriver driver;


    private static final String LOGIN_ERROR_MSG = "erro";

    @Given("SSN website is open")
    public void ssn_website_is_open() {
        setupDriver();
        driver = getDriver();
    }

    @And("is on login page")
    public void is_on_login_page() {
        WebElement element = driver.findElement(By.id(getUSERNAME_ID()));
        assertTrue(element.isDisplayed());
    }

    @When("^enters (.*) and (.*)$")
    public void enters_username_and_password(String username, String password) {
        if (username.equals("invalid"))
            username = getInvalid();

        driver.findElement(By.id(getUSERNAME_ID())).sendKeys(username);
        driver.findElement(By.id(getPASSWORD_ID())).sendKeys(password);
    }

    @And("clicks on login")
    public void clicks_on_login() {
        driver.findElement(By.name(getSUBMIT_BUTTON())).click();
    }

    @Then("^checks (.*) for login incorrect$")
    public void checks_message_for_login_incorrect(String message) {
        String errorText = driver.findElement(By.className(LOGIN_ERROR_MSG)).getText();

        if (errorText.contains("\n")) {
            boolean hasErrorMsgID = errorText.contains("Introduza o seu NISS");
            boolean hasErrorMsgPW = errorText.contains("Introduza a palavra-passe");
            assertTrue(hasErrorMsgID && hasErrorMsgPW);
        } else {
            assertEquals(message, errorText);
        }
        saveScreenshot("ErrorMessage");
        tearDown();
    }
}