import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

import common.User;
import common.GenerateUser;
import common.RequestCreateUser;
import common.AuthData;
import pageobj.MainPage;
import pageobj.LoginPage;
import pageobj.RegisterPage;
public class RegistrationTest {
    WebDriver driver;
    private User user;
    private RequestCreateUser userClient;
    private String accessToken;
    private AuthData authData;

    @Before
    @Step("Предусловия для тестов")
    public void setUp() {
        System.setProperty("web-driver.http.factory", "C:\\WebDriver\\bin\\chromedriver_win32\\chromedriver");
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        user = GenerateUser.usersRandomCreate();
    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    @Description("Утверждение, что после регистрации отображается главная страница")
    public void successfullyRegistrationOnRegisterPage() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.openRegisterPage()
                .registerNewUser(user)
                .clickRegistrationButton()
                .authorizationFromLoginPage(user.getEmail(), user.getPassword())
                .clickLoginEnterButton();
        MainPage mainPage = new MainPage(driver);
        Assert.assertTrue(mainPage.isManePageOpen());
    }

    @Test
    @DisplayName("Регистрация через страницу входа")
    @Description("Утверждение, что после регистрации отображается главная страница")
    public void successfullyRegistrationOnLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage()
                .clickRegisterButtonLoginPage()
                .registerNewUser(user)
                .clickRegistrationButton()
                .authorizationFromLoginPage(user.getEmail(), user.getPassword())
                .clickLoginEnterButton();
        MainPage mainPage = new MainPage(driver);
        Assert.assertTrue(mainPage.isManePageOpen());
    }

    @Test
    @DisplayName("Регистрация через главную страницу")
    @Description("CУтверждение, что после регистрации отображается главная страница")
    public void successfullyRegistrationOnMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage()
                .clickAccountButton()
                .clickRegisterButtonLoginPage()
                .registerNewUser(user)
                .clickRegistrationButton()
                .authorizationFromLoginPage(user.getEmail(), user.getPassword())
                .clickLoginEnterButton();
        Assert.assertTrue(mainPage.isManePageOpen());
    }

    @After
    @Step("Удаление тестового пользователя и закрытие браузера")
    public void tearDown() {
        userClient = new RequestCreateUser();
        authData = new AuthData(user.getEmail(), user.getPassword());
        ValidatableResponse responseLoginUser = userClient.loginUser(authData);
        accessToken = responseLoginUser.extract().path("accessToken").toString().substring(7);
        userClient.deleteUser(accessToken);
        driver.quit();
    }
}
