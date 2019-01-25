package sr.unasat.jpa.services;

import sr.unasat.jpa.dao.PersonDao;
import sr.unasat.jpa.entities.Person;

import javax.persistence.EntityManager;

public class PersonService {

    private PersonDao personDao;

    public PersonService(EntityManager entityManager) {
        this.personDao = new PersonDao(entityManager);
    }

    public void insertPerson(Person person) {
        personDao.insertPerson(person);
    }

    public void selectPersonList() {
        personDao.selectAllPersons();
    }

    public void selectPersonById(int id) {
        personDao.selectPersonById(id);
    }

    public void selectAllPersonByAdres(Person person) {
        personDao.selectAllPersonsByAdres(person.getAdres());
    }

    public void removePersonById(int id) {
        personDao.deletePerson(id);
    }
}
