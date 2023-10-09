package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Film_actor {
    @Id
    private Integer actor_id;
    @Id
    private Integer film_id;
    private Timestamp last_update;
}

