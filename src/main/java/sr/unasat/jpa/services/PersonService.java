package sr.unasat.jpa.services;

import sr.unasat.jpa.dao.AdresDao;
import sr.unasat.jpa.dao.PersonDao;
import sr.unasat.jpa.entities.Adres;
import sr.unasat.jpa.entities.Person;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class PersonService {

    private PersonDao personDao;
    private AdresDao adresDao;

    public PersonService(EntityManager entityManager) {
        this.personDao = new PersonDao(entityManager);
        this.adresDao = new AdresDao(entityManager);
    }

    public void insertPerson(Person person) {
        try {
            personDao.insertPerson(person);
            System.out.println("Toegevoegd: " + person.toString());
        } catch (EntityExistsException e) {
            System.out.println("Deze persoon is al toegevoegd");
        }
    }

    public void viewPersonList() {
        personDao.selectAllPersons().forEach(person -> System.out.println(person));
    }

    public void selectPersonById(int id) {
        try {
            Person selectedPerson = personDao.selectPersonById(id);
            System.out.println("Geselecteerd:" + selectedPerson.getFullName());
        } catch (Exception e) {
            System.out.println("Persoon met id: " + id + " komt niet voor");
        }
    }

    public void viewAllPersonsByAdres(int adresId) {
        try {
            Adres adres = adresDao.selectAdresById(adresId);
            personDao.selectAllPersonsByAdres(adres).forEach(person -> System.out.println(person));
        } catch (NoResultException e) {
            System.out.println("Adres met id " + adresId + " komt niet voor");
        }

    }

    public void deletePersonById(int personId) {
        try {
            Person deletedPerson = personDao.deletePerson(personId);
            System.out.println("Deleted: " + deletedPerson.getFullName());
        } catch (NoResultException e) {
            System.out.println("Deze persoon bestaat niet");
        }
    }

    public void viewAllPersons() {
        personDao.selectAllPersons().forEach(person -> System.out.println(person));
    }
}
