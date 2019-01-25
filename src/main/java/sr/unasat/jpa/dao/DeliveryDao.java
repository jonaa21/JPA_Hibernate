package sr.unasat.jpa.dao;

import sr.unasat.jpa.entities.Delivery;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DeliveryDao {

    private EntityManager entityManager;

    public DeliveryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Delivery> selectDeliveries() {
        entityManager.getTransaction().begin();
        String jpql = "select d from Delivery d";
        TypedQuery<Delivery> query = entityManager.createQuery(jpql, Delivery.class);
        List<Delivery> deliveryList = query.getResultList();
        entityManager.getTransaction().commit();
        return deliveryList;
    }

    public Delivery selectAllAdresById(String method) {
        entityManager.getTransaction().begin();
        String jpql = "select d from Delivery d where d.method = :method";
        TypedQuery<Delivery> query = entityManager.createQuery(jpql, Delivery.class);
        query.setParameter("method", method);
        Delivery delivery = query.getSingleResult();
        entityManager.getTransaction().commit();
        return delivery;
    }
}
