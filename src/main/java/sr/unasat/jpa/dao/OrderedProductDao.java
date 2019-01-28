package sr.unasat.jpa.dao;

import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.entities.AvailableProduct;
import sr.unasat.jpa.entities.OrderedProduct;

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

    public List<OrderedProduct> viewAllOrderedProducts() {
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

    public AvailableProduct addToOrders(int productId, int quantity) {
        AvailableProduct selectedAvailableProduct = productDao.selectProductById(productId);
        checkIfAlreadyInOrders(selectedAvailableProduct);
        OrderedProduct orderedProduct = new OrderedProduct(selectedAvailableProduct, quantity);
        productDao.removeFromStock(orderedProduct.getAvailableProduct().getId(), quantity);
        entityManager.getTransaction().begin();
        entityManager.persist(orderedProduct);
        entityManager.getTransaction().commit();
        orders.add(orderedProduct);
        return selectedAvailableProduct;
    }

    public OrderedProduct removeFromOrders(int orderedProductId) {
        OrderedProduct orderedProduct = selectOrderedProduct(orderedProductId);
        productDao.addToStock(orderedProduct.getAvailableProduct().getId(), orderedProduct.getQuantity());
        orders.remove(orderedProduct);
        entityManager.getTransaction().begin();
        entityManager.remove(orderedProduct);
        entityManager.getTransaction().commit();
        return orderedProduct;
    }

    private void checkIfAlreadyInOrders(AvailableProduct availableProduct) {
        for (OrderedProduct orderedProduct : orders) {
            if (orderedProduct.getAvailableProduct().getName().equals(availableProduct.getName())) {
                throw new EntityExistsException();
            }
        }
    }

    public List<OrderedProduct> getOrderList() {
        return this.orders;
    }
}
