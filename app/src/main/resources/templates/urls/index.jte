@import hexlet.code.dto.UrlsPage
@import hexlet.code.dto.enums.Status
@param UrlsPage page



@template.layout.page(
content = @`
    @if(page.getFlash() != null)
        @if(page.getStatus() == Status.SUCCESS)
            <div class="alert alert-dismissible fade show alert-success" role="alert">
                <p class="m-0">${page.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @else
            <div class="alert alert-dismissible fade show alert-primary" role="alert">
                <p class="m-0">${page.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif
    @endif
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
                </tr>
                </thead>
                <tbody>
                @for(var url : page.getUrls())
                    <tr>
                        <td>${url.getId()}</td>
                        <td>
                            <a href="/urls/${url.getId()}">${url.getName()}</a>
                        </td>
                    </tr>
                @endfor
                </tbody>
            </table>
        @endif
    </div>
`
)
