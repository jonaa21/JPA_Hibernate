package sr.unasat.jpa.dao;

import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.entities.OrderedProduct;
import sr.unasat.jpa.entities.Product;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class OrderedProductDao {

    private EntityManager entityManager;
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

    public OrderedProduct selectOrderedProduct(int orderedProductId) {
        entityManager.getTransaction().begin();
        String jpql = "select o from OrderedProduct o where o.id = :id";
        TypedQuery<OrderedProduct> query = entityManager.createQuery(jpql, OrderedProduct.class);
        query.setParameter("id", orderedProductId);
        OrderedProduct selectedOrderedProduct = query.getSingleResult();
        entityManager.getTransaction().commit();
        return selectedOrderedProduct;
    }

    public Product addToOrders(int productId, int quantity) {
        Product selectedProduct = productDao.selectProductById(productId);
        checkIfAlreadyInOrders(selectedProduct);
        OrderedProduct orderedProduct = new OrderedProduct(selectedProduct, quantity);
        productDao.removeFromStock(orderedProduct.getProduct().getId(), quantity);
        entityManager.getTransaction().begin();
        entityManager.persist(orderedProduct);
        entityManager.getTransaction().commit();
        orders.add(orderedProduct);
        return selectedProduct;
    }

    public OrderedProduct removeFromOrders(int orderedProductId) {
        OrderedProduct orderedProduct = selectOrderedProduct(orderedProductId);
        productDao.addToStock(orderedProduct.getProduct().getId(), orderedProduct.getQuantity());
        orders.remove(orderedProduct);
        entityManager.getTransaction().begin();
        entityManager.remove(orderedProduct);
        entityManager.getTransaction().commit();
        return orderedProduct;
    }

    public double calculatePriceFromOrder(int OrderedProductId) {//TODO: calculate in receipt
        double totalPrice = 0.0;
        for (OrderedProduct order : orders) {
            double price = order.getProduct().getPrice() * order.getQuantity();
            totalPrice += price;
        }
        return totalPrice;
    }

    private void checkIfAlreadyInOrders(Product product) {
        for (OrderedProduct orderedProduct : orders) {
            if (orderedProduct.getProduct().getName().equals(product.getName())) {
                throw new EntityExistsException();
            }
        }
    }
}
