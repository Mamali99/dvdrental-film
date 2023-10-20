package entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Film implements Serializable {
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



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    @JsonbTransient
    private List<Actor> actors;

    @ManyToMany
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonbTransient
    private List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "language_id", insertable = false, updatable = false)
    private Language language;
}
