package sr.unasat.jpa.app;


import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.entities.Adres;
import sr.unasat.jpa.entities.Customer;
import sr.unasat.jpa.entities.Person;
import sr.unasat.jpa.services.CustomerService;
import sr.unasat.jpa.services.OrderedProductsService;
import sr.unasat.jpa.services.ProductService;

public class Application {

    public static void main(String[] args) {

        CustomerService customerService = new CustomerService(JPAConfiguration.getEntityManager());
        OrderedProductsService orderedProductsService = new OrderedProductsService(JPAConfiguration.getEntityManager());
        ProductService productService = new ProductService(JPAConfiguration.getEntityManager());
        Customer person = new Customer(new Person("John", "Doe", new Adres("Lauwmangpasi")));
        customerService.insertCustomer(person);
        customerService.viewAllCustomers();
        System.out.println("------------------");
        orderedProductsService.viewAllOrders();
        System.out.println("------------------");
        orderedProductsService.addToOrders(2, 1);
        System.out.println("------------------");
        orderedProductsService.addToOrders(1, 10);
        System.out.println("------------------");
        orderedProductsService.viewAllOrders();
        System.out.println("------------------");
        orderedProductsService.removeFromOrders(2);
        productService.viewAllProducts();
    }
}
