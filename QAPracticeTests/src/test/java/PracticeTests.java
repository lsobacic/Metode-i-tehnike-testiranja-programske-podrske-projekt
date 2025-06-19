import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PracticeTests {

    WebDriver driver;
    WebDriverWait wait;
    String baseURL = "https://qa-practice.netlify.app/";

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(baseURL);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState=='complete'"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testCheckboxesSection() {

        driver.get(baseURL + "checkboxes");
        List<WebElement> checkboxes = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("input[type='checkbox']"))
        );

        Assert.assertEquals(checkboxes.size(), 3, "Očekuju se 3 checkboxa");

        for (int i = 0; i < checkboxes.size(); i++) {
            checkboxes.get(i).click();
            Assert.assertTrue(checkboxes.get(i).isSelected(), "Checkbox " + (i + 1) + " treba biti selektiran");
        }
        WebElement resetBtn = driver.findElement(By.xpath("//button[text()='Reset']"));
        resetBtn.click();

        for (WebElement cb : checkboxes) {
            Assert.assertFalse(cb.isSelected(), "Checkbox treba biti odčekiran nakon Reset");
        }
    }



    @Test
    public void testRadioButtons() {

        driver.get(baseURL + "radiobuttons");

        List<WebElement> radioButtons = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("input[type='radio']"))
        );

        radioButtons.get(1).click();
        Assert.assertTrue(radioButtons.get(1).isSelected(), "Radio button 2 treba biti selektiran");

        radioButtons.get(2).click();
        Assert.assertTrue(radioButtons.get(2).isSelected(), "Radio button 3 treba biti selektiran");
        Assert.assertFalse(radioButtons.get(1).isSelected(), "Radio button 2 više ne smije biti selektiran");

        Assert.assertFalse(radioButtons.get(3).isEnabled(), "Radio button 4 treba biti onemogućen");

        Assert.assertFalse(radioButtons.get(0).isSelected(), "Radio button 1 ne bi smio biti selektiran");
    }


    @Test
    public void testDropdown() {
        driver.get(baseURL+"dropdowns");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement dropdownElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("dropdown-menu"))
        );

        Select countryDropdown = new Select(dropdownElement);

        countryDropdown.selectByVisibleText("Croatia");

        String selectedOptionText = countryDropdown.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOptionText, "Croatia", "Odabrana država nije 'Croatia'.");
    }



    @Test
    public void testRegisterForm() {

        driver.get(baseURL+"register");

        driver.findElement(By.id("firstName")).sendKeys("User");
        driver.findElement(By.id("lastName")).sendKeys("User");
        driver.findElement(By.id("phone")).sendKeys("123456789");

        WebElement countryDropdownElement = driver.findElement(By.id("countries_dropdown_menu"));
        Select countrySelect = new Select(countryDropdownElement);
        countrySelect.selectByVisibleText("Zambia");

        driver.findElement(By.id("emailAddress")).sendKeys("test@test.com");
        driver.findElement(By.id("exampleCheck1")).click();


        driver.findElement(By.id("registerBtn")).click();
        WebElement passwordInput = driver.findElement(By.id("password"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", passwordInput);

        Assert.assertEquals(validationMessage, "Ispunite ovo polje.", "Validacijska poruka za lozinku nije ispravna.");
    }


    @Test
    public void testBasicDatePickerSelection() {

        driver.get(baseURL+"calendar");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("calendar")));
        dateInput.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("datepicker-days")));

        WebElement day15 = driver.findElement(By.xpath("//td[@class='day' and text()='15']"));
        day15.click();

        LocalDate today = LocalDate.now();
        LocalDate selectedDate = today.withDayOfMonth(15);
        String expectedDate = selectedDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        String actualDate = dateInput.getAttribute("value");

        Assert.assertEquals(actualDate, expectedDate, "Odabrani datum nije ispravno upisan u polje.");
    }




    @Test
    public void testVerifyTotal() {
        driver.get(baseURL+"products_list");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement nokiaAddToCartButton = driver.findElement(By.xpath("//span[text()='Nokia 105, Black']/following-sibling::div/button"));
        nokiaAddToCartButton.click();

        wait.until(ExpectedConditions.textToBe(By.className("cart-total-price"), "$19.99"));

        WebElement huaweiAddToCartButton = driver.findElement(By.xpath("//span[text()='Huawei Mate 20 Lite, 64GB, Black']/following-sibling::div/button"));
        huaweiAddToCartButton.click();
        String expectedTotal = "$256.11";

        wait.until(ExpectedConditions.textToBe(By.className("cart-total-price"), expectedTotal));

        WebElement totalPrice = driver.findElement(By.className("cart-total-price"));
        Assert.assertEquals(totalPrice.getText(), expectedTotal, "Ukupna cijena proizvoda nije ispravna.");


        List<WebElement> cartRows = driver.findElements(By.className("cart-row"));

        Assert.assertEquals(cartRows.size(), 3, "Broj artikala u košarici nije ispravan.");
    }






}
