package instructions;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 18.05.13
 */
public enum ElementType {
    FORM("Форма", new HtmlTag("form", null, false)),
    LINK("Кликните по ссылке", new HtmlTag("a", null, false)),
    BUTTON("Нажмите кнопку",
            new HtmlTag("button", null, true),
            new HtmlTag("input", "button", true),
            new HtmlTag("input", "image", true)),
    SUBMIT("Нажмите кнопку", new HtmlTag("button", "submit", true), new HtmlTag("input", "submit", true)),
    RESET("Нажмите кнопку", new HtmlTag("button", "reset", true), new HtmlTag("input", "reset", true)),
    TEXT("Заполните текстовое поле", new HtmlTag("input", "text", true)),
    PASSWORD("Заполните поле с паролем", new HtmlTag("input", "password", true)),
    RADIO("Установите переключатель", new HtmlTag("input", "radio", true)),
    CHECKBOX("Устнаовите флажок", new HtmlTag("input", "checkbox", true)),
    FILE("Выберите файл в поле", new HtmlTag("input", "file", true)),
    SELECT("Выберите пункт из списка", new HtmlTag("select", null, false));

    private String message;
    private List<HtmlTag> tags;

    private ElementType(String message, HtmlTag... tags) {
        this.message = message;
        this.tags = Arrays.asList(tags);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HtmlTag> getTags() {
        return tags;
    }

    public void setTags(List<HtmlTag> tags) {
        this.tags = tags;
    }
}
