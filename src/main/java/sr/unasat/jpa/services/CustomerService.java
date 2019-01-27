package sr.unasat.jpa.services;

import sr.unasat.jpa.dao.CustomerDao;
import sr.unasat.jpa.entities.Customer;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class CustomerService {

    private CustomerDao customerDao;

    public CustomerService(EntityManager entityManager) {
        this.customerDao = new CustomerDao(entityManager);
    }

    public void viewAllCustomers() {
        customerDao.selectAllCustomers().forEach(customer -> System.out.println(customer));
    }

    public void selectCustomerById(int customerId) {
        try {
            Customer customer = customerDao.selectCustomerById(customerId);
            System.out.println("Geselecteerd:" + customer.toString());
        } catch (NoResultException e) {
            System.out.println("Klant met id: " + customerId + " komt niet voor");
        }
    }

    public void insertCustomer(Customer customer) {
        try {
            customerDao.insertCustomer(customer);
            System.out.println("Toegevoegd: " + customer.getPerson().getFullName() +
                    "met klantnummer: " + customer.getCustomerNumber());
        } catch (EntityExistsException e) {
            System.out.println(customer.getPerson().getFullName() + " is al toegevoegd als klant");
        }
    }

    public void deleteCustomerById(int customerId) {
        try {
            Customer deletedCustomer = customerDao.deleteCustomer(customerId);
            System.out.println("Verwijderd: " + deletedCustomer.getPerson().getFullName());
        } catch (NoResultException e) {
            System.out.println("Deze klant bestaat niet");
        }
    }
}
