package sr.unasat.jpa.services;

import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.dao.OrderedProductDao;
import sr.unasat.jpa.entities.OrderedProduct;
import sr.unasat.jpa.entities.Product;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class OrderedProductsService {

    private OrderedProductDao orderedProductDao;
    private List<OrderedProduct> orders = new ArrayList<>();

    public OrderedProductsService(EntityManager entityManager) {
        this.orderedProductDao = new OrderedProductDao(entityManager);
    }

    public void selectAllOrderedProducts() {
        orderedProductDao.selectAllOrderedProducts().forEach(orderedProduct -> System.out.println(orderedProduct));
    }

    public void addToOrders(int productId, int quantity) {
        try {
            Product orderedProduct = orderedProductDao.addToOrders(productId, quantity);
            orders.add(new OrderedProduct(orderedProduct, quantity));
            System.out.println("U heeft " + orderedProduct.getName() + " toegevoegd");
        } catch (EntityExistsException e) {
            System.out.println("U heeft dit product al toegevoegd");
            viewAllOrders();
            return;
        } catch (NullPointerException e) {
            System.out.println("Er zijn niet genoeg van deze item in voorraad");
            return;
        }
        viewAllOrders();
    }

    public void viewAllOrders() {
        if (!orders.isEmpty()) {
            orders.forEach(order -> System.out.println("Dit zijn uw bestellingen: \n" +
                    "Item: " + order.getProduct().getName() + "\n" +
                    "Aantal: " + order.getQuantity()));
        } else {
            System.out.println("U heeft geen bestellingen");
        }
    }

    public void removeFromOrders(int orderedProduct) {
        try {
            orderedProductDao.removeFromOrders(orderedProduct);
            orders.remove(orderedProduct);
        } catch (NoResultException e) {
            System.out.println("Deze item komt niet voor in uw bestellingen");
            JPAConfiguration.getEntityManager().getTransaction().rollback();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Deze selectie is niet mogelijk te verwijderen");
        }
    }

}
