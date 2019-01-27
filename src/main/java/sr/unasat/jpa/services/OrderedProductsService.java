package sr.unasat.jpa.services;

import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.dao.OrderedProductDao;
import sr.unasat.jpa.entities.OrderedProduct;
import sr.unasat.jpa.entities.Product;

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

    public List<OrderedProduct> selectAllOrderedProducts() {
        return orderedProductDao.selectAllOrderedProducts();
    }

    public void addToOrders(int productId, int quantity) {
        Product orderedProduct = orderedProductDao.addToOrders(productId, quantity);
        orders.add(new OrderedProduct(orderedProduct, quantity));
    }

    public void viewAllOrders() {
        if (!orders.isEmpty()) {
            orders.forEach(order -> System.out.println(order));
        } else {
            System.out.println("You have no orders");
        }
    }

    public void removeFromOrders(int orderedProduct) {
        try {
            orderedProductDao.removeFromOrders(orderedProduct);
        } catch (NoResultException e) {
            System.out.println("This item does not exist in your order list");
            JPAConfiguration.getEntityManager().getTransaction().rollback();
        }
    }

}
