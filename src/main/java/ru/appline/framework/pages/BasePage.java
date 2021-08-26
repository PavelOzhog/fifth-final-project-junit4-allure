package ru.appline.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.framework.managers.DriverManager;
import ru.appline.framework.managers.PageManager;

import java.util.Locale;
import java.util.Optional;

public class BasePage {


    protected DriverManager driverManager = DriverManager.getDriverManager();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 15, 2000);
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();

    protected PageManager pageManager = PageManager.getPageManager();


    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);

    }

    //метод ожидания кликабельности элемента
    protected WebElement waitUtilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    protected WebElement scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }


    public void switchToFrame(WebElement iFrame) {
        driverManager.getDriver().switchTo().defaultContent(); // you are now outside both frames
        driverManager.getDriver().switchTo().frame(iFrame);
    }

    //удаление пробелов в string
    protected String changeString(String string) {
        return string.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "");
    }


    //аналог метода changeString
    protected static String changeStringCustom(String string) {
        return string.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "");
    }

    //преобразование в число
    protected int getIntFromString(String string) {
        return Integer.parseInt(changeString(string).replaceAll("[^\\d.]", ""));
    }


    //метод заполнения поля (простой)
    public void fill(WebElement element, String value) {
        element.click();
        wait.until(MyCustomAttributeToBeEmptyVer2(element, "value"));
        element.sendKeys(value);
        wait.until(myCustomAttributeToBe(element, "value", value));
    }


    /**
     * Метод для ожидания значения текстового поля
     *
     * @param webElement параметр используется для получения значения поля, которое в дальнейшем будет использовано для сравнения
     * @param value      значение вписываемое в поле эелемента
     * @return Boolean true вернется если значения эквивалентны
     * <p>
     * так же в методе используетя метод changeToString - метод удаляет пробелы из параметра
     */
    public static ExpectedCondition<Boolean> MyTextToBe(WebElement webElement, String value) {
        return new ExpectedCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    currentValue = webElement.getText();
                    currentValue = changeStringCustom(currentValue);
                    return currentValue.equals(value);
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return String.format("element found by %s to have text \"%s\". Current text: \"%s\"",
                        webElement.getText(), value, currentValue);
            }
        };
    }


    //метод проверяющий заполнения поля
//        private void checkValue(String expectedValue, String element) {
//            Assert.assertEquals("Значение " + element.getText() + " расчитано некорректно", changeString(expectedValue),
//
//                    changeString(element.getAttribute("innerText")));
//        }


    protected void checkValueByText(WebElement element, String value, String atribute) {
        wait.until(myCustomAttributeToBe(element, atribute, value));
        Assert.assertEquals("Значение " + element.getText() + " расчитано некорректно", value,
                changeString(element.getText()));
    }


    public static ExpectedCondition<Boolean> myCustomAttributeToBe(final WebElement element,
                                                                   final String attribute,
                                                                   final String value) {
        return new ExpectedCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(WebDriver driver) {
                currentValue = element.getAttribute(attribute);
                if (currentValue == null || currentValue.isEmpty()) {
                    currentValue = element.getCssValue(attribute);
                }
                return value.equals(changeStringCustom(currentValue));
            }

            @Override
            public String toString() {
                return String.format(attribute + " to be \"%s\". Current " + attribute + ": \"%s\"", value,
                        currentValue);
            }
        };
    }


    public static ExpectedCondition<Boolean> MyCustomAttributeToBeEmpty(final WebElement element,
                                                                        final String attribute) {
        return driver -> !getAttributeOrCssValue(element, attribute).isPresent();

    }

    private static Optional<String> getAttributeOrCssValue(WebElement element, String name) {
        String value = element.getAttribute(name);
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }

        return Optional.empty();
    }


    public static ExpectedCondition<Boolean> MyCustomAttributeToBeEmptyVer2(final WebElement element,
                                                                            final String attribute) {
        return new ExpectedCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(WebDriver driver) {
                currentValue = element.getAttribute(attribute);
                return currentValue == null || currentValue.isEmpty();
            }

            @Override
            public String toString() {
                return String.format(attribute + " to be \"%s\". Current " + attribute + ": \"%s\"",
                        currentValue,"мое сообщение");
            }
        };
    }


}
