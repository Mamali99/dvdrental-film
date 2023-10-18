package entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_category_id_seq")
    @SequenceGenerator(name = "category_category_id_seq", sequenceName = "category_category_id_seq", allocationSize = 1)
    private Integer category_id;
    private String name;
    private Timestamp last_update;

    @JsonbTransient
    @ManyToMany(mappedBy = "categories")
    private List<Film> films;
}
