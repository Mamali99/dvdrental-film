package entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ActorDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private List<FilmsHref> films;


}
