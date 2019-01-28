package sr.unasat.jpa.dao;

import org.hibernate.procedure.NoSuchParameterException;
import sr.unasat.jpa.entities.AvailableProduct;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private EntityManager entityManager;
    private List<AvailableProduct> availableProducts = new ArrayList<>();

    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<AvailableProduct> selectAllProducts() {
        entityManager.getTransaction().begin();
        String jpql = "select p from AvailableProduct p";
        TypedQuery<AvailableProduct> query = entityManager.createQuery(jpql, AvailableProduct.class);
        availableProducts = query.getResultList();
        entityManager.getTransaction().commit();
        return availableProducts;
    }

    public AvailableProduct selectProductById(int id) {
        entityManager.getTransaction().begin();
        String jpql = "select p from AvailableProduct p where p.id = :id";
        TypedQuery<AvailableProduct> query = entityManager.createQuery(jpql, AvailableProduct.class);
        query.setParameter("id", id);
        AvailableProduct availableProduct = query.getSingleResult();
        entityManager.getTransaction().commit();
        return availableProduct;
    }

    public void insertProduct(AvailableProduct availableProduct) {
        selectAllProducts();
        entityManager.getTransaction().begin();
        for (AvailableProduct prod : availableProducts) {
            if (prod.getName().equals(availableProduct.getName())) {
                entityManager.getTransaction().rollback();
                throw new EntityExistsException();
            }
        }
        entityManager.persist(availableProduct);
        entityManager.getTransaction().commit();
    }

    public AvailableProduct deleteProduct(int id) {
        AvailableProduct availableProduct = selectProductById(id);
        entityManager.getTransaction().begin();
        entityManager.remove(availableProduct);
        entityManager.getTransaction().commit();
        return availableProduct;
    }

    public AvailableProduct removeFromStock(int productId, int quantity) {
        AvailableProduct availableProduct = selectProductById(productId);
        int stock = availableProduct.getStockQuantity() - quantity;
        if (stock <= 0) {
            throw new NullPointerException();
        }
        stockManager(quantity, availableProduct, stock);
        return availableProduct;
    }

    private int stockManager(int quantity, AvailableProduct prod, int stock) {
        prod.setStockQuantity(stock);
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.merge(prod);
        entityManager.getTransaction().commit();
        return quantity;
    }

    public AvailableProduct addToStock(int productId, int quantity) {
        AvailableProduct availableProduct = selectProductById(productId);
        int stock = availableProduct.getStockQuantity() + quantity;
        stockManager(quantity, availableProduct, stock);
        return availableProduct;
    }

    public List<AvailableProduct> selectProductRating(int rating) {
        entityManager.getTransaction().begin();
        String jpql = "select p from AvailableProduct p where p.rating >= :rating ";
        TypedQuery<AvailableProduct> query = entityManager.createQuery(jpql, AvailableProduct.class);
        query.setParameter("rating", rating);
        availableProducts = query.getResultList();
        entityManager.getTransaction().commit();
        return availableProducts;
    }

    public List<AvailableProduct> getProductsPriceRange(double minAmount, double maxAmount) {
        if (minAmount > maxAmount) {
            throw new NoSuchParameterException(null);
        }
        entityManager.getTransaction().begin();
        String jpql = "select p from AvailableProduct p where p.price >= :minAmount AND p.price <= :maxAmount";
        TypedQuery<AvailableProduct> query = entityManager.createQuery(jpql, AvailableProduct.class);
        query.setParameter("minAmount", minAmount);
        query.setParameter("maxAmount", maxAmount);
        availableProducts = query.getResultList();
        entityManager.getTransaction().commit();
        return availableProducts;
    }
}
