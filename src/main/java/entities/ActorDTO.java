package entities;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
@JsonPropertyOrder({"id", "firstName", "lastName", "films"})
@Setter
@Getter
public class ActorDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private List<FilmsHref> films;


}
