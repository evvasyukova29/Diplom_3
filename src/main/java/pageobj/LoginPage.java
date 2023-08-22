package pageobj;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static constant.Constants.LOGIN_PAGE_URL;

public class LoginPage {
    private static final By loginEmail = By.xpath(".//label[text() = 'Email']/../input[contains(@name, 'name')]");
    private static final By loginPassword = By.xpath(".//label[text() = 'Пароль']/../input[contains(@type, 'password')]");
    private static final By loginEnterButton = By.xpath(".//*[text()='Войти']");
    private static final By registerButtonFromLogin = By.xpath(".//*[text()='Зарегистрироваться']");

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть страницу входа")
    public LoginPage openLoginPage() {
        driver.get(LOGIN_PAGE_URL);
        return this;
    }

    @Step("Клик на кнопку регистрации")
    public RegisterPage clickRegisterButtonLoginPage() {
        driver.findElement(registerButtonFromLogin).click();
        return new RegisterPage(driver);
    }

    @Step("Клик на кнопку Вход на странице входа")
    public MainPage clickLoginEnterButton() {
        driver.findElement(loginEnterButton).click();
        return new MainPage(driver);
    }
    @Step("Заполнить форму авторизации")
    public LoginPage authorizationFromLoginPage(String email, String password) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(LOGIN_PAGE_URL));
        driver.findElement(loginEmail).click();
        driver.findElement(loginEmail).sendKeys(email);
        driver.findElement(loginPassword).click();
        driver.findElement(loginPassword).sendKeys(password);
        return this;
    }
}

