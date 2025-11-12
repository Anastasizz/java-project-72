package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.UrlsPage;
import hexlet.code.dto.enums.Status;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,2,6,6,8,8,9,9,10,10,12,12,12,15,15,17,17,17,20,20,21,21,24,24,26,26,35,35,37,37,37,39,39,39,39,39,39,39,42,42,45,45,47,47,47,48,48,48,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\r\n\r\n\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    ");
				if (page.getFlash() != null) {
					jteOutput.writeContent("\r\n        ");
					if (page.getStatus() == Status.SUCCESS) {
						jteOutput.writeContent("\r\n            <div class=\"alert alert-dismissible fade show alert-success\" role=\"alert\">\r\n                <p class=\"m-0\">");
						jteOutput.setContext("p", null);
						jteOutput.writeUserContent(page.getFlash());
						jteOutput.writeContent("</p>\r\n                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n            </div>\r\n        ");
					} else {
						jteOutput.writeContent("\r\n            <div class=\"alert alert-dismissible fade show alert-primary\" role=\"alert\">\r\n                <p class=\"m-0\">");
						jteOutput.setContext("p", null);
						jteOutput.writeUserContent(page.getFlash());
						jteOutput.writeContent("</p>\r\n                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n            </div>\r\n        ");
					}
					jteOutput.writeContent("\r\n    ");
				}
				jteOutput.writeContent("\r\n    <div class=\"container-lg mt-5\">\r\n        <h1>Сайты</h1>\r\n        ");
				if (page.getUrls().isEmpty()) {
					jteOutput.writeContent("\r\n            <p>Пока не добавлено ни одного сайта</p>\r\n        ");
				} else {
					jteOutput.writeContent("\r\n            <table class=\"table table-bordered table-hover mt-3\">\r\n                <thead>\r\n                <tr>\r\n                    <th class=\"col-1\">ID</th>\r\n                    <th>Имя</th>\r\n                </tr>\r\n                </thead>\r\n                <tbody>\r\n                ");
					for (var url : page.getUrls()) {
						jteOutput.writeContent("\r\n                    <tr>\r\n                        <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(url.getId());
						jteOutput.writeContent("</td>\r\n                        <td>\r\n                            <a href=\"/urls/");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(url.getId());
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\">");
						jteOutput.setContext("a", null);
						jteOutput.writeUserContent(url.getName());
						jteOutput.writeContent("</a>\r\n                        </td>\r\n                    </tr>\r\n                ");
					}
					jteOutput.writeContent("\r\n                </tbody>\r\n            </table>\r\n        ");
				}
				jteOutput.writeContent("\r\n    </div>\r\n");
			}
		});
		jteOutput.writeContent("\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
