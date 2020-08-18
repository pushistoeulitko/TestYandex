package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface Setting extends ExtendedWebElement {
    @FindBy(".//button[contains(@class,'mail-SettingsButton')]")
    @Description("зайти в настройки")
    ExtendedWebElement settingfield();
}
