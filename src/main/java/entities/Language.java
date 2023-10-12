package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@Entity
public class Language {
    @Id
    private Integer language_id;
    private String name;
    private Timestamp last_update;
}
