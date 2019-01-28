package sr.unasat.jpa.dao;

import sr.unasat.jpa.entities.OrderedProduct;
import sr.unasat.jpa.entities.Receipt;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDao {

    private EntityManager entityManager;
    private OrderedProductDao orderList;
    private List<Receipt> receiptList = new ArrayList<>();
    private CustomerDao customerDao;
    private DeliveryDao deliveryDao;
    private OrderedProductDao orderedProductDao;

    public ReceiptDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.customerDao = new CustomerDao(entityManager);
        this.deliveryDao = new DeliveryDao(entityManager);
        this.orderedProductDao = new OrderedProductDao(entityManager);
    }

    public List<Receipt> selectAllReceipts() {
        entityManager.getTransaction().begin();
        String jpql = "select r from Receipt r";
        TypedQuery<Receipt> query = entityManager.createQuery(jpql, Receipt.class);
        receiptList = query.getResultList();
        entityManager.getTransaction().commit();
        return receiptList;
    }


    private double calculateTotalPrice(List<OrderedProduct> orderedProducts) {
        orderedProducts = orderList.getOrderList();
        double totalPrice = 0.0;
        for (OrderedProduct order : orderedProducts) {
            double price = order.getProduct().getPrice() * order.getQuantity();
            totalPrice += price;
        }
        return totalPrice;
    }
}
