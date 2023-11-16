package entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



@Entity
public class Actor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actor_actor_id_seq")
    @SequenceGenerator(name = "actor_actor_id_seq", sequenceName = "actor_actor_id_seq", allocationSize = 1)
    private Integer actor_id;
    private String first_name;
    private String last_name;
    private Timestamp last_update;


    @JsonbTransient
    @ManyToMany(mappedBy = "actors")
    private List<Film> films = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.last_update = new Timestamp(System.currentTimeMillis());
    }

    public Integer getActor_id() {
        return actor_id;
    }

    public void setActor_id(Integer actor_id) {
        this.actor_id = actor_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}