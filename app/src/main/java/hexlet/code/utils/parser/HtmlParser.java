package hexlet.code.utils.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public final class HtmlParser {
    private final Document doc;

    public HtmlParser(String html) {
        doc = Jsoup.parse(html == null ? "" : html);
    }
    public String getText(String tag) {
        Element element = doc.selectFirst(tag);
        return element != null ? element.text() : "";
    }

    public String getAttribute(String tag, String attr) {
        Element element = doc.selectFirst(tag);

        return element != null ? element.attr(attr) : "";
    }
    public String getTitle() {
        return doc.title();
    }
}
