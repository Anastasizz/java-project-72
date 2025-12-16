package hexlet.code.service;

import java.net.URI;

public class UrlService {
    public static String normalize(URI uri) throws Exception {
        var url = uri.toURL();
        var port = url.getPort();
        return url.getProtocol() + "://" + url.getHost().toLowerCase()
                + (port != -1 ? ":" + port : "");
    }
}
