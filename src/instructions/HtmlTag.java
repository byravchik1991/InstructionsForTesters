package instructions;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 19.05.13
 */
public class HtmlTag {
    private String tagName;
    private String type;
    private boolean checkBoth;

    public HtmlTag(String tagName, String type, boolean checkBoth) {
        this.tagName = tagName;
        this.type = type;
        this.checkBoth = checkBoth;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCheckBoth() {
        return checkBoth;
    }

    public void setCheckBoth(boolean checkBoth) {
        this.checkBoth = checkBoth;
    }
}
