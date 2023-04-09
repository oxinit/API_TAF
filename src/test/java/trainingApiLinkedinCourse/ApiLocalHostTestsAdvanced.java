package trainingApiLinkedinCourse;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.specification.Argument;
import models.api.linkedin.course.Product;
import models.api.linkedin.course.Products;
import org.hamcrest.Matcher;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.DataInput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class ApiLocalHostTestsAdvanced {

    Product expectedProduct = new Product(
            2,
            "Cross-Back Training Tank",
            "The most awesome phone of 2013!",
            299.00,
            2,
            "Active Wear - Women"
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
    public void getProducts() throws IOException {
        String endpoint = "read.php";
        var response =
                given()
                        .when().get(endpoint);
        response.then().log().headers();

        response.then().assertThat().statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=UTF-8"));

        Products products = new Products(response.as(Products.class).getRecords());
        SoftAssert softAssert= new SoftAssert();
        softAssert.assertTrue(products.getRecords().size()>0);
        softAssert.assertTrue(products!=nullValue());
        softAssert.assertEquals(products.getRecords().get(17),expectedProduct, "not expected object");
                      softAssert.assertAll();

    }

    @Test
    public void getDeserializedProduct() {
        String endpoint ="read_one.php";

        Product actualProduct =
                given().queryParam("id", "2")
                        .when().get(endpoint)
                        .as(Product.class);

        assertThat(actualProduct, samePropertyValuesAs(expectedProduct));
    }

}
