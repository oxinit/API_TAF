package models.api.linkedin.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.ArrayList;

@Data
public class Products {
    @JsonProperty("records")
    private ArrayList<Product> records;
    public Products() {
    }
    public Products(ArrayList<Product> records) {
        this.records = records;
    }

    public ArrayList<Product> getRecords() {
        return records;
    }


}
