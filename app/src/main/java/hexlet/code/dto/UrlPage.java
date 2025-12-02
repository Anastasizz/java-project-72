package hexlet.code.dto;

import hexlet.code.dto.enums.Status;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UrlPage extends MainPage {
    private Url url;
    private List<UrlCheck> urlChecks;

    public UrlPage(Url url, List<UrlCheck> urlChecks, String flash, Status status) {
        super(flash, status);
        this.url = url;
        this.urlChecks = urlChecks;
    }
}
