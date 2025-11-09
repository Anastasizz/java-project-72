package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.dto.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UrlsPage extends MainPage {
    private List<Url> urls;

    public UrlsPage(List<Url> urls, String flash, Status status) {
        super(flash, status);
        this.urls = urls;
    }
}
