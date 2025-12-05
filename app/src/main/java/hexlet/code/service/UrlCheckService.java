package hexlet.code.service;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.utils.parser.HtmlParser;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class UrlCheckService {
    public static UrlCheck check(Url url) throws UnirestException {
        HttpResponse<String> response = Unirest.get(url.getName()).asString();
        var statusCode = response.getStatus();
        var body = response.getBody();

        var parser = new HtmlParser(body);
        var h1 = parser.getText("h1");
        var title = parser.getTitle();
        var content = parser.getAttribute("meta[name=description]", "content");

        return new UrlCheck(statusCode, title, h1, content, url.getId());
    }
}
