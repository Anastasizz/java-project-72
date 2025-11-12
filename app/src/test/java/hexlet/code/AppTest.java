package hexlet.code;

import hexlet.code.model.Url;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import hexlet.code.repository.UrlRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppTest {
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
            assertTrue(response.body().string().contains(String.valueOf(url.getName())));
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertEquals(404, response.code());
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
            assertTrue(response.body().string().contains(name));
            assertTrue(UrlRepository.findByName(name).isPresent());

        });
    }
}
