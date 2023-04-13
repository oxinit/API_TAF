package trainingApiLinkedinCourse;



import io.restassured.RestAssured;

import models.api.linkedin.course.Product;
import models.api.linkedin.course.Products;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ApiLocalHostTestsAdvanced {
    Product expectedProduct = new Product(//valid existing database entry
            2,
            "Cross-Back Training Tank",
            "The most awesome phone of 2013!",
            299.00,
            2,
            "Active Wear - Women"
    );
    Product wrongProduct = new Product(//negative testing sample
            2,
            "Cross-Back Training Tank3",
            "Thef most awesome phone of 2013!",
            2995.00,
            32,
            "Activae Wear - Women"
    );
    @BeforeClass
    public void setup() {
        RestAssured.baseURI="http://localhost:80/api_testing/product/";
    }
    @AfterMethod
    public void getTestExecutionTime(ITestResult result) {
        String methodName = result.getMethod()
                .getMethodName();
        long totalExecutionTime = (result.getEndMillis() - result.getStartMillis());
        System.out.println(
                "Total Execution time: " + totalExecutionTime + " milliseconds" + " for method " + methodName);
    }
    @Test
    public void getOneProduct() {
        final String endpoint = "read_one.php";

        var response =
                given().queryParam("id", 2)
                        .when().get(endpoint)
                        .then();
        response.log().body();
        response.assertThat().statusCode(200);

        Product actualProduct =
                given().queryParam("id", "2")
                        .when().get(endpoint)
                        .as(Product.class);
        Assert.assertEquals(actualProduct, expectedProduct, "not expected object");
    }

    @Test
    public void getProducts() {
        String endpoint = "read.php";
        var response =
                given()
                        .when().get(endpoint);
        response.then().log().headers();
       // response.then().log().body();
        response.then().assertThat().statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=UTF-8"));

         Products products = new Products(response.as(Products.class).getRecords());

        SoftAssert softAssert= new SoftAssert();
        softAssert.assertTrue(products.getRecords().size()>0,"array of products size lesser then 0");
        softAssert.assertFalse(products.getRecords().isEmpty(),"array of products empty");
        softAssert.assertEquals(products.getRecords().get(
                products.getRecords().size()-2
        ),expectedProduct, "not expected object");
                      softAssert.assertAll();

    }

    @Test
    public void getDeserializedProduct() {
        String endpoint ="read_one.php";
        Product actualProduct =
                given().queryParam("id", "2")
                        .when().get(endpoint)
                        .as(Product.class);

        Assert.assertEquals(actualProduct,expectedProduct,
                "product does`t match! Expected:"+expectedProduct.toString()+" but found: "
                        +actualProduct.toString());
    }

    @Test
    public void createProduct(){
        String endpoint="create.php";
        Product product = Product.builder()
                .name("Water Bottle")
                .description("Blue water bottle.Holds 64 ounces")
                .price(2)
                .categoryId(3)
                .categoryName("Active Wear - Women")
                .build();
        var response=given()
                .body(product)
                .when().post(endpoint);
        response.then().log().body();
       Assert.assertEquals(response.getStatusCode(),201,"assert status code wrong");
    }
    @Test
    public void updateProduct(){
        String endpoint="update.php";
          Product product = new Product(
                  20,
               "Water Bottle",
                "Blue water bottle.Holds 64 ounces",
                15.00,
                3,
                  "Active Wear - Unisex");

        var response=
                given().body(product)
                .when().put(endpoint);
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(),200,"assert status code wrong");
    }
    @Test
    public void deleteProduct(){
        String endpoint="delete.php";
        Product product=Product.builder()
                .id(27).build();
        var response=
                given().body(product)
                        .when().delete(endpoint)
                        .then();
        response.log().body();
        Assert.assertEquals(getOneProduct(27),404,"element not found");
    }//in case of pre or after steps
    public void deleteProduct(int productId){
        String endpoint="delete.php";
        Product product=Product.builder()
                .id(productId).build();
        var response=
                given().body(product)
                        .when().delete(endpoint)
                        .then();
        response.log().body();
    }
    public int getOneProduct(int productId) {
        final String endpoint = "read_one.php";

        var response =
                given().queryParam("id", productId)
                        .when().get(endpoint);
        response.then().log().body();
        return response.getStatusCode();
    }

}
