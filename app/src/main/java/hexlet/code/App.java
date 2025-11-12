package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.UrlsController;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

public final class App {
    private static final String H2_URL = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";

    public static Javalin getApp() throws IOException, SQLException {
        var hikariConfig = new HikariConfig();
        var jdbcUrl = getDataBaseUrl();
        hikariConfig.setJdbcUrl(jdbcUrl);

        var connPool = new HikariDataSource(hikariConfig);
        var sql = readResourceFile("schema.sql");

        try (var connection = connPool.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.connPool = connPool;

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.before(ctx -> {
            ctx.res().setCharacterEncoding("UTF-8");
            ctx.req().setCharacterEncoding("UTF-8");
            ctx.contentType("text/html; charset=UTF-8");
        });

        app.get("/", UrlsController::main);
        app.post("/urls", UrlsController::create);
        app.get("/urls", UrlsController::index);
        app.get("/urls/{id}", UrlsController::show);
        return app;
    }

    public static void main(String[] args) throws IOException, SQLException {
        System.setProperty("file.encoding", "UTF-8");
        var app = getApp();
        app.start(getPort());
    }

    private static String getDataBaseUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", H2_URL);
    }

    private static int getPort() {
        return Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
    }

    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IOException(fileName + " not found!");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }
}
