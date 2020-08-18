package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface ConfirmDelete2 extends ExtendedWebElement {
    @FindBy(".//span[@class ='mail-Toolbar-Item-Text js-toolbar-item-title js-toolbar-item-title-delete']")
    @Description("подтверждение удаления 2")
    ExtendedWebElement confirm2();
}
