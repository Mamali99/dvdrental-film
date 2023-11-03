package services;

import dto.ActorDTO;
import dto.CategoryDTO;
import dto.FilmDTO;
import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import utils.FilmsHref;
import utils.UpdateRequestFilm;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Named
@Stateless
public class FilmService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private LanguageService languageService;

    @Inject
    private ActorService actorService;

    @Inject
    private CategoryService categoryService;

    /*
    public List<Film> getFirst20Films() {
        TypedQuery<Film> query = entityManager.createQuery("SELECT f FROM Film f", Film.class);
        query.setMaxResults(20);
        return query.getResultList();
    }

     */

    public List<Film> getFilmsByPage(int page){
        int startIndex = (page - 1) * 20;
        TypedQuery<Film> query = entityManager.createQuery("SELECT f FROM Film f ORDER BY f.film_id ASC", Film.class);
        query.setFirstResult(startIndex);
        query.setMaxResults(20);
        return query.getResultList();
    }

    @Transactional
    public List<FilmDTO> getFilmDTOs(int page) {
        List<Film> films = getFilmsByPage(page);

        List<FilmDTO> filmDTOs = new ArrayList<>();
        for (Film film: films){
            filmDTOs.add(convertFilmToDTO(film));
        }
        return filmDTOs;
    }




    @Transactional
    public Film createFilmFromDTO(FilmDTO filmDTO) {
        Film film = convertDTOToFilm(filmDTO);
        if(film != null){
            entityManager.persist(film);
            return film;
        }
        return null;
    }

    public int getFilmCount() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(f) FROM Film f", Long.class);
        return query.getSingleResult().intValue();
    }


    public Film getFilmById(int id) {
        return entityManager.find(Film.class, id);
    }

    public FilmDTO getFilmDTOById(int id) {
        Film film = getFilmById(id);
        if (film == null) {
            return null;
        }
        return convertFilmToDTO(film);
    }




    public boolean updateFilm(int id, List<UpdateRequestFilm> updates){
        Film film = getFilmById(id);

        if (film == null) {
            return false;
        }

        for (UpdateRequestFilm update : updates) {
            switch (update.getKey()) {
                case "title":
                    film.setTitle(update.getValue());
                    break;
                case "description":
                    film.setDescription(update.getValue());
                    break;
                case "rentalRate":
                    film.setRental_rate(new BigDecimal(update.getValue()));
                    break;
                case "replacementCost":
                    film.setReplacement_cost(new BigDecimal(update.getValue()));
                    break;
                default:

                    break;
            }
        }

        entityManager.merge(film);
        return true;
    }

    public List<ActorDTO> getActorsByFilmId(int id) {
        Film film = entityManager.find(Film.class, id);
        if (film == null) {
            return null;
        }

        List<ActorDTO> actorDTOs = new ArrayList<>();
        for (Actor actor : film.getActors()) {
            ActorDTO actorDTO = new ActorDTO();
            actorDTO.setId(actor.getActor_id());
            actorDTO.setFirstName(actor.getFirst_name());
            actorDTO.setLastName(actor.getLast_name());

            // Setzen der Filme, in denen der Schauspieler gespielt hat
            List<FilmsHref> filmsLinks = new ArrayList<>();
            for (Film actorFilm : actor.getFilms()) {
                filmsLinks.add(new FilmsHref("/films/" + actorFilm.getFilm_id()));
            }
            actorDTO.setFilms(filmsLinks);

            actorDTOs.add(actorDTO);
        }

        return actorDTOs;
    }

    public List<CategoryDTO> getCategoriesByFilmId(int id){
        Film film = entityManager.find(Film.class, id);
        if (film == null) {
            return null;
        }

        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (Category category : film.getCategories()) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    category.getCategory_id(),
                    category.getName(),
                    category.getLast_update()
            );
            categoryDTOs.add(categoryDTO);
        }

        return categoryDTOs;
    }

    @Transactional
    public void addActorToFilm(int filmId, int actorId) {
        // Film und Schauspieler aus der Datenbank abrufen
        Film film = getFilmById(filmId);
        Actor actor = actorService.getActorById(actorId);

        // Überprüfen, ob Film und Schauspieler existieren
        if (film == null || actor == null) {
            throw new IllegalArgumentException("Film oder Schauspieler nicht gefunden.");
        }

        // Überprüfen, ob der Schauspieler bereits dem Film zugeordnet ist
        if (film.getActors().contains(actor)) {
            throw new IllegalArgumentException("Der Schauspieler ist bereits dem Film zugeordnet.");
        }

        // Schauspieler zum Film hinzufügen
        film.getActors().add(actor);

        // Film zur Liste der Filme des Schauspielers hinzufügen
        actor.getFilms().add(film);

        // Film und Schauspieler in der Datenbank aktualisieren
        entityManager.merge(film);
        entityManager.merge(actor);
    }

    @Transactional
    public void addCategoryToFilm(int filmId, int categoryId) {

        Film film = getFilmById(filmId);
        Category category = categoryService.getCategoryById(categoryId);


        if (film == null || category == null) {
            throw new IllegalArgumentException("Film oder Kategorie nicht gefunden.");
        }

        // Überprüfen, ob die Kategorie bereits dem Film zugeordnet ist
        if (film.getCategories().contains(category)) {
            throw new IllegalArgumentException("Die Kategorie ist bereits dem Film zugeordnet.");
        }


        film.getCategories().add(category);
        category.getFilms().add(film);


        entityManager.merge(film);
        entityManager.merge(category);
    }

    /**
     * Konvertiert ein Film-Objekt in ein FilmDTO-Objekt.
     *
     * @param film Das Film-Objekt, das konvertiert werden soll.
     * @return Das konvertierte FilmDTO-Objekt.
     */
    public FilmDTO convertFilmToDTO(Film film) {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setId(film.getFilm_id());
        filmDTO.setTitle(film.getTitle());
        filmDTO.setDescription(film.getDescription());
        filmDTO.setLength(film.getLength());
        filmDTO.setRating(film.getRating());
        filmDTO.setReleaseYear(film.getRelease_year());
        filmDTO.setRentalDuration(film.getRental_duration());
        filmDTO.setRentalRate(film.getRental_rate());
        filmDTO.setReplacementCost(film.getReplacement_cost());
        filmDTO.setLanguage(film.getLanguage().getName().trim());

        List<FilmsHref> actorsLinks = new ArrayList<>();
        for (Actor actor : film.getActors()) {
            actorsLinks.add(new FilmsHref("http://localhost:8081/actors/" + actor.getActor_id() + "/films"));
        }
        filmDTO.setActors(actorsLinks);

        List<String> categories = new ArrayList<>();
        for (Category category : film.getCategories()) {
            categories.add(category.getName());
        }
        filmDTO.setCategories(categories);

        return filmDTO;
    }


    /**
     * Konvertiert ein FilmDTO-Objekt in ein Film-Objekt.
     *
     * @param filmDTO Das FilmDTO-Objekt, das konvertiert werden soll.
     * @return Das konvertierte Film-Objekt.
     */
    public Film convertDTOToFilm(FilmDTO filmDTO) {
        Film film = new Film();
        film.setTitle(filmDTO.getTitle());
        film.setDescription(filmDTO.getDescription());
        film.setRelease_year(filmDTO.getReleaseYear());
        film.setRental_duration(filmDTO.getRentalDuration());
        film.setRental_rate(filmDTO.getRentalRate());
        film.setLength(filmDTO.getLength());
        film.setReplacement_cost(filmDTO.getReplacementCost());
        film.setRating(filmDTO.getRating());

        if (filmDTO.getLanguage() != null) {
            Language language = languageService.getLanguageByName(filmDTO.getLanguage());
            if (language != null) {
                film.setLanguage(language);
                film.setLanguage_id(language.getLanguage_id());
            } else{
                return null;
            }
        }

        if (filmDTO.getActors() != null) {
            for (FilmsHref actorHref : filmDTO.getActors()) {
                if (actorHref.getHref() != null) {
                    String actorIdStr = actorHref.getHref().replaceAll("[^0-9]", "");
                    Integer actorId = Integer.parseInt(actorIdStr);
                    Actor actor = actorService.getActorById(actorId);
                    if (actor != null) {
                        film.getActors().add(actor);
                    }else{
                        return null;
                    }
                }
            }
        }

        if (filmDTO.getCategories() != null) {
            for (String categoryName : filmDTO.getCategories()) {
                Category category = categoryService.getCategoryByName(categoryName);
                if (category != null) {
                    film.getCategories().add(category);
                }else{
                    return null;
                }
            }
        }
        film.setLast_update(new Timestamp(System.currentTimeMillis()));

        return film;

    }


}
