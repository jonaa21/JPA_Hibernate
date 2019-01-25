package sr.unasat.jpa.dao;

import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.entities.Receipt;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDao {

    private EntityManager entityManager;
    private List<Receipt> receiptList = new ArrayList<>();

    public ReceiptDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Receipt> selectAllReceipts() {
        entityManager.getTransaction().begin();
        String jpql = "select r from Receipt r";
        TypedQuery<Receipt> query = entityManager.createQuery(jpql, Receipt.class);
        receiptList = query.getResultList();
        entityManager.getTransaction().commit();
        return receiptList;
    }

    public void createReceipt(){
        OrderedProductDao orderedProductDao = new OrderedProductDao(JPAConfiguration.getEntityManager());
    }
}
