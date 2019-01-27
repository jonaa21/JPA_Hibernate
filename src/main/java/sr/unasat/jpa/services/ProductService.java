package sr.unasat.jpa.services;

import org.hibernate.procedure.NoSuchParameterException;
import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.dao.ProductDao;
import sr.unasat.jpa.entities.Product;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class ProductService {

    private ProductDao productDao;

    public ProductService(EntityManager entityManager) {
        this.productDao = new ProductDao(entityManager);
    }

    public void insertProduct(Product product){
        try {
            productDao.insertProduct(product);
            System.out.println("Added:" + product.toString());
        } catch (EntityExistsException e) {
            System.out.println(product.getName() + " already exists");
        }
    }

    public void viewAllProducts() {
        List<Product> products = productDao.selectAllProducts();
        products.forEach(product -> System.out.println(product));
    }

    public void selectProductById(int id) {
        try {
            Product selectedProduct = productDao.selectProductById(id);
            System.out.println("Geselecteerd:" + selectedProduct.getName());
        } catch (NoResultException e) {
            System.out.println("Item met id: " + id + " komt niet voor");
        }
    }

    public void removeFromStock(int productId, int quantity) {
        try {
            Product deletedProduct = productDao.removeFromStock(productId, quantity);
            System.out.println("U heeft " + quantity + " uit voorraad verwijderd van " + deletedProduct.getName());
            System.out.println("De huidige voorraad is: " + deletedProduct.getStockQuantity());
        } catch (NoResultException e) {
            System.out.println("Item met id: " + productId + " komt niet voor");
            JPAConfiguration.getEntityManager().getTransaction().rollback();
        } catch (NullPointerException e) {
            System.out.println("Voorraad kan niet lager zijn dan 0");
        }
    }

    public void addToStock(int productId, int quantity) {
        Product product = productDao.addToStock(productId, quantity);
        System.out.println("Product " + product.getName() +
                "\n Current stock" + product.getStockQuantity());
    }

    public void deleteProductById(int productId) {
        try {
            Product deletedProduct = productDao.deleteProduct(productId);
            System.out.println("Deleted: " + deletedProduct.getName());
        } catch (NoResultException e) {
            System.out.println("This product does not exist");
        }
    }

    public void selectProductRating(int rating) {
        try {
            List<Product> products = productDao.selectProductRating(rating);
            products.forEach(product -> System.out.println(product));
        } catch (NoResultException e) {
            System.out.println("No products found with requested rating");
        }
    }

    public void getProductsPriceRange(double minAmount, double maxAmount) {
        try {
            List<Product> products = productDao.getProductsPriceRange(minAmount, maxAmount);
            System.out.println("Product(s) between " + minAmount + " and " + maxAmount + " are:\n");
            products.forEach(product -> System.out.println(product));
        } catch (NoResultException e) {
            System.out.println("Unable to filter on price range");
        } catch (NoSuchParameterException e) {
            System.out.println("Invalid parameters");
        }
    }
}

