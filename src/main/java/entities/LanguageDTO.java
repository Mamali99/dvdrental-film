package entities;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class LanguageDTO {
    private Integer languageId;
    private String name;
    private Timestamp lastUpdate;

    public LanguageDTO() {
    }

    public LanguageDTO(Integer languageId, String name, Timestamp lastUpdate) {
        this.languageId = languageId;
        this.name = name;
        this.lastUpdate = lastUpdate;
    }

}
