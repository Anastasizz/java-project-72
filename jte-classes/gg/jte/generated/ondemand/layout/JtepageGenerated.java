package gg.jte.generated.ondemand.layout;
import gg.jte.Content;
import hexlet.code.dto.MainPage;
import hexlet.code.dto.enums.Status;
@SuppressWarnings("unchecked")
public final class JtepageGenerated {
	public static final String JTE_NAME = "layout/page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,4,4,4,4,34,34,34,35,35,37,37,37,40,40,41,41,43,43,43,46,46,48,48,48,51,51,52,52,53,53,54,54,54,65,65,65,4,5,5,5,5};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, MainPage mainPage, Content content) {
		jteOutput.writeContent("\r\n\r\n<!DOCTYPE html>\r\n<html lang=\"ru\">\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n    <title>Анализатор страниц</title>\r\n\r\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB\" crossorigin=\"anonymous\">\r\n</head>\r\n<body class=\"d-flex flex-column min-vh-100\">\r\n<nav class=\"navbar navbar-expand-lg bg-body-tertiary\">\r\n    <div class=\"container-fluid\">\r\n        <a class=\"navbar-brand\" href=\"/\">Анализатор страниц</a>\r\n        <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\r\n            <ul class=\"navbar-nav\">\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link\" href=\"/\">Главная</a>\r\n                </li>\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link\" href=\"/urls\">Сайты</a>\r\n                </li>\r\n            </ul>\r\n        </div>\r\n    </div>\r\n</nav>\r\n<main class=\"flex-grow-1\">\r\n    ");
		if (mainPage.getFlash() != null) {
			jteOutput.writeContent("\r\n        ");
			if (mainPage.getStatus() == Status.SUCCESS) {
				jteOutput.writeContent("\r\n            <div class=\"alert alert-dismissible fade show alert-success\" role=\"alert\">\r\n                <p class=\"m-0\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(mainPage.getFlash());
				jteOutput.writeContent("</p>\r\n                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n            </div>\r\n        ");
			} else {
				jteOutput.writeContent("\r\n            ");
				if (mainPage.getStatus() == Status.PRIMARY) {
					jteOutput.writeContent("\r\n                <div class=\"alert alert-dismissible fade show alert-primary\" role=\"alert\">\r\n                    <p class=\"m-0\">");
					jteOutput.setContext("p", null);
					jteOutput.writeUserContent(mainPage.getFlash());
					jteOutput.writeContent("</p>\r\n                    <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n                </div>\r\n            ");
				} else {
					jteOutput.writeContent("\r\n                <div class=\"alert alert-dismissible fade show alert-danger\" role=\"alert\">\r\n                    <p class=\"m-0\">");
					jteOutput.setContext("p", null);
					jteOutput.writeUserContent(mainPage.getFlash());
					jteOutput.writeContent("</p>\r\n                    <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n                </div>\r\n            ");
				}
				jteOutput.writeContent("\r\n        ");
			}
			jteOutput.writeContent("\r\n    ");
		}
		jteOutput.writeContent("\r\n    ");
		jteOutput.setContext("main", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\r\n</main>\r\n\r\n<footer class=\"border-top py-3 mt-auto\">\r\n    <div class=\"text-center\">\r\n        <small>created by <a href=\"https://github.com/Anastasizz\" target=\"_blank\">Lolly Mir</a></small>\r\n    </div>\r\n</footer>\r\n\r\n<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI\" crossorigin=\"anonymous\"></script>\r\n</body>\r\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		MainPage mainPage = (MainPage)params.get("mainPage");
		Content content = (Content)params.get("content");
		render(jteOutput, jteHtmlInterceptor, mainPage, content);
	}
}
