package sr.unasat.jpa.services;

import sr.unasat.jpa.dao.DeliveryDao;
import sr.unasat.jpa.entities.Delivery;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class DeliveryService {

    private DeliveryDao deliveryDao;

    public DeliveryService(EntityManager entityManager) {
        deliveryDao = new DeliveryDao(entityManager);
    }

    public void viewAllDeliveryMethods() {
        deliveryDao.selectAllDeliveries().forEach(delivery -> System.out.println(delivery));
    }

    public int selectDeliveryMethod(int deliveryMethodId) {
        try {
            Delivery selectedMethod = deliveryDao.selectDeliveryMethod(deliveryMethodId);
            deliveryMethodId = selectedMethod.getId();
        } catch (NoResultException e) {
            System.out.println("De gekozen leveringsmethode is niet van toepassing");
        }
        return deliveryMethodId;
    }
}
