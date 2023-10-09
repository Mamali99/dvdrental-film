package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Film_category {
    @Id
    private Integer film_id;
    @Id
    private Integer category_id;
    private Timestamp last_update;
}
