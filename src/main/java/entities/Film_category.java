package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;


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

    public Integer getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Integer film_id) {
        this.film_id = film_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
}
