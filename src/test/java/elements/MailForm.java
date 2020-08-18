package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface MailForm extends ExtendedWebElement {
    @FindBy(".//div[contains(@class,'tst-field-to')]//div[@class='composeYabbles']")
    @Description("кому")
    ExtendedWebElement addressField();

    @FindBy(".//div/button[contains(@class,'ComposeControlPanelButton-Button_action')]")
    @Description("отправить письмо")
    ExtendedWebElement sentButton();
}

