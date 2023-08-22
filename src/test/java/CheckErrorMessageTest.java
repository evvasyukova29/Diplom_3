import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import common.User;
import common.GenerateUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageobj.RegisterPage;
import java.time.Duration;
import static constant.Constants.ERROR_MESSAGE_OF_WRONG_PASSWORD;
import static pageobj.RegisterPage.registerWrongPasswordMessageInRegisterPage;
public class CheckErrorMessageTest
{
    private WebDriver driver;
    private User user;
    @Before
    @Step("Предусловия для тестов")
    public void setUp() {
        System.setProperty("web-driver.http.factory", "jdk-http-client");
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        user = GenerateUser.usersRandomCreate();
    }

    @Test
    @DisplayName("Создание заказа с неверным паролем")
    @Description("Проверка пароля состоящего из 3 символов")
    public void shownErrorMessageWithWrongPassword() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.openRegisterPage()
                .enterRegisterName(user.getName())
                .enterRegisterEmail(user.getEmail())
                .enterRegisterPassword("666")
                .clickRegistrationButton();
        Assert.assertEquals("Ошибка не появилась", ERROR_MESSAGE_OF_WRONG_PASSWORD,
                driver.findElement(registerWrongPasswordMessageInRegisterPage).getText());
    }
    @After
    @Step("Закрыть браузер")
    public void tearDown() {
        driver.quit();
    }
}
