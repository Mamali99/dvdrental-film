package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestActor {
    private String valueType;
    private String key;
    private String value;

}
