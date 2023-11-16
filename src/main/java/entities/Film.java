package entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



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



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    @JsonbTransient
    private List<Actor> actors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "language_id", insertable = false, updatable = false)
    private Language language;

    public Integer getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Integer film_id) {
        this.film_id = film_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Short release_year) {
        this.release_year = release_year;
    }

    public Integer getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(Integer language_id) {
        this.language_id = language_id;
    }

    public Short getRental_duration() {
        return rental_duration;
    }

    public void setRental_duration(Short rental_duration) {
        this.rental_duration = rental_duration;
    }

    public BigDecimal getRental_rate() {
        return rental_rate;
    }

    public void setRental_rate(BigDecimal rental_rate) {
        this.rental_rate = rental_rate;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public BigDecimal getReplacement_cost() {
        return replacement_cost;
    }

    public void setReplacement_cost(BigDecimal replacement_cost) {
        this.replacement_cost = replacement_cost;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
