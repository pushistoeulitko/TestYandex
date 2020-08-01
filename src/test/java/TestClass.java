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

    public static void waitupdate(long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String howManyLettersSent() { //удаленные
        clickItem("//a[@href='#sent']//span[@class='mail-NestedList-Item-Name']");
        waitupdate(5000);
        String lettersSent = driver.findElement(By.xpath("//a[@href='#sent']//span[@class='mail-NestedList-Item-Info-Extras']")).getText();
        return lettersSent;
    }

    public static String howManyLettersDelete() { //отправленные
        clickItem("//a[@href='#trash']//span[@class='mail-NestedList-Item-Name']");
        waitupdate(5000);
        String lettersDelete = driver.findElement(By.xpath("//a[@href='#trash']//span[@class='mail-NestedList-Item-Info-Extras']")).getText();
        return lettersDelete;
    }

    //глобальные переменные

    public static void settingLanguage() {
        clickItem("//button[contains(@class,'mail-SettingsButton')]"); //зайти в настройки
        clickItem("//a[@class='mail-SettingsPopup__title']/span");
    }

    public void setDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(920, 800));
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
        waitupdate(3000);
        String language = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText();
        Assert.assertEquals(language, needLanguage);
    }

    public void selectCheckbox() {
        List<WebElement> elem = driver.findElements(By.xpath("//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']"));
        elem.forEach(WebElement::click);
    }

    public void pressDelete() {
        Actions action = new Actions(driver);
        action.sendKeys(DELETE).build().perform();
    }

    public void clickDelete() {
        clickItem("//div[contains(@data-key, 'delete')]");
    }

    public void confirmDelete() {
        clickItem("//button[contains(@class, 'js-confirm-mops')]");
    }

    public void writeMessage(String ToName) {
        clickItem("//a[contains(@class,'js-main-action-compose')]");
        sendKeys("//div[contains(@class,'tst-field-to')]//div[@class='composeYabbles']", ToName);
        clickItem("//div/button[contains(@class,'ComposeControlPanelButton-Button_action')]");
    }

    public void returnToMail() {
        clickItem("//div [@class='ComposeDoneScreen-Actions']/a[@href='#inbox']");
        waitupdate(3000);
    }

    public void assertionEverything(String x, String y) {
        System.out.println(x + " " + y);
        Assert.assertEquals(x, y);
    }

    public void assertionNotEverithing(String x, String y) {
        System.out.println(x + " " + y);
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
        confirmDelete();
        waitupdate(8000);
        String y = howManyLettersDelete();
        System.out.println(x + " " + y);
        assertionNotEverithing(x, y);
    }

    @Test(description = "Удалить сообщение кликнув на значок удалить")
    public void deletingMessagesTestWithClick() {
        String x = howManyLettersDelete();
        selectCheckbox();
        clickDelete();
        confirmDelete();
        //waitupdate(3000);
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
        String n0 = howManyLettersSent();
        writeMessage("pushist0eulitko@yandex.ru");
        returnToMail(); //вернуться в почту
        String n = howManyLettersSent();
        assertionNotEverithing(n0, n);
    }

    @Test(description = "Отправка сообщения по неверному адресу")
    public void sendingMessageFalseEmail() {
        String n0 = howManyLettersSent();
        writeMessage("fxjfsjf");
        String n = howManyLettersSent();
        assertionEverything(n0, n);
        //String message = driver.findElement(By.xpath("//div[@class='ComposeConfirmPopup-Title']/span")).getText();
        // Assert.assertEquals(message, "Проверьте получателя");
    }

    @Test(description = "Отправка сообщения по пустому адресу")
    public void clickSendMessageButton() {
        String n0 = howManyLettersSent();
        writeMessage("");
        String n = howManyLettersSent();
        assertionEverything(n0, n);
        //String message = driver.findElement(By.xpath("//div[@class='ComposeConfirmPopup-Title']/span")).getText();
        //Assert.assertEquals(message, "Письмо не отправлено");
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