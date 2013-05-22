package instructions;

import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 18.05.13
 */
public class TestGenerator {
    public static String VALUE = "value";
    public static String TITLE = "tittle";

    public static List<String> generateTests(Map<ElementType, List<WebElement>> elementsMap) {
        TreeSet<Map.Entry<WebElement, String>> items = new TreeSet<Map.Entry<WebElement, String>>(
                new Comparator<Map.Entry<WebElement, String>>() {
                    @Override
                    public int compare(Map.Entry<WebElement, String> o1, Map.Entry<WebElement, String> o2) {
                        Integer y1 = ((Locatable) o1.getKey()).getCoordinates().onPage().getY();
                        Integer y2 = ((Locatable) o2.getKey()).getCoordinates().onPage().getY();

                        if (!y1.equals(y2)) {
                            return y1.compareTo(y2);
                        }

                        Integer x1 = ((Locatable) o1.getKey()).getCoordinates().onPage().getX();
                        Integer x2 = ((Locatable) o2.getKey()).getCoordinates().onPage().getX();

                        return x1.compareTo(x2);
                    }
                });

        List<WebElement> buttons = elementsMap.get(ElementType.SUBMIT);
        buttons.addAll(elementsMap.get(ElementType.RESET));

        for (ElementType type : elementsMap.keySet()) {
            if (ElementType.SUBMIT.equals(type) || ElementType.RESET.equals(type)) {
                continue;
            }

            for (WebElement element : elementsMap.get(type)) {
                items.add(new DefaultMapEntry(element, generateTest(type, getElementText(element))));
            }
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<WebElement, String> item : items) {
            builder.append(item.getValue()).append("\r\n");
        }
        String test = builder.toString();

        List<String> tests = new ArrayList<String>();

        if (buttons.isEmpty()) {
            tests.add(test);

        } else {
            for (WebElement button : buttons) {
                tests.add(test + generateTest(ElementType.SUBMIT, getElementText(button)));
            }
        }

        return tests;
    }

    public static String generateTest(ElementType type, String elementName) {
        StringBuilder builder = new StringBuilder();
        builder.append(type.getMessage()).append(" \"").append(elementName).append("\"");

        return builder.toString();
    }

    public static String getElementText(WebElement element) {
        String text = element.getText().trim();
        if (!text.isEmpty()) {
            return text;
        }

        String value = element.getAttribute(VALUE);
        if (value != null && !value.isEmpty()) {
            return value;
        }

        String title = element.getAttribute(TITLE);
        if (title != null && !title.isEmpty()) {
            return title;
        }

        String childrenText = getChildrenText(element).trim();
        if (!childrenText.isEmpty()) {
            return childrenText;
        }

        String parentText = getParentText(element).trim();
        if (!parentText.isEmpty()) {
            return parentText;
        }

        String brothersText = getBrothersText(element).trim();
        if (!brothersText.isEmpty()) {
            return brothersText;
        }

        return text;
    }

    private static String getParentText(WebElement element) {
        return element.findElement(By.xpath("..")).getText().trim();
    }

    private static String getBrothersText(WebElement element) {
        List<WebElement> brothers = element.findElements(By.xpath("../*"));
        StringBuilder text = new StringBuilder();

        for (WebElement brother : brothers) {
            text.append(brother.getText().trim()).append(" ");
        }

        return text.toString();
    }

    private static String getChildrenText(WebElement element) {
        List<WebElement> children = element.findElements(By.xpath("*"));
        StringBuilder text = new StringBuilder();

        for (WebElement child : children) {
            text.append(child.getText().trim()).append(" ");
        }

        return text.toString();
    }
}
