package sr.unasat.jpa.dao;

import sr.unasat.jpa.entities.Adres;
import sr.unasat.jpa.entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {

    private EntityManager entityManager;
    private List<Person> personList = new ArrayList<>();

    public PersonDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Person> selectAllPersons() {
        entityManager.getTransaction().begin();
        String jpql = "select p from Person p";
        TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
        personList = query.getResultList();
        entityManager.getTransaction().commit();
        return personList;
    }

    public Person selectPersonById(int id) {
        Person selectedPerson;
        entityManager.getTransaction().begin();
        try {
            String jpql = "select p from Person p where p.id = :id";
            TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
            query.setParameter("id", id);
            selectedPerson = query.getSingleResult();
            entityManager.getTransaction().commit();
            return selectedPerson;
        } catch (NoResultException e) {
            System.out.println("Person doesn't exist");
            entityManager.getTransaction().rollback();
        }
        return null;
    }

    public List<Person> selectAllPersonsByAdres(Adres adres) {
        entityManager.getTransaction().begin();
        String jpql = "select p from Person p where p.adres.name = :adres";
        TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
        query.setParameter("adres", adres);
        List<Person> personList = query.getResultList();
        entityManager.getTransaction().commit();
        return personList;
    }

    public void insertPerson(Person p) {
        selectAllPersons();
        entityManager.getTransaction().begin();
        for (Person pers : personList) {
            if (p.getFirstname().equals(pers.getFirstname()) && p.getLastname().equals(pers.getLastname())) {
                System.out.println("This person has already been added");
                entityManager.getTransaction().rollback();
                return;
            }
        }
        entityManager.persist(p.getAdres());
        entityManager.persist(p);
        entityManager.getTransaction().commit();
    }

    public void deletePerson(int id) {
        Person p = selectPersonById(id);
        if (p == null) {
            return;
        }
        entityManager.getTransaction().begin();
        entityManager.remove(p);
        entityManager.getTransaction().commit();
    }
}

