package hexlet.code.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class UrlService {
    public static Optional<String> normalize(String name) {

        try {
            var url = new URI(name).toURL();
            var port = url.getPort();
            return Optional.of(url.getProtocol() + "://" + url.getHost()
                    + (port != -1 ? ":" + port : ""));
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
