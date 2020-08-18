package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.annotation.Param;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface SelectLanguage extends ExtendedWebElement {
    @FindBy(".//a[@data-params=\"lang={{ param }}\"]")
    @Description("выставить язык: {param}")
    ExtendedWebElement settingLanguegeParam(@Param("param") String param);
}