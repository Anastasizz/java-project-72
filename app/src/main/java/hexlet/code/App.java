package hexlet.code;

import io.javalin.Javalin;

public final class App {
    public static Javalin getApp() {
        var app = Javalin.create(config -> {
            //настройки
            config.bundledPlugins.enableDevLogging(); //логирование запросов
        });
        app.get("/", ctx -> ctx.result("Hello World"));
        return app;
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
        var app = getApp();
        app.start(port);
    }
}
