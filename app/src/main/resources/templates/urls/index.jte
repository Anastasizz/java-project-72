@import hexlet.code.dto.UrlItemsPage
@import java.time.format.DateTimeFormatter
@param UrlItemsPage page



@template.layout.page(
    mainPage = page,
    content = @`
    <div class="container-lg mt-5">
        <h1>Сайты</h1>
        @if(page.getUrls().isEmpty())
            <p>Пока не добавлено ни одного сайта</p>
        @else
            <table class="table table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <th class="col-1">ID</th>
                    <th>Имя</th>
                    <th>Последняя проверка</th>
                    <th>Код ответа</th>
                </tr>
                </thead>
                <tbody>
                @for(var url : page.getUrls())
                    <tr>
                        <td>${url.getId()}</td>
                        <td>
                            <a href="/urls/${url.getId()}">${url.getName()}</a>
                        </td>
                        <td>
                            @if(url.getLastCheck() != null)
                                ${url.getLastCheck().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}
                            @endif
                        </td>
                        <td>${url.getStatusCode()}</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        @endif
    </div>
`
)
