package dto;

import utils.FilmsHref;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class FilmDTO {
    private Integer id;
    private String title;
    private String description;
    private Short length;
    private String rating;
    private Short releaseYear;
    private Short rentalDuration;
    private BigDecimal rentalRate;
    private BigDecimal replacementCost;
    private String language;
    private List<FilmsHref> actors;
    private List<String> categories;


}

