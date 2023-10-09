package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Actor {
    @Id
    private Integer actor_id;
    private String first_name;
    private String last_name;
    private Timestamp last_update;

}