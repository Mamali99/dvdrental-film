package dto;

import utils.FilmsHref;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
//@JsonPropertyOrder({"id", "firstName", "lastName", "films"})
public class ActorDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private List<FilmsHref> films;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<FilmsHref> getFilms() {
        return films;
    }

    public void setFilms(List<FilmsHref> films) {
        this.films = films;
    }
}
