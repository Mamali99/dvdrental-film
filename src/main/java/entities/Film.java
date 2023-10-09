package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Film {
    @Id
    private Integer film_id;
    private String title;
    private String description;
    private Short release_year;
    private Integer language_id;
    private Short rental_duration;
    private BigDecimal rental_rate;
    private Short length;
    private BigDecimal replacement_cost;
    private String rating;
    private Timestamp last_update;
}
