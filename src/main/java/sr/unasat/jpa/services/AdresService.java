package sr.unasat.jpa.services;

import sr.unasat.jpa.dao.AdresDao;
import sr.unasat.jpa.entities.Adres;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class AdresService {

    private AdresDao adresDao;

    public AdresService(EntityManager entityManager) {
        this.adresDao = new AdresDao(entityManager);
    }

    public void viewAllAdressen() {
        List<Adres> adresList = adresDao.selectAllAdres();
        adresList.forEach(adres -> System.out.println(adres));
    }

    public void selectAdresById(int id) {
        try {
            Adres adres = adresDao.selectAdresById(id);
            System.out.println("Geselecteerd:" + adres.getName());
        } catch (NoResultException e) {
            System.out.println("Adres met id " + id + " komt niet voor");
        }
    }

    public void insertAdres(Adres adres) {
        try {
            adresDao.insertAdres(adres);
            System.out.println("Toegevoegd: " + adres.getName());
        } catch (EntityExistsException e) {
            System.out.println(adres.getName() + " is al ingevoerd");
        }
    }

    public void deleteAdresById(int id) {
        try {
            Adres adres = adresDao.deleteAdres(id);
            System.out.println("Verwijderd: " + adres.getName());
        } catch (NoResultException e) {
            System.out.println("Adres met id " + id + " komt niet voor");
        }
    }
}
