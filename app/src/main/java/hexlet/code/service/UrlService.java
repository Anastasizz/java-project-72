package hexlet.code.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlService {
    public static String normalize(String name) {

        try {
            var url = new URI(name).toURL();
            var port = url.getPort();
            return url.getProtocol() + "://" + url.getHost()
                    + (port != -1 ? ":" + port : "");
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            throw new RuntimeException("Некорректный URL");
        }
    }
}
