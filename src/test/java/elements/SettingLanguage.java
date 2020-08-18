package elements;
import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.annotation.Param;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface SettingLanguage extends ExtendedWebElement{
    @FindBy(".//span[@class='b-selink__link mail-Settings-Lang'] ")
    @Description("выставленный язык")
    ExtendedWebElement languegeField();

    @FindBy(".//span[contains(@class,'mail-Settings-Lang_arrow')]")
    @Description("раскрыть меню выбора языка")
    ExtendedWebElement arrow();


}
