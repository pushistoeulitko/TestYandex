package elements;

import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.element.ExtendedWebElement;


public interface LoginPage extends ExtendedWebElement {
    @FindBy(".//button[@type='submit']")
    @Description("кнопка принять")
    ExtendedWebElement loginSubmit();

    @FindBy(".//*[@id='passp-field-login']")
    @Description("поле логин")
    ExtendedWebElement loginField();

    @FindBy(".//*[@id='passp-field-passwd']")
    @Description("поле пароль")
    ExtendedWebElement passwordField();
}
