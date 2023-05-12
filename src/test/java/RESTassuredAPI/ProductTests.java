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
    }

    @Test
    public void ProductSpecificCategoryTest() {
        var response = theGetApiCall();
        Products products = response.as(Products.class);
        Product product = products.getRecords()
                .stream()
                .filter(x -> x.getCategoryName().equals("Active Wear - Women")
                        &&
                        x.getName().equals("Stretchy Dance Pants"))
                .findAny().get();
        Assert.assertTrue(product.getCategoryName().equals("Active Wear - Women")
                        && product.getName().equals("Stretchy Dance Pants"),
                "Product does`nt match. Product name are: " + product.getName()
                        + "category name :" + product.getCategoryName()
        );
    }

    @Test
    public void PriceCheckForProductWithId3() {
        var response = theGetApiCall();
        Products products = response.as(Products.class);
        Product product = products.getRecords()
                .stream()
                .filter(x -> x.getId() == 3)
                .findAny().get();
        Assert.assertEquals(product.getPrice(), 68.0,
                "Product price does`nt match. Product price are: " + product.getPrice());
    }

    @Test
    public void PriceCheckForProductWithId1() {
        var response = theGetApiCall();
        Products products = response.as(Products.class);
        Product product = products.getRecords()
                .stream()
                .filter(x -> x.getId() == 1)
                .findAny().get();
        Assert.assertTrue(product.getPrice() > 90.0,
                "Product price less then 90.0. Product price are: " + product.getPrice());
    }
}
