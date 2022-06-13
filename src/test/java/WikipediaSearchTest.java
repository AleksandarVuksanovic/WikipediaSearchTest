import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.openqa.selenium.Keys.ENTER;

public class WikipediaSearchTest {
    WebDriver driver;

    @BeforeClass
    public void setup () {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        WebDriverWait wdwait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeMethod
    public void pageSetup () {
        driver.manage().window().maximize();
        driver.navigate().to("https://www.google.com");
    }

    @Test
    public void test1 () throws InterruptedException {

        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Wikipedia");
        searchBox.sendKeys(ENTER);
        WebElement wikipedia = driver.findElement(By.cssSelector(".LC20lb.MBeuO.DKV0Md"));
        wikipedia.click();
        WebElement wikiSearchBox = driver.findElement(By.id("searchInput"));
        wikiSearchBox.sendKeys("Nikola Tesla");
        wikiSearchBox.sendKeys(ENTER);

        String expectedHeader= "Никола Тесла";
        WebElement header = driver.findElement(By.id("firstHeading"));
        String actualHeader = header.getText();
        Assert.assertEquals(actualHeader, expectedHeader);

        String expectedUrl = "https://sr.wikipedia.org/wiki/%D0%9D%D0%B8%D0%BA%D0%BE%D0%BB%D0%B0_%D0%A2%D0%B5%D1%81%D0%BB%D0%B0";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
    }

    @BeforeMethod
    public void removeCookies () {
        driver.manage().deleteAllCookies();
    }


    @AfterClass
    public void tearDown () {
        driver.close();
        driver.quit();
    }
}
