package sr.unasat.jpa.app;


import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.services.OrderedProductsService;

public class Application {

    public static void main(String[] args) {

//        ProductService productService = new ProductService(JPAConfiguration.getEntityManager());
//        List<Product> productList = productService.selectAllProducts();
//        productService.addToStock(1, 6);
//
//        productList.forEach(p -> System.out.println(p));

//        ProductService productService = new ProductService(JPAConfiguration.getEntityManager());
//        productService.selectAllProducts().forEach(p -> System.out.println(p));
        OrderedProductsService orderedProductsService = new OrderedProductsService(JPAConfiguration.getEntityManager());
        orderedProductsService.removeFromOrders(4);
        orderedProductsService.removeFromOrders(6);
        orderedProductsService.removeFromOrders(7);
        orderedProductsService.viewAllOrders();
//        OrderedProduct orders = orderedProductDao.
    }
}
