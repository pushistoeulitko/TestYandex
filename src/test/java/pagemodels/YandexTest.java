package pagemodels;

import io.qameta.allure.Step;
import io.qameta.htmlelements.WebPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.MailPage;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.Keys.DELETE;

public class YandexTest {
    MailPage mp;

    public static void waitUpdate(long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod(alwaysRun = true, description = "Перешли по адресу yandex.ru")
    private void signInTest() {
        WebPageFactory factory = new WebPageFactory();
        WebDriver driver;
        driver = new ChromeDriver();
        mp = factory.get(driver, MailPage.class);
        mp.getWrappedDriver().manage().window().setSize(new Dimension(1920, 1080));
        mp.getWrappedDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        mp.getWrappedDriver().get("https://mail.yandex.ru");
        mp.authorization().mailAuthorization().click();
        mp.loginPage().loginField().sendKeys("pushist0eulitko@yandex.ru");
        mp.loginPage().loginField().click();
        mp.loginPage().loginSubmit().click();
        mp.loginPage().passwordField().sendKeys("050211xx");
        mp.loginPage().loginSubmit().click();
    }

    @Step("Написать сообщение {s}")
    private void writeMessage (String s ){
        mp.leftPanel().write().click();
        mp.mailForm().addressField().sendKeys(s);
        mp.mailForm().sentButton().click();
        waitUpdate(5000);
    }
    @Step("Прочитать сообщение об отправке")
    private void assertionMessage (String x, String y) {
        Assert.assertEquals(x, y, "тест не пройден");
        System.out.println(y);

    }
    @Step ("Кликнуть на удаленные")
    public String howManyLettersDelete() { //отправленные
        waitUpdate(8000);
        mp.leftPanel().delete().click();
        waitUpdate(5000);
        return mp.leftPanel().numberDelete().getText();
    }
    @Step ("Выделить чекбоксы")
    public void selectCheckbox() {
        mp.leftPanel().inbox().click();
        mp.upperpanel().checkbox().click();
    }
    @Step ("Нажать делит на клавиатуре")
    public void pressDelete() {
        Actions action = new Actions(mp.getWrappedDriver());
        action.sendKeys(DELETE).build().perform();
        confirmDelete();
    }
    @Step ("Кликнуть на делит на панели")
    public void clickDelete() {
        mp.upperpanel().deleteButton().click();
        confirmDelete();
    }
    @Step ("согласиться на удаление")
    public void confirmDelete() {
        try {
            if (mp.confirmDelete().confirm1().isDisplayed()) {
                mp.confirmDelete().confirm1().click();
            }
            if (mp.confirmDelete2().confirm2().isDisplayed()) {
                mp.confirmDelete2().confirm2().click();
            }
        } catch (org.openqa.selenium.NoSuchElementException ignored) {
        }
    }
    @Step ("проверить удаление писем")
    public void assertionMessageNotDelete(String x) {
        String y = howManyLettersDelete();
        Assert.assertEquals(x, y, "тест не пройден");
        System.out.println("Письма не удалены");
    }

    @Step ("проверить удаление писем")
    public void assertionDelete(String x){
        String y = howManyLettersDelete();
        Assert.assertTrue(Integer.parseInt(y)> Integer.parseInt(x));
        System.out.println("Удалено всего "+ ((Integer.parseInt(y))-(Integer.parseInt(x))) +" писем");
    }

    @Step ("Зайти в настройки выбора языка")
    public void settingLanguage() {
        mp.setting().settingfield().click();
        mp.allSetting().selectAllSetting().click();
    }

    @Step ("выбор языка, если не совпадает")
    public void changeLanguageEnum(Language searchLanguage) {
       // mp.settingLanguage().languegeField().getText();
        if (!mp.settingLanguage().languegeField().getText().equals(searchLanguage.getSearchLanguage())) {
            mp.settingLanguage().arrow().click();
            mp.selectLanguage().settingLanguegeParam(searchLanguage.getAddress()).click();
        }
    }
    @Step ("проверка языка")
    public void checkLanguage(String needLanguage) {
        waitUpdate(3000);
        Assert.assertEquals(mp.settingLanguage().languegeField().getText(), needLanguage);
    }

    @Test(groups = "LanguageTest", description = "Проверка и выставление русского языка")
    public void switchOverLanguageTestRu() {
        settingLanguage();
        Language russian = Language.RUSSIAN;
        changeLanguageEnum(russian);
        checkLanguage(russian.getSearchLanguage());
    }

    @Test(groups = "LanguageTest", description = "Проверка и выставление английского языка")
    public void switchOverLanguageTestEn() {
        settingLanguage();
        Language english = Language.ENGLISH;
        changeLanguageEnum(english);
        checkLanguage(english.getSearchLanguage());
    }

    @Test(groups = "DeletingMessages", description = "Удалить сообщение клавишей делит в класиватуры")
    public void deletingMessagesTestWithoutClick() {
        String x = howManyLettersDelete();
        selectCheckbox();
        pressDelete();
        assertionDelete(x);
    }

    @Test(groups = "DeletingMessages", description = "Удалить сообщение кликнув на значок удалить")
    public void deletingMessagesTestWithClick() {
        String x = howManyLettersDelete();
        selectCheckbox();
        clickDelete();
        assertionDelete(x);
    }

    @Test(groups = "DeletingMessages", description = "Попытка удалить сообщения не выделяя чекбоксы")
    public void noDeletingMessagesTestWithClick() {
        String x = howManyLettersDelete();
        pressDelete();
        assertionMessageNotDelete(x);
    }

    @Test(groups = "SendMessage", description = "Отправка сообщения себе")
    public void sendingMessageTrueEmail() {
        writeMessage("pushist0eulitko@yandex.ru");
        assertionMessage(mp.popUp2().elem2().getText(), "Письмо отправлено");
    }

    @Test(groups = "SendMessage", description = "Отправка сообщения по неверному адресу")
    public void sendingMessageFalseEmail() {
        writeMessage("fxjfsjf");
        assertionMessage(mp.popUp().elem1().getText(), "Проверьте получателя");
    }

    @Test(groups = "SendMessage", description = "Отправка сообщения по пустому адресу")
    public void clickSendMessageButton() {
        writeMessage("");
        assertionMessage(mp.popUp().elem1().getText(), "Письмо не отправлено");
    }

    @AfterTest
    public void exit() {
        mp.getWrappedDriver().quit();
    }

}
