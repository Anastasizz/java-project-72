package hexlet.code.dto;

import hexlet.code.dto.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MainPage {
    private String flash;
    private Status status;
}
