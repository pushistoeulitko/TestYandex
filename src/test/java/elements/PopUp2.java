package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface PopUp2 extends ExtendedWebElement {
    @FindBy(".//div[@class='ComposeDoneScreen-Title']/span")
    @Description("TextPopUp2")
    ExtendedWebElement elem2();
}
