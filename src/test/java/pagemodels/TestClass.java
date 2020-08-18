package pagemodels;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.Keys.DELETE;

public class TestClass {
    static WebDriver driver;

    public static void sendKeys(String address, String sent) {
        driver.findElement(By.xpath(address)).sendKeys(sent);
    }

    public static void clickItem(String name) {
        driver.findElement(By.xpath(name)).click();
    }

    public static String getText(String s) {
        return driver.findElement(By.xpath(s)).getText();
    }

    public static void waitDisplay(String s) {
        WebDriverWait wait = new WebDriverWait(driver, 8);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(s)));
    }

    public static String howManyLettersDelete() { //отправленные
        waitUpdate(8000);
        //waitDisplay("//span[text()='Удалённые']");
        clickItem("//span[text()='Удалённые']");
        //waitDisplay("//a[@href='#trash']//span[@class='mail-NestedList-Item-Info-Extras']");
        waitUpdate(5000);
        return getText("//a[@href='#trash']//span[@class='mail-NestedList-Item-Info-Extras']");
    }

    public static void waitUpdate(long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    public static void settingLanguage() {
        clickItem("//button[contains(@class,'mail-SettingsButton')]"); //зайти в настройки
        clickItem("//a[@class='mail-SettingsPopup__title']/span");
    }

    public void clickButton() {
        clickItem("//button[@type='submit']");
    }

    public void clickLogin() {
        clickItem("//*[@id='passp-field-login']");
    }

    public void changeLanguageEnum(Language searchLanguage) {
        String language = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText();
        if (!language.equals(searchLanguage.getSearchLanguage())) {
            clickItem("//span[contains(@class,'mail-Settings-Lang_arrow')]");
            clickItem(searchLanguage.getAddress());
        }
    }

    public void checkLanguage(String needLanguage) {
        waitUpdate(3000);
        String language = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText();
        Assert.assertEquals(language, needLanguage);
    }

    public void selectCheckbox() {
        clickItem("//a[@href='#inbox']//span[@class='mail-NestedList-Item-Name']");
        List<WebElement> elem = driver.findElements(By.xpath("//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']"));
        elem.forEach(WebElement::click);
    }

    public void selectCheckbox2() {
        clickItem("//a[@href='#inbox']//span[@class='mail-NestedList-Item-Name']");
        clickItem("//div[contains(@class, 'mail-Toolbar-Item_main-select-all')]//span[@class='checkbox_view']");
    }

    public void pressDelete() {
        Actions action = new Actions(driver);
        action.sendKeys(DELETE).build().perform();
        confirmDelete();
    }

    public void clickDelete() {
        clickItem("//div[contains(@data-key, 'delete')]");
        confirmDelete();
    }

    public void confirmDelete() {
        try {
            if (driver.findElement(By.xpath("//button[contains(@class, 'js-confirm-mops')]")).isDisplayed()) {
                clickItem("//button[contains(@class, 'js-confirm-mops')]");
            }
            if (driver.findElement(By.xpath("//span[@class ='mail-Toolbar-Item-Text js-toolbar-item-title js-toolbar-item-title-delete']")).isDisplayed()) {
                clickItem("//span[@class ='mail-Toolbar-Item-Text js-toolbar-item-title js-toolbar-item-title-delete']");
            }
        } catch (org.openqa.selenium.NoSuchElementException ignored) {
        }
    }

    public void writeMessage(String toName) {
        clickItem("//a[contains(@class,'js-main-action-compose')]");
        sendKeys("//div[contains(@class,'tst-field-to')]//div[@class='composeYabbles']", toName);
        clickItem("//div/button[contains(@class,'ComposeControlPanelButton-Button_action')]");
        waitUpdate(5000);
    }

    public String getTextPopUp() {
        //String x = driver.findElement(By.xpath("//div[@class='ComposeConfirmPopup-Title']/span")).getText();
        return getText("//div[@class='ComposeConfirmPopup-Title']/span");
    }

    public String getTextPopUp2() {
        //String x = driver.findElement(By.xpath("//div[@class='ComposeDoneScreen-Title']/span")).getText();
        return getText("//div[@class='ComposeDoneScreen-Title']/span");
    }

    public void assertionMessage(String x, String y) {
        Assert.assertEquals(x, y, "тест не пройден");
        System.out.println(y);
    }

    public void assertionMessageNotDelete(String x) {
        String y = howManyLettersDelete();
        Assert.assertEquals(x, y, "тест не пройден");
        System.out.println("Письма не удалены");
    }
    public void assertionNotEverything(String x, String y) {
        Assert.assertNotEquals(Integer.valueOf(x), Integer.valueOf(y), "тест не пройден");
        System.out.println("Удалено всего "+ ((Integer.parseInt(x))-(Integer.parseInt(y))) +" писем");
    }
    public void assertionDelete(String x){
        String y = howManyLettersDelete();
        Assert.assertTrue(Integer.parseInt(y)< Integer.parseInt(x));
        System.out.println("Удалено всего "+ ((Integer.parseInt(x))-(Integer.parseInt(y))) +" писем");
    }

    @BeforeMethod
    public void signInTest() {
        setDriver();
        driver.get("https://yandex.ru");
        Set<String> windowHandles0 = driver.getWindowHandles();
        driver.findElement(By.linkText("Войти в почту")).click();
        //не работают
        //clickItem("//span[text()='Войти в почту']");
        //clickItem("//a[contains(@class,'button_js_inited')]");
        Set<String> windowHandles = driver.getWindowHandles();
        windowHandles.removeAll(windowHandles0);
        driver.switchTo().window(windowHandles.iterator().next());
        clickLogin();
        sendKeys("//*[@id='passp-field-login']", "pushist0eulitko@yandex.ru");
        clickLogin();
        clickButton();
        sendKeys("//*[@id='passp-field-passwd']", "050211xx");
        clickButton();
    }

    @Test(description = "Проверка и выставление русского языка")
    public void switchOverLanguageTestRu() {
        settingLanguage();
        Language russian = Language.RUSSIAN;
        changeLanguageEnum(russian);
        checkLanguage(russian.getSearchLanguage());
    }

    @Test(description = "Проверка и выставление английского языка")
    public void switchOverLanguageTestEn() {
        settingLanguage();
        Language english = Language.ENGLISH;
        changeLanguageEnum(english);
        checkLanguage(english.getSearchLanguage());
    }

    @Test(description = "Удалить сообщение клавишей делит в класиватуры")
    public void deletingMessagesTestWithoutClick() {
        String x = howManyLettersDelete();
        selectCheckbox2();
        pressDelete();
        String y = howManyLettersDelete();
        assertionNotEverything(x, y);
    }

    @Test(description = "Удалить сообщение кликнув на значок удалить")
    public void deletingMessagesTestWithClick() {
        String x = howManyLettersDelete();
        selectCheckbox2();
        clickDelete();
        assertionDelete(x);
    }

    @Test(description = "Попытка удалить сообщения не выделяя чекбоксы")
    public void noDeletingMessagesTestWithClick() {
        String x = howManyLettersDelete();
        pressDelete();
        assertionMessageNotDelete(x);
    }

    @Test(description = "Отправка сообщения себе")
    public void sendingMessageTrueEmail() {
        writeMessage("pushist0eulitko@yandex.ru");
        assertionMessage(getTextPopUp2(), "Письмо отправлено");
    }

    @Test(description = "Отправка сообщения по неверному адресу")
    public void sendingMessageFalseEmail() {
        writeMessage("fxjfsjf");
        assertionMessage(getTextPopUp(), "Проверьте получателя");
    }

    @Test(description = "Отправка сообщения по пустому адресу")
    public void clickSendMessageButton() {
        writeMessage("");
        assertionMessage(getTextPopUp(), "Письмо не отправлено");
    }


    @AfterTest
    public void exit() {
        driver.quit();
    }

}


// clickItem("//button/span[@class='_nb-button-content']"); добавлять еще сообщения