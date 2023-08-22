package pageobj;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static constant.Constants.RECOVERY_PASSWORD_URL;
public class PasswordPageRecovery {
    private static final By enterButtonOnRecoverPage = By.xpath(".//a[text()='Войти']");
    private final WebDriver driver;
    public PasswordPageRecovery(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Открыть страницу восстановления")
    public PasswordPageRecovery openRecoveryPage() {
        driver.get(RECOVERY_PASSWORD_URL);
        return this;
    }

    @Step("Клик на кнопку Восстановить")
    public LoginPage clickEnterButtonOnRecoveryPage() {
        driver.findElement(enterButtonOnRecoverPage).click();
        return new LoginPage(driver);
    }
}
