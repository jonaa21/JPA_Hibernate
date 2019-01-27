package sr.unasat.jpa.app;


import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.entities.Product;
import sr.unasat.jpa.services.ProductService;

public class Application {

    public static void main(String[] args) {

//        ProductService productService = new ProductService(JPAConfiguration.getEntityManager());
//        List<Product> productList = productService.selectAllProducts();
//        productService.addToStock(1, 6);
//
//        productList.forEach(p -> System.out.println(p));

        ProductService productService = new ProductService(JPAConfiguration.getEntityManager());
        productService.viewAllProducts();
        System.out.println("----------------------------");
        productService.insertProduct(new Product("Sharp 27\" Monitor", 560.0, 2, 0));
    }
}
