package dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import utils.FilmHref;
import utils.StoreHref;

//@JsonPropertyOrder({"id", "store", "film"})

public class InventoryDTO {

    private Integer id;
    private StoreHref store;
    private FilmHref film;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StoreHref getStore() {
        return store;
    }

    public void setStore(StoreHref store) {
        this.store = store;
    }

    public FilmHref getFilm() {
        return film;
    }

    public void setFilm(FilmHref film) {
        this.film = film;
    }
}
