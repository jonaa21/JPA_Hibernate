package sr.unasat.jpa.dao;

import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.entities.OrderedProduct;
import sr.unasat.jpa.entities.Product;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class OrderedProductDao {

    private EntityManager entityManager;
    private List<Product> products = new ArrayList<>();
    private List<OrderedProduct> orders = new ArrayList<>();
    private ProductDao productDao = new ProductDao(JPAConfiguration.getEntityManager());

    public OrderedProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<OrderedProduct> selectAllOrderedProducts() {
        entityManager.getTransaction().begin();
        String jpql = "select o from OrderedProduct o";
        TypedQuery<OrderedProduct> query = entityManager.createQuery(jpql, OrderedProduct.class);
        orders = query.getResultList();
        entityManager.getTransaction().commit();
        return orders;
    }

    public List<OrderedProduct> addToOrders(int productId, int quantity) {
        products = productDao.selectAllProducts();
        Product selectedProduct = productDao.selectProductById(productId);

        for (Product prod : products) {
            if (!prod.getName().equals(selectedProduct.getName())) {
                continue;
            }
        }
        OrderedProduct orderedProduct = new OrderedProduct(selectedProduct, quantity);
        productDao.removeFromStock(selectedProduct.getId(), quantity);
        entityManager.getTransaction().begin();
        entityManager.persist(orderedProduct);
        entityManager.getTransaction().commit();
        orders.add(orderedProduct);
        calculatePrice();
        return orders;
    }

    public double calculatePrice() {
        double totalPrice = 0.0;
        for (OrderedProduct order : orders) {
            double price = order.getProduct().getPrice() * order.getQuantity();
            totalPrice += price;
        }
        return totalPrice;
    }
}
