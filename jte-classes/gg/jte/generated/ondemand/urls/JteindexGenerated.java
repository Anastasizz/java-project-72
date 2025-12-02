package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.UrlsPage;
import java.time.format.DateTimeFormatter;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,2,6,6,9,9,12,12,14,14,25,25,27,27,27,29,29,29,29,29,29,29,32,32,33,33,33,34,34,36,36,36,38,38,41,41,43,43,43,44,44,44,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\r\n\r\n\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, page, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    <div class=\"container-lg mt-5\">\r\n        <h1>Сайты</h1>\r\n        ");
				if (page.getUrls().isEmpty()) {
					jteOutput.writeContent("\r\n            <p>Пока не добавлено ни одного сайта</p>\r\n        ");
				} else {
					jteOutput.writeContent("\r\n            <table class=\"table table-bordered table-hover mt-3\">\r\n                <thead>\r\n                <tr>\r\n                    <th class=\"col-1\">ID</th>\r\n                    <th>Имя</th>\r\n                    <th>Последняя проверка</th>\r\n                    <th>Код ответа</th>\r\n                </tr>\r\n                </thead>\r\n                <tbody>\r\n                ");
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
						jteOutput.writeContent("</a>\r\n                        </td>\r\n                        <td>\r\n                            ");
						if (url.getLastCheck() != null) {
							jteOutput.writeContent("\r\n                                ");
							jteOutput.setContext("td", null);
							jteOutput.writeUserContent(url.getLastCheck().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
							jteOutput.writeContent("\r\n                            ");
						}
						jteOutput.writeContent("\r\n                        </td>\r\n                        <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(url.getStatusCode());
						jteOutput.writeContent("</td>\r\n                    </tr>\r\n                ");
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
