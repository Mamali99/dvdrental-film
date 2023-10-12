package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
public class Film_category {
    @Id
    private Integer film_id;
    @Id
    private Integer category_id;
    private Timestamp last_update;
}
