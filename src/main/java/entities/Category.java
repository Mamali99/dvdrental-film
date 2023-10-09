package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@Entity
public class Category {
    @Id
    private Integer category_id;
    private String name;
    private Timestamp last_update;
}
