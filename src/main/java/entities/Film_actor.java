package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Setter
@Getter
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
}

