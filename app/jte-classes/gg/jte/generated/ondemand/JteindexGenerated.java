package gg.jte.generated.ondemand;
import hexlet.code.dto.MainPage;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,3,3,5,5,6,6,8,8,8,11,11,34,34,34,34,34,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, MainPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    ");
				if (page.getFlash() != null) {
					jteOutput.writeContent("\r\n        <div class=\"alert alert-dismissible fade show alert-danger\" role=\"alert\">\r\n            <p class=\"m-0\">");
					jteOutput.setContext("p", null);
					jteOutput.writeUserContent(page.getFlash());
					jteOutput.writeContent("</p>\r\n            <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n        </div>\r\n    ");
				}
				jteOutput.writeContent("\r\n    <div class=\"container-fluid bg-dark p-5\">\r\n        <div class=\"row\">\r\n            <div class=\"col-md-10 col-lg-8 mx-auto text-white\">\r\n                <h1 class=\"display-3 mb-0\">Анализатор страниц</h1>\r\n                <p class=\"lead\">Быстрая проверка сайта на SEO пригодность</p>\r\n                <form method=\"post\" action=\"/urls\" class=\"rss-form text-body\">\r\n                    <div class=\"row\">\r\n                        <div class=\"col\">\r\n                            <div class=\"form-floating\">\r\n                                <input id=\"url-input\" type=\"text\" class=\"form-control w-100\" placeholder=\"Введите ссылку\" name=\"url\">\r\n                                <label for=\"url-input\">Введите ссылку</label>\r\n                            </div>\r\n                        </div>\r\n                        <div class=\"col-auto\">\r\n                            <button class=\"h-100 btn btn-lg btn-primary px-sm-5\" type=\"submit\">Найти</button>\r\n                        </div>\r\n                    </div>\r\n                </form>\r\n                <p class=\"mt-2 mb-0 text-muted\">Пример: https://example.com</p>\r\n            </div>\r\n        </div>\r\n    </div>\r\n");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		MainPage page = (MainPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
