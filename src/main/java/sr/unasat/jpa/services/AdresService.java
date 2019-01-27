package sr.unasat.jpa.services;

import sr.unasat.jpa.dao.AdresDao;
import sr.unasat.jpa.entities.Adres;

import javax.persistence.EntityManager;
import java.util.List;

public class AdresService {

    private AdresDao adresDao;

    public AdresService(EntityManager entityManager) {
        this.adresDao = new AdresDao(entityManager);
    }

    public List<Adres> selectAllAdres() {
        return adresDao.selectAllAdres();
    }

    public void selectAdresById(int id) {
        adresDao.selectAdresById(id);
    }

    public void insertAdres(Adres adres) {
        adresDao.insertAdres(adres);
    }

    public void deleteAdres(int id) {
        adresDao.deleteAdres(id);
    }
}
