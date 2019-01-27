package sr.unasat.jpa.app;


import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.services.OrderedProductsService;
import sr.unasat.jpa.services.ProductService;

public class Application {

    public static void main(String[] args) {

        OrderedProductsService orderedProductsService = new OrderedProductsService(JPAConfiguration.getEntityManager());
        OrderedProductsService orderedProductsService1 = new OrderedProductsService(JPAConfiguration.getEntityManager());
        ProductService productService = new ProductService(JPAConfiguration.getEntityManager());
        orderedProductsService.addToOrders(1, 2);
        orderedProductsService1.addToOrders(1, 5);
    }
}
