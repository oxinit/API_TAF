package RESTassuredAPI;


import models.Product;
import models.Products;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.hamcrest.Matchers.*;


public class ApiLocalHostTests extends CrudApi {
    Product expectedProduct = new Product(//valid existing database entry
            2,
            "Cross-Back Training Tank",
            "The most awesome phone of 2013!",
            299.00,
            2,
            "Active Wear - Women");
    Product wrongProduct = new Product(//negative testing sample
            2,
            "Cross-Back Training Tank3",
            "Thef most awesome phon3e of 2013!",
            2995.00,
            32,
            "Activae Wear - Women");

    @Test
    public void getOneProduct() {
        var response = theGetApiCall(2);
        response.then().log().body();
        response.then().assertThat().statusCode(200);
        Product actualProduct = theGetApiCall(2).as(Product.class);
        Assert.assertEquals(actualProduct, expectedProduct,
                "product does`t match! Expected:" + expectedProduct.toString() + " but found: "
                        + actualProduct.toString());
    }

    @Test
    public void getProducts() {
        var response = theGetApiCall();
        response.then().log().headers();

        Products products = response.as(Products.class);

        response.then().assertThat().statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=UTF-8"));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(products.getRecords().size() > 0, "array of products size lesser then 0");
        softAssert.assertFalse(products.getRecords().isEmpty(), "array of products empty");
        softAssert.assertEquals(products.getRecords().get(products.getRecords().size() - 2)
                , expectedProduct, "not expected object");
        softAssert.assertAll();
    }

    @Test
    public void createProduct() {
        //BD gives id automatically , so we use this kind of obj construct
        Product product = Product.builder()
                .name("Water Bottle")
                .description("Blue water bottle.Holds 64 ounces")
                .price(2)
                .categoryId(3)
                .categoryName("Active Wear - Women")
                .build();
        var response = thePostApiCall(product);
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(), 201, "asserted status code is wrong");
    }

    @Test
    public void updateProduct() {
        Product product = new Product(
                20,
                "Water Bottle",
                "Blue water bottle.Holds 64 ounces",
                15.00,
                3,
                "Active Wear - Unisex");
        var response = thePutApiCall(product);
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(), 200, "asserted status code is wrong");
    }

    @Test
    public void deleteProduct() {
        var response = theDeleteApiCall(21);
        response.then().log().body();

        Assert.assertEquals(theGetApiCall(21).getStatusCode(), 404, "element not have been found");
    }
}
