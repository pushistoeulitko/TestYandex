package pages;

import elements.*;
import io.qameta.htmlelements.WebPage;
import io.qameta.htmlelements.annotation.Description;
import io.qameta.htmlelements.annotation.FindBy;

public interface MailPage extends WebPage {

    @FindBy("//div[@class='HeadBanner-ButtonsWrapper']")
    @Description("авторизация")
    Authorization authorization();

    @FindBy("//form")
    @Description("форма регистрации")
    LoginPage loginPage();

    @FindBy("//div[@class='yandex-header__actions']")
    @Description("выбрать настройки")
    Setting setting();

    @FindBy("//div[@class='mail-SettingsPopup__top']")
    @Description("все настройки")
    AllSetting allSetting();

    @FindBy("//span[@data-click-action='dropdown.toggle']")
    @Description("панель языка")
    SettingLanguage settingLanguage();

    @FindBy("//div[@class='b-mail-dropdown__box__content']")
    @Description("панель языка")
    SelectLanguage selectLanguage();

    @FindBy("//div[contains(@class, 'mail-Layout-Aside_maximum')]")
    @Description("кнопки с левой панели")
    LeftPanel leftPanel();

    @FindBy("//div[contains(@class, 'ns-view-toolbar-buttons')][1]")
    @Description("верхней панели")
    UpperPanel upperpanel();

    @FindBy(".//div[@class='composeReact__inner']")
    @Description("форма для отправки письма")
    MailForm mailForm();

    @FindBy("//div[@class='tooltip__buttons']")
    @Description("Подтверждение удаления1")
    ConfirmDelete confirmDelete();

    @FindBy("//div[@class='tooltip__description']")
    @Description("Подтверждение удаления2")
    ConfirmDelete2 confirmDelete2();

    @FindBy("//div[@class='modal__content']")
    @Description("всплывающее сообщение при неправильной отправке")
    PopUp popUp();

    @FindBy("//div[@class='ComposeDoneScreen-Wrapper']")
    @Description("письмо отправляется")
    PopUp2 popUp2();

}