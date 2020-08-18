package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface AllSetting extends ExtendedWebElement{
    @FindBy(".//a[@class='mail-SettingsPopup__title']/span")
    @Description("все настройки")
    ExtendedWebElement selectAllSetting();
}
