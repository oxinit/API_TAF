package RESTassuredAPI;

import models.Product;
import models.Products;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductTests extends CrudApi {
    @Test
    public void StreamPrepareCode() {
        var response = theGetApiCall();
        Products products = response.as(Products.class);
        Double productAveragePrice = products.getRecords()
                .stream()
                .mapToDouble(a -> a.getPrice())
                .average().getAsDouble();
        System.out.println(productAveragePrice + " average price of products");

        Map<Integer, String> employeesMap = products.getRecords().stream()
                .collect(Collectors.toMap(Product::getId,
                        Product::getName));
        System.out.println(employeesMap);

        response.then().statusCode(200);
    }

    @Test
    public void ProductSpecificCategoryTest() {
        String categoryName = "Active Wear - Women";
        String productName = "Stretchy Dance Pants";
        var response = theGetApiCall();
        Products products = response.as(Products.class);
        Product product = products.getRecords()
                .stream()
                .filter(x -> x.getCategoryName().equals(categoryName)
                        &&
                        x.getName().equals(productName))
                .findAny().get();
        Assert.assertTrue(product.getCategoryName().equals(categoryName)
                        && product.getName().equals(productName),
                "Product does`nt match. Product name are: " + product.getName()
                        + "category name :" + product.getCategoryName()
        );
    }

    @Test
    public void PriceCheckForProductWithId3() {
        int productID = 3;
        double expectedPrice = 68.0;
        var response = theGetApiCall();
        Products products = response.as(Products.class);
        Product product = products.getRecords()
                .stream()
                .filter(x -> x.getId() == productID)
                .findAny().get();
        Assert.assertEquals(product.getPrice(), expectedPrice,
                "Product price does`nt match. Product price are: " + product.getPrice());
    }

    @Test
    public void PriceCheckForProductWithId1() {
        int productID = 1;
        double productPrice = 90.0;
        var response = theGetApiCall();
        Products products = response.as(Products.class);
        Product product = products.getRecords()
                .stream()
                .filter(x -> x.getId() == productID)
                .findAny().get();
        Assert.assertTrue(product.getPrice() > productPrice,
                "Product price less then 90.0. Product price are: " + product.getPrice());
    }
}
