import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static org.openqa.selenium.Keys.*;

public class TestClass {
    static WebDriver driver;

    @BeforeMethod
    public void signInTest() {
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(920, 800));
        driver.get("https://yandex.ru");
        Set<String> windowHandles0 = driver.getWindowHandles();
        driver.findElement(By.linkText("Войти в почту")).click();

        Set<String> windowHandles = driver.getWindowHandles();
        windowHandles.removeAll(windowHandles0);
        driver.switchTo().window(windowHandles.iterator().next());

        clickItem("//*[@id='passp-field-login']");
        driver.findElement(By.xpath("//*[@id='passp-field-login']")).sendKeys("pushist0eulitko@yandex.ru");
        clickItem("//*[@id='passp-field-login']");
        clickItem("//button[@type='submit']");
        WebElement password = driver.findElement(By.xpath("//label[text()='Введите пароль']"));
        password.findElement(By.xpath("//*[@id='passp-field-passwd']")).sendKeys("050211xx");
        clickItem("//button[@type='submit']");
    }

    public static void clickItem(String name) {
        driver.findElement(By.xpath(name)).click();
    }

    @Test(description = "Проверка и выставление русского языка")
    // работает
    public void switchOverLanguageTestRu() {
        clickItem("//button[contains(@class,'mail-SettingsButton')]");
        clickItem("//a[@class='mail-SettingsPopup__title']/span");

        String languageRu = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText();
        if (!languageRu.equals("Русский")) {
            clickItem("//span[contains(@class,'mail-Settings-Lang_arrow')]");
            clickItem("//a[@data-params='lang=ru']");
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String languageRu1 = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText();
        Assert.assertEquals(languageRu1, "Русский");
    }

    @Test(description = "Проверка и выставление английского языка")
    // работает
    public void switchOverLanguageTestEn() {
        clickItem("//button[contains(@class,'mail-SettingsButton')]");
        clickItem("//a[@class='mail-SettingsPopup__title']/span");

        String languageEn = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText();
        if (!languageEn.equals("English")) {
            clickItem("//span[contains(@class,'mail-Settings-Lang_arrow')]");
            clickItem("//a[@data-params='lang=en']");
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String languageEn1 = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText();
        Assert.assertEquals(languageEn1, "English");
    }

    @Test(description = "Удалить сообщение клавишей делит в класиватуры")
    // работает
    public void deletingMessagesTestWithoutClick() {
        String x = driver.findElement(By.xpath("//span[@class='mail-NestedList-Item-Info-Link-Text']")).getText();
        List<WebElement> elem = driver.findElements(By.xpath("//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']"));
        for (WebElement Element : elem) {
            Element.click();
        }

        Actions action = new Actions(driver);
        action.sendKeys(DELETE).build().perform();
        clickItem("//button[contains(@class, 'js-confirm-mops')]");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String y = driver.findElement(By.xpath("//span[@class='mail-NestedList-Item-Info-Link-Text']")).getText();
        System.out.println(x + " " + y);
        Assert.assertNotEquals(x, y);
    }

    @Test(description = "Удалить сообщение кликнув на значок удалить")
    // работает
    public void deletingMessagesTestWithClick() {
        //по одному
        // clickItem("//input[@id='nb-checkbox_0']/following-sibling::span[contains(@class,'_nb-checkbox-normal-flag')]");
        String x = driver.findElement(By.xpath("//span[@class='mail-NestedList-Item-Info-Link-Text']")).getText();
        List<WebElement> elem = driver.findElements(By.xpath("//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']"));
        elem.forEach(WebElement::click);
        clickItem("//div[contains(@data-key, 'delete')]");
        clickItem("//button[contains(@class, 'js-confirm-mops')]");
        // clickItem("//button/span[@class='_nb-button-content']"); добавлять еще сообщения
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String y = driver.findElement(By.xpath("//span[@class='mail-NestedList-Item-Info-Link-Text']")).getText();
        System.out.println(x + " " + y);
        Assert.assertNotEquals(x, y);
    }

    @Test(description = "Попытка удалить сообщения не выделяя чекбоксы")
    // работает
    public void noDeletingMessagesTestWithClick() {
        String x = driver.findElement(By.xpath("//span[@class='mail-NestedList-Item-Info-Link-Text']")).getText();
        clickItem("//span[contains(@class,'js-toolbar-item-title-delete')]");

        String y = driver.findElement(By.xpath("//span[@class='mail-NestedList-Item-Info-Link-Text']")).getText();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(x + " " + y);
        Assert.assertEquals(x, y);
    }

    @Test(description = "Отправка сообщения себе")
    public void sendingMessageTrueEmail() {
        clickItem("//a[@href='#sent']/span[@class='mail-NestedList-Item-Name']");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String n0 = driver.findElement(By.xpath("//a[@href='#sent']//span[@class='mail-NestedList-Item-Info-Extras']")).getText();

        clickItem("//a[contains(@class,'js-main-action-compose')]");
        driver.findElement(By.xpath("//div[contains(@class,'tst-field-to')]//div[@class='composeYabbles']")).sendKeys("pushist0eulitko@yandex.ru");
        clickItem("//div/button[contains(@class,'ComposeControlPanelButton-Button_action')]");
        clickItem("//div [@class='ComposeDoneScreen-Actions']/a[@href='#inbox']"); //вернуться в почту
        clickItem("//a[@href='#sent']/span[@class='mail-NestedList-Item-Name']");//тыкнуть на отправленные
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String n = driver.findElement(By.xpath("//a[@href='#sent']//span[@class='mail-NestedList-Item-Info-Extras']")).getText(); //количество отправленных
        System.out.println(n0 + " " + n);
        Assert.assertNotEquals(n0, n);
    }

    @Test(description = "Отправка сообщения по неверному адресу")
    // работает
    public void sendingMessageFalseEmail() {
        clickItem("//a[contains(@class,'js-main-action-compose')]");
        driver.findElement(By.xpath("//div[contains(@class,'tst-field-to')]//div[@class='composeYabbles']")).sendKeys("fxjfsjf");
        clickItem("//div/button[contains(@class,'ComposeControlPanelButton-Button_action')]");
        String message = driver.findElement(By.xpath("//div[@class='ComposeConfirmPopup-Title']/span")).getText();
        Assert.assertEquals(message, "Проверьте получателя");
    }

    @Test(description = "Отправка сообщения по пустому адресу")
    // работает
    public void clickSendMessageButton() {
        clickItem("//a[contains(@class,'js-main-action-compose')]");
        clickItem("//div/button[contains(@class,'ComposeControlPanelButton-Button_action')]");
        String message = driver.findElement(By.xpath("//div[@class='ComposeConfirmPopup-Title']/span")).getText();
        Assert.assertEquals(message, "Письмо не отправлено");
    }

    @AfterMethod
    public void exit() {
        driver.close();
    }
}


//WebDriverWait wait = new WebDriverWait (driver,5);
//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@class='b-mail-icon b-mail-icon_lang-ru']")));
// String currentUrl = driver.getCurrentUrl();