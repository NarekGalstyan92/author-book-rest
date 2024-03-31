package am.itspace.authorbookrest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveBookDto {

    @NotEmpty(message = "description is required")
    private String description;
    private double price;
    @NotEmpty(message = "Title is required")
    private String title;
    private int authorId;

}
