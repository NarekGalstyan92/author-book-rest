package am.itspace.authorbookrest.dto;

import am.itspace.authorbookrest.entity.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAuthorDto {

    /**
     *  @JsonProperty("name") indicates that the JSON file can have different field names,
     *  such as "firstName" or "first_name". This annotation specifies that this field
     *  should be mapped to the "name" property in the JSON.
     */
    @JsonProperty("name")
    private String name;

    private String surname;

    private Gender gender;

    private int age;
}
