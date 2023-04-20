package RESTassuredAPI;

import io.restassured.response.Response;
import models.Product;
import static io.restassured.RestAssured.given;

public class CrudApi {
    public Response theGetApiCall(int productID){
            final String endpoint = "read_one.php";
            return given().queryParam("id", productID)
                        .when().get(endpoint);
    }
    public Response theGetApiCall(){
        String endpoint = "read.php";
        return given().when().get(endpoint);
    }
    public Response thePostApiCall(Product product){
        final String endpoint="create.php";
        return given().body(product)
                .when().post(endpoint);
    }
    public Response thePutApiCall(Product product){
        String endpoint="update.php";
        return given().body(product)
                        .when().put(endpoint);
    }
    public Response theDeleteApiCall(int productId){
       final String endpoint="delete.php";
        Product product=Product.builder()
                .id(productId).build();
        return given().body(product)
                        .when().delete(endpoint);
    }

}
