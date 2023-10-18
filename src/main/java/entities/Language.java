package entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_language_id_seq")
    @SequenceGenerator(name = "language_language_id_seq", sequenceName = "language_language_id_seq", allocationSize = 1)
    private Integer language_id;
    private String name;
    private Timestamp last_update;

    @JsonbTransient
    @OneToMany(mappedBy = "language")
    private List<Film> films;
}
