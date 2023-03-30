package trainingApiLinkedinCourse;

import modelsAPILinkedincourse.Product;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiLocalhostTestsAdvanced {
    final String endpointBase="http://localhost:80/api_testing/product/";
    @Test
    public void getProducts(){
        String endpoint =endpointBase+"read.php";
        var response =
                        given()
                        .when().get(endpoint)
                        .then();
        response.log().headers();
        response.assertThat().statusCode(200)
                .header("Content-Type",equalTo("application/json; charset=UTF-8"))

                .body("records.size()",greaterThan(0))
                .body("records.id",everyItem(notNullValue()))
                .body("records.name",everyItem(notNullValue()))
                .body("records.description",everyItem(notNullValue()))
                .body("records.price",everyItem(notNullValue()))
                .body("records.category_id",everyItem(notNullValue()))
                .body("records.category_name",everyItem(notNullValue()))
                .body("records.id[0]",equalTo("20"));
    }
    @Test
    public void getDeserializedProduct(){
        String endpoint=endpointBase+"read_one.php";
        Product expectedProduct=new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women"
        );

        Product actualProduct=
                given().queryParam("id","2")
                        .when().get(endpoint)
                        .as(Product.class);
                assertThat(actualProduct,samePropertyValuesAs(expectedProduct));
    }

}
