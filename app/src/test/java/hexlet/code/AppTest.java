package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.service.UrlCheckService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppTest {
    private static final Logger LOG = LoggerFactory.getLogger(AppTest.class);
    private Javalin app;

    @BeforeEach
    public final void clearData() throws SQLException, IOException {
        app = App.getApp();
        UrlRepository.removeAll();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            var body = response.body();
            assertNotNull(body);
            String html = body.string();
            LOG.info("HTML RESPONSE:\n{}", html);

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
            assertEquals(200, response.code());
            assertNotNull(response.body());
            assertTrue(response.body().string().contains(name));
            assertTrue(UrlRepository.findByName(name).isPresent());

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
            var url = new Url(1L, "http://example.com", LocalDateTime.now());
            UrlRepository.save(url);
            var check =  new UrlCheck(url.getId(), 200, "Title", "Hello", "description");
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

}
