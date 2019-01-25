package sr.unasat.jpa.app;


import com.sun.org.apache.xpath.internal.operations.Or;
import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.dao.OrderedProductDao;
import sr.unasat.jpa.entities.OrderedProduct;
import sr.unasat.jpa.entities.Product;
import sr.unasat.jpa.services.ProductService;

import java.util.List;

public class Application {

    public static void main(String[] args) {

//        ProductService productService = new ProductService(JPAConfiguration.getEntityManager());
//        List<Product> productList = productService.selectAllProducts();
//        productService.addToStock(1, 6);
//
//        productList.forEach(p -> System.out.println(p));

        OrderedProductDao orderedProductDao = new OrderedProductDao(JPAConfiguration.getEntityManager());
        List<OrderedProduct> orders = orderedProductDao.addToOrders(2, 3);
    }
}
