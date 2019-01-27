package sr.unasat.jpa.services;

import sr.unasat.jpa.dao.PersonDao;
import sr.unasat.jpa.entities.Person;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<Person> selectAllPersonByAdres(Person person) {
        return personDao.selectAllPersonsByAdres(person.getAdres());
    }

    public void removePersonById(int id) {
        personDao.deletePerson(id);
    }

    public List<Person> selectAllPersons() {
        return personDao.selectAllPersons();
    }
}
