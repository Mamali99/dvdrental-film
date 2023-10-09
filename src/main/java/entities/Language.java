package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Language {
    @Id
    private Integer language_id;
    private String name;
    private Timestamp last_update;
}
