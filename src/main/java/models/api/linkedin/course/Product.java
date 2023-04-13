package models.api.linkedin.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    @JsonProperty(value = "category_id")
    private  int categoryId;
    @JsonProperty(value = "category_name")
    private String categoryName;

}
