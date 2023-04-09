package models.api.linkedin.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    @JsonProperty(value = "category_id")
    private  int categoryId;
    @JsonProperty(value = "category_name")
    private String categoryName;

    public Product(){}//default constructor

    public Product(String name , String description , double price, int categoryId, String categoryName){
        setName(name);
        setDescription(description);
        setPrice(price);
        setCategoryId(categoryId);
        setCategoryName(categoryName);
    }
    public Product(int id , String name , String description , double price, int categoryId, String categoryName){
        setId(id);
        setName(name);
        setDescription(description);
        setPrice(price);
        setCategoryId(categoryId);
        setCategoryName(categoryName);
    }

}
