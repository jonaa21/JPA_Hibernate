package sr.unasat.jpa.dao;

import sr.unasat.jpa.entities.Adres;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class AdresDao {

    private EntityManager entityManager;
    private List<Adres> adresList = new ArrayList<>();

    public AdresDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Adres> selectAllAdres() {
        entityManager.getTransaction().begin();
        String jpql = "select a from Adres a";
        TypedQuery<Adres> query = entityManager.createQuery(jpql, Adres.class);
        adresList = query.getResultList();
        entityManager.getTransaction().commit();
        return adresList;
    }

    public Adres selectAdresById(int id) {
        Adres adres;
        try {
            entityManager.getTransaction().begin();
            String jpql = "select a from Adres a where a.id = :id";
            TypedQuery<Adres> query = entityManager.createQuery(jpql, Adres.class);
            query.setParameter("id", id);
            adres = query.getSingleResult();
            entityManager.getTransaction().commit();
            return adres;
        } catch (NoResultException e) {
            System.out.println("Adres doesn't exist");
            entityManager.getTransaction().rollback();
        }
        return null;
    }

    public void insertAdres(Adres adres){
        selectAllAdres();
        for (Adres a : adresList) {
            if (a.getName().equals(adres.getName())){
                return;
            }
        }
        entityManager.getTransaction().begin();
        entityManager.persist(adres);
        entityManager.getTransaction().commit();
    }

    public void deleteAdres(int id) {
        Adres adres = selectAdresById(id);
        if (adres == null) {
            return;
        }
        entityManager.getTransaction().begin();
        entityManager.remove(adres);
        entityManager.getTransaction().commit();
    }
}
