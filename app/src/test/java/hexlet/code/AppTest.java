package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.service.UrlService;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import hexlet.code.repository.UrlRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public final class AppTest {
    private static Javalin app;

    @BeforeEach
    public void clearData() throws SQLException, IOException  {
        app = App.getApp();
        UrlCheckRepository.removeAll();
        UrlRepository.removeAll();
        log.info("===Database cleared===");
    }

    @AfterEach
    void closePool() {
        var pool = BaseRepository.connPool;
        if (pool != null && !pool.isClosed()) {
            pool.close();
        }
        log.info("===Connection pool closed===");
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            log.info("GET / -> status {}", response.code());

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
            log.info("Saved URL with id={}", url.getId());

            var response = client.get("/urls/" + url.getId());
            log.info("GET /urls/{} -> status {}", url.getId(), response.code());

            assertEquals(200, response.code());
            assertNotNull(response.body());
            assertTrue(response.body().string().contains(String.valueOf(url.getName())));
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            log.info("GET /urls/999999 -> status {}", response.code());

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
            log.info("Saved URL with id={}", url.getId());

            var response = client.get("/urls");
            log.info("GET /urls -> status {}", response.code());

            assertEquals(200, response.code());
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var name = "https://mypage.com";
            var requestBody = "url=" + name;
            var response = client.post("/urls", requestBody);
            log.info("POST /urls -> status {}", response.code());


            var body = response.body().string();
            assertEquals(200, response.code());
            assertTrue(body.contains(name));

            name = " ";
            requestBody = "url=" + name;
            response = client.post("/urls", requestBody);
            log.info("POST /urls -> status {}", response.code());
            assertEquals(200, response.code());
        });
    }

    @Test
    public void testNormalizeUrl() throws Exception {
        var name = new URI("https://some-domain.org/example/path");
        var expected = "https://some-domain.org";
        var actual = UrlService.normalize(name);
        assertEquals(expected, actual);

        name = new URI("https://some-domain.org:8080/example/path");
        expected = "https://some-domain.org:8080";
        actual = UrlService.normalize(name);
        assertEquals(expected, actual);

        name = new URI("https://some-domain.org:8080/example/path");
        expected = "https://some-domain.org:8080";
        actual = UrlService.normalize(name);
        assertEquals(expected, actual);

        var invalidUrl = new URI("this-is-not-a-url");
        RuntimeException exc = assertThrows(RuntimeException.class, () -> {
            UrlService.normalize(invalidUrl);
        });
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

            JavalinTest.test(app, (server, client) -> {
                var url = new Url(baseUrl);
                UrlRepository.save(url);
                var urlId = url.getId();
                log.info("Saved URL with id={}", urlId);

                var response = client.post("/urls/" + urlId + "/checks");
                assertEquals(200, response.code());

                var checks = UrlCheckRepository.findAllByUrlId(urlId);
                assertFalse(checks.isEmpty());

                var check = checks.getFirst();
                assertEquals(urlId, check.getUrlId());
                assertEquals(200, check.getStatusCode());
                assertEquals("Title", check.getTitle());
                assertEquals("Hello", check.getH1());
                assertEquals("description", check.getDescription());

            });

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

    @Test
    public void testGetEntities() throws Exception {
        var url1 = new Url("https://example1.com");
        var url2 = new Url("https://example2.com");

        UrlRepository.save(url1);
        UrlRepository.save(url2);

        var urls = UrlRepository.getEntities();

        assertNotNull(urls);
        assertEquals(2, urls.size());

        var names = urls.stream()
                       .map(Url::getName)
                       .toList();

        assertTrue(names.contains("https://example1.com"));
        assertTrue(names.contains("https://example2.com"));
    }

}





















