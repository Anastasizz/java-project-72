package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.service.UrlCheckService;
import hexlet.code.service.UrlService;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import hexlet.code.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {
    private Javalin app;
    private static final Logger LOG = LoggerFactory.getLogger(AppTest.class);

    @BeforeEach
    public final void clearData() throws SQLException, IOException {
        app = App.getApp();
        UrlRepository.removeAll();
        UrlCheckRepository.removeAll();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            var body = response.body();
            assertNotNull(body);
            assertEquals(200, response.code());
        });
    }

    @Test
    public void testShowPage() {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("https://mypage.com");
            UrlRepository.save(url);

            var response = client.get("/urls/" + url.getId());
            assertEquals(200, response.code());
            assertNotNull(response.body());
            assertTrue(response.body().string().contains(String.valueOf(url.getName())));
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertEquals(404, response.code());
            assertNotNull(response.body());
            assertTrue(response.body().string().contains("Url with id = 999999 not found"));
        });
    }

    @Test
    public void testIndexPage() {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("https://mypage.com");
            UrlRepository.save(url);
            var response = client.get("/urls");
            assertEquals(200, response.code());

        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var name = "https://mypage.com";
            var requestBody = "url=" + name;
            var response = client.post("/urls", requestBody);


            var body = response.body().string();
            assertEquals(200, response.code());
            assertTrue(body.contains(name));

            name = " ";
            requestBody = "url=" + name;
            response = client.post("/urls", requestBody);
            assertEquals(200, response.code());
        });
    }

    @Test
    public void testNormalizeUrl() {
        var name = "https://some-domain.org/example/path";
        var expected = "https://some-domain.org";
        var actual = UrlService.normalize(name);
        assertEquals(expected, actual);

        name = "https://some-domain.org:8080/example/path";
        expected = "https://some-domain.org:8080";
        actual = UrlService.normalize(name);
        assertEquals(expected, actual);

        name = "https://some-domain.org:8080/example/path";
        expected = "https://some-domain.org:8080";
        actual = UrlService.normalize(name);
        assertEquals(expected, actual);

        var invalidUrl = "this-is-not-a-url";
        RuntimeException exc = assertThrows(RuntimeException.class, () -> {
            UrlService.normalize(invalidUrl);
        });
        //assertEquals("Некорректный URL", exc.getMessage());
    }

    @Test
    public void testUrlCheck() throws Exception {
        try (MockWebServer mockServer = new MockWebServer()) {
            mockServer.enqueue(new MockResponse()
                    .setBody("<html><head><title>Title</title><meta name=\"description\" content=\"description\">"
                            + "</head><body><h1>Hello</h1></body></html>")
                    .setResponseCode(200));

            mockServer.start();

            var baseUrl = mockServer.url("/").toString();
            var url = new Url(1L, baseUrl, LocalDateTime.now());

            UrlCheck check = UrlCheckService.check(url);

            assertNotNull(check);
            assertEquals(1L, check.getUrlId());
            assertEquals(200, check.getStatusCode());
            assertEquals("Title", check.getTitle());
            assertEquals("Hello", check.getH1());
            assertEquals("description", check.getDescription());

            var recordedRequest = mockServer.takeRequest();
            assertEquals("/", recordedRequest.getPath());
        }
    }

    @Test
    public void testCheckPage() {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("http://example.com");
            UrlRepository.save(url);
            var check =  new UrlCheck(200, "Title", "Hello", "description", url.getId());
            UrlCheckRepository.save(check);


            var response = client.get("/urls/" + url.getId());
            assertEquals(200, response.code());

            assertNotNull(response.body());
            var body = response.body().string();
            assertTrue(body.contains("Title"));
            assertTrue(body.contains("Hello"));
            assertTrue(body.contains("description"));
        });
    }

    @Test
    public void testCheckUrl() {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("http://example.com");
            UrlRepository.save(url);

            var response = client.post("/urls/" + url.getId() + "/checks");
            assertEquals(200, response.code());

            var checks = UrlCheckRepository.findAllByUrlId(url.getId());
            assertFalse(checks.isEmpty());

            var check = checks.getFirst();
            assertEquals(url.getId(), check.getUrlId());
        });
    }

    @Test
    public void testCheckUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls/999999/checks");

            assertEquals(404, response.code());
            assertNotNull(response.body());
            assertTrue(response.body().string()
                    .contains("Url with id = 999999 not found"));
        });
    }

}
