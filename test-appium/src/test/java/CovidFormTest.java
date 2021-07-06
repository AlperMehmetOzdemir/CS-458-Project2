import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Locale;

public class CovidFormTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

    // Elements for testing
    By titleBy = By.id("com.example.cs_458covidformapplication:id/title");
    By nameInputBy = By.id("com.example.cs_458covidformapplication:id/name_input");
    By lastNameInputBy = By.id("com.example.cs_458covidformapplication:id/last_name_input");
    By dateTextBy = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.ScrollView/android.widget.LinearLayout/android.widget.TextView[2]");

    By datePicker = By.id("com.example.cs_458covidformapplication:id/date_of_birth");
    By citySelectionBy = By.id("com.example.cs_458covidformapplication:id/citySelection");
    By genderTextBy = By.id("com.example.cs_458covidformapplication:id/genderText");
    By maleRadioBy = By.id("com.example.cs_458covidformapplication:id/genderMale");
    By femaleRadioBy = By.id("com.example.cs_458covidformapplication:id/genderFemale");
    By otherRadioBy = By.id("com.example.cs_458covidformapplication:id/genderOther");
    By vaccineSelectionBy = By.id("com.example.cs_458covidformapplication:id/vaccine_type_selection");
    By sideEffectsInputBy = By.id("com.example.cs_458covidformapplication:id/side_effects_input");


    @BeforeMethod
    public void setUp() throws MalformedURLException {
        Reporter.log("Setting up test environment", true);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("platformVersion", "11");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("skipUnlock", "true");
        caps.setCapability("appPackage", "com.example.cs_458covidformapplication");
        caps.setCapability("appActivity", "com.example.cs_458covidformapplication.MainActivity");
        caps.setCapability("noReset", "false");

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 10);


    }

    @Test(enabled = true)
    public void basicTest() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInputBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInputBy)).sendKeys("Mehmet");

        String nameInputStr = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInputBy)).getText();
        Assert.assertTrue(nameInputStr.toLowerCase(Locale.ROOT).contains("mehmet"));

    }

    @Test(enabled = true, description = "At most one radio button (initially zero) for a radio button group should be checked at any time.")
    public void radioButtonTest() throws InterruptedException {
        Reporter.log("[Test-Case 1] - At most one radio button (initially zero) for a radio button group should be checked at any time.", true);
        // Check that initially no radio buttons are selected
        Reporter.log("[Test-Case 1.1] No radio buttons should be initially checked", true);
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.1] Male should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.1] Female should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.1] Other should not be checked");




        // Check for cases where only the male radio button should be checked
        Reporter.log("[Test-Case 1.2] Male radio button should be the only radio button checked when male radio button is clicked last", true);
        // male clicked directly
        wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.2] Male should be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.2] Female should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.2] Other should not be checked");
        // male clicked after female
        wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.2] Male should be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.2] Female should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.2] Other should not be checked");
        // male clicked after other
        wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.2] Male should be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.2] Female should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.2] Other should not be checked");
        // male clicked after other clicked after female
        wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.2] Male should be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.2] Female should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.2] Other should not be checked");
        // male clicked directly after female clicked after other
        wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.2] Male should be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.2] Female should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.2] Other should not be checked");




        // Check for cases where only the female radio button should be checked
        Reporter.log("[Test-Case 1.3] Female radio button should be the only radio button checked when female radio button is clicked last",true);
        // female clicked directly
        wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.3] Male should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.3] Female should be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.3] Other should not be checked");
        // male clicked after female
        wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.3] Male should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.3] Female should be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.3] Other should not be checked");
        // male clicked after other
        wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.3] Male should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.3] Female should be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.3] Other should not be checked");
        // femmale clicked after other clicked after male
        wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.3] Male should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.3] Female should be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.3] Other should not be checked");
        // female clicked directly after male clicked after other
        wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.3] Male should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.3] Female should be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.3] Other should not be checked");



        // Check for cases where only the other radio button should be checked
        Reporter.log("[Test-Case 1.4] Other radio button should be the only radio button checked when other radio button is clicked last", true);
        // other clicked directly
        wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.4] Male should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.4] Female should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.4] Other should be checked");
        // other clicked after male
        wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.4] Male should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.4] Female should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.4] Other should be checked");
        // other clicked after female
        wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.4] Male should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.4] Female should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.4] Other should be checked");
        // other clicked after male clicked after female
        wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.4] Male should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.4] Female should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.4] Other should be checked");
        // other clicked after female clicked after male
        wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(maleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.4] Male should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(femaleRadioBy)).getAttribute("checked"),
                "false",
                "[Test-Case 1.4] Female should not be checked");
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRadioBy)).getAttribute("checked"),
                "true",
                "[Test-Case 1.4] Other should be checked");

        Reporter.log("[Test-Case 1] Complete", true);

    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
