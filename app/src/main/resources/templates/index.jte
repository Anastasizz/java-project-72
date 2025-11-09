@import hexlet.code.dto.MainPage
@param MainPage page

@template.layout.page(
content = @`
    @if(page.getFlash() != null)
        <div class="alert alert-dismissible fade show alert-danger" role="alert">
            <p class="m-0">${page.getFlash()}</p>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    @endif
    <div class="container-fluid bg-dark p-5">
        <div class="row">
            <div class="col-md-10 col-lg-8 mx-auto text-white">
                <h1 class="display-3 mb-0">Анализатор страниц</h1>
                <p class="lead">Быстрая проверка сайта на SEO пригодность</p>
                <form method="post" action="/urls" class="rss-form text-body">
                    <div class="row">
                        <div class="col">
                            <div class="form-floating">
                                <input id="url-input" type="text" class="form-control w-100" placeholder="Введите ссылку" name="url">
                                <label for="url-input">Введите ссылку</label>
                            </div>
                        </div>
                        <div class="col-auto">
                            <button class="h-100 btn btn-lg btn-primary px-sm-5" type="submit">Найти</button>
                        </div>
                    </div>
                </form>
                <p class="mt-2 mb-0 text-muted">Пример: https://example.com</p>
            </div>
        </div>
    </div>
`
)