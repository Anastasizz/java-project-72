package hexlet.code.controller;

import hexlet.code.dto.MainPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.dto.enums.Status;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.service.UrlCheckService;
import hexlet.code.service.UrlService;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;
import java.util.List;


import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static void create(Context ctx) throws Exception {
        var rawName = ctx.formParam("url");

        if (rawName == null || rawName.isBlank()) {
            ctx.sessionAttribute("flash", "URL не введен");
            ctx.redirect("/");
            return;
        }

        var maybeName = UrlService.normalize(rawName);
        if (maybeName.isEmpty()) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("status", Status.DANGER);
            ctx.redirect("/");
            return;
        }
        var name = maybeName.get();

        var maybeUrl = UrlRepository.findByName(name);
        if (maybeUrl.isPresent()) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("status", Status.PRIMARY);
            ctx.redirect("/urls");
            return;
        }
        var url = new Url(name);
        UrlRepository.save(url);
        ctx.sessionAttribute("flash", "Страница успешно добавлена");
        ctx.sessionAttribute("status", Status.SUCCESS);
        ctx.redirect("/urls");
    }

    public static void index(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        Status status = ctx.consumeSessionAttribute("status");
        //var urls = UrlRepository.getEntities();
        var urls = UrlRepository.getItems();
        var page = new UrlsPage(urls, flash, status);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void home(Context ctx) {
        String flash = ctx.consumeSessionAttribute("flash");
        Status status = ctx.consumeSessionAttribute("status");
        var page = new MainPage(flash, status);
        ctx.render("index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        Status status = ctx.consumeSessionAttribute("status");
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Url with id = " + id + " not found"));
        List<UrlCheck> urlChecks = UrlCheckRepository.findAllByUrlId(id);
        var page = new UrlPage(url, urlChecks, flash, status);
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void check(Context ctx) throws Exception {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Url with id = " + id + " not found"));

        try {
            var urlCheck = UrlCheckService.check(url);
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("status", Status.SUCCESS);
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Страницы не существует");
            ctx.sessionAttribute("status", Status.DANGER);
        }

        ctx.redirect("/urls/" + id);

    }
}
