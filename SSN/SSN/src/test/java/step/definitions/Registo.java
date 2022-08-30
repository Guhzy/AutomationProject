package step.definitions;

import setup.BaseClass;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Registo {

    private WebDriver driver;
    private BaseClass base;

    private static final String NISS_ID = "aderirForm:niss";
    private static final String BIRTHDATE_ID = "aderirForm:dataNascimento:calendar_input";
    private static final String TERMS_AND_CONDITIONS_XPATH = "//*[@id=\"aderirForm:aceitarTermos\"]/span";
    private static final String NEXT_XPATH = "//*[@id=\"aderirForm:gusWizardNextBtn\"]/span[2]";
    private static final String ERROR_CAPTCHA_XPATH = "//*[@id=\"aderirForm:j_idt134\"]/span[2]";
    private static final String ERROR_MSG_CLASS = "ui-message-error-detail";
    private static final String CAPTCHA_IMG_CSS = "div.rc-imageselect-desc-no-canonical";

    @Given("SSN web page")
    public void ssn_web_page() {
        base = new BaseClass();
        base.setupDriver();
        driver = base.getDriver();
        WebElement element = driver.findElement(By.id(base.getUSERNAME_ID()));
        assertTrue(element.isDisplayed());
    }

    @When("click on register")
    public void click_on_register() {
        base.getToNewTab(driver);
    }

    @And("accept terms and conditions")
    public void accept_terms_and_conditions() {
        base.saveScreenshot("TermsAndConditions");
        driver.findElement(By.xpath(TERMS_AND_CONDITIONS_XPATH)).click();
    }

    @And("^insert (.*) and (.*)$")
    public void insert_ssn_and_birthdate(String ssn, String birthdate) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(NISS_ID)));
        driver.findElement(By.id(NISS_ID)).sendKeys(ssn);
        base.waitForElementsId(driver, BIRTHDATE_ID);
        driver.findElement(By.id(BIRTHDATE_ID)).sendKeys(birthdate);
        base.saveScreenshot("InsertNSSAndBirthdate");
    }

    @Then("^check (.*) for register successful or not$")
    public void check_message_for_register_successful_or_not(String message) {
        driver.findElement(By.xpath(NEXT_XPATH)).click();
        base.waitForElementsXpath(driver, ERROR_CAPTCHA_XPATH);
        assertEquals(message, driver.findElement(By.className(ERROR_MSG_CLASS)).getText());
        base.getToFrame();

        try {
            new WebDriverWait(driver, Duration.ofSeconds(1)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CAPTCHA_IMG_CSS)));
        } catch (TimeoutException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            base.saveScreenshot("Captcha");
            base.tearDown();
        }
    }
}