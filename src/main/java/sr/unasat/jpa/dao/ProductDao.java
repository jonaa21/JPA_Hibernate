package sr.unasat.jpa.dao;

import org.hibernate.procedure.NoSuchParameterException;
import sr.unasat.jpa.entities.Product;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private EntityManager entityManager;
    private List<Product> products = new ArrayList<>();

    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Product> selectAllProducts() {
        entityManager.getTransaction().begin();
        String jpql = "select p from Product p";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        products = query.getResultList();
        entityManager.getTransaction().commit();
        return products;
    }

    public Product selectProductById(int id) {
        entityManager.getTransaction().begin();
        String jpql = "select p from Product p where p.id = :id";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("id", id);
        Product product = query.getSingleResult();
        entityManager.getTransaction().commit();
        return product;
    }

    public void insertProduct(Product product) {
        selectAllProducts();
        entityManager.getTransaction().begin();
        for (Product prod : products) {
            if (prod.getName().equals(product.getName())) {
                entityManager.getTransaction().rollback();
                throw new EntityExistsException();
            }
        }
        entityManager.persist(product);
        entityManager.getTransaction().commit();
    }

    public Product deleteProduct(int id) {
        Product product = selectProductById(id);
        entityManager.getTransaction().begin();
        entityManager.remove(product);
        entityManager.getTransaction().commit();
        return product;
    }

    public Product removeFromStock(int productId, int quantity) {
        Product product = selectProductById(productId);
        int stock = product.getStockQuantity() - quantity;
        if (stock <= 0) {
            throw new NullPointerException();
        }
        stockManager(quantity, product, stock);
        return product;
    }

    private int stockManager(int quantity, Product prod, int stock) {
        prod.setStockQuantity(stock);
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.merge(prod);
        entityManager.getTransaction().commit();
        return quantity;
    }

    public Product addToStock(int productId, int quantity) {
        Product product = selectProductById(productId);
        int stock = product.getStockQuantity() + quantity;
        stockManager(quantity, product, stock);
        return product;
    }

    public List<Product> selectProductRating(int rating){
        entityManager.getTransaction().begin();
        String jpql = "select p from Product p where p.rating >= :rating ";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("rating", rating);
        products = query.getResultList();
        entityManager.getTransaction().commit();
        return products;
    }

    public List<Product> getProductsPriceRange(double minAmount, double maxAmount){
        if (minAmount > maxAmount) {
            throw new NoSuchParameterException(null);
        }
        entityManager.getTransaction().begin();
        String jpql = "select p from Product p where p.price >= :minAmount AND p.price <= :maxAmount";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("minAmount", minAmount);
        query.setParameter("maxAmount", maxAmount);
        products = query.getResultList();
        entityManager.getTransaction().commit();
        return products;
    }
}
