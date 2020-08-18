package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface ConfirmDelete extends ExtendedWebElement {
    @FindBy(".//button[contains(@class, 'js-confirm-mops')] ")
    @Description("подтверждение удаления 1")
    ExtendedWebElement confirm1();
}
