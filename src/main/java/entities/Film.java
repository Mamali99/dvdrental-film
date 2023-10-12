package entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "film_film_id_seq")
    @SequenceGenerator(name = "film_film_id_seq", sequenceName = "film_film_id_seq", allocationSize = 1)
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


    @ManyToMany(mappedBy = "films")
    @JsonbTransient
    private List<Actor> actors;


}
