package elements;

import io.qameta.htmlelements.WebPage;
import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;
import io.qameta.htmlelements.element.ExtendedWebElement;
import org.openqa.selenium.WebElement;

public interface Authorization extends ExtendedWebElement {
    @FindBy("./a[2]")
    @Description("Кнопка для перехода на страницу залогинивания")
    ExtendedWebElement mailAuthorization();
}

//".//div[contains(@class, 'card_login_yes')]"