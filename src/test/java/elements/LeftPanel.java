package elements;

import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;

public interface LeftPanel extends ExtendedWebElement {
    @FindBy(".//span[text()='Удалённые']")
    @Description ("тыкнуть на удаленные")
    ExtendedWebElement delete();

    @FindBy(".//a[@href='#trash']//span[@class='mail-NestedList-Item-Info-Extras']")
    @Description("кол-во удаленных")
    ExtendedWebElement numberDelete();

    @FindBy(".//a[contains(@class,'js-main-action-compose')]")
    @Description("написать письмо")
    ExtendedWebElement write();

    @FindBy(".//a[@href='#inbox']//span[@class='mail-NestedList-Item-Name']")
    @Description("входящие")
    ExtendedWebElement inbox();

}
