package sr.unasat.jpa.services;

import sr.unasat.jpa.dao.ProductDao;
import sr.unasat.jpa.entities.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductService {

    private ProductDao productDao;

    public ProductService(EntityManager entityManager) {
        this.productDao = new ProductDao(entityManager);
    }

    public void insertProduct(Product product){
        productDao.insertProduct(product);
    }

    public List<Product> selectAllProducts(){
        return productDao.selectAllProducts();
    }

    public Product selectProductById(int id){
        return productDao.selectProductById(id);
    }

    public int removeFromStock(Product product, int quantity) {
        return productDao.removeFromStock(product, quantity);
    }

    public int addToStock(Product product, int quantity) {
        return productDao.addToStock(product, quantity);
    }

    public int deleteProductById(int productId){ return productDao.deleteProduct(productId);}

    public List<Product> selectProductRating(int rating){ return productDao.selectProductRating(rating);}

    public List<Product> getProductsPriceRange(double minAmount, double maxAmount){ return productDao.getProductsPriceRange(minAmount, maxAmount);}
}
