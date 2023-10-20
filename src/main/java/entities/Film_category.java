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
public class Film_category {
    @Id
    private Integer film_id;
    @Id
    private Integer category_id;
    private Timestamp last_update;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film_category that = (Film_category) o;
        return Objects.equals(film_id, that.film_id) &&
                Objects.equals(category_id, that.category_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film_id, category_id);
    }
}
