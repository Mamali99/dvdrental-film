package entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilmsHref {
    private String href;

    @JsonCreator
    public FilmsHref(@JsonProperty("href") String href) {
        this.href = href;
    }
}
