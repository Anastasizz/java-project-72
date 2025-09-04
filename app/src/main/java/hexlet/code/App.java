package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;

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
        hikariConfig.setJdbcUrl(getDataBaseUrl());

        var connPool = new HikariDataSource(hikariConfig);
        var sql = readResourceFile("schema.sql");

        try (var connection = connPool.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(connPool::close));
        BaseRepository.connPool = connPool;

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        app.get("/", ctx -> ctx.result("Hello World"));
        return app;
    }

    public static void main(String[] args) throws IOException, SQLException {
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
}
