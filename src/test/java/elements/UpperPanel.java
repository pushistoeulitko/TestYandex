package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface UpperPanel extends ExtendedWebElement {
    @FindBy(".//div[contains(@class, 'mail-Toolbar-Item_main-select-all')]//span[@class='checkbox_view']")
    @Description("выбрать чекбоксы")
    ExtendedWebElement checkbox();

    @FindBy(".//div[contains(@data-key, 'delete')]")
    @Description("кнопка удалить")
    ExtendedWebElement deleteButton();
}
