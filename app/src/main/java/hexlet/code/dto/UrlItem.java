package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UrlItem {
    private Long id;
    private String name;
    private LocalDateTime lastCheck;
    private int statusCode;
}
