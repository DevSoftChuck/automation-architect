package models.sObjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String name;

    public User(String id, String name){
        this.id = id;
        this.name = name;
    }
}
