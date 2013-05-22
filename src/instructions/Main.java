package instructions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 15.05.13
 */
public class Main {
    public static String LOGGER = "InstructionsForTesters";
    public static String OUTPUT_FILE = "tests.txt";
    public static String NEW_LINE = "\r\n";
    public static String TEST = "Тест";
    public static String MESSAGE = "Введите адрес страницы!";

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 1) {
            Logger.getLogger(LOGGER).severe(MESSAGE);
            return;
        }

        WebDriver driver = new FirefoxDriver();
        driver.get(args[0]);

        int testesCount = 0;

        PrintWriter printWriter = new PrintWriter(new FileOutputStream(OUTPUT_FILE));

        Map<ElementType, List<WebElement>> allElements = SearchHelper.findAllElements(driver);

        List<WebElement> forms = SearchHelper.findElements(ElementType.FORM, driver);

        for (WebElement form : forms) {
            Map<ElementType, List<WebElement>> formElements = SearchHelper.findAllElements(form);

            for (ElementType key : formElements.keySet()) {
                allElements.get(key).removeAll(formElements.get(key));
            }

            for (String test : TestGenerator.generateTests(formElements)) {
                printWriter.print(TEST + " " + testesCount + NEW_LINE + test + NEW_LINE + NEW_LINE);
                testesCount++;
            }
        }

        for (ElementType type : allElements.keySet()) {
            for (WebElement element : allElements.get(type)) {
                String test = TestGenerator.generateTest(type, TestGenerator.getElementText(element));
                printWriter.println(TEST + " " + testesCount + NEW_LINE + test + NEW_LINE);
                testesCount++;
            }
        }

        printWriter.close();
    }
}
