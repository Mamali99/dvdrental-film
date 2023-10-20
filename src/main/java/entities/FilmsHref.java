package entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilmsHref {
    private String href;

    public FilmsHref (String href){
        this.href = href;
    }
}
