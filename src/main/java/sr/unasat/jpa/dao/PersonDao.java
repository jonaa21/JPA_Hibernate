package sr.unasat.jpa.dao;

import sr.unasat.jpa.entities.Adres;
import sr.unasat.jpa.entities.Person;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
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
        entityManager.getTransaction().begin();
        String jpql = "select p from Person p where p.id = :id";
        TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
        query.setParameter("id", id);
        Person selectedPerson = query.getSingleResult();
        entityManager.getTransaction().commit();
        return selectedPerson;
    }

    public List<Person> selectAllPersonsByAdres(Adres adres) {
        entityManager.getTransaction().begin();
        String jpql = "select p from Person p where p.adres = :adres";
        TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
        query.setParameter("adres", adres);
        List<Person> personList = query.getResultList();
        entityManager.getTransaction().commit();
        return personList;
    }

    public Person insertPerson(Person person) {
        selectAllPersons();
        entityManager.getTransaction().begin();
        for (Person pers : personList) {
            if (person.getFirstname().equals(pers.getFirstname()) && person.getLastname().equals(pers.getLastname())) {
                entityManager.getTransaction().rollback();
                continue;
            } else {
                throw new EntityExistsException();
            }
        }
        entityManager.persist(person.getAdres());
        entityManager.persist(person);
        entityManager.getTransaction().commit();
        return person;
    }

    public Person deletePerson(int personId) {
        Person person = selectPersonById(personId);
        entityManager.getTransaction().begin();
        entityManager.remove(person.getCustomer());
        entityManager.remove(person);
        entityManager.getTransaction().commit();
        return person;
    }
}

