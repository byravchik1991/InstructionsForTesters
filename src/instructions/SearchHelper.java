package instructions;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 18.05.13
 */
public class SearchHelper {

    public static String TYPE = "type";

    public static List<WebElement> findElements(ElementType type, SearchContext context) {

        List<WebElement> elements = new ArrayList<WebElement>();

        for (HtmlTag tag : type.getTags()) {
            List<WebElement> currentElements = context.findElements(By.tagName(tag.getTagName()));

            for (WebElement currentElement : currentElements) {
                if (isElementSuitable(tag, currentElement)) {
                    elements.add(currentElement);
                }
            }
        }

        return elements;
    }

    private static boolean isElementSuitable(HtmlTag tag, WebElement currentElement) {
        return isTypeSuitable(tag, currentElement)
                && isVisible(currentElement)
                && isCoordinatesCorrect((Locatable) currentElement);
    }

    private static boolean isTypeSuitable(HtmlTag tag, WebElement currentElement) {
        if (!tag.isCheckBoth()) {
            return true;
        }

        String type = currentElement.getAttribute(TYPE);
        if (tag.getType() == null) {
            return type == null;
        }

        return tag.getType().equals(type);
    }

    private static boolean isCoordinatesCorrect(Locatable currentElement) {
        return (currentElement.getCoordinates().onPage().getX() >= 0)
                && (currentElement.getCoordinates().onPage().getY() >= 0);
    }

    public static Map<ElementType, List<WebElement>> findAllElements(SearchContext context) {
        Map<ElementType, List<WebElement>> elementsMap = new HashMap<ElementType, List<WebElement>>();

        elementsMap.put(ElementType.LINK, findElements(ElementType.LINK, context));
        elementsMap.put(ElementType.SUBMIT, findElements(ElementType.SUBMIT, context));
        elementsMap.put(ElementType.RESET, findElements(ElementType.RESET, context));
        elementsMap.put(ElementType.BUTTON, findElements(ElementType.BUTTON, context));
        elementsMap.put(ElementType.TEXT, findElements(ElementType.TEXT, context));
        elementsMap.put(ElementType.PASSWORD, findElements(ElementType.PASSWORD, context));
        elementsMap.put(ElementType.RADIO, findElements(ElementType.RADIO, context));
        elementsMap.put(ElementType.CHECKBOX, findElements(ElementType.CHECKBOX, context));
        elementsMap.put(ElementType.FILE, findElements(ElementType.FILE, context));
        elementsMap.put(ElementType.SELECT, findElements(ElementType.SELECT, context));

        return elementsMap;
    }

    private static boolean isVisible(WebElement element) {
        String opacity = element.getCssValue("opacity");
        if ("0".equals(opacity)) {
            return true;
        }

        return element.isDisplayed();
    }
}
