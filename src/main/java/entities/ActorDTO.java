package entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActorDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private FilmsHref films;


}
