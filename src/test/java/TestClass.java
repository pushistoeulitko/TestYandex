import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.Keys.*;

public class TestClass {
    static WebDriver driver;

    @BeforeTest
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

    @Test (description ="Проверка и выставление русского языка")
    public void switchOverLanguageTestRu() {
        clickItem("//button[contains(@class,'mail-SettingsButton')]");
        clickItem("//a[@class='mail-SettingsPopup__title']/span");

        String languageRu = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText();
        if (languageRu.equals("Русский")) {
            System.out.println("Выставлен русский язык");
        } else {
            clickItem("//span[contains(@class,'mail-Settings-Lang_arrow')]");
            clickItem("//a[@data-params='lang=ru']");
            System.out.println("Изменен на русский язык");
        }
    }

    @Test (description ="Проверка и выставление английского языка")
    public void switchOverLanguageTestEn() {
        clickItem("//button[contains(@class,'mail-SettingsButton')]");
        clickItem("//a[@class='mail-SettingsPopup__title']/span");

        String languageEn = driver.findElement(By.xpath("//span[@class='b-selink__link mail-Settings-Lang']")).getText();
        if (languageEn.equals("English")) {
            System.out.println("Выставлен English язык");
        } else {
            clickItem("//span[contains(@class,'mail-Settings-Lang_arrow')]");
            clickItem("//a[@data-params='lang=en']");
            System.out.println("Изменен на English");
        }
    }

    @Test (description ="Удалить сообщение клавишей делит в класиватуры")
    public void deletingMessagesTestWithoutClick() {
        List<WebElement> chk = driver.findElements(By.xpath("//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']"));
        for (WebElement webElement : chk) {
            webElement.click();
        }
        driver.findElement(By.xpath("//input[@class='textinput__control']")).sendKeys(Keys.DELETE);
    }
      //"//div[contains(@class,'mail-Toolbar-Item_main-select-all')]//span[@class='checkbox_view']"
       // List<WebElement> elem = driver.findElements(By.xpath("//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']"));
       // for (WebElement webElement : elem) {
         //   webElement.click();
       // }
//        driver.findElement(By.xpath("//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']"));
       // driver.findElement(By.xpath("//div[contains(@class,'js-layout-aside-inner-box')]")).sendKeys(Keys.DELETE);
        //clickItem("//button/span[@class='_nb-button-content']");
       // clickItem("//input[@id='nb-checkbox_0']/following-sibling::span[contains(@class,'_nb-checkbox-normal-flag')]");
        //driver.findElement(By.xpath("//input[@id='nb-checkbox_0']/following-sibling::span[contains(@class,'_nb-checkbox-normal-flag')]")).sendKeys(Keys.DELETE);


    @Test (description ="Удалить сообщение кликнув на значок удалить")
    public void deletingMessagesTestWithClick() {
       //по одному
       // clickItem("//input[@id='nb-checkbox_0']/following-sibling::span[contains(@class,'_nb-checkbox-normal-flag')]");
        List<WebElement> elem = driver.findElements(By.xpath("//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']"));
        elem.forEach(WebElement::click);
        clickItem("//span[contains(@class,'js-toolbar-item-title-delete')]");
        // clickItem("//button/span[@class='_nb-button-content']"); добавлять еще сообщения
    }

    @Test (description ="Попытка удалить сообщения не выделяя чекбоксы")
    public void noDeletingMessagesTestWithClick() {
        clickItem("//span[contains(@class,'js-toolbar-item-title-delete')]");

    }

    @Test (description = "Отправка сообщения себе")
    public void sendingMessageTrueEmail() {
        clickItem("//a[contains(@class,'js-main-action-compose')]");
        driver.findElement(By.xpath("//div[contains(@class,'tst-field-to')]//div[@class='composeYabbles']")).sendKeys("pushist0eulitko@yandex.ru");
        clickItem("//div/button[contains(@class,'ComposeControlPanelButton-Button_action')]");
        //String message = driver.findElement(By.xpath("//*[@id='nb-1']/body/div[13]/div/div[1]/div[1]/span")).getText();
        //if (message.equals("Письмо отправлено")) {
           // System.out.println("Сообщение отправлено");
        //}

    }
    @Test (description ="Отправка сообщения по неверному адресу")
    public void sendingMessageFalseEmail() {
        clickItem("//a[contains(@class,'js-main-action-compose')]");
        WebElement sent = driver.findElement(By.xpath("//div[contains(@class,'tst-field-to')]//div[@class='composeYabbles']"));
        sent.sendKeys("fxjfsjf");
        clickItem("//div/button[contains(@class,'ComposeControlPanelButton-Button_action')]");
        //String message =clickItem("//div[@class='ComposeConfirmPopup-Title']/span")).getText();
       // if (message.equals("Проверьте получателя")) {
        //    System.out.println("Сообщение не отправлено");
        }

    @Test (description ="Отправка сообщения по пустому адресу")
    public void clickSendMessageButton() {
        clickItem("//a[contains(@class,'js-main-action-compose')]");
        clickItem("//div/button[contains(@class,'ComposeControlPanelButton-Button_action')]");
    }

    @AfterTest
    public void exit() {
       // driver.quit();
    }
}


//WebDriverWait wait = new WebDriverWait (driver,5);
//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@class='b-mail-icon b-mail-icon_lang-ru']")));
// String currentUrl = driver.getCurrentUrl();