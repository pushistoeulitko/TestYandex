import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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

    public static void clickItem(String name) {
        driver.findElement(By.xpath(name)).click();
    }

    public static void sendKeys(String adress, String sent) {
        driver.findElement(By.xpath(adress)).sendKeys(sent);
    }

    public static void waitUpdate(long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String howManyLettersSent() { //удаленные
        waitUpdate(3000);
        //clickItem("//a[@href='#sent']//span[@class='mail-NestedList-Item-Name']");
        clickItem("//span[text()='Отправленные']");
        waitUpdate(3000);
        return driver.findElement(By.xpath("//a[@href='#sent']//span[@class='mail-NestedList-Item-Info-Extras']")).getText();
    }

    public static String howManyLettersDelete() { //отправленные
        waitUpdate(5000);
        //clickItem("//a[@href='#trash']//span[@class='mail-NestedList-Item-Name']");
        clickItem("//span[text()='Удалённые']");
        waitUpdate(5000);
        return driver.findElement(By.xpath("//a[@href='#trash']//span[@class='mail-NestedList-Item-Info-Extras']")).getText();
    }

    public static void settingLanguage() {
        clickItem("//button[contains(@class,'mail-SettingsButton')]"); //зайти в настройки
        clickItem("//a[@class='mail-SettingsPopup__title']/span");
    }

    public void setDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @BeforeMethod
    public void signInTest() {
        setDriver();
        driver.get("https://yandex.ru");
        Set<String> windowHandles0 = driver.getWindowHandles();

        driver.findElement(By.linkText("Войти в почту")).click();

        Set<String> windowHandles = driver.getWindowHandles();
        windowHandles.removeAll(windowHandles0);
        driver.switchTo().window(windowHandles.iterator().next());

        clickItem("//*[@id='passp-field-login']");
        sendKeys("//*[@id='passp-field-login']", "pushist0eulitko@yandex.ru");
        clickItem("//*[@id='passp-field-login']");
        clickItem("//button[@type='submit']");
        sendKeys("//*[@id='passp-field-passwd']", "050211xx");
        clickItem("//button[@type='submit']");
    }

    public void changeLanguageEnum(Language searchLanguage) {
        String language = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText();
        if (!language.equals(searchLanguage.getSearchLanguage())) {
            clickItem("//span[contains(@class,'mail-Settings-Lang_arrow')]");
            clickItem(searchLanguage.getAdress());
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
    //span[@class ='mail-Toolbar-Item-Text js-toolbar-item-title js-toolbar-item-title-delete']

    public void confirmDelete() {
        if (driver.findElement(By.xpath("//button[contains(@class, 'js-confirm-mops')]")).isDisplayed()){
            clickItem("//button[contains(@class, 'js-confirm-mops')]");
        }
        //}
    }

    public void writeMessage(String toName) {
        clickItem("//a[contains(@class,'js-main-action-compose')]");
        sendKeys("//div[contains(@class,'tst-field-to')]//div[@class='composeYabbles']", toName);
        clickItem("//div/button[contains(@class,'ComposeControlPanelButton-Button_action')]");
        waitUpdate(5000);
    }

    public String getTextPOpUp() {
        String x = driver.findElement(By.xpath("//div[@class='ComposeConfirmPopup-Title']/span")).getText();
        return x;
    }

    public String getTextPOpUp2() {
        String x = driver.findElement(By.xpath("//div[@class='ComposeDoneScreen-Title']/span")).getText();
        return x;
    }

    public void assertionEverything(String x, String y) {
        waitUpdate(5000);
        Assert.assertEquals(x, y);
    }

    public void assertionNotEverithing(String x, String y) {
        waitUpdate(5000);
        Assert.assertNotEquals(x, y);
    }

    @Test(description = "Проверка и выставление русского языка")
    public void switchOverLanguageTestRu() {
        settingLanguage();
        Language russian = Language.RUSSIAN;
        changeLanguageEnum(russian);
        checkLanguage(russian.getSearchLanguage());
    }

    //public <T, x, y> void Check(<T> x, <T> y) {
    //    Assert.assertEquals(x, y);
    //}

    @Test(description = "Проверка и выставление английского языка")
    public void switchOverLanguageTestEn() {
        settingLanguage();
        changeLanguageEnum(Language.ENGLISH);
        checkLanguage("English");
    }

    @Test(description = "Удалить сообщение клавишей делит в класиватуры")
    public void deletingMessagesTestWithoutClick() {
        String x = howManyLettersDelete();
        selectCheckbox();
        pressDelete();
        String y = howManyLettersDelete();
        assertionNotEverithing(x, y);
    }

    @Test(description = "Удалить сообщение кликнув на значок удалить")
    public void deletingMessagesTestWithClick() {
        String x = howManyLettersDelete();
        selectCheckbox2();
        clickDelete();
        String y = howManyLettersDelete();
        assertionNotEverithing(x, y);
    }

    @Test(description = "Попытка удалить сообщения не выделяя чекбоксы")
    public void noDeletingMessagesTestWithClick() {
        String x = howManyLettersDelete();
        clickDelete();
        String y = howManyLettersDelete();
        assertionEverything(x, y);
    }

    @Test(description = "Отправка сообщения себе")
    public void sendingMessageTrueEmail() {
        writeMessage("pushist0eulitko@yandex.ru");
        String x = getTextPOpUp2();
        assertionEverything(x, "Письмо отправлено");
    }

    @Test(description = "Отправка сообщения по неверному адресу")
    public void sendingMessageFalseEmail() {
        writeMessage("fxjfsjf");
        String x = getTextPOpUp();
        assertionEverything(x, "Проверьте получателя");
    }

    @Test(description = "Отправка сообщения по пустому адресу")
    public void clickSendMessageButton() {
        writeMessage("");
        String x = getTextPOpUp();
        assertionEverything(x, "Письмо не отправлено" );
    }



    @AfterTest
    public void exit() {
        driver.quit();
    }

    public enum Language {
        RUSSIAN("Русский", "//a[@data-params='lang=ru']"),
        ENGLISH("English", "//a[@data-params='lang=en']");

        private final String searchLanguage;
        private final String adress;

        Language(String searchLanguage, String adress) {
            this.searchLanguage = searchLanguage;
            this.adress = adress;
        }

        public String getAdress() {
            return adress;
        }

        public String getSearchLanguage() {
            return searchLanguage;
        }
    }
}


//WebDriverWait wait = new WebDriverWait (driver,5);
//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@class='b-mail-icon b-mail-icon_lang-ru']")));
// clickItem("//button/span[@class='_nb-button-content']"); добавлять еще сообщения