package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
@NoArgsConstructor
@Data
public class Products {
    @JsonProperty("records")
    private ArrayList<Product> records;
}
