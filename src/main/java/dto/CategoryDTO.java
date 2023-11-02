package dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CategoryDTO {
    private Integer categoryId;
    private String name;
    private Timestamp lastUpdate;


    public CategoryDTO(Integer categoryId, String name, Timestamp lastUpdate) {
        this.categoryId = categoryId;
        this.name = name;
        this.lastUpdate = lastUpdate;
    }
}
