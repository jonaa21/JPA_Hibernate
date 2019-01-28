package sr.unasat.jpa.services;

import org.hibernate.procedure.NoSuchParameterException;
import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.dao.ProductDao;
import sr.unasat.jpa.entities.AvailableProduct;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class ProductService {

    private ProductDao productDao;

    public ProductService(EntityManager entityManager) {
        this.productDao = new ProductDao(entityManager);
    }

    public void insertProduct(AvailableProduct availableProduct) {
        try {
            productDao.insertProduct(availableProduct);
            System.out.println("Added:" + availableProduct.toString());
        } catch (EntityExistsException e) {
            System.out.println(availableProduct.getName() + " already exists");
        }
    }

    public void viewAllProducts() {
        List<AvailableProduct> availableProducts = productDao.selectAllProducts();
        availableProducts.forEach(product -> System.out.println(product));
    }

    public void selectProductById(int id) {
        try {
            AvailableProduct selectedAvailableProduct = productDao.selectProductById(id);
            System.out.println("Geselecteerd:" + selectedAvailableProduct.getName());
        } catch (NoResultException e) {
            System.out.println("Item met id: " + id + " komt niet voor");
        }
    }

    public void removeFromStock(int productId, int quantity) {
        try {
            AvailableProduct deletedAvailableProduct = productDao.removeFromStock(productId, quantity);
            System.out.println("U heeft " + quantity + " uit voorraad verwijderd van " + deletedAvailableProduct.getName());
            System.out.println("De huidige voorraad is: " + deletedAvailableProduct.getStockQuantity());
        } catch (NoResultException e) {
            System.out.println("Item met id: " + productId + " komt niet voor");
            JPAConfiguration.getEntityManager().getTransaction().rollback();
        } catch (NullPointerException e) {
            System.out.println("Voorraad kan niet lager zijn dan 0");
        }
    }

    public void addToStock(int productId, int quantity) {
        AvailableProduct availableProduct = productDao.addToStock(productId, quantity);
        System.out.println("AvailableProduct " + availableProduct.getName() +
                "\n Current stock" + availableProduct.getStockQuantity());
    }

    public void deleteProductById(int productId) {
        try {
            AvailableProduct deletedAvailableProduct = productDao.deleteProduct(productId);
            System.out.println("Deleted: " + deletedAvailableProduct.getName());
        } catch (NoResultException e) {
            System.out.println("This product does not exist");
        }
    }

    public void selectProductRating(int rating) {
        try {
            List<AvailableProduct> availableProducts = productDao.selectProductRating(rating);
            availableProducts.forEach(product -> System.out.println(product));
        } catch (NoResultException e) {
            System.out.println("No products found with requested rating");
        }
    }

    public void getProductsPriceRange(double minAmount, double maxAmount) {
        try {
            List<AvailableProduct> availableProducts = productDao.getProductsPriceRange(minAmount, maxAmount);
            System.out.println("AvailableProduct(s) between " + minAmount + " and " + maxAmount + " are:\n");
            availableProducts.forEach(product -> System.out.println(product));
        } catch (NoResultException e) {
            System.out.println("Unable to filter on price range");
        } catch (NoSuchParameterException e) {
            System.out.println("Invalid parameters");
        }
    }
}

