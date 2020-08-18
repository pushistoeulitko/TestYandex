package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface PopUp extends ExtendedWebElement {
    @FindBy(".//div[@class='ComposeConfirmPopup-Title']/span")
    @Description("TextPopUp")
    ExtendedWebElement elem1();
}
