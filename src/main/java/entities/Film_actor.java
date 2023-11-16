package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.util.Objects;


@Entity
public class Film_actor {
    @Id
    private Integer actor_id;
    @Id
    private Integer film_id;
    private Timestamp last_update;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film_actor that = (Film_actor) o;
        return Objects.equals(actor_id, that.actor_id) &&
                Objects.equals(film_id, that.film_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actor_id, film_id);
    }

    public Integer getActor_id() {
        return actor_id;
    }

    public void setActor_id(Integer actor_id) {
        this.actor_id = actor_id;
    }

    public Integer getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Integer film_id) {
        this.film_id = film_id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
}

