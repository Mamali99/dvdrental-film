package dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import utils.FilmHref;
import utils.StoreHref;

@JsonPropertyOrder({"id", "store", "film"})
@Getter
@Setter
public class InventoryDTO {

    private Integer id;
    private StoreHref store;
    private FilmHref film;

}
