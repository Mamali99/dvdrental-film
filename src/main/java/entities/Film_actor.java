package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
public class Film_actor {
    @Id
    private Integer actor_id;
    @Id
    private Integer film_id;
    private Timestamp last_update;
}

